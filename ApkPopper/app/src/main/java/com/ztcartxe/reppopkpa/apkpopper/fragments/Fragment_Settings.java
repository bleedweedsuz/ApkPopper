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

package com.ztcartxe.reppopkpa.apkpopper.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ztcartxe.reppopkpa.apkpopper.R;
import com.ztcartxe.reppopkpa.apkpopper.activities.Aboutus_Activity;
import com.ztcartxe.reppopkpa.apkpopper.activities.ApkFileSaveFormat_Activity;
import com.ztcartxe.reppopkpa.apkpopper.activities.License_Credits_Activity;
import com.ztcartxe.reppopkpa.apkpopper.activities.MainIntro_Activity;
import com.ztcartxe.reppopkpa.apkpopper.utils.Utility;

public class Fragment_Settings extends Fragment implements View.OnClickListener {

    private TextView text_folderPath, text_appVersion;
    private TextView gridSizeTextView;

    private Fragment_MyInstalledApps fragment_myInstalledApps;
    private Fragment_SystemApps fragment_systemApps;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);

        text_folderPath = view.findViewById(R.id.text_folderPath);
        text_appVersion = view.findViewById(R.id.text_appVersion);
        gridSizeTextView = view.findViewById(R.id.gridSize);
        view.findViewById(R.id.gridSizeBtn).setOnClickListener(this);
        view.findViewById(R.id.apk_file_save_format_btn).setOnClickListener(this);
        view.findViewById(R.id.privacypPolicyBtn).setOnClickListener(this);

        view.findViewById(R.id.aboutusBtn).setOnClickListener(this);
        view.findViewById(R.id.licenseBtn).setOnClickListener(this);
        view.findViewById(R.id.tutorialBtn).setOnClickListener(this);
        view.findViewById(R.id.saveDirectoryPathBtn).setOnClickListener(this);

        gridSizeTextView.setText("Grid Column [" + Utility.getSettingGridSizeData(getContext()) + "]");

        setLocationData();
        return view;
    }

    private void setLocationData(){
        text_folderPath.setText(Utility.extractDirectory());
        try{ text_appVersion.setText(getContext().getPackageManager().getPackageInfo(getContext().getOpPackageName(), 0).versionName); } catch (Exception ex){ ex.printStackTrace();}

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.gridSizeBtn){
            CharSequence[] choiceItems;
            if(Utility.deviceScreenType == 0){
                choiceItems = new CharSequence[]{"4 Tiles", "3 Tiles"};
            }
            else{
                choiceItems = new CharSequence[]{"8 Tiles", "6 Tiles"};
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(getString(R.string.app_grid));
            builder.setSingleChoiceItems(choiceItems, Utility.getSettingGridSize(getContext()), new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Utility.setSettingGridSize(getContext(), i);
                    gridSizeTextView.setText("Grid Column [" + Utility.getSettingGridSizeData(getContext()) + "]");
                    fragment_myInstalledApps.setLayout();
                    fragment_systemApps.setLayout();
                }
            });
            builder.setPositiveButton("Ok", null);
            builder.create().show();
        }
        else if(view.getId() == R.id.apk_file_save_format_btn){
            try {
                getContext().startActivity(new Intent(getContext(), ApkFileSaveFormat_Activity.class));
            }
            catch (NullPointerException ex){
                ex.printStackTrace();
            }
        }
        else if(view.getId() == R.id.aboutusBtn){
            getContext().startActivity(new Intent(getContext(), Aboutus_Activity.class));
        }
        else if(view.getId() == R.id.licenseBtn){
            getContext().startActivity(new Intent(getContext(), License_Credits_Activity.class));
        }
        else if(view.getId() == R.id.tutorialBtn){
            Intent i = new Intent(getContext(), MainIntro_Activity.class);
            i.putExtra("isTutorial", true);
            startActivity(i);
        }
        else if(view.getId() == R.id.privacypPolicyBtn){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://z-warrior.com/apk-popper/privacypolicy.html")));
        }
        else if(view.getId() == R.id.saveDirectoryPathBtn){
            //..
        }
    }

    public void setFragment_myInstalledApps(Fragment_MyInstalledApps fragment_myInstalledApps) {
        this.fragment_myInstalledApps = fragment_myInstalledApps;
    }

    public void setFragment_systemApps(Fragment_SystemApps fragment_systemApps) {
        this.fragment_systemApps = fragment_systemApps;
    }
}
