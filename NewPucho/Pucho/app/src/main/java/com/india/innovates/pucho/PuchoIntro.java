package com.india.innovates.pucho;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by yash on 28/3/16.
 */
public class PuchoIntro extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.newInstance("Feeds", "Get all the latest, trending posts", R.drawable.screen_one, ContextCompat.getColor(this, R.color.primaryColor)));
        addSlide(AppIntroFragment.newInstance("Answer", "Answer to any questions and earn credits", R.drawable.screen_two, ContextCompat.getColor(this, R.color.primaryColor)));
        addSlide(AppIntroFragment.newInstance("Language", "Ask in any language and get answer in your language", R.drawable.screen_three, ContextCompat.getColor(this, R.color.primaryColor)));

        setFadeAnimation();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}