package com.example.qr_ticket.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private int tblUserID;
    private int IsAdmin;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName,  int tblUserID, int IsAdmin) {
        this.displayName = displayName;
        this.tblUserID = tblUserID;
        this.IsAdmin = IsAdmin;
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


    String getDisplayName() {
        return displayName;
    }
}
