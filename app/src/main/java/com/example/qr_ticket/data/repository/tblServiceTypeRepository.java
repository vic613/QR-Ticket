package com.example.qr_ticket.data.repository;

import com.example.qr_ticket.data.ConnectionClass;
import com.example.qr_ticket.data.model.tblServiceTypeModel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class tblServiceTypeRepository extends ConnectionClass {

    public ArrayList<tblServiceTypeModel> sp_tblServiceType_SearchAllByFilter(tblServiceTypeModel servicetypemodel) {
        Connection con = getConnection();
        CallableStatement cs = null;
        ArrayList<tblServiceTypeModel> result = new ArrayList<tblServiceTypeModel>();
        tblServiceTypeModel item;
        try {
            cs = con.prepareCall("{call sp_tblServiceType_SearchAllByFilter(?,?)}");
            //cs.registerOutParameter(1, Types.VARCHAR);
            cs.setString("@sSearch", servicetypemodel.getKeywords());
            cs.setInt("@tblUserID", servicetypemodel.getTblUserID());
            //cs.setInt(2, UserSessionManager());
            cs.execute();

            ResultSet rs = cs.getResultSet();
            while(rs.next()) {
                item = new tblServiceTypeModel();  // line1
                item.setTblServiceTypeID(rs.getInt("tblServiceTypeID"));
                item.setServiceTypeName(rs.getString("ServiceTypeName"));
                item.setServiceTypeDesc(rs.getString("ServiceTypeDesc"));
                item.setTicketNumber(rs.getInt("TicketNumber"));
                result.add(item);
            }
            return result;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
        finally {
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException e) {
                    System.err.println("SQLException: " + e.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("SQLException: " + e.getMessage());
                }
            }
        }
        return result;
    }
}
