package com.example.qr_ticket;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.qr_ticket.data.model.tblServiceTypeModel;
import com.example.qr_ticket.data.model.tblUserServiceTypeModel;
import com.example.qr_ticket.data.repository.tblServiceTypeRepository;
import com.example.qr_ticket.data.repository.tblUserServiceTypeRepository;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class TicketTest {


    @Test
    public void TestGenerateTicket() {
        try {

            tblUserServiceTypeModel userservicetypemodel = new tblUserServiceTypeModel();
            tblUserServiceTypeRepository userservicetypeclass = new tblUserServiceTypeRepository();
            userservicetypemodel.setTblUserID(Integer.parseInt("1001"));
            userservicetypemodel.setTblServiceTypeID(1001);
            userservicetypeclass.sp_tblUserServiceType_InsertUpdate(userservicetypemodel);

            assertThat(userservicetypemodel.errorCode, not(equalTo(0)));
        } catch (Throwable e) {
            Log.d("TestResultError", e.getMessage());
            assertFalse(e != null);
        }
    }

    @Test
    public void TestListTicket() {
        try {

            tblServiceTypeModel servicetypemodel = new tblServiceTypeModel();
            tblServiceTypeRepository servicetypeclass = new tblServiceTypeRepository();

            servicetypemodel.setKeywords("");
            servicetypemodel.setTblUserID(1001);

            ArrayList<tblServiceTypeModel> result = servicetypeclass.sp_tblServiceType_SearchAllByFilter(servicetypemodel);

            assertThat(result.size(), not(equalTo(0)));
        } catch (Throwable e) {
            Log.d("TestResultError", e.getMessage());
            assertFalse(e != null);
        }
    }
}
