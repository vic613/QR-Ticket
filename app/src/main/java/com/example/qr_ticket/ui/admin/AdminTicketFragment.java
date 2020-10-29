package com.example.qr_ticket.ui.admin;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.qr_ticket.R;
import com.example.qr_ticket.data.UserSessionManager;
import com.example.qr_ticket.data.model.tblUserServiceTypeModel;
import com.example.qr_ticket.data.repository.tblUserServiceTypeRepository;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;


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
            String qrResult=intent.getStringExtra("QRResult");

            Gson gson = new Gson();
            tblUserServiceTypeModel obj = gson.fromJson(qrResult,tblUserServiceTypeModel.class);
            txtAdminTicketNumberValue.setPaintFlags(txtAdminTicketNumberValue.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            txtAdminTicketNumberValue.setText(String.valueOf(obj.ticketNumber));
            txtAdminTicketServiceTypeValue.setPaintFlags(txtAdminTicketServiceTypeValue.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            txtAdminTicketServiceTypeValue.setText(obj.serviceTypeName);
            txtAdminEmailValue.setPaintFlags(txtAdminEmailValue.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            txtAdminEmailValue.setText(obj.email);


        } catch (Throwable e) {
            e.printStackTrace();
        }


    }

    private void CancelTicket() {
        try {
            tblUserServiceTypeModel userservicetypemodel = new tblUserServiceTypeModel();
            tblUserServiceTypeRepository userservicetypeclass = new tblUserServiceTypeRepository();
            userservicetypemodel.setTblUserID(Integer.parseInt(user.get(UserSessionManager.KEY_USERID)));
            userservicetypemodel.setTicketNumber(Integer.parseInt(txtAdminTicketNumberValue.getText().toString()));
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

    private void CompleteTicket() {
        try {

            int reqCode = 1;
            Intent intent = new Intent(getContext().getApplicationContext(), AdminTicketFragment.class);
            showNotification(this.getContext(), "Title", "This is the message to display", intent, reqCode);

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
//            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }


    public void showNotification(Context context, String title, String message, Intent intent, int reqCode) {
        //SharedPreferenceManager sharedPreferenceManager = SharedPreferenceManager.getInstance(context);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_ONE_SHOT);
        String CHANNEL_ID = "channel_name";// The id of the channel.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(false)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(reqCode, notificationBuilder.build()); // 0 is the request code, it should be unique id

        Log.d("showNotification", "showNotification: " + reqCode);
    }
}