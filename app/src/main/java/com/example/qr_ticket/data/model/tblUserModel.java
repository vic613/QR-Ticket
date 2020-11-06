package com.example.qr_ticket.data.model;

public class tblUserModel extends  BaseModel {
    private String loginID;
    private String password;
    private int tblUserID;
    private int IsAdmin;
    public String displayname;
    public String email;
    public String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

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

    @Override
    public String toString() {
        return password;
    }
}
