package com.pTricKg.PhoneVolumeToggle;

/*RingerToggle
Copyright (C) 2014 Patrick Gorman

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.*/

import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.widget.RemoteViews;

/* why i cannot figure this out is beyond me! */

public class AppWidget extends AppWidgetProvider {

	private static final String TAG = "AppWidget";

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.e(TAG, "onReceive");
		
		if (intent.getAction() == null) {
			context.startService(new Intent(context, ToggleService.class));

		} else {
			super.onReceive(context, intent);

		}
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		Log.e(TAG, "onUpdate");

		context.startService(new Intent(context, ToggleService.class));

	}

	public static class ToggleService extends IntentService {

		public ToggleService() {
			super("AppWidget$ToggleService");

		}

		@Override
		protected void onHandleIntent(Intent intent) {

			Log.e(TAG, "onHandleIntent");

			ComponentName me = new ComponentName(this, AppWidget.class);
			AppWidgetManager mgr = AppWidgetManager.getInstance(this);
			mgr.updateAppWidget(me, buildUpdate(this));

		}

		private RemoteViews buildUpdate(Context context) {

			Log.e(TAG, "buildUpdate");

			RemoteViews updateViews = new RemoteViews(context.getPackageName(),
					R.layout.widget);

			AudioManager audioManager = (AudioManager) context
					.getSystemService(Activity.AUDIO_SERVICE);

			if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
				updateViews.setImageViewResource(R.id.phoneState,
						R.drawable.phone_on);
				//silencePhone();
				audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			} else if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL) {
				updateViews.setImageViewResource(R.id.phoneState,
						R.drawable.phone_vibrate);

				audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
			} else {
				updateViews.setImageViewResource(R.id.phoneState,
						R.drawable.phone_silent);
				silencePhone();
				//audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
			}
			
			Intent i = new Intent(this, AppWidget.class);

			PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
			updateViews.setOnClickPendingIntent(R.id.phoneState, pi);
			return updateViews;

		}
		
		private void silencePhone() {
	        setPriorityAndSilence();
	        new Thread( new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    Thread.sleep(500);
	                } catch( InterruptedException e ) {

	                }

	                setPriorityAndSilence();

	            }
	        } ).run();
	    }

	    private void setPriorityAndSilence() {
	        AudioManager audioManager;
	        audioManager = (AudioManager) getBaseContext().getSystemService( Context.AUDIO_SERVICE );
	        audioManager.setRingerMode( AudioManager.RINGER_MODE_SILENT );
	    }
	}

}
