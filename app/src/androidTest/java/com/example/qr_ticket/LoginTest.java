package com.example.qr_ticket;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.qr_ticket.data.model.tblUserModel;
import com.example.qr_ticket.data.repository.tblUserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Test
    public void TestLocalLogin() {
        try {
            //String password = "admin";
            String loginID = "admin";
            String type = "LOCAL";
            // String encrptpwd = String.valueOf(EncryptionClass.encrypt(password));
            String encrptpwd = String.valueOf("Ht3IL+4Sx2hBkJddBvsz1g==\\n");
            tblUserModel usermodel = new tblUserModel();
            usermodel.setLoginID(loginID);
            usermodel.setPassword(encrptpwd);
            usermodel.setDisplayname("");
            usermodel.setType(type);
            tblUserRepository userclass = new tblUserRepository();

            ArrayList<tblUserModel> result = userclass.sp_tblUser_SearchByLogin(usermodel);

            assertThat(result.size(), not(equalTo(0)));
        } catch (Throwable e) {
            Log.d("TestResultError", e.getMessage());
            assertFalse(e != null);
        }
    }

    @Test
    public void TestGoogleLogin() {
        try {

            String loginID = "vic613@oum.edu.my";
            String type = "GOOGLE";
            String encrptpwd = String.valueOf("");
            tblUserModel usermodel = new tblUserModel();
            usermodel.setLoginID(loginID);
            usermodel.setPassword(encrptpwd);
            usermodel.setDisplayname("");
            usermodel.setType(type);
            tblUserRepository userclass = new tblUserRepository();

            ArrayList<tblUserModel> result = userclass.sp_tblUser_SearchByLogin(usermodel);

            assertThat(result.size(), not(equalTo(0)));
        } catch (Throwable e) {
            Log.d("TestResultError", e.getMessage());
            assertFalse(e != null);
        }
    }

    @Test
    public void TestLoginWithInvalidLogin() {
        try {
            //String password = "admin";
            String loginID = "admin1";
            String type = "LOCAL";
            // String encrptpwd = String.valueOf(EncryptionClass.encrypt(password));
            String encrptpwd = String.valueOf("Ht3IL+4Sx2hBkJddBvsz1g==\\n");
            tblUserModel usermodel = new tblUserModel();
            usermodel.setLoginID(loginID);
            usermodel.setPassword(encrptpwd);
            usermodel.setDisplayname("");
            usermodel.setType(type);
            tblUserRepository userclass = new tblUserRepository();

            ArrayList<tblUserModel> result = userclass.sp_tblUser_SearchByLogin(usermodel);

            assertThat(result.size(), is(equalTo(0)));
        } catch (Throwable e) {
            Log.d("TestResultError", e.getMessage());
            assertFalse(e != null);
        }
    }

}
