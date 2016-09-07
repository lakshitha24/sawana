package com.harsha.sawanahandgesture.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.harsha.sawanahandgesture.R;

/**
 * Created by jaya on 9/3/2016.
 */
public class ModelRender  extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.model_render);
        String data = getIntent().getExtras().getString("keyName");
        final String[] splitStr = data.split("\\s+");
        final VideoView videoview = (VideoView) findViewById(R.id.videoView);
         Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/"+ splitStr[0] );
          videoview.setVideoURI(uri);
        videoview.setMediaController(null);
        videoview.requestFocus();
        videoview.start();
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Uri uri = Uri.parse("android.resource://" + getPackageName() + "/raw/" + splitStr[1]);
                    videoview.setVideoURI(uri);
                    videoview.start();

                }
            });
 }
}
