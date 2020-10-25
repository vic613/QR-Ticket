package com.example.qr_ticket.data.model;

public class tblUserModel extends  BaseModel {
    private String loginID;
    private String password;
    private int tblUserID;
    private int IsAdmin;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public int getTblUserID() {
        return tblUserID;
    }

    public void setTblUserID(int tblUserID) {
        this.tblUserID = tblUserID;
    }

    public int getIsAdmin() {
        return IsAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        IsAdmin = isAdmin;
    }
}
