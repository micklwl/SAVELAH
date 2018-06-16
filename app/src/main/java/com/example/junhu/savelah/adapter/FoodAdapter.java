//package com.example.junhu.savelah.adapter;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.example.junhu.savelah.R;
//import com.example.junhu.savelah.dataObjects.HTTP_RecipeShort;
//import com.example.junhu.savelah.dataObjects.Recipe_Short;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class FoodAdapter extends ArrayAdapter<Recipe_Short> {
//    ArrayList<Recipe> recipes;
//
//    public FoodAdapter (Context context,ArrayList<Recipe_Short> recipe_Short) {
//        super(context, 0,recipe_Short );
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
//        Recipe_Short user = getItem(position);
//        // Check if an existing view is being reused, otherwise inflate the view
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_search_list, parent, false);
//        }
//        // Lookup view for data population
//        TextView tvName = (TextView) convertView.findViewById(R.id.id);
//        TextView tvHome = (TextView) convertView.findViewById(R.id.title);
//        // Populate the data into the template view using the data object
//        tvName.setText(user.id);
//        tvHome.setText(user.title);
//        // Return the completed view to render on screen
//        return convertView;
//    }
//
//
//}
