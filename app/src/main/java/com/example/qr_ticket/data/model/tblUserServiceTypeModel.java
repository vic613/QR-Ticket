package com.example.qr_ticket.data.model;

public class tblUserServiceTypeModel extends  BaseModel {

    public int tblServiceTypeID;
    public int tblUserID;
    public int ticketNumber;
    public int errorCode;
    public String errorMessage;
    public String email;
    public String serviceTypeName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public int getTblServiceTypeID() {
        return tblServiceTypeID;
    }

    public void setTblServiceTypeID(int tblServiceTypeID) {
        this.tblServiceTypeID = tblServiceTypeID;
    }

    public int getTblUserID() {
        return tblUserID;
    }

    public void setTblUserID(int tblUserID) {
        this.tblUserID = tblUserID;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

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


}
