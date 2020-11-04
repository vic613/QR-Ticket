package com.example.qr_ticket.ui.ticket;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class TicketFragment extends Fragment {

    private HashMap<String, String> user;
    private ImageView qrImage;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private WindowManager manager;
    private TextView txtServiceTypeValue;
    private TextView txtTicketNumberValue;
    private List<tblUserServiceTypeModel> servicetypelist;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_ticket, container, false);
        user = UserSessionManager.getInstance(getContext()).getUserDetails();
        qrImage = root.findViewById(R.id.qr_image);
        txtServiceTypeValue = root.findViewById(R.id.txtServiceTypeValue);
        txtTicketNumberValue = root.findViewById(R.id.txtTicketNumberValue);
        Button btnCancel = root.findViewById(R.id.btnCancel);
        manager = (WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        this.LoadData();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelTicket();
            }
        });


        return root;
    }

    private void LoadData() {
        try {
            tblUserServiceTypeModel userservicetypemodel = new tblUserServiceTypeModel();
            tblUserServiceTypeRepository servicetypeclass = new tblUserServiceTypeRepository();

            userservicetypemodel.setTblUserID(Integer.parseInt(user.get(UserSessionManager.KEY_USERID)));

            ArrayList<tblUserServiceTypeModel> result = servicetypeclass.sp_tblUserServiceType_SearchByID(userservicetypemodel);
            if (!result.isEmpty()) {
                servicetypelist = result;
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 3 / 4;
                for (int i = 0; i < servicetypelist.size(); i++) {
                    String value = "Service: " + String.valueOf(servicetypelist.get(i).getServiceTypeName()) + "\n Ticket number: " + String.valueOf(servicetypelist.get(i).getTicketNumber()) + "\n Email: " + String.valueOf(servicetypelist.get(i).getEmail());
                    GsonBuilder builder = new GsonBuilder();
                    builder.serializeNulls();
                    Gson gson = builder.create();
                    String json = gson.toJson(servicetypelist.get(i));

                            qrgEncoder = new QRGEncoder(
                            json, null,
                            QRGContents.Type.TEXT,
                            smallerDimension);

                    bitmap = qrgEncoder.encodeAsBitmap();
                    qrImage.setImageBitmap(bitmap);
                    txtServiceTypeValue.setText(servicetypelist.get(i).getServiceTypeName());
                    txtTicketNumberValue.setText(String.valueOf(servicetypelist.get(i).getTicketNumber()));

                }
            } else {

                Toast.makeText(getActivity(), "Please create ticket first.", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.nav_home);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }


    }

    private void CancelTicket() {
        try {
            tblUserServiceTypeModel userservicetypemodel = new tblUserServiceTypeModel();
            tblUserServiceTypeRepository userservicetypeclass = new tblUserServiceTypeRepository();
            userservicetypemodel.setTblUserID(Integer.parseInt(user.get(UserSessionManager.KEY_USERID)));
            userservicetypemodel.setTicketNumber(Integer.parseInt(txtTicketNumberValue.getText().toString()));
            userservicetypeclass.sp_tblUserServiceType_DeleteByID(userservicetypemodel);
            if (userservicetypemodel.errorCode == 1) {
                Toast.makeText(getActivity(), userservicetypemodel.errorMessage, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Successful cancel", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.nav_home);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}