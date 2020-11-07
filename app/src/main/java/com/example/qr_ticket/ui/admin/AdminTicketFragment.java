package com.example.qr_ticket.ui.admin;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.qr_ticket.R;
import com.example.qr_ticket.data.SpinnerDialog;
import com.example.qr_ticket.data.UserSessionManager;
import com.example.qr_ticket.data.model.tblServiceTypeModel;
import com.example.qr_ticket.data.model.tblUserServiceTypeModel;
import com.example.qr_ticket.data.repository.tblServiceTypeRepository;
import com.example.qr_ticket.data.repository.tblUserServiceTypeRepository;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.qr_ticket.data.MyFirebaseMessagingService.sendNotification;


public class AdminTicketFragment extends Fragment {

    private HashMap<String, String> user;

    private Spinner spAdminticketServiceType;
    private TextView txtAdminTicketNumberValue;
    private TextView txtAdminEmailValue;
    private List<tblUserServiceTypeModel> userservicetypelist;
    private NavController navController;
    private Button btnAdminTicketNotice;
    private Button btnAdminTicketCancel;
    private Button btnAdminTicketComplete;
    private List<tblServiceTypeModel> servicetypelist;
    ProgressDialog progressDoalog;
    NotificationManager NM;
    FragmentManager fm;
    SpinnerDialog spinnerdialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_admin_ticket, container, false);
        user = UserSessionManager.getInstance(getContext()).getUserDetails();
        fm = getActivity().getSupportFragmentManager();
        spinnerdialog = new SpinnerDialog();
        spAdminticketServiceType = root.findViewById(R.id.spAdminticketServiceType);
        txtAdminTicketNumberValue = root.findViewById(R.id.txtAdminTicketNumberValue);
        txtAdminEmailValue = root.findViewById(R.id.txtAdminEmailValue);
        btnAdminTicketCancel = root.findViewById(R.id.btnAdminTicketCancel);
        btnAdminTicketComplete = root.findViewById(R.id.btnAdminTicketComplete);
        btnAdminTicketNotice = root.findViewById(R.id.btnAdminTicketNotice);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        this.LoadData();

        btnAdminTicketNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoticeUser();
            }
        });

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

        spAdminticketServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextSize(20);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return root;
    }

    private void LoadData() {
        try {
            spinnerdialog.show(fm, "Start");

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //...here i'm waiting 5 seconds before hiding the custom dialog
                    //...you can do whenever you want or whenever your work is done


                    Intent intent = getActivity().getIntent();
                    String qrResult = intent.getStringExtra("QRResult");
                    Gson gson = new Gson();
                    tblUserServiceTypeModel obj = gson.fromJson(qrResult, tblUserServiceTypeModel.class);
                    if (obj != null) {
                        txtAdminTicketNumberValue.setPaintFlags(txtAdminTicketNumberValue.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        txtAdminTicketNumberValue.setText(String.valueOf(obj.ticketNumber));
                        txtAdminEmailValue.setPaintFlags(txtAdminEmailValue.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        txtAdminEmailValue.setText(obj.email);
                    }

                    tblServiceTypeModel servicetypemodel = new tblServiceTypeModel();
                    tblServiceTypeRepository servicetypeclass = new tblServiceTypeRepository();
                    servicetypemodel.setKeywords("");
                    servicetypemodel.setTblUserID(Integer.parseInt(user.get(UserSessionManager.KEY_USERID)));

                    ArrayList<tblServiceTypeModel> result = servicetypeclass.sp_tblServiceType_SearchAllByFilter(servicetypemodel);
                    if (!result.isEmpty()) {
                        servicetypelist = result;

                        ArrayAdapter<tblServiceTypeModel> adapter = new ArrayAdapter<tblServiceTypeModel>(getContext(), android.R.layout.simple_spinner_item, servicetypelist);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spAdminticketServiceType.setAdapter(adapter);

                        for (int i = 0; i < servicetypelist.size(); i++) {

                            if (obj != null && servicetypelist.get(i).getTblServiceTypeID() == obj.tblServiceTypeID) {
                                spAdminticketServiceType.setSelection(i);
                            } else {
                                spAdminticketServiceType.setSelection(0);
                            }


                        }
                    }


                    tblUserServiceTypeModel userservicetypemodel = new tblUserServiceTypeModel();
                    tblUserServiceTypeRepository userservicetypeclass = new tblUserServiceTypeRepository();
                    userservicetypemodel.setTblServiceTypeID(obj.tblServiceTypeID);
                    userservicetypemodel.setTblUserID(obj.tblUserID);
                    userservicetypemodel.setTicketNumber(obj.getTicketNumber());
                    userservicetypemodel.setTicketStatus("WIP");
                    userservicetypeclass.sp_tblUserServiceType_UpdateStatus(userservicetypemodel);

                    if (userservicetypemodel.errorCode == 1) {
                        Toast.makeText(getActivity(), userservicetypemodel.errorMessage, Toast.LENGTH_SHORT).show();
                    }

                    spinnerdialog.dismiss();
                }
            }, 1000);

        } catch (Throwable e) {
            Log.d("Error", "AdminTicketFragment_LoadData" + e.getStackTrace());
        }


    }

    private void CancelTicket() {
        try {
            spinnerdialog.show(fm, "Start");

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //...here i'm waiting 5 seconds before hiding the custom dialog
                    //...you can do whenever you want or whenever your work is done


                    Intent intent = getActivity().getIntent();
                    String qrResult = intent.getStringExtra("QRResult");

                    Gson gson = new Gson();
                    tblUserServiceTypeModel obj = gson.fromJson(qrResult, tblUserServiceTypeModel.class);

                    tblUserServiceTypeModel userservicetypemodel = new tblUserServiceTypeModel();
                    tblUserServiceTypeRepository userservicetypeclass = new tblUserServiceTypeRepository();
                    userservicetypemodel.setTblUserID(obj.tblUserID);
                    userservicetypemodel.setTicketNumber(obj.ticketNumber);
                    userservicetypemodel.setTblServiceTypeID(obj.tblServiceTypeID);
                    userservicetypemodel.setTicketStatus("CANCEL");
                    userservicetypeclass.sp_tblUserServiceType_UpdateStatus(userservicetypemodel);
                    if (userservicetypemodel.errorCode == 1) {
                        Toast.makeText(getActivity(), userservicetypemodel.errorMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        ArrayList<tblUserServiceTypeModel> result = userservicetypeclass.sp_tblUserServiceType_SelectNextTicket(userservicetypemodel);
                        if (!result.isEmpty()) {
                            userservicetypelist = result;
                            int readytime = 5;
                            for (int i = 0; i < userservicetypelist.size(); i++) {
                                sendNotification("Your ticket will be ready in another" + readytime + " minutes.", "Ticket " + userservicetypelist.get(i).getTicketNumber(), userservicetypelist.get(i).getToken());
                                readytime = readytime + 5;
                            }

                        }


                        Toast.makeText(getActivity(), "Successful Cancel", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.nav_home);
                    }
                    spinnerdialog.dismiss();
                }
            }, 1000);

        } catch (Throwable e) {
            Log.d("Error", "AdminTicketFragment_CancelTicket_" + e.getStackTrace());
        }

    }

    private void CompleteTicket() {
        try {
            spinnerdialog.show(fm, "Start");

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //...here i'm waiting 5 seconds before hiding the custom dialog
                    //...you can do whenever you want or whenever your work is done


                    Intent intent = getActivity().getIntent();
                    String qrResult = intent.getStringExtra("QRResult");

                    Gson gson = new Gson();
                    tblUserServiceTypeModel obj = gson.fromJson(qrResult, tblUserServiceTypeModel.class);

                    tblUserServiceTypeModel userservicetypemodel = new tblUserServiceTypeModel();
                    tblUserServiceTypeRepository userservicetypeclass = new tblUserServiceTypeRepository();
                    userservicetypemodel.setTblUserID(obj.tblUserID);
                    userservicetypemodel.setTicketNumber(obj.ticketNumber);
                    userservicetypemodel.setTblServiceTypeID(obj.tblServiceTypeID);
                    userservicetypemodel.setTicketStatus("COMPLETE");
                    userservicetypeclass.sp_tblUserServiceType_UpdateStatus(userservicetypemodel);
                    if (userservicetypemodel.errorCode == 1) {
                        Toast.makeText(getActivity(), userservicetypemodel.errorMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        ArrayList<tblUserServiceTypeModel> result = userservicetypeclass.sp_tblUserServiceType_SelectNextTicket(userservicetypemodel);
                        if (!result.isEmpty()) {
                            userservicetypelist = result;
                            int readytime = 5;
                            for (int i = 0; i < userservicetypelist.size(); i++) {
                                sendNotification("Your ticket will be ready in another" + readytime + " minutes.", "Ticket " + userservicetypelist.get(i).getTicketNumber(), userservicetypelist.get(i).getToken());
                                readytime = readytime + 5;
                            }

                        }


                        Toast.makeText(getActivity(), "Successful Completed", Toast.LENGTH_SHORT).show();
                        navController.navigate(R.id.nav_home);
                    }
                    spinnerdialog.dismiss();
                }
            }, 1000);

        } catch (Throwable e) {
            Log.d("Error", "AdminTicketFragment_CompleteTicket_" + e.getStackTrace());
        }

    }

    private void NoticeUser() {
        try {

            spinnerdialog.show(fm, "Start");

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //...here i'm waiting 5 seconds before hiding the custom dialog
                    //...you can do whenever you want or whenever your work is done

                    Intent intent = getActivity().getIntent();
                    String qrResult = intent.getStringExtra("QRResult");

                    Gson gson = new Gson();
                    tblUserServiceTypeModel obj = gson.fromJson(qrResult, tblUserServiceTypeModel.class);

                    tblUserServiceTypeModel userservicetypemodel = new tblUserServiceTypeModel();
                    tblUserServiceTypeRepository userservicetypeclass = new tblUserServiceTypeRepository();
                    tblServiceTypeModel selectedItem = (tblServiceTypeModel) spAdminticketServiceType.getSelectedItem();
                    userservicetypemodel.setTblServiceTypeID(selectedItem.getTblServiceTypeID());
                    userservicetypemodel.setTicketNumber(0);
                    userservicetypemodel.setTblUserID(Integer.parseInt(user.get(UserSessionManager.KEY_USERID)));
                    ArrayList<tblUserServiceTypeModel> result = userservicetypeclass.sp_tblUserServiceType_SelectCurrentTicket(userservicetypemodel);
                    if (!result.isEmpty()) {
                        userservicetypelist = result;
                        int readytime = 5;
                        for (int i = 0; i < userservicetypelist.size(); i++) {
                            sendNotification("Your ticket will be ready in another" + readytime + " minutes.", "Ticket " + userservicetypelist.get(i).getTicketNumber(), userservicetypelist.get(i).getToken());
                            readytime = readytime + 5;
                        }

                    }
                    spinnerdialog.dismiss();
                }
            }, 1000);

        } catch (
                Throwable e) {
            Log.d("Error", "AdminTicketFragment_NoticeUser_" + e.getStackTrace());
        }

    }
}