package com.uiet.casehearing;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    CardView add_hearing,view_hearing,add_advocate,remove_advocate,userHearings;
    FirebaseAuth mAuth;
    AlertDialog.Builder builder;

     private String admin1 = "amanmehta4411@gmail.com";
     private  String admin2 = "dlsakrk@gmail.com";

     private String adv1 = "adityanarayan993@gmail.com";
    private String adv2 = "akgautam100@gmail.com";
    private String adv3 = "amansharmadeeg@gmail.com";
    private String adv4 = "aaman.advocate@gmail.com";
    private String adv5 = "amitbhoria85@gmail.com";
    private String adv6 = "amit.lawyer1985@gmail.com";
    private String adv7 = "ghawrianand81@gmail.com";
    private String adv8 = "Animeshbhardwajadvocatekkr@gmail.com";
    private String adv9 = "Swati.ankitgautam@gmail.com";
    private String adv10 = "Asaharan246@gmail.com";
    private String adv11 = "balwindersinghkamoda@gmail.com";
    private String adv12 = "Sunitabhav72@gmail.com";
    private String adv13 = "Divya.indian123@gmail.com";
    private String adv14 = "advocategajesingh@gmail.com";
    private String adv15 = "Harman_adv@rediffmail.com";
    private String adv16 = "Aggarwalhimanshu8@gmail.com";
    private String adv17 = "Babbu84.js@gmail.com";
    private String adv18 = "Jasdeep1718@gmail.com";
    private String adv19 = "kuldipsinghaalyan@gmail.com";
    private String adv20 = "Lovekeshmehta.adv@gmail.com";
    private String adv21 = "Mbksharma100@gmail.com";
    private String adv22 = "mangeramvarma@gmail.com";
    private String adv23 = "Mukeshkkr1972@gmail.com";
    private String adv24 = "Sharmamunish485@gmail.com";
    private String adv25 = "advnkrohilla@gmail.com";
    private String adv26 = "Pankajadv1973@gmail.com";
    private String adv27 = "chopralegalconsultant@gmail.com";
    private String adv28 = "Buchipawan86@gmail.com";
    private String adv29 = "Masih.peter@gmail.com";
    private String adv30 = "rksaini@gmail.com";
    private String adv31 = "Vrajat103@gmail.com";
    private String adv32 = "Rajinderc63@gmail.com";
    private String adv33 = "linkrakesh@gmail.com";
    private String adv34 = "Choprarenuka2@gmail.com";
    private String adv35 = "Sachinjain2412@rediffmail.com";
    private String adv36 = "Vashisth.sadhu21@gmail.com";
    private String adv37 = "Kashyapsanjeev76@gmail.com";
    private String adv38 = "Tsaroj543@gmail.com";
    private String adv39 = "Sarojsaini612@gmail.com";
    private String adv40 = "Saurabhbathla4@gmail.com";
    private String adv41 = "Shaktigautam146@gmail.com";
    private String adv42 = "Trap.sharma6@gmail.com";
    private String adv43 = "Vaibhav_advocate@yahoo.co.in";
    private String adv44 = "Varungarg999@gmail.com";
    private String adv45 = "Vktomar82@gmail.com";
    private String adv46 = "Advocatevikram83@gmail.com";
    private String adv47 = "Vineetsirohi92@gmail.com";
    private String adv48 = "deepvipinsingh@gmail.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, About_app.class);
                startActivity(i);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        add_hearing = findViewById(R.id.add_hearing);
        view_hearing = findViewById(R.id.view_hearing);
        add_advocate = findViewById(R.id.add_advocate);
        remove_advocate = findViewById(R.id.remove_advocate);
        userHearings = findViewById(R.id.userHearings);
        add_hearing.setOnClickListener(this);
        view_hearing.setOnClickListener(this);
        add_advocate.setOnClickListener(this);
        remove_advocate.setOnClickListener(this);
        userHearings.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        builder = new AlertDialog.Builder(this);

        if(mAuth.getCurrentUser()==null){

            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.nav_logout).setTitle("Login ");

            add_hearing.setVisibility(View.GONE);
            view_hearing.setVisibility(View.GONE);
            add_advocate.setVisibility(View.GONE);
            remove_advocate.setVisibility(View.GONE);
            userHearings.setVisibility(View.VISIBLE);
        }

        if(mAuth.getCurrentUser()!=null) {

            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.nav_logout).setTitle("Logout");

            String userEmail = mAuth.getCurrentUser().getEmail();

            if (userEmail.equalsIgnoreCase(admin1) || userEmail.equalsIgnoreCase(admin2)) {
                add_advocate.setVisibility(View.VISIBLE);
                remove_advocate.setVisibility(View.VISIBLE);
                userHearings.setVisibility(View.GONE);
            } else if (userEmail.equalsIgnoreCase(adv1) || userEmail.equalsIgnoreCase(adv2) || userEmail.equalsIgnoreCase(adv3) || userEmail.equalsIgnoreCase(adv4) || userEmail.equalsIgnoreCase(adv5) || userEmail.equalsIgnoreCase(adv6) || userEmail.equalsIgnoreCase(adv7) || userEmail.equalsIgnoreCase(adv8) || userEmail.equalsIgnoreCase(adv9) || userEmail.equalsIgnoreCase(adv10) || userEmail.equalsIgnoreCase(adv11) || userEmail.equalsIgnoreCase(adv12) || userEmail.equalsIgnoreCase(adv13) || userEmail.equalsIgnoreCase(adv14) || userEmail.equalsIgnoreCase(adv15) || userEmail.equalsIgnoreCase(adv16) || userEmail.equalsIgnoreCase(adv17) || userEmail.equalsIgnoreCase(adv18) || userEmail.equalsIgnoreCase(adv19) || userEmail.equalsIgnoreCase(adv20) || userEmail.equalsIgnoreCase(adv21) || userEmail.equalsIgnoreCase(adv22) || userEmail.equalsIgnoreCase(adv23) || userEmail.equalsIgnoreCase(adv24) || userEmail.equalsIgnoreCase(adv25) || userEmail.equalsIgnoreCase(adv26) || userEmail.equalsIgnoreCase(adv27) || userEmail.equalsIgnoreCase(adv28) || userEmail.equalsIgnoreCase(adv29) || userEmail.equalsIgnoreCase(adv30) || userEmail.equalsIgnoreCase(adv31) || userEmail.equalsIgnoreCase(adv32) || userEmail.equalsIgnoreCase(adv33) || userEmail.equalsIgnoreCase(adv34) || userEmail.equalsIgnoreCase(adv35) || userEmail.equalsIgnoreCase(adv36) || userEmail.equalsIgnoreCase(adv37) || userEmail.equalsIgnoreCase(adv38) || userEmail.equalsIgnoreCase(adv39) || userEmail.equalsIgnoreCase(adv40) || userEmail.equalsIgnoreCase(adv41) || userEmail.equalsIgnoreCase(adv42) || userEmail.equalsIgnoreCase(adv43) || userEmail.equalsIgnoreCase(adv44) || userEmail.equalsIgnoreCase(adv45) || userEmail.equalsIgnoreCase(adv46) || userEmail.equalsIgnoreCase(adv47) || userEmail.equalsIgnoreCase(adv48)) {
                add_advocate.setVisibility(View.GONE);
                remove_advocate.setVisibility(View.GONE);
                userHearings.setVisibility(View.GONE);
            } else {
                add_hearing.setVisibility(View.GONE);
                view_hearing.setVisibility(View.GONE);
                add_advocate.setVisibility(View.GONE);
                remove_advocate.setVisibility(View.GONE);
                userHearings.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            // alertdialog for exit the app
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set the title of the Alert Dialog
            alertDialogBuilder.setTitle("Exit");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Are You Sure?")
                    .setCancelable(false)
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // what to do if YES is tapped
                            finishAffinity();
                            System.exit(0);
                        }
                    })

                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // code to do on NO tapped
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            finish();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_addHearing) {
            if(mAuth.getCurrentUser()!=null){
                String userEmail = mAuth.getCurrentUser().getEmail();
                if(userEmail.equalsIgnoreCase(admin1) || userEmail.equalsIgnoreCase(admin2)|| userEmail.equalsIgnoreCase(adv1) || userEmail.equalsIgnoreCase(adv2) || userEmail.equalsIgnoreCase(adv3) || userEmail.equalsIgnoreCase(adv4) || userEmail.equalsIgnoreCase(adv5) || userEmail.equalsIgnoreCase(adv6) || userEmail.equalsIgnoreCase(adv7) || userEmail.equalsIgnoreCase(adv8) || userEmail.equalsIgnoreCase(adv9) || userEmail.equalsIgnoreCase(adv10) || userEmail.equalsIgnoreCase(adv11) || userEmail.equalsIgnoreCase(adv12) || userEmail.equalsIgnoreCase(adv13) || userEmail.equalsIgnoreCase(adv14) || userEmail.equalsIgnoreCase(adv15) || userEmail.equalsIgnoreCase(adv16) || userEmail.equalsIgnoreCase(adv17) || userEmail.equalsIgnoreCase(adv18) || userEmail.equalsIgnoreCase(adv19) || userEmail.equalsIgnoreCase(adv20) || userEmail.equalsIgnoreCase(adv21) || userEmail.equalsIgnoreCase(adv22) || userEmail.equalsIgnoreCase(adv23) || userEmail.equalsIgnoreCase(adv24) || userEmail.equalsIgnoreCase(adv25) || userEmail.equalsIgnoreCase(adv26) || userEmail.equalsIgnoreCase(adv27) || userEmail.equalsIgnoreCase(adv28) || userEmail.equalsIgnoreCase(adv29) || userEmail.equalsIgnoreCase(adv30) || userEmail.equalsIgnoreCase(adv31) || userEmail.equalsIgnoreCase(adv32) || userEmail.equalsIgnoreCase(adv33) || userEmail.equalsIgnoreCase(adv34) || userEmail.equalsIgnoreCase(adv35) || userEmail.equalsIgnoreCase(adv36) || userEmail.equalsIgnoreCase(adv37) || userEmail.equalsIgnoreCase(adv38) || userEmail.equalsIgnoreCase(adv39) || userEmail.equalsIgnoreCase(adv40) || userEmail.equalsIgnoreCase(adv41) || userEmail.equalsIgnoreCase(adv42) || userEmail.equalsIgnoreCase(adv43) || userEmail.equalsIgnoreCase(adv44) || userEmail.equalsIgnoreCase(adv45) || userEmail.equalsIgnoreCase(adv46) || userEmail.equalsIgnoreCase(adv47) || userEmail.equalsIgnoreCase(adv48)) {
                    Intent intent = new Intent(getApplicationContext(),Add_Hearing.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"You don't have permission to this page...",Toast.LENGTH_LONG).show();
                    return true;
                }
            } else {
                Toast.makeText(getApplicationContext(),"Please Login to continue...",Toast.LENGTH_LONG).show();
                return true;
            }


        } else if (id == R.id.nav_viewHearing) {
            if(mAuth.getCurrentUser()!=null){
                String userEmail = mAuth.getCurrentUser().getEmail();
                if(userEmail.equalsIgnoreCase(admin1) || userEmail.equalsIgnoreCase(admin2)|| userEmail.equalsIgnoreCase(adv1) || userEmail.equalsIgnoreCase(adv2) || userEmail.equalsIgnoreCase(adv3) || userEmail.equalsIgnoreCase(adv4) || userEmail.equalsIgnoreCase(adv5) || userEmail.equalsIgnoreCase(adv6) || userEmail.equalsIgnoreCase(adv7) || userEmail.equalsIgnoreCase(adv8) || userEmail.equalsIgnoreCase(adv9) || userEmail.equalsIgnoreCase(adv10) || userEmail.equalsIgnoreCase(adv11) || userEmail.equalsIgnoreCase(adv12) || userEmail.equalsIgnoreCase(adv13) || userEmail.equalsIgnoreCase(adv14) || userEmail.equalsIgnoreCase(adv15) || userEmail.equalsIgnoreCase(adv16) || userEmail.equalsIgnoreCase(adv17) || userEmail.equalsIgnoreCase(adv18) || userEmail.equalsIgnoreCase(adv19) || userEmail.equalsIgnoreCase(adv20) || userEmail.equalsIgnoreCase(adv21) || userEmail.equalsIgnoreCase(adv22) || userEmail.equalsIgnoreCase(adv23) || userEmail.equalsIgnoreCase(adv24) || userEmail.equalsIgnoreCase(adv25) || userEmail.equalsIgnoreCase(adv26) || userEmail.equalsIgnoreCase(adv27) || userEmail.equalsIgnoreCase(adv28) || userEmail.equalsIgnoreCase(adv29) || userEmail.equalsIgnoreCase(adv30) || userEmail.equalsIgnoreCase(adv31) || userEmail.equalsIgnoreCase(adv32) || userEmail.equalsIgnoreCase(adv33) || userEmail.equalsIgnoreCase(adv34) || userEmail.equalsIgnoreCase(adv35) || userEmail.equalsIgnoreCase(adv36) || userEmail.equalsIgnoreCase(adv37) || userEmail.equalsIgnoreCase(adv38) || userEmail.equalsIgnoreCase(adv39) || userEmail.equalsIgnoreCase(adv40) || userEmail.equalsIgnoreCase(adv41) || userEmail.equalsIgnoreCase(adv42) || userEmail.equalsIgnoreCase(adv43) || userEmail.equalsIgnoreCase(adv44) || userEmail.equalsIgnoreCase(adv45) || userEmail.equalsIgnoreCase(adv46) || userEmail.equalsIgnoreCase(adv47) || userEmail.equalsIgnoreCase(adv48)){
                    Intent intent = new Intent(getApplicationContext(),View_Hearing.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(),UserHearings.class);
                    startActivity(intent);
                }
            } else {
                Intent intent = new Intent(getApplicationContext(),UserHearings.class);
                startActivity(intent);
            }



        } else if (id == R.id.nav_addAdvocate) {
            if(mAuth.getCurrentUser()!=null){
                String userEmail = mAuth.getCurrentUser().getEmail();
                if(userEmail.equalsIgnoreCase(admin1) || userEmail.equalsIgnoreCase(admin2)){
                    Intent intent = new Intent(getApplicationContext(), Add_Advocate.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"You don't have permission to this page...",Toast.LENGTH_LONG).show();
                    return true;
                }
            } else {
                Toast.makeText(getApplicationContext(),"Please Login to continue...",Toast.LENGTH_LONG).show();
                return true;
            }


        } else if(id == R.id.nav_removeAdvocate) {
            if (mAuth.getCurrentUser() != null) {
                String userEmail = mAuth.getCurrentUser().getEmail();
                if (userEmail.equals(admin1) || userEmail.equals(admin2)) {
                    Intent intent = new Intent(getApplicationContext(), RemoveAdvocate.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "You don't have permission to this page...", Toast.LENGTH_LONG).show();
                    return true;
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please Login to continue...", Toast.LENGTH_LONG).show();
                return true;
            }

        }else if(id == R.id.nav_share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Download the DLSA KRK App and access all your case hearings online : https://play.google.com/store/apps/details?id=com.uiet.casehearing");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        } else if (id == R.id.nav_about){
            Intent intent = new Intent(getApplicationContext(), About_app.class);
            startActivity(intent);


        } else if (id == R.id.nav_logout){
            if(mAuth.getCurrentUser()!=null){
                builder.setMessage("Do You Want to Logout?");
                builder.setTitle("Alert");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        finish();
                        Intent intent = new Intent(getApplicationContext(),Login.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }

        } else if (id == R.id.nav_developer){
            Intent i = new Intent(getApplicationContext(),AboutDev.class);
            startActivity(i);
        }



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view==add_hearing)
        {
            Intent intent = new Intent(getApplicationContext(),Add_Hearing.class);
            startActivity(intent);
        }
        if(view==view_hearing)
        {
            Intent intent = new Intent(getApplicationContext(),View_Hearing.class);
            startActivity(intent);
        }
        if(view == add_advocate)
        {
            Intent intent = new Intent(getApplicationContext(),Add_Advocate.class);
            startActivity(intent);
        }
        if(view == remove_advocate){
            Intent intent = new Intent(getApplicationContext(),RemoveAdvocate.class);
            startActivity(intent);
        }
        if(view == userHearings){
            Intent intent = new Intent(getApplicationContext(),UserHearings.class);
            startActivity(intent);
        }
    }
}
