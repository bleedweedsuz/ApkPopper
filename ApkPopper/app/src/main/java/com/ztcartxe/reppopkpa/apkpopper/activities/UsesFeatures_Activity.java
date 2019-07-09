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

import net.dongliu.apk.parser.bean.UseFeature;

import java.util.ArrayList;

public class UsesFeatures_Activity extends AppCompatActivity {
    Toolbar toolbar;
    PackageFullDetails packageFullDetails;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uses_features);

        //Changing Status Bar Color
        Utility.setLightStatusBar(getWindow().getDecorView().getRootView(),this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rec_featurelists);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new FeaturesAdapter(LoadFeatures());
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

    private ArrayList<String> LoadFeatures(){
        packageFullDetails = new PackageFullDetails(AppDetails_Activity.packageInfoItem);
        ArrayList<String> list = new ArrayList<>();
        for(UseFeature useFeature : packageFullDetails.getApkMeta().getUsesFeatures()){
            list.add(useFeature.getName());
        }
        return list;
    }

    public class FeaturesAdapter extends RecyclerView.Adapter<FeaturesAdapter.MyViewHolder> {
        ArrayList<String> mList;

        FeaturesAdapter(ArrayList<String> mList) {
            this.mList = mList;
        }

        @Override
        public FeaturesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_2, parent, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
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
