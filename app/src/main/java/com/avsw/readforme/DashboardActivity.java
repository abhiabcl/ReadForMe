package com.avsw.readforme;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.ViewGroup;
import android.view.WindowManager;

public class DashboardActivity extends Activity implements TextToSpeech.OnInitListener{

    private static final String TAG = "DashboardActivity";
    private EditText mEditText;
    private Button mReadMeBtn;
    private Spinner mTextLang;
    private Spinner mTextLangType;
    
    private TextToSpeech mTts;
    public Locale[] locales;
    public Locale SpeechLang;
    
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
     
      try{
      setContentView(R.layout.activity_dashboard);

      mEditText = (EditText)findViewById(R.id.edit_text);
      
      mReadMeBtn = (Button)findViewById(R.id.btn_ReadMe);
      mReadMeBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			createTTS();
			sayHello(mEditText.getText().toString());
		}
	});
      }catch(Exception e)
      {
    	  Log.e(TAG, e.getMessage());
      }
      
    }
	
	public void chageTTssetting()
	{
		ComponentName componentToLaunch = new ComponentName(
		        "com.android.settings",
		        "com.android.settings.TextToSpeechSettings");
		Intent intent = new Intent();
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setComponent(componentToLaunch);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
	
	public void createTTS()
	{
		 mTts = new TextToSpeech(this,
	              this  // TextToSpeech.OnInitListener
	              );
	}
	
	@Override
    public void onDestroy() {
        // Don't forget to shutdown!
        if (mTts != null) {
            mTts.stop();
            mTts.shutdown();
        }

        super.onDestroy();
    }

    // Implements TextToSpeech.OnInitListener.
    public void onInit(int status) {
        // status can be either TextToSpeech.SUCCESS or TextToSpeech.ERROR.
        if (status == TextToSpeech.SUCCESS) {
            // Set preferred language to US english.
            // Note that a language may not be available, and the result will indicate this.
            int result = mTts.setLanguage(Locale.US);
            // Try this someday for some interesting results.
            // int result mTts.setLanguage(Locale.FRANCE);
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED) {
               // Lanuage data is missing or the language is not supported.
                Log.e(TAG, "Language is not available.");
            } else {
                // Check the documentation for other possible result codes.
                // For example, the language may be available for the locale,
                // but not for the specified country and variant.

                // The TTS engine has been successfully initialized.
                // Allow the user to press the button for the app to speak again.
            	mReadMeBtn.setEnabled(true);
                // Greet the user.
            	sayHello(mEditText.getText().toString());
            }
        } else {
            // Initialization failed.
            Log.e(TAG, "Could not initialize TextToSpeech.");
        }
    }

    private void sayHello(String pText) {
    	try{
        // Select a random hello.
//        int textLength = pText.length();
//        String hello = mEditText.getText().toString();//HELLOS[RANDOM.nextInt(helloLength)];
        Log.i(TAG, "Text:"+pText);
        mTts.speak(pText,
            TextToSpeech.QUEUE_FLUSH,  // Drop all pending entries in the playback queue.
            null);
    	}catch(Exception e)
    	{
    		Log.e(TAG,e.getMessage());
    	}
    }
	    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_dashboard, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	try{
    		finish();
    	}
    	catch(Exception ex)
    	{
    		Log.e(TAG,ex.getMessage().toString());
    	}
    	return true;
    }
}
