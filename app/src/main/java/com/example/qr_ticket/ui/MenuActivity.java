package com.example.qr_ticket.ui;

import android.content.Intent;
import android.os.Bundle;
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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.example.qr_ticket.R;
import com.example.qr_ticket.data.UserSessionManager;
import com.example.qr_ticket.data.model.tblServiceTypeModel;
import com.example.qr_ticket.data.repository.tblServiceTypeRepository;
import com.example.qr_ticket.ui.gallery.GalleryFragment;
import com.example.qr_ticket.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    // User Session Manager Class
    UserSessionManager session;
    HashMap<String, String> user;
    private List<tblServiceTypeModel> exchangelist;
    private ViewPager viewPager;

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

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPopupDialog();

            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
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

    private void showPopupDialog() {
        // Create a AlertDialog Builder.
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // Set title, icon, can not cancel properties.
//        alertDialogBuilder.setTitle("Select Service");
        //alertDialogBuilder.setIcon(R.drawable.add_icon_56);
        alertDialogBuilder.setCancelable(false);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View inputUserNameView = layoutInflater.inflate(R.layout.dialog_addticket, null);
        Spinner spServiceType = inputUserNameView.findViewById(R.id.spServiceType);
        tblServiceTypeModel servicetypemodel = new tblServiceTypeModel();
        tblServiceTypeRepository servicetypeclass = new tblServiceTypeRepository();

        servicetypemodel.setKeywords("");
        servicetypemodel.setTblUserID(Integer.parseInt(user.get(UserSessionManager.KEY_USERID)));

        ArrayList<tblServiceTypeModel> result = servicetypeclass.sp_tblServiceType_SearchAllByFilter(servicetypemodel);

        exchangelist = result;

        String[] items = new String[exchangelist.size()];

        for (int i = 0; i < exchangelist.size(); i++) {
            items[i] = exchangelist.get(i).getServiceTypeName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        spServiceType.setAdapter(adapter);

        spServiceType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int arg2, long arg3) {

                ((TextView) parent.getChildAt(0)).setTextSize(20);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        alertDialogBuilder.setView(inputUserNameView);

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        Button saveUserNameButton = inputUserNameView.findViewById(R.id.button_save_username);
        saveUserNameButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                EditText editText = inputUserNameView.findViewById(R.id.edit_text_username);
//
//                String userName = editText.getText().toString();

                GalleryFragment HelpFragment = new GalleryFragment();
                FragmentManager manager1 = getSupportFragmentManager();
                manager1.beginTransaction().replace(R.id.nav_home,
                        HelpFragment,
                        HelpFragment.getTag()
                ).commit();

            String userName = "";

                if(TextUtils.isEmpty(userName))

            {
                // If user name is empty then popup a snackbar.
                Snackbar.make(v, "User name can not be empty.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            } else

            {
                // Create a new string list to store listview item string.
                List<String> userNameList = new ArrayList<String>();

                // Indicate whether newly added name exist in current list view or not.
                boolean addedNameExist = false;

                // Add all current list view item string to string list.


                // If newly added user name do not exist in current list view.
                if (!addedNameExist) {
//                        userNameList.add(userName);
//
//                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, userNameList);
//                        userNameListView.setAdapter(arrayAdapter);
//
//                        userNameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
//                                Object clickItemObj = adapterView.getAdapter().getItem(index);
//                                Toast.makeText(getApplicationContext(), "You clicked " + clickItemObj.toString(), Toast.LENGTH_LONG).show();
//                            }
//                        });

                    alertDialog.hide();
                } else {
                    Snackbar.make(v, "User name exist, please input another one.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        }
    });
    Button cancelUserNameButton = inputUserNameView.findViewById(R.id.button_cancel_username);
        cancelUserNameButton.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View v){
        alertDialog.cancel();
    }
    });
}
    public void setCurrentItem (int item, boolean smoothScroll) {
        viewPager.setCurrentItem(item, smoothScroll);
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

    public void next_fragment(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
    }

    public void previous_fragment(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
    }


}
