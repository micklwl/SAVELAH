package com.example.junhu.savelah.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.junhu.savelah.R;
import com.example.junhu.savelah.dataObjects.Meal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MealListAdapter extends ArrayAdapter<Meal> {
    ArrayList<Meal> meals;
    Context context;
    int resource;

    public MealListAdapter(Context context, int resource, ArrayList<Meal> meals) {
        super(context, resource, meals);
        this.context = context;
        this.resource = resource;
        this.meals = meals;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.meal_planner, null, true);
        }
        Meal meal = this.getItem(position);
        ImageView image = convertView.findViewById(R.id.imageView);
        if (meal.getRecipe().getImage() != null && !meal.getRecipe().getImage().equals("")) {
            Picasso.get().load(meal.getRecipe().getImage()).into(image);
        }
        TextView title = convertView.findViewById(R.id.recipeTitle);
        TextView timePlaceHolder = convertView.findViewById(R.id.time);
        title.setText(meal.getRecipe().getTitle());
        timePlaceHolder.setText(meal.getTime());
        //   if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        //       title.setText(Html.fromHtml(recipe.getTitle(), Html.FROM_HTML_MODE_LEGACY));
        //   } else {
        //       title.setText(Html.fromHtml(recipe.getTitle()));
        //   }
        return convertView;
    }
}
