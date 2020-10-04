package com.example.qr_ticket.ui.home;

import android.content.Context;
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

    public HomeCustomAdapter(Context context, String[] mobileValues, List<tblServiceTypeModel> servicetypelist) {
        this.context = context;
        this.mobileValues = mobileValues;
        this.servicetype = servicetypelist;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;

            if (convertView == null) {

                gridView = new View(context);

                // get layout from mobile.xml
                gridView = inflater.inflate(R.layout.frangment_home_servicelist, null);

                // set value into textview
                TextView txtServiceTypeName = (TextView) gridView.findViewById(R.id.txtServiceTypeName);
                TextView txtServiceTypeNumber = (TextView) gridView.findViewById(R.id.txtServiceTypeNumber);

                String mobile = mobileValues[position];


                if (mobile.equals(Integer.toString(servicetype.get(position).getTblServiceTypeID()))) {
                    txtServiceTypeName.setText(servicetype.get(position).getServiceTypeName());
                    txtServiceTypeNumber.setText(String.valueOf(servicetype.get(position).getTblServiceTypeID()));
                }

            } else {
                gridView = (View) convertView;
            }
            return gridView;



    }

    @Override
    public int getCount() {
        return mobileValues.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
