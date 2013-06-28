package com.marakana.android.yamba;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseActivity extends Activity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Let the base class do any menu setup it does
		super.onCreateOptionsMenu(menu);
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		Intent intent;
		switch (id) {
		case R.id.action_compose:
			// Display the Compose Activity
			intent = new Intent(this, ComposeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			return true;
		case R.id.action_timeline:
			// Display the Timeline Activity
			intent = new Intent(this, TimelineActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			return true;
		case R.id.action_settings:
			// Display the Preference Activity
			intent = new Intent(this, PrefsActivity.class);
			startActivity(intent);
			return true;
		case R.id.action_refresh:
			// Start the UpdateService to fetch timeline data
			intent = new Intent(this, UpdateService.class);
			startService(intent);
			return true;
		default:
			// It's not one of ours. Let the base class handle it.
			return super.onOptionsItemSelected(item);
		}
	}

}