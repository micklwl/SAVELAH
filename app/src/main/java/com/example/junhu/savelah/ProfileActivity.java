package com.example.junhu.savelah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseAuth mAuth;
    TextView editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editTextEmail = findViewById(R.id.ShowEmail);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.buttonPW).setOnClickListener(this);
        findViewById(R.id.buttonSignOut).setOnClickListener(this);
        findViewById(R.id.buttonGrocery).setOnClickListener(this);

        loadUserInformation();


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void loadUserInformation() {
        FirebaseUser user  = mAuth.getCurrentUser();
        if (user != null){
            editTextEmail.setText(user.getEmail());
        }

    }


    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.buttonPW:
                changePassword();
                break;
            case R.id.buttonSignOut:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.buttonGrocery:
                startActivity(new Intent(this, GroceryActivity.class));
                break;
        }
    }

    private void changePassword(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = user.getEmail();
        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Password change email sent.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}

