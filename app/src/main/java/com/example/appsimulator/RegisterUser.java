package com.example.appsimulator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterUser extends AppCompatActivity {
    private EditText Name, password, dob, PostalCode, email;
    private Spinner accountSpinner;
    private FirebaseDatabase f_db;
    private DatabaseReference ref;

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
                String pwd = password.getText().toString();
                String bday = dob.getText().toString();
                String pCode = PostalCode.getText().toString();
                String em = email.getText().toString();
                String spinnerString = accountSpinner.getSelectedItem().toString();

                boolean isCustomer;
                if(spinnerString.equals("Customer"))
                    isCustomer = true;
                else
                    isCustomer = false;

                // validating input (todo)

                // adding data into database
                f_db = FirebaseDatabase.getInstance();
                ref = f_db.getReference();

                User user = new User(name, em, pwd, pCode, bday, isCustomer); // creates new user
                ref.child("Users").child("Customer").child(Integer.toString(user.get_counter())).setValue(user); // adds in database


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