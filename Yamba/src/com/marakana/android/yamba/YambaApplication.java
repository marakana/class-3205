package com.marakana.android.yamba;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

import com.marakana.android.yamba.clientlib.YambaClient;

public class YambaApplication extends Application {
	
	private static YambaClient yambaClient;
	private static final Object clientLock = new Object();
	
	private static SharedPreferences prefs;
	private AppPreferenceListener prefListener;
	
	private static final String prefKeyUser = "PREF_KEY_USER";
	private static final String prefKeyPassword = "PREF_KEY_PASSWORD";
	private static final Set<String> yambaClientPrefKeys;

	private static final String TAG = "YambaApplication";
	
	static {
		Set<String> keys = new HashSet<String>();
		keys.add(prefKeyUser);
		keys.add(prefKeyPassword);
		yambaClientPrefKeys = Collections.unmodifiableSet(keys);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefListener = new AppPreferenceListener();
		prefs.registerOnSharedPreferenceChangeListener(prefListener);
	}

	public static YambaClient getYambaClient() {
		synchronized (clientLock) {
			if (null == yambaClient) {
				// Create a YambaClient based on current preference values.
				String user = prefs.getString(prefKeyUser, null);
				String password = prefs.getString(prefKeyPassword, null);
				yambaClient = new YambaClient(user, password);
			}
			return yambaClient;
		}
	}

	// Because SharedPreferences maintains a *WeakReference* to it's listeners, we run
	// into trouble using an anonymous local class instance as a listener.
	// Here's an explicit class. We'll keep a reference to the listener object in
	// an instance field.
	private class AppPreferenceListener implements OnSharedPreferenceChangeListener {
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
				String key) {
			if (BuildConfig.DEBUG) Log.d(TAG, "Preference changed: " + key);
			// If any of the preferences related to the YambaClient change,
			// discard any existing YambaClient.
			if (yambaClientPrefKeys.contains(key)) {
				synchronized (clientLock) {
					yambaClient = null;
				}
			}
		}
	}

}
