package com.example.qr_ticket.data.model;

public class tblServiceTypeModel extends BaseModel {

    public int tblServiceTypeID;
    public String ServiceTypeName;
    public String ServiceTypeDesc;
    public int TicketNumber;
    public String Keywords;

    public tblServiceTypeModel() {

    }

    public int getTblServiceTypeID() {
        return tblServiceTypeID;
    }

    public void setTblServiceTypeID(int tblServiceTypeID) {
        this.tblServiceTypeID = tblServiceTypeID;
    }

    public String getServiceTypeName() {
        return ServiceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        ServiceTypeName = serviceTypeName;
    }

    public String getServiceTypeDesc() {
        return ServiceTypeDesc;
    }

    public void setServiceTypeDesc(String serviceTypeDesc) {
        ServiceTypeDesc = serviceTypeDesc;
    }

    public String getKeywords() {
        return Keywords;
    }

    public void setKeywords(String keywords) {
        Keywords = keywords;
    }

    public int getTicketNumber() {
        return TicketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        TicketNumber = ticketNumber;
    }


    @Override
    public String toString() {
        return ServiceTypeName;
    }
}
