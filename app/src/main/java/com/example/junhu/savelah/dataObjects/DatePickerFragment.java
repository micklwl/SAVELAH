package com.example.junhu.savelah.dataObjects;

import android.app.AlarmManager;
import android.content.SharedPreferences;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import com.example.junhu.savelah.GroceryActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;


public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    public static final String MY_PREFS_NAME = "requestCode";
    private String toUpdate;
    private String uid;
    private DatabaseReference ref;
    private DatabaseReference refII;
    private AlarmManager am;
    Calendar cal = Calendar.getInstance();
    private int requestCode;
    private int newCode;
    private int quantity;
    private String unit;
//    private int[] date = new int[3];
//
    public String getDate(int day, int month, int year) {
        month = month + 1;
        return day + "/" + month +  "/" + year;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        am =  (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        SharedPreferences preferences = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        newCode = Integer.parseInt(preferences.getString("Number", null));
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        Bundle bundle = this.getArguments();
        toUpdate = bundle.getString("item");
        unit = bundle.getString("Unit");
        uid = bundle.getString("User");
        requestCode = bundle.getInt("requestCode");
        quantity = bundle.getInt("Quantity");
        Log.d("Fragment", toUpdate + uid);
        ref = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("list")
                .child(toUpdate).child("date");
        refII = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("list")
                .child(toUpdate).child("alarmID");
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = getDate(day, month, year);
        cal.set(Calendar.YEAR, year );
        cal.set(Calendar.MONTH, month); // January has value 0
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.AM_PM, Calendar.AM );
        long interval = 60 * 60 * 1000;
        if (requestCode == 0) {
            Intent myIntent = new Intent(getActivity(), AlarmBroadcastReceiver.class);
            myIntent.putExtra("itemName", toUpdate);
            myIntent.putExtra("Quantity", quantity);
            myIntent.putExtra("Unit", unit);
            PendingIntent intent = PendingIntent.getBroadcast(getActivity(), newCode, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), interval, intent);
            ref.setValue(date);
            refII.setValue(newCode + "");
            SharedPreferences.Editor editor = this.getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("Number", (newCode + 1) + "");
            editor.apply();

        } else { // requestCode >  0
            Intent myIntent = new Intent(getActivity(), AlarmBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast( getActivity(), requestCode, myIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            am.cancel(pendingIntent);

            Intent nextIntent = new Intent(getActivity(), AlarmBroadcastReceiver.class);
            nextIntent.putExtra("itemName", toUpdate);
            nextIntent.putExtra("Quantity", quantity);
            nextIntent.putExtra("Unit", unit);
            PendingIntent intent = PendingIntent.getBroadcast(getActivity(), requestCode, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            am.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), interval, intent);
            ref.setValue(date);
        }
    }
}
