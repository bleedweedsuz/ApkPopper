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
package com.ztcartxe.reppopkpa.apkpopper.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import com.ztcartxe.reppopkpa.apkpopper.R;
import com.ztcartxe.reppopkpa.apkpopper.adapter.RecyclerAdapterApps;
import com.ztcartxe.reppopkpa.apkpopper.model.PackageInfoItem;
import com.ztcartxe.reppopkpa.apkpopper.utils.Utility;

import java.util.ArrayList;


public class Fragment_MyInstalledApps extends Fragment {
    private FastScrollRecyclerView recyclerView;
    private RecyclerAdapterApps mAdapter;
    private ArrayList<PackageInfoItem> installedPackageInfoItemArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_installed_apps,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        mAdapter = new RecyclerAdapterApps(getContext(), installedPackageInfoItemArrayList);

        setLayout();

        RecyclerAdapterApps.ItemOffsetDecoration itemDecoration = new RecyclerAdapterApps.ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setAdapter(mAdapter);
        return view;
    }

    public void setInstalledPackageInfoItemArrayList(ArrayList<PackageInfoItem> installedPackageInfoItemArrayList) {
        this.installedPackageInfoItemArrayList = installedPackageInfoItemArrayList;
    }

    public void setLayout(){
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), Utility.totalGridSize));
    }
}