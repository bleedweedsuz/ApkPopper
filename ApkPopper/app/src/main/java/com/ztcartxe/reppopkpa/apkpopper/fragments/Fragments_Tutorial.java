package com.ztcartxe.reppopkpa.apkpopper.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ztcartxe.reppopkpa.apkpopper.R;

public class Fragments_Tutorial extends Fragment {
    private int index;
    public Fragments_Tutorial(int index){
        this.index = index;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(this.index == 0){
            return inflater.inflate(R.layout.fragment_app_intro_0, null, false);
        }
        else if(this.index == 1){
            return inflater.inflate(R.layout.fragment_app_intro_1, null, false);
        }
        else if(this.index == 2){
            return inflater.inflate(R.layout.fragment_app_intro_2, null, false);
        }
        else if(this.index == 3){
            return inflater.inflate(R.layout.fragment_app_intro_3, null, false);
        }
        else if(this.index == 4){
            return inflater.inflate(R.layout.fragment_app_intro_4, null, false);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
