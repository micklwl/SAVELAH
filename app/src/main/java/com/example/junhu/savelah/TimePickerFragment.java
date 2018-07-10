package com.example.junhu.savelah;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;

import com.example.junhu.savelah.dataObjects.Meal;
import com.example.junhu.savelah.dataObjects.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    public static final String MY_PREFS_NAME = "calendar";
    public Recipe recipe;
    private int day;
    private int month;
    private int year;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        Bundle bundle = this.getArguments();
        recipe = bundle.getParcelable("Recipe");
        day = bundle.getInt("Day");
        month = bundle.getInt("Month");
        year = bundle.getInt("Year");
        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        HashMap<String, Meal> map;
        Type mapType = new TypeToken<HashMap<String, Meal>>(){}.getType();
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        String jKey = new Gson().toJson( new com.example.junhu.savelah.dataObjects.Calendar(year, month, day));
        String jValue = prefs.getString(jKey, null);
        if (jValue == null) {
            map = new HashMap<>();
        } else {
            map = new Gson().fromJson(jValue, mapType);

        }
        map.put(hourOfDay + ":" + minute, new Meal(recipe, hourOfDay, minute));
        Log.d("On activity result", jKey);
        jValue = new Gson().toJson(map);
        editor.putString(jKey, jValue);
        editor.apply();
        Intent returnIntent = new Intent();
        getActivity().setResult(Activity.RESULT_OK, returnIntent);
        getActivity().finish();
    }
}
