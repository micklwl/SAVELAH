package com.example.junhu.savelah;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.junhu.savelah.adapter.IngredientAdapter;
import com.example.junhu.savelah.dataObjects.Ingredient;
import com.example.junhu.savelah.dataObjects.Recipe_DB;
import com.example.junhu.savelah.dataObjects.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddRecipeActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText titleText;
    private EditText servingsText;
    private EditText timeText;
    private EditText instructionsText;
    private String error_msg = "";
    private ProgressBar progressBar;
    private RecyclerView mRecyclerView;
    private IngredientAdapter ingredientAdapter;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private ImageView recipeImg;
    private boolean type = true;
    private String loadedImage;
    private boolean present = false;

    private Recipe_DB recipeDb;
    private Recipe_DB recipeLoad;
    private FirebaseUser user;
    private DatabaseReference initDatabase;
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        titleText = (EditText)findViewById(R.id.edit_add_custom_recipe_title);
        servingsText = (EditText)findViewById(R.id.edit_add_custom_recipe_servings);
        timeText = (EditText)findViewById(R.id.edit_add_custom_recipe_minutes);
        mRecyclerView   = (RecyclerView) findViewById(R.id.rv_add_custom_recipe_ingredients);
        progressBar = (ProgressBar)findViewById(R.id.save_recipe_progress);
        instructionsText = (EditText)findViewById(R.id.edit_add_custom_recipe_instructions);
        recipeImg = (ImageView)findViewById(R.id.img_custom_recipe);

        findViewById(R.id.btn_custom_recipe_ing).setOnClickListener(this);
        findViewById(R.id.add_custom_recipe_ingredients).setOnClickListener(this);
        findViewById(R.id.btn_add_custom_recipe).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.add_custom_recipe_instructions).setOnClickListener(this);
        findViewById(R.id.img_custom_recipe).setOnClickListener(this);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        Intent intent = getIntent();
        Bundle extras_plus = intent.getExtras();
        if(extras_plus != null) {
            type = extras_plus.getBoolean("view");
            recipeLoad = intent.getParcelableExtra("data");
        }

        ingredientAdapter = new IngredientAdapter(this,type);
        mRecyclerView.setAdapter(ingredientAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadData(type);

    }

    // Load data if previous page was a SingleRecipleActivity
    private void loadData(boolean type) {
        if (!type){
            titleText.setText(recipeLoad.getTitle());
            servingsText.setText(String.valueOf(recipeLoad.getServings()));
            timeText.setText(String.valueOf(recipeLoad.getReadyInMinutes()));
            instructionsText.setText(recipeLoad.getInstructions());
            loadedImage = recipeLoad.getImageUrl();

            //check if imageUrl is empty
            if (loadedImage != null && !loadedImage.equals("")) {
                Picasso.get().load(loadedImage).into(recipeImg);
            }
            HashMap<String, Ingredient> ingList = recipeLoad.getIngList();
            for (Map.Entry<String, Ingredient> entry : ingList.entrySet()){
                ingredientAdapter.addItem(entry.getValue());
            }
        }
    }

    @Override
    public void onClick(View view) {
        View gist;
        switch(view.getId()){
            case R.id.add_custom_recipe_ingredients:
                ingredientAdapter.addBlankItem();
                break;

            case R.id.btn_add_custom_recipe:
                progressBar.setVisibility(View.VISIBLE);
                saveRecipe();
                break;
            //hide or unhide the instructions
            case R.id.add_custom_recipe_instructions:
                gist = findViewById(R.id.edit_add_custom_recipe_instructions);
                if (gist.isShown()) {
                    ((TextView) view).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_white, 0);
                    gist.setVisibility(View.GONE);
                } else {
                    ((TextView) view).setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);
                    gist.setVisibility(View.VISIBLE);
                }
                break;
            //hide or unhide the ingredients
            case R.id.btn_custom_recipe_ing:
                gist = findViewById(R.id.rv_add_custom_recipe_ingredients);
                if (gist.isShown()) {
                    ((ImageButton) view).setImageResource(R.drawable.ic_arrow_up_white);
                    gist.setVisibility(View.GONE);
                } else {
                    ((ImageButton) view).setImageResource(R.drawable.ic_arrow_down);
                    gist.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.img_custom_recipe:
                openFileChooser();
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    private void saveRecipe() {
        String title = titleText.getText().toString().trim();
        String servings = servingsText.getText().toString().trim();
        String time = timeText.getText().toString().trim();
        String instructions = instructionsText.getText().toString().trim();
        List<Ingredient> ingredients = getRecipeIngredients();
        HashMap<String, Ingredient> ingList = new HashMap<>();
        if (ingredients!= null) {
            for (Ingredient i : ingredients) {
                ingList.put(i.getName(),i);
            }
        }
        else{
            error_msg += "Error: Check your list of ingredients for missing information!\n";
        }

        if (title.length() < 5)
            error_msg += "Error: Title too short! Describe more!\n";

        if (title.length() > 85)
            error_msg += "Error: Title too long! Shorten it!\n";

        // Checking for Servings given + of correct format:
        int servingsInt = 0;
        if (servings.length() > 0) {
            try {
                servingsInt = Integer.valueOf(servings);
            } catch (NumberFormatException ex) {
                error_msg += "Error: SERVINGS need to be a whole number!\n";
            }
        } else { error_msg += "Error: We need to know how many servings this recipe makes!\n"; }

        // Checking for ReadyInMinutes given + of correct format:
        int readyInMinutes  = 0;
        if (time.length() > 0) {
            try {
                readyInMinutes = Integer.valueOf(time);
            } catch (NumberFormatException ex) {
                error_msg += "Error: TIME REQUIRED needs to be a number of minutes!\n";
            }
        } else { error_msg += "Error: We need to know how long is the time required for cooking is!\n"; }

        if (error_msg.length() == 0){
            recipeDb = new Recipe_DB(title, 0, "", readyInMinutes, servingsInt, ingList, instructions);
            user = FirebaseAuth.getInstance().getCurrentUser();
            initDatabase = FirebaseDatabase.getInstance().getReference("Users");
            if (!type) {
                uploadRecipe(recipeDb);
                initDatabase.child(user.getUid()).child("recipes").child(recipeDb.getTitle()).child("imageUrl").setValue(loadedImage);
                Toast.makeText(AddRecipeActivity.this, "Changes saved!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                finish();
                startActivity(new Intent(AddRecipeActivity.this, RecipeActivity.class));
            }
            else if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(AddRecipeActivity.this,"Image Upload in progress!",Toast.LENGTH_SHORT).show();
            }
            else{
                uploadRecipe(recipeDb);
                uploadFile();
            }
        }
        else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(AddRecipeActivity.this, error_msg, Toast.LENGTH_LONG).show();
            error_msg = "";
        }
    }

    private List<Ingredient> getRecipeIngredients() {
        // Prepare a list for our Ingredients:
        List<Ingredient> ingredientList = new ArrayList<>();

        // Loop through RecyclerView and get the Ingredient details:
        for (int i=0; i < mRecyclerView.getChildCount(); i++) {
            IngredientAdapter.ViewHolder vh = (IngredientAdapter.ViewHolder) mRecyclerView.findViewHolderForLayoutPosition(i);
            Ingredient ingredient = vh.getIngredient();
            if (ingredient == null || ingredient.getAmount() == 0) {
                return null;
            }
            ingredientList.add(ingredient);
        }
        return ingredientList;
    }

    private void uploadRecipe(Recipe_DB recipeDb) {
        if (!type) initDatabase.child(user.getUid()).child("recipes").child(recipeLoad.getTitle()).removeValue();
        initDatabase.child(user.getUid()).child("recipes").child(recipeDb.getTitle()).setValue(recipeDb);
    }

    private void uploadFile() {
        if (mImageUri != null){
            final String current = System.currentTimeMillis() + "." + getFileExtension(mImageUri);
            final StorageReference fileReference = mStorageRef.child(current);
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mStorageRef.child(current).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            Upload upload = new Upload(uri.toString());
                            initDatabase.child(user.getUid()).child("recipes").child(recipeDb.getTitle()).child("imageUrl").setValue(upload.getmImageUrl());
                            Log.d("uri", "onSuccess: " + upload.getmImageUrl());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });

                    Toast.makeText(AddRecipeActivity.this,"Changes saved!",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    finish();
                    startActivity(new Intent(AddRecipeActivity.this, RecipeActivity.class));
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddRecipeActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    //double progress = (100.0 * taskSnapshot.getBytesTransferred() /taskSnapshot.getTotalByteCount());
                    //progressBar.setProgress((int)progress);
                }
            });
        }
        else {
            Toast.makeText(AddRecipeActivity.this,"No image was uploaded for your recipe!",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            finish();
            startActivity(new Intent(AddRecipeActivity.this, RecipeActivity.class));
        }
    }

    private String getFileExtension (Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(recipeImg);
            recipeImg.setImageURI(mImageUri);
            type = true;
        }
    }
}
