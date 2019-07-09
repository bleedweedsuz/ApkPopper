package com.ztcartxe.reppopkpa.apkpopper.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.ztcartxe.reppopkpa.apkpopper.R;
import com.ztcartxe.reppopkpa.apkpopper.utils.Utility;

import java.net.URL;

public class License_Credits_Activity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_credits);

        //Changing Status Bar Color
        Utility.setLightStatusBar(getWindow().getDecorView().getRootView(),this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onClick(View view){
        if(view.getId() == R.id.RVFSBtn){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/timusus/RecyclerView-FastScroll/blob/master/LICENSE.md")));
        }
        else if(view.getId() == R.id.apkparserBtn){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/hsiafan/apk-parser/blob/master/LICENSE")));
        }
        else if(view.getId() == R.id.fontAwesomeBtn){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://fontawesome.com/v4.7.0/license/")));
        }
    }
}
