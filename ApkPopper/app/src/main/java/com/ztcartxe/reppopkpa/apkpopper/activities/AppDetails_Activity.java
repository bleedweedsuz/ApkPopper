/*

    ApkPopper "Simple App that lets user to get info about installed app and extract them."
    Copyright (C) 2019  Sujan Thapa

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */
package com.ztcartxe.reppopkpa.apkpopper.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.ztcartxe.reppopkpa.apkpopper.engine.PackageFullDetails;
import com.ztcartxe.reppopkpa.apkpopper.R;
import com.ztcartxe.reppopkpa.apkpopper.engine.PopManager;
import com.ztcartxe.reppopkpa.apkpopper.model.PackageInfoItem;
import com.ztcartxe.reppopkpa.apkpopper.utils.Utility;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AppDetails_Activity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{
    public static PackageInfoItem packageInfoItem;
    int WRITE_EXTERNAL_STORAGE = 200;
    PackageFullDetails packageFullDetails;
    Toolbar toolbar;
    ProgressBar progressBar;
    PopManager popManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app__details_);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.extractProgress);

        (findViewById(R.id.App_Details_cardView)).setBackgroundResource(R.drawable.rounded_corner);
        Utility.setLightStatusBar(getWindow().getDecorView().getRootView(),this);

        ((TextView)findViewById(R.id.titlebar)).setText(packageInfoItem.AppName);
        packageFullDetails = new PackageFullDetails(packageInfoItem);
        setAppInfo();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setAppInfo(){
        ((ImageView)findViewById(R.id.App_Details_ImageView)).setImageDrawable(packageInfoItem.AppIcon);
        ((TextView)findViewById(R.id.App_Details_textAppName)).setText(packageInfoItem.AppName);
        ((TextView)findViewById(R.id.App_Details_textPackageName)).setText(packageInfoItem.PackageName);

        ((TextView)findViewById(R.id.text_appSize)).setText(String.format(Locale.US, "%.2f Mb", packageFullDetails.getFileSize()));
        ((TextView)findViewById(R.id.text_compiledSdk)).setText(packageFullDetails.getApkMeta().getCompileSdkVersion());
        ((TextView)findViewById(R.id.text_CompiledSdkCodeName)).setText(packageFullDetails.getApkMeta().getCompileSdkVersionCodename());
        ((TextView)findViewById(R.id.text_installedDate)).setText(packageFullDetails.getFirstInstalledTime());
        ((TextView)findViewById(R.id.text_lastUpdate)).setText(packageFullDetails.getLastModifyTime());
        ((TextView)findViewById(R.id.text_mime)).setText(packageFullDetails.getMIME());
        ((TextView)findViewById(R.id.text_targetsdk)).setText(packageFullDetails.getApkMeta().getTargetSdkVersion());
        ((TextView)findViewById(R.id.text_minsdk)).setText(packageFullDetails.getApkMeta().getMinSdkVersion());
        ((TextView)findViewById(R.id.text_max_sdk)).setText(packageFullDetails.getApkMeta().getMaxSdkVersion());
        ((TextView)findViewById(R.id.text_OpenGLEVersion)).setText(packageFullDetails.getApkMeta().getGlEsVersion() != null? packageFullDetails.getApkMeta().getGlEsVersion().toString() : "");
        ((TextView)findViewById(R.id.text_platformbuildver)).setText(packageFullDetails.getApkMeta().getPlatformBuildVersionName());
        ((TextView)findViewById(R.id.text_verno)).setText(packageFullDetails.getApkMeta().getVersionName());
        ((TextView)findViewById(R.id.text_ver_code)).setText(packageFullDetails.getApkMeta().getVersionCode().toString());
    }

    public void onClick(View view) {
        if(view.getId() == R.id.App_Details_FeaturedTopicButton){
            Intent intent = new Intent(this, UsesFeatures_Activity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.App_Details_DexClassButton){
            Intent intent = new Intent(this, DexClass_Activity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.App_Details_ManifestButton){
            Intent intent = new Intent(this, Manifest_Activity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.App_Details_PermissionButton){
            Intent intent = new Intent(this, Permission_Activity.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.App_Details_Extract_Button){
            ExtractAPK(view);
        }
    }

    private void ExtractAPK(View view){
        if(!runWriteStoragePermission()){
            return;
        }
        final TextView extractBtnTextV = view.findViewById(R.id.extractBtnTextV);

        boolean[] vals = Utility.getSettingApkFileSaveFormat(this);
        boolean isPackageName = vals[0];
        boolean isVersionNo = vals[1];
        boolean isVersionCode = vals[2];
        boolean isDate = vals[3];

        String currentDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.US).format(Calendar.getInstance().getTime()) + "_";

        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(packageInfoItem.AppName);
        fileNameBuilder.append(isPackageName ? packageInfoItem.PackageName + "_": "");
        fileNameBuilder.append(isVersionNo ? packageFullDetails.getApkMeta().getVersionName() + "_" : "");
        fileNameBuilder.append(isVersionCode ? packageFullDetails.getApkMeta().getVersionCode() + "_": "");
        fileNameBuilder.append(isDate ? currentDate : "");
        fileNameBuilder.append(".apk");

        popManager = new PopManager(this, packageFullDetails.getFile(), Utility.extractDirectory(), fileNameBuilder.toString(), new PopManager.ProgressCallBack() {
            @Override
            public void onStart() {
                progressBar.setVisibility(View.VISIBLE);
                extractBtnTextV.setText("Extracting..");
            }

            @Override
            public void onProgress(PopManager.CallbackByteChannel cbc, double progress) {
                progressBar.setProgress((int)progress);
            }

            @Override
            public void onStop() {
                extractBtnTextV.setText("Extract");
                progressBar.setVisibility(View.GONE);
            }
        });
        popManager.ExtractApk();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == WRITE_EXTERNAL_STORAGE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted, you can extract apk now :)", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Permission Denied. You can enable from app permission setting", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean runWriteStoragePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
            return false;
        }
    }
}