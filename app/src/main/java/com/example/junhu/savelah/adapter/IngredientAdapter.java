package com.example.junhu.savelah.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.junhu.savelah.R;
import com.example.junhu.savelah.dataObjects.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private static final String TAG = "Custom_Ingredients";
    // Context + Data:
    private Context context;
    private List<Ingredient> data;
    public class ViewHolder extends RecyclerView.ViewHolder{
        private EditText edit_name;
        private EditText    edit_qty;
        private EditText    edit_qty_name;
        private ImageButton img_btn_del;

        ViewHolder(View v) {
            super(v);

            edit_name = (EditText)      v.findViewById(R.id.edit_item_ingredient_name);
            edit_qty = (EditText)       v.findViewById(R.id.edit_item_ingredient_qty);
            edit_qty_name = (EditText)  v.findViewById(R.id.edit_item_ingredient_qty_name);
            img_btn_del = (ImageButton) v.findViewById(R.id.btn_item_ingredient_del);
        }

        public Ingredient getIngredient() {
            try {
                float amount = Float.valueOf(edit_qty.getText().toString());
                String name = edit_name.getText().toString();
                String qty_name = edit_qty_name.getText().toString();

                if (name.length() + qty_name.length() > 2) {
                    return new Ingredient(name, "01/01/1996",amount,qty_name);
                }

            } catch (NumberFormatException ex) {
                Log.e(TAG, "Error parsing float from EditText in ViewHolder.");
                return null;
            }
            return null;
        }
    }

    public IngredientAdapter(Context context, Boolean load) {
        this.data       = new ArrayList<>();
        this.context    = context;
        if (load){
            addBlankItem();
        }
    }

    public void addBlankItem() {
        if (data != null) {
            data.add(0, new Ingredient("","01/01/1996", (float)0, ""));
            notifyItemInserted(0);
        }
    }

    public void addItem(Ingredient ing){
        if (data != null) {
            data.add(0, new Ingredient(ing.getName(),"01/01/1996", (float)ing.getAmount(), ing.getUnit()));
            notifyItemInserted(0);
        }

    }

    public boolean removeItemAt(int position) {
        if (data != null) {
            if (data.size() > position & data.size() != 1) {
                data.remove(position);
                notifyItemRemoved(position);
                return true;
            }
        }
        return false;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View iv = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_item_ingredient, parent, false);

        return new ViewHolder(iv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Ingredient ing = data.get(position);

        holder.edit_name.setText(ing.getName());
        holder.edit_qty.setText(String.valueOf(ing.getAmount()));
        holder.edit_qty_name.setText(ing.getUnit());
        holder.img_btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItemAt(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Ingredient> getData() {
        return data;
    }

    public void setData(List<Ingredient> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
