package com.example.qr_ticket.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.qr_ticket.R;
import com.example.qr_ticket.data.UserSessionManager;
import com.example.qr_ticket.data.model.tblServiceTypeModel;
import com.example.qr_ticket.data.repository.tblServiceTypeRepository;

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
}