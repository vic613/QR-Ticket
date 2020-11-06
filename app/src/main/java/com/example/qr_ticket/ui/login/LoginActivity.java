package com.example.qr_ticket.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.qr_ticket.R;
import com.example.qr_ticket.data.EncryptionClass;
import com.example.qr_ticket.data.SpinnerDialog;
import com.example.qr_ticket.data.UserSessionManager;
import com.example.qr_ticket.data.model.tblUserCustomInfoModel;
import com.example.qr_ticket.data.model.tblUserModel;
import com.example.qr_ticket.data.repository.tblUserCustomInfoRepository;
import com.example.qr_ticket.data.repository.tblUserRepository;
import com.example.qr_ticket.ui.MenuActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginViewModel loginViewModel;
    // User Session Manager Class
    UserSessionManager session;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    ProgressBar loadingProgressBar;
    private TextView mStatusTextView;
    String password;
    String loginID;
    String displayname;
    String type;
    String firebasetoken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());
//
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);


        final Button login = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupDialog();
            }
        });

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleSignInClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END build_client]

        // [START customize_button]
        // Customize sign-in button. The sign-in button can be displayed in
        // multiple sizes.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        // [END customize_button]

    }


    @Override
    public void onStart() {
        super.onStart();

        // Check if the user is already signed in and all required scopes are granted
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null && GoogleSignIn.hasPermissions(account, new Scope(Scopes.DRIVE_APPFOLDER))) {
            GoogleLogin(account);
        } else {
            GoogleLogin(null);
        }
    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(@Nullable Task<GoogleSignInAccount> completedTask) {
        Log.d(TAG, "handleSignInResult:" + completedTask.isSuccessful());

        try {
            // Signed in successfully, show authenticated U
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleLogin(account);
        } catch (ApiException e) {
            // Signed out, show unauthenticated UI.
            Log.w(TAG, "handleSignInResult:error", e);
            GoogleLogin(null);
        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]


    private void GoogleLogin(@Nullable final GoogleSignInAccount account) {
        if (account != null) {

            loginID = String.valueOf(account.getEmail());
            password = String.valueOf("");
            displayname = account.getDisplayName();
            type = "GOOGLE";
            ProcessLogin();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void showPopupDialog() {
        // Create a AlertDialog Builder.
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(false);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View dialog_locallogin = layoutInflater.inflate(R.layout.dialog_locallogin, null);
        alertDialogBuilder.setView(dialog_locallogin);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
        final EditText usernameEditText = dialog_locallogin.findViewById(R.id.username);
        final EditText passwordEditText = dialog_locallogin.findViewById(R.id.password);


        Button btnLocalLogin = dialog_locallogin.findViewById(R.id.btnLocalLogin);
        btnLocalLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loginID = String.valueOf(usernameEditText.getText());
                password = String.valueOf(passwordEditText.getText());
                displayname = "";
                type = "LOCAL";
                ProcessLogin();
                alertDialog.hide();
            }
        });


    }

    public void ProcessLogin() {
        try {
            FragmentManager fm = this.getSupportFragmentManager();
            final SpinnerDialog spinnerdialog = new SpinnerDialog();
            spinnerdialog.show(fm, "Start");

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //...here i'm waiting 5 seconds before hiding the custom dialog
                    //...you can do whenever you want or whenever your work is done
                    String encrptpwd = String.valueOf(EncryptionClass.encrypt(password));
                    tblUserModel usermodel = new tblUserModel();
                    usermodel.setLoginID(loginID);
                    usermodel.setPassword(encrptpwd);
                    usermodel.setDisplayname("");
                    usermodel.setType(type);
                    tblUserRepository userclass = new tblUserRepository();

                    final ArrayList<tblUserModel> result = userclass.sp_tblUser_SearchByLogin(usermodel);

                    if (!result.isEmpty()) {

                        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                            @Override
                            public void onSuccess(InstanceIdResult instanceIdResult) {
                                firebasetoken = instanceIdResult.getToken();
                                tblUserCustomInfoModel usercustominfo = new tblUserCustomInfoModel();
                                tblUserCustomInfoRepository usercustominfoclass = new tblUserCustomInfoRepository();
                                usercustominfo.setTblUserID(result.get(0).getTblUserID());
                                usercustominfo.setType("FIREBASETOKEN");
                                usercustominfo.setValue(firebasetoken);
                                usercustominfoclass.sp_tblUserCustomInfo_InsertUpdate(usercustominfo);
                                if (usercustominfo.errorCode == 1) {
                                    Log.d("error", usercustominfo.errorMessage);
                                }
                            }
                        });

                        session.createUserLoginSession(result.get(0).getDisplayname(),
                                result.get(0).getEmail(), String.valueOf(result.get(0).getTblUserID()),
                                String.valueOf(result.get(0).getIsAdmin()), firebasetoken);


                        Intent menuActivity = new Intent(getBaseContext(), MenuActivity.class);
                        startActivity(menuActivity);


                        setResult(Activity.RESULT_OK);

                        //Complete and destroy login activity once successful
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                    spinnerdialog.dismiss();
                }
            }, 1000);
        } catch (Throwable e) {
            Log.d("Error", e.getMessage());
        }
    }
}
