package com.harsha.sawanahandgesture.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.harsha.sawanahandgesture.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by harsha_m on 8/3/2016.
 */

public class SplashActivity extends Activity {

    final String TAG = SplashActivity.this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // Remove title bar
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            // Remove notification bar
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_splash);

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                            finish();
                        }
                    });
                }
            }, 2000);

        } catch (Exception ex) {
            Log.e(TAG, "onCreate: " + ex.toString());
        }
    }
}
