package com.example.casehearing;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    Button add_hearing,view_hearing,add_advocate;
    FirebaseAuth mAuth;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        add_hearing = findViewById(R.id.add_hearing);
        view_hearing = findViewById(R.id.view_hearing);
        add_advocate = findViewById(R.id.add_advocate);
        //about = findViewById(R.id.about);
        add_hearing.setOnClickListener(this);
        view_hearing.setOnClickListener(this);
        add_advocate.setOnClickListener(this);
        //about.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        builder = new AlertDialog.Builder(this);

        if(mAuth.getCurrentUser()==null){
            finish();
            Intent i = new Intent(getApplicationContext(),Login.class);
            startActivity(i);
        }

        String adminUid = "qCV4rvNowyYtSknFnwPCwAVdksq1";
        String userEmail = mAuth.getCurrentUser().getUid();
        if(userEmail.equals(adminUid)){
            add_advocate.setVisibility(View.VISIBLE);
        }else {
            add_advocate.setVisibility(View.GONE);
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
            Intent intent = new Intent(getApplicationContext(),Add_Hearing.class);
            startActivity(intent);

        } else if (id == R.id.nav_viewHearing) {
            Intent intent = new Intent(getApplicationContext(),View_Hearing.class);
            startActivity(intent);

        } else if (id == R.id.nav_addAdvocate) {
            Intent intent = new Intent(getApplicationContext(),Add_Advocate.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_about){

        } else if (id == R.id.nav_logout){
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
    }
}
