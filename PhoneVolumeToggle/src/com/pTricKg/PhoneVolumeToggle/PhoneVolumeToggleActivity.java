package com.pTricKg.PhoneVolumeToggle;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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
		
		ImageButton toggleButton = (ImageButton)findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				
				//this turns ringer on if silent is false.
				//otherwise, turns ringer off.
				if(mPhoneIsSilent == true) {
					//change to Normal
					mAudioManager
						.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					mPhoneIsSilent = false;
				}else if(mPhoneIsSilent == false){
					//change to silent
					mAudioManager
						.setRingerMode(AudioManager.RINGER_MODE_SILENT);
						mPhoneIsSilent = true;
				}else {
					mAudioManager
					.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
				}
				
				//toggle UI
				toggleUi();
			}

			});
        
        toggleButton.setOnLongClickListener(new View.OnLongClickListener() {
        	ImageButton imageButton1 = (ImageButton) findViewById(R.id.toggleButton);
        	public boolean onLongClick(View v) {
            	//change to Vibrate
				mAudioManager
					.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
				imageButton1.getResources().getDrawable(R.drawable.phone_vibrate);
				return true;
				
				} 
        		
          });
	}
	private void checkIfPhoneIsSilent() {
		
		int ringerMode = mAudioManager.getRingerMode();
		if (ringerMode == AudioManager.RINGER_MODE_SILENT) {
			mPhoneIsSilent = true;
		}else if (ringerMode == AudioManager.RINGER_MODE_NORMAL){
			mPhoneIsSilent = false;
		}
	}

//makes layout switch
private void toggleUi() {
	
	ImageButton imageButton = (ImageButton) findViewById(R.id.toggleButton);
	Drawable newPhoneImage;
	
	if (mPhoneIsSilent == true) {
		newPhoneImage = getResources().getDrawable(R.drawable.phone_silent);
	}else if(mPhoneIsSilent == false) {
		newPhoneImage = getResources().getDrawable(R.drawable.phone_on);
	}else {
		newPhoneImage = getResources().getDrawable(R.drawable.phone_vibrate);
	}
	imageButton.setImageDrawable(newPhoneImage);
}
//makes sure to check ringer state when user resumes activity
@Override
protected void onResume() {
	super.onResume();
	checkIfPhoneIsSilent();
	toggleUi();
}
}