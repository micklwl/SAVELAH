package com.example.junhu.savelah;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.junhu.savelah.R;

public class ChangeQuantityDialog extends AppCompatDialogFragment {
    private TextView saved;
    private TextView change;
    private EditText changeQuantity;
    private EditText changeUnit;
    private ChangeQuantityDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder  = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.change_quantity, null);
        Bundle bundle = getArguments();
        String ingDB = bundle.getString("Database","");
        String ingAdd = bundle.getString("Adding","");
        final String name = bundle.getString("Name","");
        builder.setView(view).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String quantity = changeQuantity.getText().toString().trim();
         //       Float quantity = Float.valueOf(changeQuantity.getText().toString().trim());
                String unit = changeUnit.getText().toString().trim();
                listener.applyTexts(quantity,unit, name);
            }
        });

        changeQuantity = view.findViewById(R.id.quantity);
        changeUnit = view.findViewById(R.id.unit);
        saved = view.findViewById(R.id.resultsDB);
        change = view.findViewById(R.id.toAdd);
        saved.setText(ingDB);
        change.setText(ingAdd);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ChangeQuantityDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ChangeQuantiyDialogListener");
        }
    }

    public interface ChangeQuantityDialogListener{
        // change float to String
        void applyTexts(String quantityResult, String unitResult, String name);
    }
}
