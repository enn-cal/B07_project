package com.example.appsimulator;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;


public class RegisterUser extends AppCompatActivity {
    private EditText Name, password, dob, PostalCode, email;
    private Spinner accountSpinner;
    private FirebaseDatabase f_db;
    private DatabaseReference ref;


    /**
     * Helper method for onCreate. Checks if string input are valid or not and prompts user to
     * resubmit info if invalid.
     * @param input : The user's input in the EditText
     * @param type : The type of info the user inputted
     */
    private boolean isValid(String input, String type){
        //if no input was given
        boolean v = true;
        if(input.isEmpty()){
            return false;
        }
        //Consider the type to compare corresponding regex with
        switch(type){
            case "name":
                if(!(Pattern.compile("[A-Za-z]+ [A-Za-z]").matcher(input).matches())) {
                    v = false;
                }
                break;
            case "email":
                if(!(Patterns.EMAIL_ADDRESS.matcher(input).matches())){
                    v = false;
                }
            case "pwd":
                //All non-empty passwords are currently valid
                break;

            case "dob":
                if(!(Pattern.compile("(0[1-9]|1[012])[/](0[1-9]|[12][0-9]|3[01])[/](19|20)\\d\\d")).matcher(input).matches()){
                    v = false;
                }
                break;
            case "postal":
                if(!(Pattern.compile("[ABCEGHJ-NPRSTVXY]\\d[ABCEGHJ-NPRSTV-Z][ -]\\d[ABCEGHJ-NPRSTV-Z]\\d")).matcher(input).matches()){
                    v = false;
                }
                break;
        }
        return v;
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
        //FirebaseAuth mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting data from user
                String name = Name.getText().toString();
                if(!(isValid(name, "name"))) {
                    Name.setError("Invalid Name. Try Again");
                    Name.requestFocus();
                    return;
                }
                String pwd = password.getText().toString();
                if(!(isValid(pwd, "pwd"))) {
                    password.setError("Invalid Password. Try Again");
                    password.requestFocus();
                    return;
                }
                String bday = dob.getText().toString();
                if(!(isValid(bday, "dob"))) {
                    dob.setError("Invalid DOB. Try Again");
                    dob.requestFocus();
                    return;
                }
                String pCode = PostalCode.getText().toString();
                if(!(isValid(pCode, "postal"))) {
                    PostalCode.setError("Invalid Postal Code. Try Again");
                    PostalCode.requestFocus();
                    return;
                }
                String em = email.getText().toString();
                if(!(isValid(em, "email"))) {
                    email.setError("Invalid Email. Try Again");
                    email.requestFocus();
                    return;
                }
                String spinnerString = accountSpinner.getSelectedItem().toString();

                boolean isCustomer;
                isCustomer = spinnerString.equals("Customer");

                // validating input (todo)

                // adding data into database
                f_db = FirebaseDatabase.getInstance();
                ref = f_db.getReference();

                User user = new User(name, em, pwd, pCode, bday, isCustomer); // creates new user
                ref.child("Users").child(spinnerString).child(Integer.toString(user.hashCode())).setValue(user); // adds in database


                // leave this section

//                mAuth.createUserWithEmailAndPassword(name,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            Toast.makeText(RegisterUser.this,"User Registered...", Toast.LENGTH_SHORT).show(); //output
//                            Intent i = new Intent(RegisterUser.this, MainActivity.class); // calls main activity
//                            startActivity(i);
//                            finish();
//                        }else {
//                            Toast.makeText(RegisterUser.this,"Failed to Register..",Toast.LENGTH_SHORT).show();//output
//                        }
//
//                    }
//                });

            }
        });

    }
}