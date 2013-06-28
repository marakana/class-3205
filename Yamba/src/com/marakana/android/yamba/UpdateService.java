package com.marakana.android.yamba;

import java.util.List;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

public class UpdateService extends IntentService {

	// Using Java introspection to get a full-qualified classname
	private static final String TAG = UpdateService.class.getName();

	public UpdateService() {
		super(TAG);
		if (BuildConfig.DEBUG) Log.d(TAG, "Service constructed");
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		if (BuildConfig.DEBUG) Log.d(TAG, "onHandleIntent() invoked");

		try {
			List<YambaClient.Status> timeline = YambaApplication.getYambaClient().getTimeline(50);
			
			ContentValues values = new ContentValues();
			for (YambaClient.Status status: timeline) {
				if (BuildConfig.DEBUG) Log.d(TAG, "Status message: " + status.getMessage());
				
				values.put(StatusContract.Columns._ID, status.getId());
				values.put(StatusContract.Columns.USER, status.getUser());
				values.put(StatusContract.Columns.MESSAGE, status.getMessage());
				values.put(StatusContract.Columns.CREATED_AT, status.getCreatedAt().getTime());
				getContentResolver().insert(StatusContract.CONTENT_URI, values);
				
			}
		} catch (YambaClientException e) {
			Log.e(TAG, "Unable to fetch timeline", e);
		} catch (IllegalArgumentException e) {
			Log.e(TAG, "Invalid YambaClient object", e);
		}
	}
}
