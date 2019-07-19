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
package com.ztcartxe.reppopkpa.apkpopper.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.view.View;

import com.ztcartxe.reppopkpa.apkpopper.R;

public class Utility {
    private static final String TAG = "Utility";

    public static int totalGridSize = 4;
    public static int deviceScreenType = 0;//0=>Mid Screen Size, 1=>Large Screen Size

    //Status Bar Color Change To Light
    public static void setLightStatusBar(View view, Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    public static String extractDirectory(){
        return  Environment.getExternalStorageDirectory().getAbsolutePath() + "/apk_popper/";
    }

    public static void setSettingGridSize(Context context, int val){
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.key_preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(context.getString(R.string.key_preference_file_key_Grid), val);
            editor.apply();

            if(deviceScreenType == 0){ totalGridSize = val == 0 ? 4 : 3;}
            else if(deviceScreenType == 1){ totalGridSize = val == 0 ? 8 : 6;}
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static int getSettingGridSize(Context context){
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.key_preference_file_key), Context.MODE_PRIVATE);
            return sharedPreferences.getInt(context.getString(R.string.key_preference_file_key_Grid), 0);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return 0;
        }
    }

    public static int getSettingGridSizeData(Context context){
        int v = getSettingGridSize(context);
        if(deviceScreenType == 0){ return v == 0 ? 4 : 3;} else{ return v == 0 ? 8 : 6;}
    }

    public static void setSettingApkFileSaveFormat(Context context, boolean[] vals){
        try {
            //0=packagename, 1=version_no, 2=version_code, 3=date
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.key_preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(context.getString(R.string.key_preference_file_key_packageName), vals[0]);
            editor.putBoolean(context.getString(R.string.key_preference_file_key_versionNo), vals[1]);
            editor.putBoolean(context.getString(R.string.key_preference_file_key_versionCode), vals[2]);
            editor.putBoolean(context.getString(R.string.key_preference_file_key_date), vals[3]);
            editor.apply();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static boolean[] getSettingApkFileSaveFormat(Context context){
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.key_preference_file_key), Context.MODE_PRIVATE);
            return new boolean[]{
                    sharedPreferences.getBoolean(context.getString(R.string.key_preference_file_key_packageName), false),
                    sharedPreferences.getBoolean(context.getString(R.string.key_preference_file_key_versionNo), false),
                    sharedPreferences.getBoolean(context.getString(R.string.key_preference_file_key_versionCode), false),
                    sharedPreferences.getBoolean(context.getString(R.string.key_preference_file_key_date), false)
                };
        }
        catch (Exception ex){
            return new boolean[]{false, false, false, false};
        }
    }

    public static void setMainIntroVals(Context context, boolean val){
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.key_preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(context.getString(R.string.key_preference_file_key_mainIntro), val);
            editor.apply();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static boolean getMainIntroValStatus(Context context){
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.key_preference_file_key), Context.MODE_PRIVATE);
            return sharedPreferences.getBoolean(context.getString(R.string.key_preference_file_key_mainIntro), false);
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public static void setDeviceScreenType(Context context){
        if((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE){
            deviceScreenType = 1;
        }
        else{
            deviceScreenType = 0;
        }
    }
}