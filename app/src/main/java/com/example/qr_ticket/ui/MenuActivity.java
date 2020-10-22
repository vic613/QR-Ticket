package com.example.qr_ticket.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.qr_ticket.R;
import com.example.qr_ticket.data.UserSessionManager;
import com.example.qr_ticket.data.model.tblServiceTypeModel;
import com.example.qr_ticket.ui.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.List;

public class MenuActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;
    // User Session Manager Class
    UserSessionManager session;
    HashMap<String, String> user;
    private List<tblServiceTypeModel> exchangelist;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Session class instance
        session = new UserSessionManager(getApplicationContext());
        user = UserSessionManager.getInstance(getApplicationContext()).getUserDetails();
        // Check user login (this is the important point)
        // If User is not logged in , This will redirect user to LoginActivity
        // and finish current activity from activity stack.
        if (session.checkLogin())
            finish();



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_logout)
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
        startActivity(loginIntent);
        // Clear the User session data
        // and redirect user to LoginActivity
        session.logoutUser();
        finish();

    }


}
