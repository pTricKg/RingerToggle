package com.pTricKg.PhoneVolumeToggle;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/* why i cannot figure this out is beyond me! */

public class PhoneVolumeToggleActivity extends Activity {

	private static final String TAG = "PhoneVolumeToggleActivity";

	private AudioManager mAudioManager;
	private boolean mPhoneIsSilent;
	private boolean mPhoneIsVibrate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.e(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// initializing AudioManager variable
		mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

		// initializing Ringer State check
		checkIfPhoneIsSilent();

		// initialize button click listener.
		// place here to allow ringer state check first, I think!
		setButtonClickListener();
	}
	
	// new method test for timer functionality
	public void startTimer(View view) {
		EditText text = (EditText) findViewById(R.id.editText1);
		int i = Integer.parseInt(text.getText().toString());
		Intent intent = new Intent(this, MyBroadcastReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 234324243, intent, 0);
	    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
	    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
	        + (i * 1000), pendingIntent);
	    Toast.makeText(this, "You've set alarm for " + i + " seconds",
	        Toast.LENGTH_LONG).show();
		
	}

	private void setButtonClickListener() {

		final ImageButton toggleButton = (ImageButton) findViewById(R.id.toggleButton);
		toggleButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Log.e(TAG, "onClick");
				// this checks to see what to set
				// if silent, turns on, if on , turn off. otherwise, switches to
				// vibrate mode

				if (mPhoneIsSilent) {
					// change to Normal
					mAudioManager
							.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					mPhoneIsSilent = false;
					mPhoneIsVibrate = false;

					// Context context = getApplicationContext();
					// CharSequence text = "Ringer On";
					// int duration = Toast.LENGTH_SHORT;
					//
					// Toast toast = Toast.makeText(context, text, duration);
					// toast.show();

				} else if (mPhoneIsVibrate) {
					// change to silent
					mAudioManager
							.setRingerMode(AudioManager.RINGER_MODE_SILENT);
					mPhoneIsSilent = true;
					mPhoneIsVibrate = false;

					// Context context = getApplicationContext();
					// CharSequence text = "Ringer Silenced";
					// int duration = Toast.LENGTH_SHORT;
					//
					// Toast toast = Toast.makeText(context, text, duration);
					// toast.show();

				} else {
					mAudioManager
							.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
					mPhoneIsVibrate = true;
					mPhoneIsSilent = false;

					// Context context = getApplicationContext();
					// CharSequence text = "Ringer vibrate";
					// int duration = Toast.LENGTH_SHORT;
					//
					// Toast toast = Toast.makeText(context, text, duration);
					// toast.show();

				}
				// calling to toggle UI
				toggleUi();
			}

		});
	}

	private void checkIfPhoneIsSilent() {

		Log.e(TAG, "checkIfPhoneIsSilent");

		int ringerMode = mAudioManager.getRingerMode();
		if (ringerMode == AudioManager.RINGER_MODE_NORMAL) {
			mPhoneIsSilent = false;
			mPhoneIsVibrate = false;
		} else if (ringerMode == AudioManager.RINGER_MODE_SILENT) {
			mPhoneIsSilent = true;
			mPhoneIsVibrate = false;
		} else if (ringerMode == AudioManager.RINGER_MODE_VIBRATE) {
			mPhoneIsSilent = false;
			mPhoneIsVibrate = true;
		} //else {
//			mPhoneIsSilent = false;
//			mPhoneIsVibrate = true;
//		}
	}

	// makes layout switch
	private void toggleUi() {

		Log.e(TAG, "toggleUi");

		ImageButton imageButton = (ImageButton) findViewById(R.id.toggleButton);
		Drawable newPhoneImage;

		if (mPhoneIsSilent) {
			newPhoneImage = getResources().getDrawable(R.drawable.phone_silent);

		} else if (mPhoneIsVibrate) {
			newPhoneImage = getResources()
					.getDrawable(R.drawable.phone_vibrate);

		} else {
			newPhoneImage = getResources().getDrawable(R.drawable.phone_on);

		}
		imageButton.setImageDrawable(newPhoneImage);
	}

	// makes sure to check ringer state when user resumes activity
	@Override
	protected void onResume() {
		Log.e(TAG, "onResume");
		super.onResume();
		checkIfPhoneIsSilent();
		toggleUi();
	}

	protected void onPause() {
		Log.e(TAG, "onPause");
		super.onPause();
		// sloppy fix but works to update widget
		// appropriately
		updateWidget();
		updateWidget();
	}

	protected void onStop() {
		Log.e(TAG, "onStop");
		// sloppy fix but works to update widget
		// appropriately
		super.onStop();
		updateWidget();
	}

	// trying to fix widget not updating when app state is changed
	// seems to work with above calls in excess
	private void updateWidget() {
		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(getApplicationContext());
		int[] appWidgetIds = appWidgetManager
				.getAppWidgetIds(new ComponentName(this, AppWidget.class));
		if (appWidgetIds.length > 0) {
			new AppWidget().onUpdate(this, appWidgetManager, appWidgetIds);
		}
	}

	/**
	 * Enum used to identify the tracker that needs to be used for tracking.
	 * 
	 * A single tracker is usually enough for most purposes. In case you do need
	 * multiple trackers, storing them all in Application object helps ensure
	 * that they are created only once per application instance.
	 */
//	public enum TrackerName {
//		APP_TRACKER, // Tracker used only in this app.
//		GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg:
//						// roll-up tracking.
//	}
//
//	HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
//
//	synchronized Tracker getTracker(TrackerName trackerId) {
//		if (!mTrackers.containsKey(trackerId)) {
//
//			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
//			Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics
//					.newTracker(PROPERTY_ID)
//					: (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics
//							.newTracker(R.xml.global_tracker);
//			mTrackers.put(trackerId, t);
//
//		}
//		return mTrackers.get(trackerId);
//	}
}
