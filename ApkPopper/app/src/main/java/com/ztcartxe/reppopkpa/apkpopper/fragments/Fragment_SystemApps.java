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

public class Fragment_SystemApps extends Fragment {

    FastScrollRecyclerView recyclerView;
    RecyclerAdapterApps mAdapter;
    ArrayList<PackageInfoItem> systemPackageInfoItemArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_system_apps,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        mAdapter = new RecyclerAdapterApps(getContext(), systemPackageInfoItemArrayList);

        setLayout();

        RecyclerAdapterApps.ItemOffsetDecoration itemDecoration = new RecyclerAdapterApps.ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    public void setSystemPackageInfoItemArrayList(ArrayList<PackageInfoItem> systemPackageInfoItemArrayList) {
        this.systemPackageInfoItemArrayList = systemPackageInfoItemArrayList;
    }

    public void setLayout(){
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), Utility.totalGridSize));
    }
}