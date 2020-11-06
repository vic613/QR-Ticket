package com.example.qr_ticket.data.repository;

import android.util.Log;

import com.example.qr_ticket.data.ConnectionClass;
import com.example.qr_ticket.data.model.tblUserServiceTypeModel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class tblUserServiceTypeRepository extends ConnectionClass {

    public boolean sp_tblUserServiceType_InsertUpdate(tblUserServiceTypeModel userservicetypemodel) {
        Connection con = getConnection();
        CallableStatement cs = null;

        ArrayList<tblUserServiceTypeModel> result = new ArrayList<tblUserServiceTypeModel>();

        tblUserServiceTypeModel item;
        try {
            cs = con.prepareCall("{call sp_tblUserServiceType_InsertUpdate(?,?,?,?)}");
            //cs.registerOutParameter(1, Types.VARCHAR);
            cs.setInt("@tblUserID", userservicetypemodel.getTblUserID());
            cs.setInt("@tblServiceTypeID", userservicetypemodel.getTblServiceTypeID());
            cs.registerOutParameter("@ErrorCode", Types.SMALLINT);
            cs.registerOutParameter("@ErrorMessage", Types.VARCHAR);
            cs.executeUpdate();

            userservicetypemodel.setErrorCode(cs.getInt("ErrorCode"));
            userservicetypemodel.setErrorMessage(cs.getString("ErrorMessage"));
            if (userservicetypemodel.errorCode == 1) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        } finally {
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
            return true;
        }

    }

    public ArrayList<tblUserServiceTypeModel> sp_tblUserServiceType_SearchByUserID(tblUserServiceTypeModel userservicetypemodel) {
        Connection con = getConnection();
        CallableStatement cs = null;
        ArrayList<tblUserServiceTypeModel> result = new ArrayList<tblUserServiceTypeModel>();
        tblUserServiceTypeModel item;
        try {
            cs = con.prepareCall("{call sp_tblUserServiceType_SearchByUserID(?)}");
            //cs.registerOutParameter(1, Types.VARCHAR);
            cs.setInt("@tblUserID", userservicetypemodel.getTblUserID());
            //cs.setInt(2, UserSessionManager());
            cs.execute();

            ResultSet rs = cs.getResultSet();
            while(rs.next()) {
                item = new tblUserServiceTypeModel();  // line1
                item.setTblServiceTypeID(rs.getInt("tblServiceTypeID"));
                item.setTicketNumber(rs.getInt("TicketNumber"));
                item.setServiceTypeName(rs.getString("ServiceTypeName"));
                item.setEmail(rs.getString("Email"));
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

    public boolean sp_tblUserServiceType_DeleteByID(tblUserServiceTypeModel userservicetypemodel) {
        Connection con = getConnection();
        CallableStatement cs = null;

        ArrayList<tblUserServiceTypeModel> result = new ArrayList<tblUserServiceTypeModel>();

        tblUserServiceTypeModel item;
        try {
            cs = con.prepareCall("{call sp_tblUserServiceType_DeleteByID(?,?,?,?)}");
            //cs.registerOutParameter(1, Types.VARCHAR);
            cs.setInt("@tblUserID", userservicetypemodel.getTblUserID());
            cs.setInt("@TicketNumber", userservicetypemodel.getTicketNumber());
            cs.registerOutParameter("@ErrorCode", Types.SMALLINT);
            cs.registerOutParameter("@ErrorMessage", Types.VARCHAR);
            cs.executeUpdate();

            userservicetypemodel.setErrorCode(cs.getInt("ErrorCode"));
            userservicetypemodel.setErrorMessage(cs.getString("ErrorMessage"));
            if (userservicetypemodel.errorCode == 1) {
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

    public boolean sp_tblUserServiceType_UpdateStatus(tblUserServiceTypeModel userservicetypemodel) {
        Connection con = getConnection();
        CallableStatement cs = null;

        ArrayList<tblUserServiceTypeModel> result = new ArrayList<tblUserServiceTypeModel>();

        tblUserServiceTypeModel item;
        try {
            cs = con.prepareCall("{call sp_tblUserServiceType_UpdateStatus(?,?,?,?,?,?)}");
            //cs.registerOutParameter(1, Types.VARCHAR);
            cs.setInt("@tblUserID", userservicetypemodel.getTblUserID());
            cs.setInt("@TicketNumber", userservicetypemodel.getTicketNumber());
            cs.setInt("@tblServiceTypeID", userservicetypemodel.getTblServiceTypeID());
            cs.setString("@TicketStatus", userservicetypemodel.getTicketStatus());
            cs.registerOutParameter("@ErrorCode", Types.SMALLINT);
            cs.registerOutParameter("@ErrorMessage", Types.VARCHAR);
            cs.executeUpdate();

            userservicetypemodel.setErrorCode(cs.getInt("ErrorCode"));
            userservicetypemodel.setErrorMessage(cs.getString("ErrorMessage"));
            if (userservicetypemodel.errorCode == 1) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            return false;
        } finally {
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
            return true;
        }

    }

    public ArrayList<tblUserServiceTypeModel> sp_tblUserServiceType_SelectNextTicket(tblUserServiceTypeModel userservicetypemodel) {
        Connection con = getConnection();
        CallableStatement cs = null;
        ArrayList<tblUserServiceTypeModel> result = new ArrayList<tblUserServiceTypeModel>();
        tblUserServiceTypeModel item;
        try {
            cs = con.prepareCall("{call sp_tblUserServiceType_SelectNextTicket(?,?,?)}");
            //cs.registerOutParameter(1, Types.VARCHAR);
            cs.setInt("@tblUserID", userservicetypemodel.getTblUserID());
            cs.setInt("@TicketNumber", userservicetypemodel.getTicketNumber());
            cs.setInt("@tblServiceTypeID", userservicetypemodel.getTblServiceTypeID());
            //cs.setInt(2, UserSessionManager());
            cs.execute();

            ResultSet rs = cs.getResultSet();
            while(rs.next()) {
                item = new tblUserServiceTypeModel();  // line1
                item.setTblUserID(rs.getInt("tblUserID"));
                item.setTicketNumber(rs.getInt("TicketNumber"));
                item.setTblServiceTypeID(rs.getInt("tblServiceTypeID"));
                item.setServiceTypeName(rs.getString("ServiceTypeName"));
                item.setToken(rs.getString("Token"));
                result.add(item);
            }
            return result;
        } catch (SQLException e) {
            Log.d("SQLException: ", "sp_tblUserServiceType_SelectNextTicket" + e.getMessage());
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

    public ArrayList<tblUserServiceTypeModel> sp_tblUserServiceType_SelectCurrentTicket(tblUserServiceTypeModel userservicetypemodel) {
        Connection con = getConnection();
        CallableStatement cs = null;
        ArrayList<tblUserServiceTypeModel> result = new ArrayList<tblUserServiceTypeModel>();
        tblUserServiceTypeModel item;
        try {
            cs = con.prepareCall("{call sp_tblUserServiceType_SelectCurrentTicket(?,?,?)}");
            //cs.registerOutParameter(1, Types.VARCHAR);
            cs.setInt("@tblUserID", userservicetypemodel.getTblUserID());
            cs.setInt("@TicketNumber", userservicetypemodel.getTicketNumber());
            cs.setInt("@tblServiceTypeID", userservicetypemodel.getTblServiceTypeID());
            //cs.setInt(2, UserSessionManager());
            cs.execute();

            ResultSet rs = cs.getResultSet();
            while(rs.next()) {
                item = new tblUserServiceTypeModel();  // line1
                item.setTblUserID(rs.getInt("tblUserID"));
                item.setTicketNumber(rs.getInt("TicketNumber"));
                item.setTblServiceTypeID(rs.getInt("tblServiceTypeID"));
                item.setServiceTypeName(rs.getString("ServiceTypeName"));
                item.setToken(rs.getString("Token"));
                result.add(item);
            }
            return result;
        } catch (SQLException e) {
            Log.d("SQLException: ", "sp_tblUserServiceType_SelectNextTicket" + e.getMessage());
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
