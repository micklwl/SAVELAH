package com.example.junhu.savelah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.junhu.savelah.dataObjects.Customer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.HashMap;

public class SharingActivity extends AppCompatActivity implements AddMemberDialog.ShareListListener {
    private FirebaseUser user;
    private DatabaseReference initDatabase;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing);
        user = FirebaseAuth.getInstance().getCurrentUser();
        initDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase = initDatabase.child(user.getUid());

        BottomNavigationViewEx bottombar = (BottomNavigationViewEx) findViewById(R.id.navigation);
        bottombar.enableAnimation(false);
        bottombar.enableShiftingMode(false);
        bottombar.enableItemShiftingMode(false);
        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_grocery:
                        startActivity(new Intent(SharingActivity.this, GroceryActivity.class)) ;
                        break;
                    case R.id.navigation_recipe:
                        startActivity(new Intent(SharingActivity.this, RecipeActivity.class));
                        break;
                    case R.id.navigation_calendar:
                        startActivity(new Intent(SharingActivity.this, CalendarActivity.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(SharingActivity.this, ProfileActivity.class)) ;
                        break;
                }
                return false;
            }
        });

    }

    public void addMemberListener(View view) {
        openDialog();
    }

    public void accessSharedListListener(View view) {
        startActivity(new Intent(this, SharedListActivity.class));
    }

    private void openDialog() {
        AddMemberDialog dialog = new AddMemberDialog();
        dialog.show(getSupportFragmentManager(), "sharedList dialog");
    }

    @Override
    public void applyTexts(String email) {
        final String mail = email;
        final String encodedEmail = user.getEmail().replace(".", ",");
        Query query = initDatabase.orderByChild("email").equalTo(mail);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    GenericTypeIndicator<HashMap<String, Customer>> t = new GenericTypeIndicator<HashMap<String,Customer>>() {};
                    String[] a = new String[1];
                    String[] userID = dataSnapshot.getValue(t).keySet().toArray(a);
                    mDatabase.child("members").child(userID[0]).setValue(mail);
                    initDatabase.child(userID[0]).child("access").child(encodedEmail).setValue(user.getUid());
                    Toast.makeText(SharingActivity.this,"User added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SharingActivity.this,"No such user in SAVELAH", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

