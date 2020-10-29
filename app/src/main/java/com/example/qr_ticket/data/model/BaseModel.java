package com.example.qr_ticket.data.model;

public class BaseModel {

    public int tblUserID;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int errorCode;
    public String errorMessage;

    public int getTblUserID() {
        return tblUserID;
    }

    public void setTblUserID(int tblUserID) {
        this.tblUserID = tblUserID;
    }


}
