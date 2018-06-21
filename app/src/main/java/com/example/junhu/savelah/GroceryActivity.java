package com.example.junhu.savelah;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.junhu.savelah.dataObjects.Customer;
import com.example.junhu.savelah.dataObjects.DatePickerFragment;
import com.example.junhu.savelah.dataObjects.Ingredient;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GroceryActivity extends AppCompatActivity implements AddGroceryDialogListener {
    public static final String EXTRA_MESSAGE = "com.example.junhu.savelah.GroceryActivity.MESSAGE";
    private EditText toAdd;
    private EditText findList;
    private ListView groceryList;
    private ArrayList<String> list = new ArrayList<>();
    private FirebaseUser user;
    private DatabaseReference initDatabase;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);
        // Initalise widgets
        toAdd = findViewById(R.id.addText);
        findList = findViewById(R.id.findText);
        groceryList = findViewById(R.id.groceryList);
        registerForContextMenu(groceryList);
        user = FirebaseAuth.getInstance().getCurrentUser();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        groceryList.setAdapter(adapter);
        Toast.makeText(this, user.getUid(), Toast.LENGTH_LONG).show();
        initDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase = initDatabase.child(user.getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customer c = dataSnapshot.getValue(Customer.class);
                if (c != null) {
                    list.clear();
                    HashMap<String, Ingredient> map = c.getList();
                    ArrayList<String> temp = new ArrayList<>();
                    for (Map.Entry<String, Ingredient> entry : map.entrySet()) {
                        String key = entry.getKey();
                        Ingredient value = entry.getValue();
                        temp.add(key + " " + value.getAmount());
                    }
                    list.addAll(temp);
                    // list.addAll(new ArrayList<String>(c.getList().keySet()));
                    Log.d("hello", "onDataChange: " + list);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
   // Toast.makeText(this, user.getEmail(), Toast.LENGTH_LONG).show();
        BottomNavigationViewEx bottombar = (BottomNavigationViewEx) findViewById(R.id.navigation);
        bottombar.enableAnimation(false);
        bottombar.enableShiftingMode(false);
        bottombar.enableItemShiftingMode(false);
        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_grocery:
                        break;
                    case R.id.navigation_recipe:
                        startActivity(new Intent(GroceryActivity.this, RecipeSearchActivity.class)) ;
                        break;
                    case R.id.navigation_calendar:
                        startActivity(new Intent(GroceryActivity.this, CalendarActivity.class));
                        break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(GroceryActivity.this, ProfileActivity.class)) ;
                        break;
                }
                return false;
            }
        });
    }
    // to create options list to delete grocery
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.grocery, menu);
    }

    private void deleteGrocery(int key) {
        String item = findItem(key);
        mDatabase.child("list").child(item).removeValue();
    }

    public String findItem(int position) {
        String item = list.get(position);
        item = item.substring(0, item.lastIndexOf(" "));
        return item;
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delGrocery :
             int position = info.position;
             deleteGrocery(position);
             return true;
            case R.id.updateDate :
                int p = info.position;
                DatePickerFragment newFragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putString("item", findItem(p));
                bundle.putString("User", user.getUid());
                newFragment.setArguments(bundle);
             //   setDate(newFragment.getDate(), p);
                newFragment.show(getFragmentManager(), "datePicker");
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void addGrocery(String quantity) {
        Log.d("entered", "entered");
        final String str = toAdd.getText().toString().trim();
        mDatabase.child("list").child(str).setValue(new Ingredient(str, "default", Integer.parseInt(quantity)));
      //  mDatabase.child("list").child(nextIndex + "").setValue(str);
        Log.d("hello", "addGrocery: " + str);
    }

    public void addGroceryListener(View view) {
        openDialog();
    }

    public void findListListener(View view) {
        final String sharedEmail = findList.getText().toString().trim();
        final String decodedEmail = sharedEmail.replace(".", ",");
        Query query = initDatabase.orderByChild("email").equalTo(sharedEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    GenericTypeIndicator<HashMap<String, Customer>> t = new GenericTypeIndicator<HashMap<String,Customer>>() {};
                    HashMap<String,Customer> map = dataSnapshot.getValue(t);
                    String[] a = new String[1];
                    String shareeID = dataSnapshot.getValue(t).keySet().toArray(a)[0];
                    Customer sharee = map.get(shareeID);
                    if (sharee.getMembers().containsKey(decodedEmail)) {
                        Intent intent = new Intent(GroceryActivity.this, SharedListActivity.class);
                        intent.putExtra(EXTRA_MESSAGE, shareeID);
                        startActivity(intent);
                    } else {
                        Toast.makeText(GroceryActivity.this,"You do not have access to this email", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(GroceryActivity.this,"No such User in SAVELAH", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void shareListListener(View view) {
        final String emailToShare = findList.getText().toString().trim();
        // encode email by replacing . with ,
        final String newEmail = emailToShare.replace(".", ",");
        Log.d("Listener", newEmail);
        Query query = initDatabase.orderByChild("email").equalTo(emailToShare);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //Customer c = dataSnapshot.child("Users").getValue(DataSnapshot.class).getValue(Customer.class);
                    //String ID = c.getUid();
                    GenericTypeIndicator<HashMap<String, Customer>> t = new GenericTypeIndicator<HashMap<String,Customer>>() {};
                    String[] a = new String[1];
                    String[] b = dataSnapshot.getValue(t).keySet().toArray(a);
                    mDatabase.child("members").child(newEmail).setValue(b[0]);
//                    Log.d("shareHello", dataSnapshot.getValue(t).keySet().toString());
                //    mDatabase.child("members").child(ID).setValue(emailToShare);
                } else {
                    Toast.makeText(GroceryActivity.this,"No such user in SAVELAH", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void openDialog() {
        AddGroceryDialog dialog = new AddGroceryDialog();
        dialog.show(getSupportFragmentManager(), "add grocery dialog");
    }


    @Override
    public void applyText(String quantity) {
        addGrocery(quantity);
    }
}