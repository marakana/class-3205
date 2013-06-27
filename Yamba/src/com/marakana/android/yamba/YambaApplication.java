package com.marakana.android.yamba;

import com.marakana.android.yamba.clientlib.YambaClient;

import android.app.Application;

public class YambaApplication extends Application {
	
	private static YambaClient yambaClient;

	public static synchronized YambaClient getYambaClient() {
		if (null == yambaClient) {
			String user = "student";
			String password = "password";
			yambaClient = new YambaClient(user, password);
		}
		return yambaClient;
	}

}
