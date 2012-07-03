package com.pTricKg.PhoneVolumeToggle;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class PhoneVolumeToggleActivity extends Activity {
    	
	private AudioManager mAudioManager;
	private boolean mPhoneIsSilent;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //initializing AudioManager variable
        mAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
        
        //initializing Ringer State check
        checkIfPhoneIsSilent();
        
        //initialize button click listener. 
        //place here to allow ringer state check first, I think!
        setButtonClickListener();
    }

	private void setButtonClickListener() {
		
		Button toggleButton = (Button)findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
								
				//this turns ringer on if silent is false.
				//otherwise, turns ringer off.
				if(mPhoneIsSilent) {
					//change to Normal
					mAudioManager
						.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					mPhoneIsSilent = false;
				}else {
					//change to silent
					mAudioManager
						.setRingerMode(AudioManager.RINGER_MODE_SILENT);
						mPhoneIsSilent = true;
				}
				
				//toggle UI
				toggleUi();
			}

			});
	}
	private void checkIfPhoneIsSilent() {
		
		int ringerMode = mAudioManager.getRingerMode();
		if (ringerMode == AudioManager.RINGER_MODE_SILENT) {
			mPhoneIsSilent = true;
		}else {
			mPhoneIsSilent = false;
		}
	}

//makes layout switch
private void toggleUi() {
	
	ImageView imageView = (ImageView) findViewById(R.id.phone_icon);
	Drawable newPhoneImage;
	
	if (mPhoneIsSilent) {
		newPhoneImage = getResources().getDrawable(R.drawable.phone_silent);
	}else {
		newPhoneImage = getResources().getDrawable(R.drawable.phone_on);
	}
	imageView.setImageDrawable(newPhoneImage);
}
//makes sure to check ringer state when user resumes activity
@Override
protected void onResume() {
	super.onResume();
	checkIfPhoneIsSilent();
	toggleUi();
}
}