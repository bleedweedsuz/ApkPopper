package com.ztcartxe.reppopkpa.apkpopper.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.ztcartxe.reppopkpa.apkpopper.R;
import com.ztcartxe.reppopkpa.apkpopper.engine.PackageFullDetails;
import com.ztcartxe.reppopkpa.apkpopper.utils.Utility;
import java.io.IOException;

public class Manifest_Activity extends AppCompatActivity {
    EditText codeView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifest_);
        Utility.setLightStatusBar(getWindow().getDecorView().getRootView(),this);
        codeView = findViewById(R.id.codeContainer);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            PackageFullDetails packageFullDetails = new PackageFullDetails(AppDetails_Activity.packageInfoItem);
            codeView.setText(packageFullDetails.getApkFile().getManifestXml());
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}