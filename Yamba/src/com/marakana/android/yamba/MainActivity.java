package com.marakana.android.yamba;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button buttonSubmit;
    private EditText editMsg;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) Log.v(TAG, "onCreate() invoked");
        
        setContentView(R.layout.activity_main);
        
        // Look up the user text entry field
        editMsg = (EditText) findViewById(R.id.edit_message);
        
        // Register our button click listener
        buttonSubmit = (Button) findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener( this );
        
/*        buttonSubmit.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (BuildConfig.DEBUG) Log.v(TAG, "Button clicked");
			}
		});
*/
        }

	@Override
	protected void onStart() {
		super.onStart();
	    if (BuildConfig.DEBUG) Log.v(TAG, "onStart() invoked");
	}

	@Override
	protected void onResume() {
		super.onResume();
	    if (BuildConfig.DEBUG) Log.v(TAG, "onResume() invoked");
	}

	@Override
	protected void onPause() {
		super.onPause();
	    if (BuildConfig.DEBUG) Log.v(TAG, "onPause() invoked");
	}

	@Override
	protected void onStop() {
		super.onStop();
	    if (BuildConfig.DEBUG) Log.v(TAG, "onStop() invoked");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	    if (BuildConfig.DEBUG) Log.v(TAG, "onRestart() invoked");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (BuildConfig.DEBUG) Log.v(TAG, "onDestroy() invoked");
	}
	
	@Override
	public void onClick(View v) {
		if (BuildConfig.DEBUG) Log.v(TAG, "Button clicked");
		int id = v.getId();
		switch (id) {
		case R.id.button_submit:
			// The Submit button was clicked
			String msg = editMsg.getText().toString();
			Log.i(TAG, "User entered: " + msg);
			break;
		default:
			// Unknown button? We shouldn't be here!
			Log.w(TAG, "An unknown button was clicked!");
		}
	}

    
}
