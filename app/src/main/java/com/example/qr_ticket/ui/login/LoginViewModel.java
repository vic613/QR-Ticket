package com.example.qr_ticket.ui.login;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.qr_ticket.R;
import com.example.qr_ticket.data.EncryptionClass;
import com.example.qr_ticket.data.LoginRepository;
import com.example.qr_ticket.data.model.tblUserModel;
import com.example.qr_ticket.data.repository.tblUserRepository;

import java.util.ArrayList;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        tblUserModel usermodel = new tblUserModel();
        usermodel.setLoginID(username);
        usermodel.setPassword(EncryptionClass.encrypt(password));
        tblUserRepository userclass = new tblUserRepository();

        ArrayList<tblUserModel> result = userclass.sp_tblUser_SearchByLogin(usermodel);

        if (result.isEmpty() ){

        }else{
           // LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(result.get(0).getLoginID(), result.get(0).getTblUserID(),result.get(0).getIsAdmin())));


        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
