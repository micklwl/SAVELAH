package com.example.junhu.savelah;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.junhu.savelah.adapter.CustomListAdapter;
import com.example.junhu.savelah.adapter.SavedRecipesAdapter;
import com.example.junhu.savelah.dataObjects.Customer;
import com.example.junhu.savelah.dataObjects.Recipe;
import com.example.junhu.savelah.dataObjects.Recipe_DB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecipeToCalendarActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ListView savedRecipes;
    private TextView recipesCheck;
    private CalendarView mCalendarView;
    private Button button;
    private ArrayList<Recipe_DB> results;
    private SavedRecipesAdapter adapter;
    private Recipe selectedRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_to_calendar);
//        mCalendarView = findViewById(R.id.datePicker);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        savedRecipes= findViewById(R.id.listOfSavedRecipes);
        recipesCheck = findViewById(R.id.noRecipes);
        results = new ArrayList<Recipe_DB>();
        adapter = new SavedRecipesAdapter(this, R.layout.activity_recipe_to_calendar, results);
        Log.d("adapter", adapter.toString());
        savedRecipes.setAdapter(adapter);
//        mCalendarView.setOnDateChangeListener(new android.widget.CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull android.widget.CalendarView view, int year, int month, int dayOfMonth) {
//                com.example.junhu.savelah.dataObjects.Calendar cal = new com.example.junhu.savelah.dataObjects.Calendar(year, month, dayOfMonth);
//                DialogFragment newFragment = new TimePickerFragment();
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("Recipe", selectedRecipe);
//                newFragment.setArguments(bundle);
//                //   setDate(newFragment.getDate(), p);
//                newFragment.show(getFragmentManager(), "timePicker");
//            }
//        });
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Customer c = dataSnapshot.getValue(Customer.class);
//                results.clear();
//                if (c != null) {
//                    HashMap<String, Recipe_DB> map = c.getRecipes();
//                    ArrayList<Recipe> temp = new ArrayList<>();
//                    if (map != null) {
//                        for (Recipe_DB value : map.values()) {
//                            temp.add(new Recipe(value.getTitle(), value.getImageUrl(), value.getId()));
//                        }
//                        results.addAll(temp);
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customer c = dataSnapshot.getValue(Customer.class);
                if (c != null) {
                    results.clear();
                    HashMap<String, Recipe_DB> map = c.getRecipes();
                    ArrayList<Recipe_DB> temp = new ArrayList<>();
                    if (map != null) {
                        for (Map.Entry<String, Recipe_DB> entry : map.entrySet()) {
                            Recipe_DB one = entry.getValue();
                            //Ingredient value = entry.getValue();
                            temp.add(one);
                        }
                        results.addAll(temp);
                        //Log.d("hello", "onDataChange: " + list);
                        adapter.notifyDataSetChanged();
                    }
                }
                if (results.isEmpty()) recipesCheck.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        savedRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe_DB temp = (Recipe_DB) savedRecipes.getItemAtPosition(position);
                selectedRecipe = temp.convertToRecipe();
                Log.d("selectedRecipe", selectedRecipe.getTitle());
                DialogFragment newFragment = new DatePickerIIFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("Recipe", selectedRecipe);
                newFragment.setArguments(bundle);
                //   setDate(newFragment.getDate(), p);
                newFragment.show(getFragmentManager(), "datePickerII");
            }
        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent returnIntent = new Intent();
////                Recipe myRecipe = new Recipe(datePicker.getSelectedDate(),R.drawable.ic_check_box_black_24dp,
////                       selectedRecipe.getTitle(),selectedRecipe.getImage(), selectedRecipe.getId());
//                returnIntent.putExtra("Result", selectedRecipe);
//                setResult(Activity.RESULT_OK, returnIntent);
//                finish();
//            }
//        });
    }

}
