package com.marakana.android.yamba;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (BuildConfig.DEBUG)
			Log.v(TAG, "onCreate() invoked");

		setContentView(R.layout.activity_main);
	}

}
