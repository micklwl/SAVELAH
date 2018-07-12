package com.example.junhu.savelah;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.junhu.savelah.dataObjects.ChangeQuantityDialog;
import com.example.junhu.savelah.dataObjects.Customer;
import com.example.junhu.savelah.dataObjects.DatePickerFragment;
import com.example.junhu.savelah.dataObjects.Ingredient;
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

public class ListOfOtherUser extends AppCompatActivity
        implements AddGroceryDialogListener, ChangeQuantityDialog.ChangeQuantityDialogListener{

    private EditText toAdd;
    private String message;
    private ListView groceryList;
    private AlarmManager am;
    private ArrayList<String> list = new ArrayList<>();
    private HashMap<String, String> requestID;
    private FirebaseUser user;
    private DatabaseReference initDatabase;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_other_user);
        Intent intent = getIntent();
        message = intent.getStringExtra("UID");
        toAdd = findViewById(R.id.addText);
        am =  (AlarmManager) this.getSystemService(ALARM_SERVICE);
        requestID = new HashMap<>();
        groceryList = findViewById(R.id.sharedList);
        registerForContextMenu(groceryList);
        user = FirebaseAuth.getInstance().getCurrentUser();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        groceryList.setAdapter(adapter);
        //Toast.makeText(this, user.getUid(), Toast.LENGTH_LONG).show();
        initDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase = initDatabase.child(message);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customer c = dataSnapshot.getValue(Customer.class);
                if (c != null) {
                    list.clear();
                    requestID.clear();
                    HashMap<String, Ingredient> map = c.getList();
                    ArrayList<String> temp = new ArrayList<>();
                    if (map != null) {
                        for (Map.Entry<String, Ingredient> entry : map.entrySet()) {
                            String key = entry.getKey();
                            Ingredient value = entry.getValue();
                            requestID.put(key, value.getAlarmID());
                            temp.add(key + " (" + value.getAmount() + " " + value.getUnit() + ")");
                        }
                        list.addAll(temp);
                        // list.addAll(new ArrayList<String>(c.getList().keySet()));
                        Log.d("hello", "onDataChange: " + list);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.grocery, menu);
    }

    private void deleteGrocery(int key) {
        String item = findItem(key).get("Name").trim();
        Log.d("delGrocery", item);
        Toast.makeText(this, item, Toast.LENGTH_LONG).show();
        mDatabase.child("list").child(item).removeValue();
        if(!(requestID.get(item).equals(0 + ""))) {
            Intent myIntent = new Intent(this, AlarmBroadcastReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast( this, Integer.parseInt(requestID.get(item)), myIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            am.cancel(pendingIntent);
        }
    }

    public HashMap<String, String> findItem(int position) {
        //regex: .split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[0];
        HashMap<String, String> result = new HashMap<>();
        String item = list.get(position);
        String[] temp = item.split("\\(");
        String name = temp[0].trim();
        String str = temp[1].substring(0, temp[1].length() -1);
        String quantity = str.split(" ")[0].trim();
        String unit = str.split(" ")[1].trim();
        Log.d("MyUnits", unit);
        result.put("Name", name);
        result.put("Quantity", quantity);
        result.put("Unit", unit);
        return result;
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
                String ingredient = findItem(p).get("Name");
                bundle.putString("item", ingredient);
                bundle.putFloat("Quantity", Float.parseFloat(findItem(p).get("Quantity")));
                bundle.putString("Unit", findItem(p).get("Unit") );
                bundle.putString("User", user.getUid());
                bundle.putInt("requestCode", Integer.parseInt(requestID.get(ingredient)));
                newFragment.setArguments(bundle);
                //   setDate(newFragment.getDate(), p);
                newFragment.show(getFragmentManager(), "datePicker");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void addGroceryListener(View view) {
        openDialog();
    }

    private void openDialog() {
        AddGroceryDialog dialog = new AddGroceryDialog();
        dialog.show(getSupportFragmentManager(), "add grocery dialog");
    }

    private void openQuantityDialog(Ingredient ingredientDB, Ingredient ingredientAdd) {
        ChangeQuantityDialog changeQuantityDialog = new ChangeQuantityDialog();
        String ingDB = "Current List has " + String.valueOf(ingredientDB.getAmount())+ " " + ingredientDB.getUnit() + " of " + ingredientDB.getName();
        String ingAdd = "You want to add " + String.valueOf(ingredientAdd.getAmount()) + " " + ingredientAdd.getUnit();
        Bundle bundle = new Bundle();
        bundle.putString("Database",ingDB);
        bundle.putString("Adding",ingAdd);
        bundle.putString("Name",ingredientDB.getName());
        changeQuantityDialog.setArguments(bundle);
        changeQuantityDialog.show(getSupportFragmentManager(),"change quantity dialog");
    }


    @Override
    public void applyText(String quantity, String unit) {
        addGrocery(quantity, unit);
    }

    @Override
    public void applyTexts(float quantityResult, String unitResult, String name) {
        mDatabase.child("list").child(name).child("amount").setValue(quantityResult);
        mDatabase.child("list").child(name).child("unit").setValue(unitResult);
    }

    public void addGrocery(String quantity, String unit) {
        final float qty = Float.parseFloat(quantity);
        final String ut = unit;
        final String name = toAdd.getText().toString().trim();
        final Ingredient newIngredient = new Ingredient(name, "default", Float.parseFloat(quantity), unit);
        mDatabase.child("list").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(name).exists()) {
                    Ingredient ingredientDB = dataSnapshot.child(name).getValue(Ingredient.class);
                    if (ingredientDB.getUnit().equals(ut)) {
                        float endAmount = qty + ingredientDB.getAmount();
                        mDatabase.child("list").child(name).child("amount").setValue(endAmount);
                    } else {
                        // ingredient present but unit on list and unit inside DB diff
                        openQuantityDialog(ingredientDB, newIngredient);
                    }
                } else {
                    mDatabase.child("list").child(name).setValue(newIngredient);
                }
                toAdd.setText("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
