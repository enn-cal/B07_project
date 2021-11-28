package com.example.appsimulator;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.regex.Pattern;

public class addProductDialog extends AppCompatDialogFragment {

    private EditText editTextItemName;
    private EditText editTextItemBrand;
    private EditText editTextItemPrice;
    private EditText editTextItemQuantity;
    private Products p = new Products();

    public boolean isValid(String input, String type){
        //Consider the type to compare corresponding regex with
        boolean v = true;
        switch(type){
            case "itemName":
                if(input.isEmpty()){
                    displayError("Empty Item Name. Try Again", "itemName");
                    v = false;
                }
                break;
            case "brandName":
                if(input.isEmpty()){
                    displayError("Empty Brand Name. Try Again", "brandName");
                    v = false;
                }
                break;
            case "itemPrice":
                if(input.isEmpty()){
                    displayError("Empty Price. Try Again", "itemPrice");
                    v = false;
                }
                else if(!(Pattern.compile("\\$\\d+\\.\\d{1,2}")).matcher(input).matches()){
                    displayError("Invalid Price. Try Again", "itemPrice");
                    v = false;
                }
                break;
            case "itemQuantity":
                if(input.isEmpty()){
                    displayError("Empty Quantity. Try Again", "itemQuantity");
                    v = false;
                }
                else if(!(Pattern.compile("[1-9][0-9]*")).matcher(input).matches()){
                    displayError("Invalid Quantity. Try Again", "itemQuantity");
                    v = false;
                }
                break;
        }
        return v;
    }

    public void displayError(String error, String type) {
        //displays the corresponding error based on type of info
        switch(type){
            case "itemName":
                editTextItemName.setError(error);
                editTextItemName.requestFocus();
                return;
            case "brandName":
                editTextItemBrand.setError(error);
                editTextItemBrand.requestFocus();
                return;
            case "itemPrice":
                editTextItemPrice.setError(error);
                editTextItemPrice.requestFocus();
                return;
            case "itemQuantity":
                editTextItemQuantity.setError(error);
                editTextItemQuantity.requestFocus();
                return;
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_product_dialog,null);

        //create a view with the layout add_product_dialog
        builder.setView(view).setTitle("Add a Product to Store").setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
        .setPositiveButton("ADD", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        editTextItemName = view.findViewById(R.id.add_item);
        editTextItemBrand = view.findViewById(R.id.add_brand);
        editTextItemPrice = view.findViewById(R.id.add_price);
        editTextItemQuantity = view.findViewById(R.id.add_quantity);
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null){
            Button pd = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            pd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String itemName = editTextItemName.getText().toString();
                    if(!isValid(itemName, "itemName")) return;

                    String brandName = editTextItemBrand.getText().toString();
                    if(!isValid(brandName, "brandName")) return;

                    String itemPrice = editTextItemPrice.getText().toString();
                    if(!(isValid(itemPrice, "itemPrice")))return;

                    String itemQuantity = editTextItemQuantity.getText().toString();
                    if(!isValid(itemQuantity, "itemQuantity")) return;

                    p.setItem(itemName);
                    p.setBrand(brandName);
                    p.setPrice(itemPrice);
                    p.setQuantity(itemQuantity);
                    d.dismiss();
                }
            });
        }
    }

    public Products getProduct(){
        return p;
    }

}
