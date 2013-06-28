package com.marakana.android.yamba;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class TimelineFragment extends Fragment 
		implements LoaderManager.LoaderCallbacks<Cursor> {

	private SimpleCursorAdapter adapter;

	private static final String[] FROM = { StatusContract.Columns.USER,
			StatusContract.Columns.MESSAGE };
	private static final int[] TO = { android.R.id.text1, android.R.id.text2 };

	private static final int TIMELINE_LOADER = 42;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View top = inflater.inflate(R.layout.fragment_timeline, container, false);
		
		adapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_list_item_2, null, FROM, TO, 0);
		ListView listTimeline = (ListView) top.findViewById(R.id.list_timeline);
		listTimeline.setAdapter(adapter);

		// Wait until *after* you've created the adapter before you init the loader.
		// The onLoadFinished needs an adapter instantiated for it to swap the cursor.
		getLoaderManager().initLoader(TIMELINE_LOADER, null, this);

		return top;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(getActivity().getApplicationContext(),
				StatusContract.CONTENT_URI, null, null, null,
				StatusContract.Columns.DEFAULT_SORT_ORDER);
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
