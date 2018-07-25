package com.example.junhu.savelah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.applandeo.materialcalendarview.EventDay;
import com.google.gson.Gson;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {
    private static final int ADD_RECIPE = 44;
    public static final String MY_PREFS_NAME = "calendar";
    private android.widget.CalendarView mCalendarView;
    private Calendar cal;
    private FloatingActionButton Button;
    private List<EventDay> mEventDays = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, true);
        mCalendarView= findViewById(R.id.calendarView);
//      mCalendarView = new Gson().fromJson(json, CalendarView.class);
        // String json = preferences.getString("Calendar", null);
        Button = findViewById(R.id.floatingActionButton);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View v) {
                addRecipeToCalendar(); 
            }
        });
        mCalendarView.setOnDateChangeListener(new android.widget.CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull android.widget.CalendarView view, int year, int month, int dayOfMonth) {
                com.example.junhu.savelah.dataObjects.Calendar cal = new com.example.junhu.savelah.dataObjects.Calendar(year, month, dayOfMonth);
                previewRecipe(cal);
            }
        });

        BottomNavigationViewEx bottombar = (BottomNavigationViewEx) findViewById(R.id.navigation);
        bottombar.enableAnimation(false);
        bottombar.enableShiftingMode(false);
        bottombar.enableItemShiftingMode(false);
        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_grocery:
                        startActivity(new Intent(CalendarActivity.this, GroceryActivity.class));
                        break;
                    case R.id.navigation_recipe:
                        startActivity(new Intent(CalendarActivity.this, RecipeActivity.class)) ;
                        break;
                    case R.id.navigation_calendar:
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(CalendarActivity.this, ProfileActivity.class)) ;
                        break;
                    case R.id.sharing:
                        startActivity(new Intent(CalendarActivity.this, SharingActivity.class));
                }
                return false;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == ADD_RECIPE && resultCode == RESULT_OK) {
//            Recipe dayRecipe = data.getParcelableExtra("Result");
//
////            mCalendarView.setDate(dayRecipe.getCalendar());
////            mEventDays.add(dayRecipe);
////            mCalendarView.setEvents(mEventDays);
//            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
//            Gson gson = new Gson();
//            String jKey = gson.toJson(dayRecipe.getCalendar());
//            Log.d("On activity result", jKey);
//            String chosenRecipe = gson.toJson(dayRecipe);
//            HashMap<String, String> jValue = new HashMap<>();
//            jValue.put()
//            editor.putString(jKey, jValue);
//            editor.apply(); }
    }

    private void  previewRecipe(com.example.junhu.savelah.dataObjects.Calendar date) {
        String key = new Gson().toJson(date);
        SharedPreferences preferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String result =  preferences.getString(key, null);
        if (result == null) {
            Toast.makeText(this, "No Recipes to preview", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, PreviewRecipes.class);
            int month = date.getMonth();
            int day = date.getDayOfMonth();
            int year = date.getYear();
            intent.putExtra("Meal", result);
            intent.putExtra("Day", day);
            intent.putExtra("Month", month);
            intent.putExtra("Year", year);
            startActivity(intent);
            return;
        }
    }

    private void addRecipeToCalendar() {
        Intent intent = new Intent(this, RecipeToCalendar.class);
        startActivityForResult(intent, ADD_RECIPE);
    }

    public void clearAll(View view) {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        Toast.makeText(this, "All saved recipes have been cleared!", Toast.LENGTH_SHORT).show();
    }
    //    private void previewRecipe(int year, int month, int day) {
//     //   Log.d("preview", day + "");
////        SharedPreferences preferences = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
////        Map<String, ?> keys = preferences.getAll();
////        Log.d("preview",  keys + "");
////        Intent intent = new Intent(this, SingleRecipeActivity.class);
////        if (keys != null) {
////            for (Map.Entry<String, ?> entry : keys.entrySet()) {
////                com.example.junhu.savelah.dataObjects.Calendar key = new Gson().fromJson(entry.getKey(),
////                        com.example.junhu.savelah.dataObjects.Calendar.class);
////                Log.d("preview",  + key.getDayOfMonth() + "");
////                if (key.getYear() == year && key.getMonth() == month && key.getDayOfMonth() == day) {
////                    intent.putExtra("Recipe", new Gson().fromJson
////                            (preferences.getString(entry.getKey(), null), PreviewRecipes.class));
////                    intent.putExtra("type", "true");
////                    startActivity(intent);
////                    return;
////                }
////            }
////                Toast.makeText(this, "No Recipe to preview", Toast.LENGTH_LONG).show();
////        } else {
////            Toast.makeText(this, "No Recipe to preview", Toast.LENGTH_LONG).show();
////        }
////    }

}
