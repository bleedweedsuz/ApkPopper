/*
    <one line to give the program's name and a brief idea of what it does.>
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ztcartxe.reppopkpa.apkpopper.R;
import com.ztcartxe.reppopkpa.apkpopper.engine.PackageFullDetails;
import com.ztcartxe.reppopkpa.apkpopper.utils.Utility;

import java.util.List;

public class Permission_Activity extends AppCompatActivity {
    Toolbar toolbar;
    PackageFullDetails packageFullDetails;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_);

        //Changing Status Bar Color
        Utility.setLightStatusBar(getWindow().getDecorView().getRootView(),this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rec_permission);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerAdapter = new PermissionAdapter(LoadPermissionList());
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<String> LoadPermissionList(){
        packageFullDetails = new PackageFullDetails(AppDetails_Activity.packageInfoItem);
        return packageFullDetails.getApkMeta().getUsesPermissions();
    }

    public class PermissionAdapter extends RecyclerView.Adapter<PermissionAdapter.MyViewHolder> {
        List<String> mList;

        PermissionAdapter(List<String> mList) {
            this.mList = mList;
        }

        @Override
        public PermissionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_2, parent, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.itemTitle.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView itemTitle;
            MyViewHolder(View v) {
                super(v);
                itemTitle = v.findViewById(R.id.itemName);
            }
        }
    }
}
