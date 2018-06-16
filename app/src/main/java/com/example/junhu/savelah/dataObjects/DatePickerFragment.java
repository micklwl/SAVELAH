package com.example.junhu.savelah.dataObjects;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import com.example.junhu.savelah.GroceryActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private String toUpdate;
    private String uid;
    private DatabaseReference ref;
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
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        Bundle bundle = this.getArguments();
        toUpdate = bundle.getString("item");
        uid = bundle.getString("User");
        Log.d("Fragment", toUpdate + uid);
        ref = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("recipe")
                .child(toUpdate).child("date");
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
            String date = getDate(day, month, year);
            //Log.d("FragmentIII", ref.toString());
          ref.setValue(date);
    }
}
