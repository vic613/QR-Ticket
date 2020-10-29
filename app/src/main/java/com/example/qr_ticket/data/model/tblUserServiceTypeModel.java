package com.example.qr_ticket.data.model;

public class tblUserServiceTypeModel extends BaseModel {

    public int tblServiceTypeID;
    public int ticketNumber;
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

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

}
