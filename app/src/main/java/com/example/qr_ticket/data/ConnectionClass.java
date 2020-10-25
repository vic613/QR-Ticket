package com.example.qr_ticket.data;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import com.example.qr_ticket.BuildConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionClass {

    String classs = "net.sourceforge.jtds.jdbc.Driver";
    String instance = BuildConfig.SQLSERVER;
    String username = BuildConfig.USERNAME;
    String password = BuildConfig.PASSWORD;
    String connString = "jdbc:jtds:sqlserver://" + instance + ";encrypt=fasle;user=" + username + ";password=" + password + ";instance=SQLEXPRESS2017;";

    @SuppressLint("NewApi")
    public Connection getConnection() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;

        try {
            Class.forName(classs);
            conn = DriverManager.getConnection(connString);
        } catch (SQLException se) {
            Log.e("safiya", se.getMessage());
        } catch (ClassNotFoundException e) {
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
        return conn;
    }
}
