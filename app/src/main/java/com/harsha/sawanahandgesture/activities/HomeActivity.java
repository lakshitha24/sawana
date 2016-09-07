package com.harsha.sawanahandgesture.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.harsha.sawanahandgesture.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by harsha_m on 9/2/2016.
 */
public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.coordinator_layout_main)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override protected void onStart() {
        super.onStart();
    }

    @Override protected void onStop() {
        super.onStop();
    }
    @OnClick(R.id.hand_gesture) void search() {
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
    }
    @OnClick(R.id.alarm_recognizer) void tearch() {
        startActivity(new Intent(HomeActivity.this, AnalyzeActivity.class));
    }
    @OnClick(R.id.three_d_model) void model() {
        startActivity(new Intent(HomeActivity.this, VoiceActivity.class));
    }

}
