package com.example.qr_ticket.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qr_ticket.R;
import com.example.qr_ticket.data.model.tblServiceTypeModel;

import java.util.List;

public class HomeCustomAdapter extends BaseAdapter {
    private Context context;
    private final String[] mobileValues;
    private List<tblServiceTypeModel> servicetype;
    View gridView;
    public HomeCustomAdapter(Context context, String[] mobileValues, List<tblServiceTypeModel> servicetypelist) {
        this.context = context;
        this.mobileValues = mobileValues;
        this.servicetype = servicetypelist;
    }

    public View getView(int position, View convertView, ViewGroup parent) {


        try {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);



            if (convertView == null) {

                gridView = new View(context);

                // get layout from mobile.xml
                gridView = inflater.inflate(R.layout.frangment_home_servicelist, null);

                // set value into textview
                TextView txtServiceTypeName = (TextView) gridView.findViewById(R.id.txtServiceTypeName);
                TextView txtHomeTicketNumber = (TextView) gridView.findViewById(R.id.txtHomeTicketNumber);
                TextView txtHomeTicketNextNumber = (TextView) gridView.findViewById(R.id.txtHomeTicketNextNumber);
                int mobile = Integer.parseInt(mobileValues[position]);


                if (mobile == servicetype.get(position).getTblServiceTypeID()) {
                    txtServiceTypeName.setText(servicetype.get(position).getServiceTypeName());
                    txtHomeTicketNumber.setText("Current Ticket: " + String.valueOf(servicetype.get(position).getTicketNumber()));
                    txtHomeTicketNextNumber.setText("Next Ticket: " + String.valueOf(servicetype.get(position).getTicketNumber() + 1));
                }

            } else {
                gridView = (View) convertView;
            }

        } catch (Throwable e) {
            Log.d("Error", "HomeFragment_LoadData" + e.getStackTrace());

        }
        return gridView;
    }

        @Override
        public int getCount () {
            return mobileValues.length;
        }

        @Override
        public Object getItem ( int position){
            return null;
        }

        @Override
        public long getItemId ( int position){
            return 0;
        }
    }
