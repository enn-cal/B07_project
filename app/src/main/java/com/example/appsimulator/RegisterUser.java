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


public class RegisterUser extends AppCompatActivity implements Contract.View{
    private EditText Name, password, dob, PostalCode, email;
    private Spinner accountSpinner;
    private FirebaseDatabase f_db;
    private DatabaseReference ref;

    private Contract.Presenter presenter;

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
        presenter = new Presenter(new Model(FirebaseDatabase.getInstance(),FirebaseDatabase.getInstance().getReference()), this);
        //private ProgressBar loading;
        //FirebaseAuth mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting data from user
                String name = Name.getText().toString();
                if(!(presenter.isValid(name, "name")))return;
                String bday = dob.getText().toString();
                if(!(presenter.isValid(bday, "dob")))return;
                String pCode = PostalCode.getText().toString();
                if(!(presenter.isValid(pCode, "postal")))return;
                String pwd = password.getText().toString();
                if(!(presenter.isValid(pwd, "pwd")))return;
                String em = email.getText().toString();
                if(!(presenter.isValid(em, "email")))return;
                String spinnerString = accountSpinner.getSelectedItem().toString();

                boolean isCustomer;
                isCustomer = spinnerString.equals("Customer");

                // validating input (todo)

                // adding data into database
                //f_db = FirebaseDatabase.getInstance();
                //ref = f_db.getReference();
                presenter.addUser(new User(name, em, pwd, pCode, bday, isCustomer), spinnerString);
                //User user = new User(name, em, pwd, pCode, bday, isCustomer); // creates new user
                //ref.child("Users").child(spinnerString).child(Integer.toString(user.hashCode())).setValue(user); // adds in database


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