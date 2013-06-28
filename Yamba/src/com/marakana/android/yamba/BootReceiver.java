package com.marakana.android.yamba;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
	public static final int UPDATE_ALARM_PENDING_INTENT = 42;
	private static final int UPDATE_INTERVAL = 60000;

	@Override
	public void onReceive(Context context, Intent intent) {
		// Run the UpdateService on a recurring basis.
		Intent startUpdateService = new Intent(context, UpdateService.class);
		PendingIntent pi = PendingIntent.getService(context,
					UPDATE_ALARM_PENDING_INTENT,
					startUpdateService,
					PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 10, UPDATE_INTERVAL, pi);
	}

}
