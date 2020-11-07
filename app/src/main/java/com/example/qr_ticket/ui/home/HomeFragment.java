package com.example.qr_ticket.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.qr_ticket.R;
import com.example.qr_ticket.data.SpinnerDialog;
import com.example.qr_ticket.data.UserSessionManager;
import com.example.qr_ticket.data.model.tblServiceTypeModel;
import com.example.qr_ticket.data.repository.tblServiceTypeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {

    private String[] servicetype;
    private List<tblServiceTypeModel> servicetypelist;
    HashMap<String, String> user;
    FragmentManager fm ;
    SpinnerDialog spinnerdialog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final GridView gvExchange = (GridView) root.findViewById(R.id.gvExchange);
        fm = getActivity().getSupportFragmentManager();
        user = UserSessionManager.getInstance(getContext()).getUserDetails();
        spinnerdialog= new SpinnerDialog();
        this.GetServiceTypeList(gvExchange);

        return root;
    }

    private void GetServiceTypeList(final GridView gvExchange) {
        try {

            spinnerdialog.show(fm, "Start");

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //...here i'm waiting 5 seconds before hiding the custom dialog
                    //...you can do whenever you want or whenever your work is done

                    tblServiceTypeModel servicetypemodel = new tblServiceTypeModel();
                    tblServiceTypeRepository servicetypeclass = new tblServiceTypeRepository();

                    servicetypemodel.setKeywords("");
                    servicetypemodel.setTblUserID(Integer.parseInt(user.get(UserSessionManager.KEY_USERID)));

                    ArrayList<tblServiceTypeModel> result = servicetypeclass.sp_tblServiceType_SearchAllByFilter(servicetypemodel);
                    if (!result.isEmpty()) {
                        servicetypelist = result;

                        servicetype = new String[servicetypelist.size()];

                        for (int i= 0; i < servicetypelist.size(); i++) {
                            servicetype[i] = Integer.toString(servicetypelist.get(i).getTblServiceTypeID());
                        }

                        gvExchange.setAdapter(new HomeCustomAdapter(getContext(), servicetype, servicetypelist));
                    }
                    spinnerdialog.dismiss();
                }
            }, 1000);
        } catch (Throwable e) {
            Log.d("Error", "HomeFragment_LoadData" + e.getStackTrace());
        }


    }


}