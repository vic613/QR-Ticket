package com.example.qr_ticket.data.model;

public class tblServiceTypeModel extends BaseModel {
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


    public int getServiceTypeNumber() {
        return ServiceTypeNumber;
    }

    public void setServiceTypeNumber(int serviceTypeNumber) {
        ServiceTypeNumber = serviceTypeNumber;
    }

    public int tblServiceTypeID;
    public String ServiceTypeName;
    public String ServiceTypeDesc;
    public int ServiceTypeNumber;
    public String Keywords;
}
