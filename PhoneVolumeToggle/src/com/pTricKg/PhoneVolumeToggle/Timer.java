package com.pTricKg.PhoneVolumeToggle;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.text.format.DateUtils;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DecimalFormat;
import java.util.Calendar;

import org.w3c.dom.Text;

/**
 * Created by pTricKg on 5/21/2016.
 */
public class Timer extends Activity {

    public static TimePicker timePicker;
    final Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int min = c.get(Calendar.MINUTE);
    String ampm;
    String minuteString;

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private static Timer alarmActivity;

    public static Timer instance() {
        return alarmActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        //button for timer
        ToggleButton timerButton = (ToggleButton) findViewById(R.id.button);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //timepicker setup
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(false);

        onTimerButtonClicked(timerButton);
    }


    public void onTimerButtonClicked(View view) {

        if (((ToggleButton) view).isChecked()) {

            Log.d("Should turn on", "start");
            timePicker.clearFocus(); // remove system time for update
            hour = timePicker.getCurrentHour(); // set input time
            min = timePicker.getCurrentMinute();

            if (hour > 12) {
                hour -= 12;
                ampm = "PM";
            } else ampm = "AM";

            if (min < 10) {
                minuteString = "0" + min;
            } else {
                minuteString = "" + min;
            }
            makeToast();


            // activate alarm
            c.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
            c.set(Calendar.MINUTE, timePicker.getCurrentMinute());
            Intent myIntent = new Intent(Timer.this, MyBroadcastReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(Timer.this, 0, myIntent, 0);
            alarmManager.set(AlarmManager.RTC, c.getTimeInMillis(), pendingIntent);


        } else {
            Log.d("Should turn of here", "end");
            ((ToggleButton) view).setChecked(false);
            Intent myIntent = new Intent(Timer.this, MyBroadcastReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(Timer.this, 0, myIntent, 0);
            alarmManager.set(AlarmManager.RTC, c.getTimeInMillis(), pendingIntent);
            alarmManager.cancel(pendingIntent);
        }

    }

    private void makeToast() {

        Toast.makeText(this, "Time Set: " + hour + ":" + minuteString + ampm, Toast.LENGTH_LONG).show();
    }

    public void onStart() {
        super.onStart();
        alarmActivity = this;
    }

    public void onDestroy() {
        super.onDestroy();
        alarmActivity = this;
    }


}


