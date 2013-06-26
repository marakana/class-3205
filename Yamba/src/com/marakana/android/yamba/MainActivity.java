package com.marakana.android.yamba;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button buttonSubmit;
    private EditText editMsg;
    private Toast toast;
    
    private YambaClient client;

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
        
        // Create and cache a Toast for reuse
        toast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_LONG);
        
        // Create a YambaClient object
        client = new YambaClient("student", "password");
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
			
			// Clear the EditText content
			editMsg.setText("");
			
			// Post the message -- as long as there is a message to post
			if (!TextUtils.isEmpty(msg)) {
				new PostStatusTask().execute(msg);
			}
			
			break;
		default:
			// Unknown button? We shouldn't be here!
			Log.w(TAG, "An unknown button was clicked!");
		}
	}
	
	private class PostStatusTask extends AsyncTask<String, Void, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			int resultMsg = R.string.post_status_fail;
			try {
				// Post the new status message
				client.postStatus(params[0]);
				resultMsg = R.string.post_status_success;
			} catch (YambaClientException e) {
				Log.e(TAG, "Unable to post status update", e);
			}
			return resultMsg;
		}

		@Override
		protected void onPostExecute(Integer result) {
			toast.setText(result);
			toast.show();
		}
		
	}

    
}
