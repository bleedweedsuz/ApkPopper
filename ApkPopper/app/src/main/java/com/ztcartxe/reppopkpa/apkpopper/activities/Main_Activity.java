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

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.ztcartxe.reppopkpa.apkpopper.engine.PackageInfoManager;
import com.ztcartxe.reppopkpa.apkpopper.R;
import com.google.android.material.tabs.TabLayout;

import com.ztcartxe.reppopkpa.apkpopper.adapter.ViewPagerAdapter;
import com.ztcartxe.reppopkpa.apkpopper.fragments.Fragment_MyInstalledApps;
import com.ztcartxe.reppopkpa.apkpopper.fragments.Fragment_Settings;
import com.ztcartxe.reppopkpa.apkpopper.fragments.Fragment_SystemApps;
import com.ztcartxe.reppopkpa.apkpopper.model.PackageInfoItem;
import com.ztcartxe.reppopkpa.apkpopper.utils.Utility;

import java.util.ArrayList;
import java.util.Collections;

public class Main_Activity extends AppCompatActivity {

    private static final String TAG = "Main_Activity";

    int currentSelectedTab;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TextView textToolbarTitle;
    LinearLayout refreshButtonHolder;
    float px;
    boolean translate_flag = false;
    Fragment_SystemApps fragment_systemApps;
    Fragment_MyInstalledApps fragment_myInstalledApps;
    Fragment_Settings fragment_settings;
    ProgressBar progressBar;
    boolean isDataLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utility.setDeviceScreenType(this);


        //Set Grid Size
        Utility.totalGridSize = Utility.getSettingGridSizeData(this);

        toolbar = findViewById(R.id.activityMainToolBar);
        tabLayout = findViewById(R.id.activityMainTabLayout);
        viewPager = findViewById(R.id.activityMainViewPager);
        textToolbarTitle = findViewById(R.id.activityMainToolbarTitle);
        refreshButtonHolder = findViewById(R.id.activityMainRefreshButtonHolder);
        progressBar = findViewById(R.id.progressBar);

        currentSelectedTab = 0;

        //Changing Status Bar Color
        Utility.setLightStatusBar(getWindow().getDecorView().getRootView(),this);
        //Toolbar Refresh Button
        float density = this.getResources().getDisplayMetrics().density;
        px = 47 * density;

        textToolbarTitle.setText(R.string.tab1_all_apps);

        //Load Apps Lists
        //LoadAppsLists();

        InitLayout();

        LoadAppsLists();
    }

    private void LoadAppsLists(){
        PackageInfoManager packageInfoManager = new PackageInfoManager(this);
        packageInfoManager.getPackages(new PackageInfoManager.PackageListener() {
            @Override
            public void onStart() {
                progressBar.setVisibility(View.VISIBLE);
                isDataLoading = true;
            }

            @Override
            public void onProgress(int progress) {
                progressBar.setProgress(progress);
            }

            @Override
            public void onFinished(ArrayList<PackageInfoItem> systemPackageInfoItemArrayList, ArrayList<PackageInfoItem> installedPackageInfoItemArrayList) {
                try {
                    isDataLoading = false;
                    progressBar.setVisibility(View.GONE);

                    //Sort Lists
                    Collections.sort(systemPackageInfoItemArrayList);
                    Collections.sort(installedPackageInfoItemArrayList);

                    fragment_systemApps.setSystemPackageInfoItemArrayList(systemPackageInfoItemArrayList);
                    fragment_myInstalledApps.setInstalledPackageInfoItemArrayList(installedPackageInfoItemArrayList);

                    tabLayout.getTabAt(currentSelectedTab).select();
                }
                catch (Exception ex){
                    Log.e(TAG, ex.toString());
                    ex.printStackTrace();
                }
            }
        });
    }

    public void ReloadAppLists(View view){
        try{
            currentSelectedTab = tabLayout.getSelectedTabPosition();
            LoadAppsLists();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void InitLayout(){
        fragment_systemApps = new Fragment_SystemApps();
        fragment_myInstalledApps = new Fragment_MyInstalledApps();

        fragment_settings = new Fragment_Settings();
        fragment_settings.setFragment_systemApps(fragment_systemApps);
        fragment_settings.setFragment_myInstalledApps(fragment_myInstalledApps);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(fragment_systemApps);
        viewPagerAdapter.addFragment(fragment_myInstalledApps);
        viewPagerAdapter.addFragment(fragment_settings);

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);

        //TabLayout Setup
        tabLayout.setupWithViewPager(viewPager);
        try {
            TabLayout.Tab tabCall = tabLayout.getTabAt(0);
            tabCall.setIcon(R.drawable.icon_system_apps);
            TabLayout.Tab tabCall2 = tabLayout.getTabAt(1);
            tabCall2.setIcon(R.drawable.icon_installed);
            TabLayout.Tab tabCall3 = tabLayout.getTabAt(2);
            tabCall3.setIcon(R.drawable.icon_setting);
        }
        catch (NullPointerException ex){
            ex.printStackTrace();
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    progressBar.setVisibility(isDataLoading?View.VISIBLE:View.GONE);
                    currentSelectedTab = 0;
                    textToolbarTitle.setText(R.string.tab1_all_apps);
                    if(translate_flag) {
                        ObjectAnimator transAnimation = ObjectAnimator.ofFloat(refreshButtonHolder, "x", px, 0);
                        transAnimation.setDuration(100);//set duration
                        transAnimation.start();
                        translate_flag = false;
                    }
                }
                else if(tab.getPosition() == 1){
                    progressBar.setVisibility(isDataLoading?View.VISIBLE:View.GONE);
                    currentSelectedTab = 1;
                    textToolbarTitle.setText(R.string.tab2_installed_apps);
                    if(translate_flag) {
                        ObjectAnimator transAnimation = ObjectAnimator.ofFloat(refreshButtonHolder, "x", px, 0);
                        transAnimation.setDuration(100);//set duration
                        transAnimation.start();
                        translate_flag = false;
                    }
                }
                else if(tab.getPosition() == 2){
                    progressBar.setVisibility(View.GONE);
                    currentSelectedTab = 2;
                    textToolbarTitle.setText(R.string.tab3_setting);
                    if(!translate_flag) {
                        ObjectAnimator transAnimation = ObjectAnimator.ofFloat(refreshButtonHolder, "x", 0, px);
                        transAnimation.setDuration(100);//set duration
                        transAnimation.start();
                        translate_flag = true;
                    }
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}