package com.example.casehearing;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class AboutDev extends AppCompatActivity implements View.OnClickListener {

    TextView ln_aman,ln_mohit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_dev);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("App Developers");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

       ln_aman = findViewById(R.id.ln_aman);
        ln_mohit = findViewById(R.id.ln_mohit);
        ln_aman.setOnClickListener(this);
        ln_mohit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == ln_aman){
            String linkedInProfileUrl = "https://www.linkedin.com/in/aman4411/";

            try {
                getPackageManager().getPackageInfo("com.linkedin.android", 0);
                Intent tw = new Intent(Intent.ACTION_VIEW, Uri.parse("tw://page/"));
                startActivity(tw);
            } catch (Exception e) {
                Intent twi = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedInProfileUrl));
                startActivity(twi);
            }
        }
        if(view == ln_mohit){
            String linkedInProfileUrl = "https://www.linkedin.com/in/mohit-sharma-863025146/";

            try {
                getPackageManager().getPackageInfo("com.linkedin.android", 0);
                Intent tw = new Intent(Intent.ACTION_VIEW, Uri.parse("tw://page/"));
                startActivity(tw);
            } catch (Exception e) {
                Intent twi = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedInProfileUrl));
                startActivity(twi);
            }
        }
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
