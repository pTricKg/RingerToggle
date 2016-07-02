package com.pTricKg.PhoneVolumeToggle;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Calendar;

import org.w3c.dom.Text;

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
                timePicker.clearFocus(); // remove system time for update
                hour   = timePicker.getCurrentHour(); // set input time
                min = timePicker.getCurrentMinute();
                if(hour > 12){
                    hour -= 12;
                }

                makeToast();

                }
        });
    }


    private void makeToast() {

        Toast.makeText(this, "Blah,Blah,Blah " + hour + ":" + min , Toast.LENGTH_LONG).show();
    }


}


