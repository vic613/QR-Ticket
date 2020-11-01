package com.example.qr_ticket.data.repository;

import android.util.Log;

import com.example.qr_ticket.data.ConnectionClass;
import com.example.qr_ticket.data.model.tblUserCustomInfoModel;
import com.example.qr_ticket.data.model.tblUserServiceTypeModel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class tblUserCustomInfoRepository extends ConnectionClass {

    public boolean sp_tblUserCustomInfo_InsertUpdate(tblUserCustomInfoModel usercustominfo) {
        Connection con = getConnection();
        CallableStatement cs = null;

        ArrayList<tblUserServiceTypeModel> result = new ArrayList<tblUserServiceTypeModel>();

        tblUserCustomInfoModel item;
        try {
            cs = con.prepareCall("{call sp_tblUserCustomInfo_InsertUpdate(?,?,?,?,?)}");
            //cs.registerOutParameter(1, Types.VARCHAR);
            cs.setInt("@tblUserID", usercustominfo.getTblUserID());
            cs.setString("@Type", usercustominfo.getType());
            cs.setString("@Value", usercustominfo.getValue());
            cs.registerOutParameter("@ErrorCode", Types.SMALLINT);
            cs.registerOutParameter("@ErrorMessage", Types.VARCHAR);
            cs.executeUpdate();

            usercustominfo.setErrorCode(cs.getInt("ErrorCode"));
            usercustominfo.setErrorMessage(cs.getString("ErrorMessage"));

            if (usercustominfo.errorCode == 1) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            Log.d("SQLException: ", e.getMessage());
            return false;
        } finally {
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException e) {
                    Log.d("SQLException: ", e.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();

                } catch (SQLException e) {
                    Log.d("SQLException: ", e.getMessage());
                }
            }
            return true;
        }

    }
}
