package com.example.qr_ticket.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.qr_ticket.R;
import com.example.qr_ticket.data.UserSessionManager;
import com.example.qr_ticket.data.model.tblServiceTypeModel;
import com.example.qr_ticket.data.repository.tblServiceTypeRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    private String[] exchanges;
    private List<tblServiceTypeModel> exchangelist;
    HashMap<String, String> user;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final GridView gvExchange = (GridView) root.findViewById(R.id.gvExchange);

        user = UserSessionManager.getInstance(getContext()).getUserDetails();

        this.GetServiceTypeList(gvExchange);

        FloatingActionButton fabAdd = (FloatingActionButton)getActivity().findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPopupDialog();

            }
        });
        return root;
    }

    private void GetServiceTypeList(final GridView gvExchange) {
        try {
            tblServiceTypeModel servicetypemodel = new tblServiceTypeModel();
            tblServiceTypeRepository servicetypeclass = new tblServiceTypeRepository();

            servicetypemodel.setKeywords("");
            servicetypemodel.setTblUserID(Integer.parseInt(user.get(UserSessionManager.KEY_USERID)));

            ArrayList<tblServiceTypeModel> result = servicetypeclass.sp_tblServiceType_SearchAllByFilter(servicetypemodel);
            if (result.isEmpty()) {

            } else {
                // LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                exchangelist = result;

                exchanges = new String[exchangelist.size()];

                for (int i = 0; i < exchangelist.size(); i++) {
                    exchanges[i] = Integer.toString(exchangelist.get(i).getTblServiceTypeID());
                }

                gvExchange.setAdapter(new HomeCustomAdapter(getContext(), exchanges, exchangelist));

            }
        } catch (Throwable e) {
            e.printStackTrace();
        }


    }

    private void showPopupDialog() {
        // Create a AlertDialog Builder.
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getActivity());
        alertDialogBuilder.setCancelable(false);
        LayoutInflater layoutInflater = LayoutInflater.from(this.getActivity());
        final View inputUserNameView = layoutInflater.inflate(R.layout.dialog_addticket, null);
        Spinner spServiceType = inputUserNameView.findViewById(R.id.spServiceType);

        tblServiceTypeModel servicetypemodel = new tblServiceTypeModel();
        tblServiceTypeRepository servicetypeclass = new tblServiceTypeRepository();
        servicetypemodel.setKeywords("");
        servicetypemodel.setTblUserID(Integer.parseInt(user.get(UserSessionManager.KEY_USERID)));
        ArrayList<tblServiceTypeModel> result = servicetypeclass.sp_tblServiceType_SearchAllByFilter(servicetypemodel);
        exchangelist = result;
        String[] items = new String[exchangelist.size()];
        for (int i = 0; i < exchangelist.size(); i++) {
            items[i] = exchangelist.get(i).getServiceTypeName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, items);
        spServiceType.setAdapter(adapter);
        spServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextSize(20);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        alertDialogBuilder.setView(inputUserNameView);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        Button saveUserNameButton = inputUserNameView.findViewById(R.id.button_save_username);
        saveUserNameButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.nav_gallery);
                alertDialog.hide();
            }


        });

        Button cancelUserNameButton = inputUserNameView.findViewById(R.id.button_cancel_username);
        cancelUserNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }




}