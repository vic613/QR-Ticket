package com.example.qr_ticket.data.model;

public class tblUserCustomInfoModel extends BaseModel  {
    public String Type;
    public String Value;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
