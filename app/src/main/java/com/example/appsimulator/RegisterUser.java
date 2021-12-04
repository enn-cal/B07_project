package com.example.appsimulator;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;


public class RegisterUser extends AppCompatActivity{
    private EditText Name, password, dob, PostalCode, email;
    private Spinner accountSpinner;
    private Calendar calendar = Calendar.getInstance();
    private int day, month, year;


    public boolean isValid(String input, String type) {
        //Consider the type to compare corresponding regex with
        boolean v = true;
        switch(type){
            case "name":
                if(input.isEmpty()){
                    displayError("Empty Name. Try Again", "name");
                    v = false;
                }
                else if(!(Pattern.compile("[A-Za-z]+( [A-Za-z]* | )[A-Za-z]+").matcher(input).matches())) {
                    displayError("Invalid Name. Try Again", "name");
                    v = false;
                }
                break;
            case "email":
                if(input.isEmpty()){
                    displayError("Empty Email. Try Again", "email");
                    v = false;
                }
                else if(!(Patterns.EMAIL_ADDRESS.matcher(input).matches())){
                    displayError("Invalid Email. Try Again", "email");
                    v = false;
                }
                break;
            case "pwd":
                if(input.isEmpty()){
                    displayError("Empty Password. Try Again", "pwd");
                    v = false;
                }
                break;

            case "dob":
                if(input.isEmpty()){
                    displayError("Empty DOB. Try Again", "dob");
                    v = false;
                }
                else if(!(Pattern.compile("(0[1-9]|1[012])[/](0[1-9]|[12][0-9]|3[01])[/](19|20)\\d\\d")).matcher(input).matches()) {
                    displayError("Invalid DOB. Try Again", "dob");
                    v = false;
                }
                break;
            case "postal":
                if(input.isEmpty()){
                    displayError("Empty Postal Code. Try Again", "postal");
                    v = false;
                }
                else if(!(Pattern.compile("[ABCEGHJ-NPRSTVXY]\\d[ABCEGHJ-NPRSTV-Z][ -]\\d[ABCEGHJ-NPRSTV-Z]\\d")).matcher(input).matches()){
                    displayError("Invalid Postal Code. Try Again", "postal");
                    v = false;
                }
                break;
        }
        return v;
    }

    //@Override
    public void displayError(String error, String type) {
        //displays the corresponding error based on type of info
        switch(type){
            case "name":
                Name.setError(error);
                Name.requestFocus();
                return;
            case "email":
                email.setError(error);
                email.requestFocus();
                return;
            case "pwd":
                password.setError(error);
                password.requestFocus();
                return;
            case "dob":
                dob.setError(error);
                dob.requestFocus();
                return;
            case "postal":
                PostalCode.setError(error);
                PostalCode.requestFocus();
                return;
        }
    }

    private void addToFirebase(User user, String userType){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(userType);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean userExists = false;
                for (DataSnapshot ds : snapshot.getChildren()){
                    if (ds.getKey().equals(Integer.toString(user.hashCode()))) { // user exists will output error
                        userExists = true;
                        break;
                    }
                }
                if (!userExists) {
                    ref.child(Integer.toString(user.hashCode())).setValue(user); // adds in database
                }
                else {
                    Toast.makeText(RegisterUser.this,"User Already Exists, Please Login",
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
/*
    private void addToFirebase(Stores store){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        //if store has no products
        if(store.products.size() == 0){
            ref.child("Stores").child(Integer.toString(store.getStoreID()));
            return;
        }
        //if store has products
        for(Products p: store.products){
            ref.child("Stores").child(Integer.toString(store.getStoreID())).child(Integer.toString(p.hashCode())).setValue(p);
        }
    }

    private void addToFirebase(Orders o){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders");
        //if user has no orders
        if(o.order.size() == 0){
            ref.child(Integer.toString(o.getCustomerID())).setValue(o.order);
            return;
        }
        //if user has orders
        for(Products p: o.order){
            ref.child(Integer.toString(o.getCustomerID())).child(Integer.toString(p.hashCode())).setValue(p);
        }
    }

 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        Name = findViewById(R.id.editTextTextPersonName3);
        PostalCode = findViewById(R.id.editTextTextPostalAddress2);
        email = findViewById(R.id.editTextTextEmailAddress3);
        password = findViewById(R.id.editTextTextPassword3);
        Button register = findViewById(R.id.button3);
        accountSpinner = findViewById(R.id.spinner);

        dob = (EditText)findViewById(R.id.editTextDate3);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarPopUp();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Orders").child("Store Owner").child("1902570695").child("Products");
                // getting data from user
                String name = Name.getText().toString();
                if(!(isValid(name, "name")))return;
                String bday = dob.getText().toString();
                if(!(isValid(bday, "dob")))return;
                String pCode = PostalCode.getText().toString();
                if(!(isValid(pCode, "postal")))return;
                String em = email.getText().toString();
                if(!(isValid(em, "email")))return;
                String pwd = password.getText().toString();
                if(!(isValid(pwd, "pwd")))return;
                String spinnerString = accountSpinner.getSelectedItem().toString();

                // adding data into database
                //if user is Store Owner
                //Log.i("TAG", spinnerString);
                User user = new User(name, em, pwd, pCode, bday);
                addToFirebase(user,spinnerString);
                /*
                  following code is to manually enter data in database for storeowner  1902570695
                  feel free to remove it or keep it if you want to add data manually again.
                 */
//                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child("Store Owner").child("1902570695").child("Products");
//                Products products = new Products("Pizza6", "Dominoes", "$500", "20");
//                ref.child(Integer.toString(products.hashCode())).setValue(products);

            }
        });
    }

    public void CalendarPopUp(){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                dob.setText(month+1 + "/" + day + "/" + year);
            }
        };

        DatePickerDialog popUp = new DatePickerDialog(RegisterUser.this, listener, year, month, day);
        popUp.show();
    }
}