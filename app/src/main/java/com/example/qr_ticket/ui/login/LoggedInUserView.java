package com.example.qr_ticket.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;


    private int tblUserID;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName,  int tblUserID) {
        this.displayName = displayName;
        this.tblUserID = tblUserID;
    }

    public int getTblUserID() {
        return tblUserID;
    }

    public void setTblUserID(int tblUserID) {
        this.tblUserID = tblUserID;
    }


    String getDisplayName() {
        return displayName;
    }
}
