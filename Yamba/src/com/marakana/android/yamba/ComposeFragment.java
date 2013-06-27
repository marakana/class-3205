package com.marakana.android.yamba;

import java.lang.ref.WeakReference;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.marakana.android.yamba.clientlib.YambaClientException;

public class ComposeFragment extends Fragment {

	private static final String TAG = "ComposeFragment";

	private Button buttonSubmit;
	private EditText editMsg;
	private Toast toast;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRetainInstance(true);
	}

	@SuppressLint("ShowToast")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_compose, container,
				false);
		// Look up the user text entry field
		editMsg = (EditText) view.findViewById(R.id.edit_message);

		// Register our button click listener
		buttonSubmit = (Button) view.findViewById(R.id.button_submit);
		buttonSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (BuildConfig.DEBUG)
					Log.v(TAG, "Button clicked");
				postStatus();
			}
		});

		// Create and cache a Toast for reuse
		toast = Toast.makeText(getActivity().getApplicationContext(), null,
				Toast.LENGTH_LONG);

		return view;
	}

	private void postStatus() {
		// The Submit button was clicked
		String msg = editMsg.getText().toString();
		Log.i(TAG, "User entered: " + msg);

		// Clear the EditText content
		editMsg.setText("");

		// Post the message -- as long as there is a message to post
		if (!TextUtils.isEmpty(msg)) {
			new PostStatusTask(this).execute(msg);
		}
	}
	
	public void reportPostResult(int result) {
		toast.setText(result);
		toast.show();
	}

	private static class PostStatusTask extends AsyncTask<String, Void, Integer> {
		
		private WeakReference<ComposeFragment> fragmentRef;
		public PostStatusTask(ComposeFragment fragment) {
			super();
			fragmentRef = new WeakReference<ComposeFragment>(fragment);
		}

		@Override
		protected Integer doInBackground(String... params) {
			int resultMsg = R.string.post_status_fail;
			try {
				// Post the new status message
				YambaApplication.getYambaClient().postStatus(params[0]);
				resultMsg = R.string.post_status_success;
			} catch (YambaClientException e) {
				Log.e(TAG, "Unable to post status update", e);
			}
			return resultMsg;
		}

		@Override
		protected void onPostExecute(Integer result) {
			ComposeFragment fragment = fragmentRef.get();
			if (null != fragment) {
				fragment.reportPostResult(result);
			}
			else {
				if (BuildConfig.DEBUG)
					Log.d(TAG, "Compose fragment destroyed before status post completed");
			}
		}
	}
}
