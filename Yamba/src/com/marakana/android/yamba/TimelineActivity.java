package com.marakana.android.yamba;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class TimelineActivity extends BaseActivity 
		implements LoaderManager.LoaderCallbacks<Cursor> {
	
	private SimpleCursorAdapter adapter;
	
	private static final String[] FROM = {
		StatusContract.Columns.USER,
		StatusContract.Columns.MESSAGE
	};
	private static final int[] TO = {
		android.R.id.text1,
		android.R.id.text2
	};
	
	private static final int TIMELINE_LOADER = 42;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, null, FROM, TO, 0);
		ListView listTimeline = (ListView) findViewById(R.id.list_timeline);
		listTimeline.setAdapter(adapter);
		
		getLoaderManager().initLoader(TIMELINE_LOADER, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getApplicationContext(), StatusContract.CONTENT_URI,
					null, null, null, StatusContract.Columns.DEFAULT_SORT_ORDER);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
		adapter.swapCursor(c);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}
	
}
