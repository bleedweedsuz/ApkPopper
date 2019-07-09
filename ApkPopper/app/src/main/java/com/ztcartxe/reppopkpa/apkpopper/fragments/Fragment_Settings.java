package com.ztcartxe.reppopkpa.apkpopper.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.ztcartxe.reppopkpa.apkpopper.utils.Utility;

public class Fragment_Settings extends Fragment implements View.OnClickListener {

    private TextView text_folderPath, text_appVersion;
    private AlertDialog alertDialog;
    private TextView gridSizeTextView;

    Fragment_MyInstalledApps fragment_myInstalledApps;
    Fragment_SystemApps fragment_systemApps;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings,container,false);

        text_folderPath = view.findViewById(R.id.text_folderPath);
        text_appVersion = view.findViewById(R.id.text_appVersion);
        gridSizeTextView = view.findViewById(R.id.gridSize);
        view.findViewById(R.id.gridSizeBtn).setOnClickListener(this);
        view.findViewById(R.id.apk_file_save_format_btn).setOnClickListener(this);

        view.findViewById(R.id.aboutusBtn).setOnClickListener(this);
        view.findViewById(R.id.licenseBtn).setOnClickListener(this);

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
            CharSequence[] choiceItems = {"4 Tiles", "3 Tiles"};
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
            alertDialog = builder.create();
            alertDialog.show();
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
    }

    public void setFragment_myInstalledApps(Fragment_MyInstalledApps fragment_myInstalledApps) {
        this.fragment_myInstalledApps = fragment_myInstalledApps;
    }

    public void setFragment_systemApps(Fragment_SystemApps fragment_systemApps) {
        this.fragment_systemApps = fragment_systemApps;
    }
}
