package com.example.qr_ticket.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.qr_ticket.R;
import com.example.qr_ticket.data.SpinnerDialog;
import com.example.qr_ticket.data.UserSessionManager;
import com.example.qr_ticket.data.model.tblServiceTypeModel;
import com.example.qr_ticket.data.model.tblUserServiceTypeModel;
import com.example.qr_ticket.data.repository.tblServiceTypeRepository;
import com.example.qr_ticket.data.repository.tblUserServiceTypeRepository;
import com.example.qr_ticket.ui.login.LoginActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    // User Session Manager Class
    private UserSessionManager session;
    private HashMap<String, String> user;
    private List<tblServiceTypeModel> servicetypelist;
    private NavController navController;
    private Spinner spServiceType;
    private GoogleSignInClient mGoogleSignInClient;
    FragmentManager fm;
    SpinnerDialog spinnerdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        TextView txtheaderemail = (TextView) headerLayout.findViewById(R.id.txtheaderemail);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        FloatingActionButton fabAdd = (FloatingActionButton) this.findViewById(R.id.fabAdd);

        fm = getSupportFragmentManager();
        spinnerdialog = new SpinnerDialog();

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleSignInClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END build_client]

        session = new UserSessionManager(getApplicationContext());
        user = UserSessionManager.getInstance(getApplicationContext()).getUserDetails();

        if (session.checkLogin())
            finish();

        txtheaderemail.setText(user.get(UserSessionManager.KEY_EMAIL));

        Menu nav_Menu = navigationView.getMenu();

        if (Integer.parseInt(user.get(UserSessionManager.KEY_ISADMIN)) == 1) {
            fabAdd.setImageResource(R.drawable.ic_baseline_photo_camera);
            nav_Menu.findItem(R.id.nav_ticket).setVisible(false);
        } else {
            nav_Menu.findItem(R.id.nav_adminticket).setVisible(false);
        }


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_ticket, R.id.nav_adminticket, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        final MenuItem Logout = navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                MenuActivity.this.logout();
                return true;
            }
        });


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinnerdialog.show(fm, "Start");

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //...here i'm waiting 5 seconds before hiding the custom dialog
                        //...you can do whenever you want or whenever your work is done
                        if (Integer.parseInt(user.get(UserSessionManager.KEY_ISADMIN)) == 1) {
                            startActivity(new Intent(MenuActivity.this, ScannedBarcodeActivity.class));
                        } else {
                            showPopupDialog();
                        }
                        spinnerdialog.dismiss();
                    }
                }, 1000);
            }
        });

        Intent intent = getIntent();
        String qrResult = intent.getStringExtra("QRResult");
        if (!TextUtils.isEmpty(qrResult)) {
            navController.navigate(R.id.nav_adminticket);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void logout() {
        Intent loginIntent = new Intent(MenuActivity.this, LoginActivity.class);
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
                });
        
        startActivity(loginIntent);
        // Clear the User session data
        // and redirect user to LoginActivity
        session.logoutUser();
        finish();

    }

    private void showPopupDialog() {
        // Create a AlertDialog Builder.
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(false);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View dialogaddticket = layoutInflater.inflate(R.layout.dialog_addticket, null);
        spServiceType = dialogaddticket.findViewById(R.id.spServiceType);

        tblServiceTypeModel servicetypemodel = new tblServiceTypeModel();
        tblServiceTypeRepository servicetypeclass = new tblServiceTypeRepository();
        servicetypemodel.setKeywords("");
        servicetypemodel.setTblUserID(Integer.parseInt(user.get(UserSessionManager.KEY_USERID)));

        ArrayList<tblServiceTypeModel> result = servicetypeclass.sp_tblServiceType_SearchAllByFilter(servicetypemodel);
        if (!result.isEmpty()) {
            servicetypelist = result;

            ArrayAdapter<tblServiceTypeModel> adapter = new ArrayAdapter<tblServiceTypeModel>(this, android.R.layout.simple_spinner_item, servicetypelist);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spServiceType.setAdapter(adapter);
        }

        spServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View arg1, int arg2, long arg3) {
                ((TextView) parent.getChildAt(0)).setTextSize(20);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        alertDialogBuilder.setView(dialogaddticket);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        Button saveUserNameButton = dialogaddticket.findViewById(R.id.button_save_username);
        saveUserNameButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tblUserServiceTypeModel userservicetypemodel = new tblUserServiceTypeModel();
                tblUserServiceTypeRepository userservicetypeclass = new tblUserServiceTypeRepository();
                userservicetypemodel.setTblUserID(Integer.parseInt(user.get(UserSessionManager.KEY_USERID)));
                tblServiceTypeModel selectedItem = (tblServiceTypeModel) spServiceType.getSelectedItem();
                userservicetypemodel.setTblServiceTypeID(selectedItem.getTblServiceTypeID());
                userservicetypeclass.sp_tblUserServiceType_InsertUpdate(userservicetypemodel);
                if (userservicetypemodel.errorCode == 1) {
                    Toast.makeText(MenuActivity.this, userservicetypemodel.errorMessage, Toast.LENGTH_SHORT).show();
                } else {
                    navController.navigate(R.id.nav_ticket);
                    alertDialog.hide();
                }

            }


        });

        Button cancelUserNameButton = dialogaddticket.findViewById(R.id.button_cancel_username);
        cancelUserNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
    }


}
