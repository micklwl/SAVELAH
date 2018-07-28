package com.example.junhu.savelah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junhu.savelah.adapter.CustomListAdapter;
import com.example.junhu.savelah.adapter.MealListAdapter;
import com.example.junhu.savelah.dataObjects.DatePickerFragment;
import com.example.junhu.savelah.dataObjects.Meal;
import com.example.junhu.savelah.dataObjects.Recipe;
import com.example.junhu.savelah.dataObjects.Recipe_Short;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class PreviewRecipesActivity extends AppCompatActivity {
    public static final String MY_PREFS_NAME = "calendar";
    HashMap<String, Meal> meals;
    ArrayList<Meal> list;
    MealListAdapter adapter;
    ListView MealResults;
    int day;
    int month;
    int year;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_recipes);
        Type mapType = new TypeToken<HashMap<String, Meal>>(){}.getType();
        Intent intent = getIntent();
        meals = new Gson().fromJson(intent.getStringExtra("Meal"), mapType);
        day = intent.getIntExtra("Day", 1);
        month = intent.getIntExtra("Month", 0) + 1;
        year = intent.getIntExtra("Year", 2018);
        text = findViewById(R.id.text);
        String txt = "Meal plan for " + day + "/" + month + "/" + year;
        text.setText(txt);
        MealResults = findViewById(R.id.listOfMeals);
        list = new ArrayList<Meal>();
        registerForContextMenu(MealResults);
        adapter = new MealListAdapter(getApplicationContext(), R.layout.activity_preview_recipes, list);
        MealResults.setAdapter(adapter);
        list.clear();
        Meal[] arr = meals.values().toArray(new Meal[0]);
        Arrays.sort(arr, new Comparator<Meal>() {
            @Override
            public int compare(Meal o1, Meal o2) {
                if (o1.getHour() == o2.getHour()) {
                    return o1.getMinute() - o2.getMinute();
                } else {
                    return o1.getHour() - o2.getHour();
                }
            }
        });
        ArrayList<Meal> temp = new ArrayList<Meal>(Arrays.asList(arr));
        list.addAll(temp);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mealplanner, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delMeal :
                int position = info.position;
                deleteMeal(position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteMeal(int position) {
        Meal meal = list.get(position);
        String key = meal.getTime();
        list.remove(position);
        adapter.notifyDataSetChanged();
        SharedPreferences prefs = this.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = this.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        String jKey = new Gson().toJson( new com.example.junhu.savelah.dataObjects.Calendar(year, month - 1, day));
        String jValue = prefs.getString(jKey, null);
        if (jValue == null) {
           Toast.makeText(this, "Sorry, please try again", Toast.LENGTH_SHORT).show();
        } else {
            Type mapType = new TypeToken<HashMap<String, Meal>>(){}.getType();
            HashMap<String, Meal> map = new Gson().fromJson(jValue, mapType);
            map.remove(key);
            jValue = new Gson().toJson(map);
            editor.putString(jKey, jValue);
            editor.apply();
        }
    }
}
