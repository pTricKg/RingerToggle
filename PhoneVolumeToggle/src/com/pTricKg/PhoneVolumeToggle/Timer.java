package com.pTricKg.PhoneVolumeToggle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by pTricKg on 5/21/2016.
 */
public class Timer extends Activity{

    public static TimePicker timePicker;
    final Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int min = c.get(Calendar.MINUTE);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        //timepicker setup
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(false);
        //another button for timer
        final Button timerButton = (Button) findViewById(R.id.button);
        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.clearFocus();
                hour   = timePicker.getCurrentHour();
                min = timePicker.getCurrentMinute();
                makeToast();

                }
        });
    }


    private void makeToast() {

        Toast.makeText(this, "Blah,Blah,Blah " + hour + ":" + min, Toast.LENGTH_LONG).show();
    }


}


