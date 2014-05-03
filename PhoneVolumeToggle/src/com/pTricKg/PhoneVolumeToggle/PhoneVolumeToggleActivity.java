package com.pTricKg.PhoneVolumeToggle;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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

	private void setButtonClickListener() {
		/*
		 * maybe issue with invoking without asking for it.. has something to do
		 * with using ImageButton instead of Button.. now trying fix with
		 * removal of auto-updater..
		 */

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

					Context context = getApplicationContext();
					CharSequence text = "Ringer On";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();

				} else if (mPhoneIsVibrate) {
					// change to silent
					mAudioManager
							.setRingerMode(AudioManager.RINGER_MODE_SILENT);
					mPhoneIsSilent = true;
					mPhoneIsVibrate = false;

					Context context = getApplicationContext();
					CharSequence text = "Ringer Silenced";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();

				} else {
					mAudioManager
							.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
					mPhoneIsVibrate = true;
					mPhoneIsSilent = false;

					Context context = getApplicationContext();
					CharSequence text = "Ringer vibrate";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();

				}
				// calling to toggle UI
				toggleUi();
			}

		});

	}

	private void checkIfPhoneIsSilent() {

		Log.e(TAG, "checkIfPhoneIsSilent");

		int ringerMode = mAudioManager.getRingerMode();
		if (ringerMode == AudioManager.RINGER_MODE_SILENT) {
			mPhoneIsSilent = true;
			mPhoneIsVibrate = false;
		} else if (ringerMode == AudioManager.RINGER_MODE_NORMAL) {
			mPhoneIsSilent = false;
			mPhoneIsVibrate = false;
		} else if (ringerMode == AudioManager.RINGER_MODE_VIBRATE) {
			mPhoneIsSilent = false;
			mPhoneIsVibrate = true;
		} else {
			mPhoneIsSilent = false;
			mPhoneIsVibrate = false;
		}
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
	
	// trying to fix widget not updating when app state is changed
	private void updateWidget() {
		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(getApplicationContext());
		int[] appWidgetIds = appWidgetManager
				.getAppWidgetIds(new ComponentName(this, AppWidget.class));
		if (appWidgetIds.length > 0) {
			new AppWidget().onUpdate(this, appWidgetManager, appWidgetIds);
		}
	}
	
}