package com.ztcartxe.reppopkpa.apkpopper.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.ztcartxe.reppopkpa.apkpopper.R;
import com.ztcartxe.reppopkpa.apkpopper.utils.Utility;

public class Aboutus_Activity extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus_);

        //Changing Status Bar Color
        Utility.setLightStatusBar(getWindow().getDecorView().getRootView(),this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view){
        if(view.getId() == R.id.lugpl3Btn){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gnu.org/licenses/gpl-3.0.en.html")));
        }
        else if(view.getId() == R.id.sourceCodeGithubBtn){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/bleedweedsuz/AndroidEbookViewer")));
        }
    }
}
