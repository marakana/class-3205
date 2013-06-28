package com.marakana.android.yamba;

import android.app.Fragment;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailFragment extends Fragment {
	
	private static final String KEY_ID = "KEY_ID";

	private static final String TAG = "DetailsFragment";
	
	private TextView statusUser;
	private TextView statusDate;
	private TextView statusMessage;

	public static DetailFragment newInstance(long id) {
		DetailFragment f = new DetailFragment();

		Bundle args = new Bundle();
		args.putLong(KEY_ID, id);
		f.setArguments(args);
		
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View top = inflater.inflate(R.layout.fragment_detail, container, false);
		
		statusUser = (TextView) top.findViewById(R.id.status_user);
		statusDate = (TextView) top.findViewById(R.id.status_date);
		statusMessage = (TextView) top.findViewById(R.id.status_message);
		
		Bundle args = getArguments();
		if (null != args) {
			showStatus(args.getLong(KEY_ID));
		}
		
		return top;
	}
	
	public void showStatus(long id) {
		Uri uri = ContentUris.withAppendedId(StatusContract.CONTENT_URI, id);
		Cursor c = null;
		try {
			c = getActivity().getContentResolver()
							.query(uri, null, null, null, null);
			if (c.moveToFirst()) {
				String user = c.getString(c.getColumnIndex(StatusContract.Columns.USER));
				String message = c.getString(c.getColumnIndex(StatusContract.Columns.MESSAGE));
				long timestamp = c.getLong(c.getColumnIndex(StatusContract.Columns.CREATED_AT));
				CharSequence relTime = DateUtils.getRelativeTimeSpanString(timestamp);
				
				statusUser.setText(user);
				statusDate.setText(relTime);
				statusMessage.setText(message);
			}
		} catch (Exception e) {
			Log.w(TAG, "Problem accessing content provider", e);
		} finally {
			try {
				if (null != c) c.close();
			} catch (Exception e) {}
		}
	}
	
	
}
