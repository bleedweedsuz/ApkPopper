package com.ztcartxe.reppopkpa.apkpopper.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.ztcartxe.reppopkpa.apkpopper.R;
import com.ztcartxe.reppopkpa.apkpopper.fragments.Fragments_Tutorial;
import com.ztcartxe.reppopkpa.apkpopper.utils.Utility;

public class MainIntro_Activity extends AppIntro2 {

    private boolean isTutorial = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().getExtras() !=null && getIntent().getExtras().containsKey("isTutorial") && getIntent().getExtras().getBoolean("isTutorial"))
            isTutorial = true;
        else
            if(Utility.getMainIntroValStatus(this)) {
                startActivity(new Intent(this, Main_Activity.class));
                finish();
            }

        showBackButtonWithDone = true;
        showSkipButton(false);
        setProgressButtonEnabled(true);
        showStatusBar(false);
        setIndicatorColor(R.color.colorPrimaryDark, R.color.colorAccent);
        addSlide( new Fragments_Tutorial(0)); //Info About App
        addSlide( new Fragments_Tutorial(1)); //Info About App
        addSlide( new Fragments_Tutorial(2)); //Info About App
        addSlide( new Fragments_Tutorial(3)); //Info About App
        addSlide( new Fragments_Tutorial(4)); //Info About App
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        if(!isTutorial) startActivity(new Intent(this, Main_Activity.class));

        Utility.setMainIntroVals(this, true);
        finish();
    }
}