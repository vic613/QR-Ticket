package com.example.qr_ticket.data.repository;

import android.util.Log;

import com.example.qr_ticket.data.ConnectionClass;
import com.example.qr_ticket.data.model.tblUserModel;
import com.example.qr_ticket.data.model.tblUserServiceTypeModel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class tblUserRepository extends ConnectionClass {

    public ArrayList<tblUserModel> sp_tblUser_SearchByLogin(tblUserModel usermodel) {
        Connection con = getConnection();
        CallableStatement cs = null;
        ArrayList<tblUserModel> result = new ArrayList<tblUserModel>();
        tblUserModel item;
        try {
            cs = con.prepareCall("{call sp_tblUser_SearchByLogin(?,?,?,?)}");
            //cs.registerOutParameter(1, Types.VARCHAR);
            cs.setString("@LoginID", usermodel.getLoginID());
            cs.setString("@Password",  usermodel.getPassword());
            cs.setString("@DisplayName", usermodel.getDisplayname());
            cs.setString("@Type", usermodel.getType());
            cs.execute();

            ResultSet rs = cs.getResultSet();
            while (rs.next()) {
                item = new tblUserModel();  // line1
                item.setLoginID(rs.getString("LoginID"));
                item.setTblUserID(rs.getInt("tblUserID"));
                item.setIsAdmin(rs.getInt("IsAdmin"));
                item.setEmail(rs.getString("Email"));
                item.setDisplayname(rs.getString("DisplayName"));
                result.add(item);
            }
            return result;
        } catch (SQLException e) {
            Log.d("SQLException: ", e.getMessage());
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
        }
        return result;
    }

    public boolean sp_tblUser_InsertUpdate(tblUserModel usermodel) {
        Connection con = getConnection();
        CallableStatement cs = null;

        ArrayList<tblUserServiceTypeModel> result = new ArrayList<tblUserServiceTypeModel>();

        tblUserServiceTypeModel item;
        try {
            cs = con.prepareCall("{call sp_tblUser_InsertUpdate(?,?,?,?)}");
            //cs.registerOutParameter(1, Types.VARCHAR);
            cs.setString("@LoginID", usermodel.getLoginID());
            cs.setString("@DisplayName", usermodel.getDisplayname());
            cs.registerOutParameter("@ErrorCode", Types.SMALLINT);
            cs.registerOutParameter("@ErrorMessage", Types.VARCHAR);
            cs.executeUpdate();

            usermodel.setErrorCode(cs.getInt("ErrorCode"));
            usermodel.setErrorMessage(cs.getString("ErrorMessage"));
            if (usermodel.errorCode == 1) {
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
