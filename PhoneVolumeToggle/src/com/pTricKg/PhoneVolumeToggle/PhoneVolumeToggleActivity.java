package com.pTricKg.PhoneVolumeToggle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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

		        //message to say can long-press for vibrate
				Context context = getApplicationContext();
				CharSequence text = "Please long-press to set ringer to vibrate.";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();		
				   
				//this turns ringer on if silent is false.
				//otherwise, turns ringer off.
				if(mPhoneIsSilent == true) {
					//change to Normal
					mAudioManager
						.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
					mPhoneIsSilent = false;
				}else{
					//change to silent
					mAudioManager
						.setRingerMode(AudioManager.RINGER_MODE_SILENT);
						mPhoneIsSilent = true;
				}

				//calling to toggle UI
				toggleUi();
				
			}

			});
        
        	toggleButton.setOnLongClickListener(new View.OnLongClickListener() {
        	@SuppressLint("CutPasteId")
			ImageButton imageButton1 = (ImageButton) findViewById(R.id.toggleButton);
        	Drawable newPhoneImage1;
        	public boolean onLongClick(View v) {
            	//change to Vibrate
				mAudioManager
					.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
				newPhoneImage1 = getResources().getDrawable(R.drawable.phone_vibrate);
				imageButton1.setImageDrawable(newPhoneImage1);
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
		}else {
			
		}
	}

//makes layout switch
private void toggleUi() {
		
	ImageButton imageButton = (ImageButton) findViewById(R.id.toggleButton);
	Drawable newPhoneImage;
	
	if (mPhoneIsSilent == true) {
		newPhoneImage = getResources().getDrawable(R.drawable.phone_silent);
	}else{
		newPhoneImage = getResources().getDrawable(R.drawable.phone_on);
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