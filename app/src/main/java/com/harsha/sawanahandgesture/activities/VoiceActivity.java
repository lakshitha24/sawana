package com.harsha.sawanahandgesture.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.harsha.sawanahandgesture.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import android.view.ViewGroup.LayoutParams;
import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;

import static edu.cmu.pocketsphinx.SpeechRecognizerSetup.defaultSetup;

/**
 * Created by jaya on 9/3/2016.
 */
public class VoiceActivity extends Activity implements RecognitionListener {
    SpeechRecognizer recognizer;
    TextView recognizer_state, recognized_word;
     Button click_btn,suggestion_btn;
    int conteo = 0;
    int permiso_flag=0;
    Handler a = new Handler();
    ArrayList<String> arr = new ArrayList<String>(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_main_activity);

        recognizer_state = (TextView) findViewById(R.id.textView2);
        recognized_word = (TextView) findViewById(R.id.textView3);
        click_btn = (Button) findViewById(R.id.buttonstart);
        suggestion_btn=(Button)findViewById(R.id.buttonsug);

        click_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v1) {
                FireRecognition();
                new CountDownTimer(6000, 1000) {
                    public void onTick(long millisUntilFinished) {
                       }

                    public void onFinish() {

                        suggestion_btn.setVisibility(View.VISIBLE);
                       recognizer.stop();
                    }
                }.start();
            }
        });
        suggestion_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getBaseContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = layoutInflater.inflate(R.layout.popup, null);
                LinearLayout jkl = (LinearLayout) popupView.findViewById(R.id.test);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                final int N = 10;
                final TextView[] myTextViews = new TextView[N]; // create an empty array;

                for (String string : arr) {
                    // create a new textview
                    final TextView rowTextView = new TextView(VoiceActivity.this);

                    // set some properties of rowTextView or something
                    rowTextView.setText(string);
                    rowTextView.setTextSize(20);
                    rowTextView.setPadding(10,0, 10,20);

                    rowTextView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String text = new String(rowTextView.getText().toString());
                            Intent intent = new Intent(VoiceActivity.this, ModelRender.class);
                            intent.putExtra("keyName",text);
                            startActivity(intent);
                        }
                    });

                    // add the textview to the linearlayout
                    jkl.addView(rowTextView);

                    // save a reference to the textview for later
                   // myTextViews[i] = rowTextView;
                }

                Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
                btnDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {

                        popupWindow.dismiss();
                    }});

                popupWindow.showAsDropDown(suggestion_btn, 50, -30);
            }

        });


        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(getApplicationContext());
                    File assetDir = assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                } else {
                   // FireRecognition();
                }
            }
        }.execute();

    }

    @Override
    public void onStop(){
        super.onStop();
        recognizer.removeListener(this);
    }


    public void FireRecognition(){
        Log.d("Recognition","Recognition Started");
        recognizer_state.setText("Recognition Started!");
        conteo = 0;
        recognizer.stop();
        recognizer.startListening("digits");
    }

    @Override
    public void onBeginningOfSpeech() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onEndOfSpeech() {
        // TODO Auto-generated method stub

    }

    private void setupRecognizer(File assetsDir) {
        File modelsDir = new File(assetsDir, "models");
        recognizer = defaultSetup()
                .setAcousticModel(new File(modelsDir, "hmm/en-us-semi"))
                .setDictionary(new File(modelsDir, "dict/cmu07a.dic"))
                .setRawLogDir(assetsDir).setKeywordThreshold(1e-40f)
                .getRecognizer();
        recognizer.addListener(this);


        File digitsGrammar = new File(modelsDir, "grammar/digits.gram");
        recognizer.addGrammarSearch("digits", digitsGrammar);

    }

    @Override
    public void onResult(Hypothesis hup) {
        conteo +=1;
        if(conteo==1){
            //recognizer.stop();
            Timer();
        }
    }

    public void Timer(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    a.post(new Runnable() {
                        @Override
                        public void run() {

                            FireRecognition();
                        }
                    });
                } catch (Exception e) {
                }
            }

        }).start();
    }

    @Override
    public void onPartialResult(Hypothesis arg0) {
        if(arg0 == null){ return; }
        String comando = arg0.getHypstr();
       // results(comando);
        arr.add(comando);
        recognized_word.setText(comando);
         conteo +=1;
        if(conteo==1){
            conteo = 0;
            Log.d("Res", comando);
            recognizer.stop();
            Timer();
        }

    }

    public  void results(String command){
        //	arr.add(command);
        String text=command;
        Log.d("Res", "ssssssssssss"+ text);
        if("hello".equals(text)) {
            arr.add(text);
            if("hello".equals(arr.get(0))){
                recognizer.stop();


            }
        }
        else{

        }
    }

}
