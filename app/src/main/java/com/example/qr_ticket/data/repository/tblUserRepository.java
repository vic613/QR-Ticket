package com.example.qr_ticket.data.repository;

import android.util.Log;

import com.example.qr_ticket.data.ConnectionClass;
import com.example.qr_ticket.data.model.tblUserModel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class tblUserRepository extends ConnectionClass {

    public ArrayList<tblUserModel> sp_tblUser_SearchByLogin(tblUserModel usermodel) {
        Connection con = getConnection();
        CallableStatement cs = null;
        ArrayList<tblUserModel> result = new ArrayList<tblUserModel>();

        tblUserModel item;
        try {
            cs = con.prepareCall("{call sp_tblUser_SearchByLogin(?,?)}");
            //cs.registerOutParameter(1, Types.VARCHAR);
            cs.setString("@LoginID", usermodel.getLoginID());
            cs.setString("@Password", usermodel.getPassword());
            cs.execute();

            ResultSet rs = cs.getResultSet();
                while(rs.next()) {
                    item = new tblUserModel();  // line1
                    item.setLoginID(rs.getString("LoginID"));
                    item.setTblUserID(rs.getInt("tblUserID"));
                    item.setIsAdmin(rs.getInt("IsAdmin"));
                    result.add(item);
                }
            return result;
        } catch (SQLException e) {
            Log.d("SQLException: ", e.getMessage());
        }
        finally {
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
        }
        return result;
    }
}
