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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ztcartxe.reppopkpa.apkpopper.R;
import com.ztcartxe.reppopkpa.apkpopper.utils.Utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ApkFileSaveFormat_Activity extends AppCompatActivity {
    Toolbar toolbar;
    TextView formatStr;
    Switch PackageName_Switch, VersionNo_Switch, VersionCode_Switch, Date_Switch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apk_file_save_format);
        Utility.setLightStatusBar(getWindow().getDecorView().getRootView(),this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        formatStr = findViewById(R.id.apk_file_save_format_string);

        boolean[] vals = Utility.getSettingApkFileSaveFormat(this);

        PackageName_Switch = findViewById(R.id.PackageName_Switch);
        PackageName_Switch.setChecked(vals[0]);
        PackageName_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {setFormatSetting();}
        });

        VersionNo_Switch  = findViewById(R.id.VersionNo_Switch);
        VersionNo_Switch.setChecked(vals[1]);
        VersionNo_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {setFormatSetting();}
        });

        VersionCode_Switch  = findViewById(R.id.VersionCode_Switch);
        VersionCode_Switch.setChecked(vals[2]);
        VersionCode_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {setFormatSetting();}
        });

        Date_Switch  = findViewById(R.id.Date_Switch);
        Date_Switch.setChecked(vals[3]);
        Date_Switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {setFormatSetting();}
        });

        makeFormatSample(vals);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void makeFormatSample(boolean[] vals){
        boolean isPackageName = vals[0];
        boolean isVersionNo = vals[1];
        boolean isVersionCode = vals[2];
        boolean isDate = vals[3];

        String currentDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.US).format(Calendar.getInstance().getTime()) + "_";

        StringBuilder builder = new StringBuilder();
        builder.append("appname_");
        builder.append(isPackageName ? "com.example.appname_" : "");
        builder.append(isVersionNo ? "1.0.x_" : "");
        builder.append(isVersionCode ? "42085x_" : "");
        builder.append(isDate ? currentDate : "");
        builder.append(".apk");

        formatStr.setText(builder.toString());
    }

    public void setFormatSetting(){
        boolean vals[] = {PackageName_Switch.isChecked(), VersionNo_Switch.isChecked(), VersionCode_Switch.isChecked(), Date_Switch.isChecked()};
        Utility.setSettingApkFileSaveFormat(this, vals);
        makeFormatSample(vals);
    }
}
