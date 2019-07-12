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

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

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
        catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
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