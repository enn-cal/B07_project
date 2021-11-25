package com.example.appsimulator;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;


public class RegisterUser extends AppCompatActivity implements Contract.View{
    private EditText Name, password, dob, PostalCode, email;
    private Spinner accountSpinner;


    public boolean isValid(String input, String type) {
        //Consider the type to compare corresponding regex with
        boolean v = true;
//        switch(type){
//            case "name":
//                if(input.isEmpty()){
//                    displayError("Empty Name. Try Again", "name");
//                    v = false;
//                }
//                if(!(Pattern.compile("[A-Za-z]+( [A-Za-z]* | )[A-Za-z]+").matcher(input).matches())) {
//                    displayError("Invalid Name. Try Again", "name");
//                    v = false;
//                }
//                break;
//            case "email":
//                if(input.isEmpty()){
//                    displayError("Empty Email. Try Again", "email");
//                    v = false;
//                }
//                if(!(Patterns.EMAIL_ADDRESS.matcher(input).matches())){
//                    displayError("Invalid Email. Try Again", "email");
//                    v = false;
//                }
//            case "pwd":
//                if(input.isEmpty()){
//                    displayError("Empty Password. Try Again", "pwd");
//                    v = false;
//                }
//                break;
//
//            case "dob":
//                if(input.isEmpty()){
//                    displayError("Empty DOB. Try Again", "dob");
//                    v = false;
//                }
//                if(!(Pattern.compile("(0[1-9]|1[012])[/](0[1-9]|[12][0-9]|3[01])[/](19|20)\\d\\d")).matcher(input).matches()) {
//                    displayError("Invalid DOB. Try Again", "dob");
//                    v = false;
//                }
//                break;
//            case "postal":
//                if(input.isEmpty()){
//                    displayError("Empty Postal Code. Try Again", "postal");
//                    v = false;
//                }
//                if(!(Pattern.compile("[ABCEGHJ-NPRSTVXY]\\d[ABCEGHJ-NPRSTV-Z][ -]\\d[ABCEGHJ-NPRSTV-Z]\\d")).matcher(input).matches()){
//                    displayError("Invalid Postal Code. Try Again", "postal");
//                    v = false;
//                }
//                break;
//        }
        return v;
    }

    @Override
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
                if (userExists == false) {
                    ref.child(Integer.toString(user.hashCode())).setValue(user); // adds in database
                    return;
                }
                else if (userExists)
                    Toast.makeText(RegisterUser.this,"User Already Exists, Please Login",
                            Toast.LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Name = findViewById(R.id.editTextTextPersonName3);
        dob = findViewById(R.id.editTextDate3);
        PostalCode = findViewById(R.id.editTextTextPostalAddress2);
        email = findViewById(R.id.editTextTextEmailAddress3);
        password = findViewById(R.id.editTextTextPassword3);
        Button register = findViewById(R.id.button3);
        accountSpinner = findViewById(R.id.spinner);
        //private ProgressBar loading;

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting data from user
                String name = Name.getText().toString();
                if(!(isValid(name, "name")))return;
                String bday = dob.getText().toString();
                if(!(isValid(bday, "dob")))return;
                String pCode = PostalCode.getText().toString();
                if(!(isValid(pCode, "postal")))return;
                String pwd = password.getText().toString();
                if(!(isValid(pwd, "pwd")))return;
                String em = email.getText().toString();
                if(!(isValid(em, "email")))return;
                String spinnerString = accountSpinner.getSelectedItem().toString();


                // adding data into database
                User user = new User(name, em, pwd, pCode, bday); // creates new user
                addToFirebase(user,spinnerString);
            }
        });

    }
}