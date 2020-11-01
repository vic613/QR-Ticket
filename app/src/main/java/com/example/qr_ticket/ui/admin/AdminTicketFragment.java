package com.example.qr_ticket.ui.admin;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.qr_ticket.R;
import com.example.qr_ticket.data.UserSessionManager;
import com.example.qr_ticket.data.model.tblUserServiceTypeModel;
import com.example.qr_ticket.data.repository.tblUserServiceTypeRepository;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.qr_ticket.data.MyFirebaseMessagingService.sendNotification;


public class AdminTicketFragment extends Fragment {

    private HashMap<String, String> user;

    private TextView txtAdminTicketServiceTypeValue;
    private TextView txtAdminTicketNumberValue;
    private TextView txtAdminEmailValue;
    private List<tblUserServiceTypeModel> servicetypelist;
    private NavController navController;
    NotificationManager NM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_admin_ticket, container, false);
        user = UserSessionManager.getInstance(getContext()).getUserDetails();

        txtAdminTicketServiceTypeValue = root.findViewById(R.id.txtAdminTicketServiceTypeValue);
        txtAdminTicketNumberValue = root.findViewById(R.id.txtAdminTicketNumberValue);
        txtAdminEmailValue = root.findViewById(R.id.txtAdminEmailValue);
        Button btnAdminTicketCancel = root.findViewById(R.id.btnAdminTicketCancel);
        Button btnAdminTicketComplete = root.findViewById(R.id.btnAdminTicketComplete);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        this.LoadData();


        btnAdminTicketCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelTicket();
            }
        });

        btnAdminTicketComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompleteTicket();
            }
        });
        return root;
    }

    private void LoadData() {
        try {
            Intent intent = getActivity().getIntent();
            String qrResult = intent.getStringExtra("QRResult");

            Gson gson = new Gson();
            tblUserServiceTypeModel obj = gson.fromJson(qrResult, tblUserServiceTypeModel.class);
            txtAdminTicketNumberValue.setPaintFlags(txtAdminTicketNumberValue.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            txtAdminTicketNumberValue.setText(String.valueOf(obj.ticketNumber));
            txtAdminTicketServiceTypeValue.setPaintFlags(txtAdminTicketServiceTypeValue.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            txtAdminTicketServiceTypeValue.setText(obj.serviceTypeName);
            txtAdminEmailValue.setPaintFlags(txtAdminEmailValue.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            txtAdminEmailValue.setText(obj.email);


            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);


        } catch (Throwable e) {
            e.printStackTrace();
        }


    }

    private void CancelTicket() {
        try {
//            tblUserServiceTypeModel userservicetypemodel = new tblUserServiceTypeModel();
//            tblUserServiceTypeRepository userservicetypeclass = new tblUserServiceTypeRepository();
//            userservicetypemodel.setTblUserID(Integer.parseInt(user.get(UserSessionManager.KEY_USERID)));
//            userservicetypemodel.setTicketNumber(Integer.parseInt(txtAdminTicketNumberValue.getText().toString()));
//            userservicetypeclass.sp_tblUserServiceType_DeleteByID(userservicetypemodel);
//            if (userservicetypemodel.errorCode == 1) {
//                Toast.makeText(getActivity(), userservicetypemodel.errorMessage, Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getActivity(), "Successful cancel", Toast.LENGTH_SHORT).show();
//                navController.navigate(R.id.nav_home);


        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    private void CompleteTicket() {
        try {
            Intent intent = getActivity().getIntent();
            String qrResult = intent.getStringExtra("QRResult");

            Gson gson = new Gson();
            tblUserServiceTypeModel obj = gson.fromJson(qrResult, tblUserServiceTypeModel.class);

            tblUserServiceTypeModel userservicetypemodel = new tblUserServiceTypeModel();
            tblUserServiceTypeRepository userservicetypeclass = new tblUserServiceTypeRepository();
            userservicetypemodel.setTblUserID(Integer.parseInt(user.get(UserSessionManager.KEY_USERID)));
            userservicetypemodel.setTicketNumber(obj.ticketNumber);
            userservicetypemodel.setTblServiceTypeID(obj.tblServiceTypeID);
            userservicetypemodel.setTicketStatus("COMPLETE");
            userservicetypeclass.sp_tblUserServiceType_UpdateStatus(userservicetypemodel);
            if (userservicetypemodel.errorCode == 1) {
                Toast.makeText(getActivity(), userservicetypemodel.errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                ArrayList<tblUserServiceTypeModel> result = userservicetypeclass.sp_tblUserServiceType_SelectNextTicket(userservicetypemodel);
                if (!result.isEmpty()) {
                    servicetypelist = result;
                    int readytime = 5;
                    for (int i = 0; i < servicetypelist.size(); i++) {
                        sendNotification("Your ticket will be ready in another" + readytime + " minutes.", "Ticket " + servicetypelist.get(i).getTicketNumber(), servicetypelist.get(i).getToken());
                        readytime = readytime + 5;
                    }

                }


                Toast.makeText(getActivity(), "Successful Completed", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.nav_home);
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

    }


}