package com.marakana.android.yamba;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class TimelineActivity extends Activity {
	
	private Cursor cursor;
	private SimpleCursorAdapter adapter;
	private static final String[] FROM = {
		StatusContract.Columns.USER,
		StatusContract.Columns.MESSAGE
	};
	private static final int[] TO = {
		android.R.id.text1,
		android.R.id.text2
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		cursor = getContentResolver().query(StatusContract.CONTENT_URI,
					null, null, null, StatusContract.Columns.DEFAULT_SORT_ORDER);
		
		adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, FROM, TO, 0);
		ListView listTimeline = (ListView) findViewById(R.id.list_timeline);
		listTimeline.setAdapter(adapter);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		cursor.close();
	}
}
