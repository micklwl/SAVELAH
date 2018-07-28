package com.example.junhu.savelah;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AddGroceryDialog extends AppCompatDialogFragment {
    private EditText addQuantity;
    private EditText unit;
    private AddGroceryDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog  = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_grocery, null);
        addQuantity = view.findViewById(R.id.addQuantity);
        unit = view.findViewById(R.id.unit);
        dialog.setView(view).setTitle("Add Grocery")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String quantity = addQuantity.getText().toString().trim();
                String str_unit = unit.getText().toString().trim();
                listener.applyText(quantity, str_unit);
            }
        });
        return dialog.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (AddGroceryDialogListener) context;
    }
}

interface AddGroceryDialogListener {
    void applyText(String quantity, String unit);
}
