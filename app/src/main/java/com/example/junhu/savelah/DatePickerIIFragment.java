package com.example.junhu.savelah;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import com.example.junhu.savelah.dataObjects.Recipe;

public class DatePickerIIFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private Recipe selectedRecipe;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final java.util.Calendar c = java.util.Calendar.getInstance();
        int year = c.get(java.util.Calendar.YEAR);
        int month = c.get(java.util.Calendar.MONTH);
        int day = c.get(java.util.Calendar.DAY_OF_MONTH);
        Bundle bundle = this.getArguments();
        selectedRecipe = bundle.getParcelable("Recipe");
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        DialogFragment newFragment = new TimePickerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("Recipe", selectedRecipe);
        bundle.putInt("Year",year);
        bundle.putInt("Month", month);
        bundle.putInt("Day", day);
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(), "timePicker");
    }
}
