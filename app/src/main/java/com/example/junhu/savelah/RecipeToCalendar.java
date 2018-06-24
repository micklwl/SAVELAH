package com.example.junhu.savelah;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.applandeo.materialcalendarview.CalendarView;
import com.example.junhu.savelah.adapter.CustomListAdapter;
import com.example.junhu.savelah.dataObjects.Customer;
import com.example.junhu.savelah.dataObjects.Ingredient;
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
import java.util.List;
import java.util.Map;

public class RecipeToCalendar extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ListView savedRecipes;
    private Button button;
    private ArrayList<Recipe> results;
    private CustomListAdapter adapter;
    private Recipe selectedRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_to_calendar);
        final CalendarView datePicker = findViewById(R.id.datePicker); //recipes
        mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        button = findViewById(R.id.addRecipeButton);
        savedRecipes= findViewById(R.id.listOfSavedRecipes);
        results = new ArrayList<Recipe>();
        adapter = new CustomListAdapter(getApplicationContext(), R.layout.recipe_search_list, results);
        Log.d("adapter", adapter.toString());
        savedRecipes.setAdapter(adapter);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customer c = dataSnapshot.getValue(Customer.class);
                if (c != null) {
                    results.clear();
                    HashMap<String, Recipe_DB> map = c.getRecipes();
                    ArrayList<Recipe> temp = new ArrayList<>();
                    if (map != null) {
                        for (Recipe_DB value : map.values()) {
                            temp.add(new Recipe(value.getTitle(), value.getImageUrl(), value.getId()));
                            results.addAll(temp);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        savedRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedRecipe = (Recipe) savedRecipes.getItemAtPosition(position);
                Log.d("selectedRecipe", selectedRecipe.getTitle());
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                Recipe myRecipe = new Recipe(datePicker.getSelectedDate(),R.drawable.ic_check_box_black_24dp,
                       selectedRecipe.getTitle(),selectedRecipe.getImage(), selectedRecipe.getId());
                returnIntent.putExtra("Result", myRecipe);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

}
