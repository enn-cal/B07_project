package com.example.appsimulator;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements Contracts.ViewLogin {

    private FirebaseDatabase f_db;
    private DatabaseReference ref;
    private EditText email, password;
    private Button login;
    private Contracts.PresenterLogin presenterLogin;
    private Spinner userType;

    public void setEmail(){
        this.email = findViewById(R.id.editTextTextEmailAddress);
    }

    public String getEmail(){
        return this.email.getText().toString();
    }

    public void setPassword(){
        this.password = findViewById(R.id.editTextTextPassword);
    }

    public String getPassword(){ return this.password.getText().toString(); }

    public void setUserType(){
        this.userType = findViewById(R.id.spinner);
    }

    public String getUserType(){ return this. userType.getSelectedItem().toString(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenterLogin = new PresenterLogin(FirebaseDatabase.getInstance(), FirebaseDatabase.getInstance().getReference(), this);

        login = findViewById(R.id.button);
        setEmail();
        setPassword();
        setUserType();

        DatabaseReference m = FirebaseDatabase.getInstance().getReference();
       // m.push().setValue("Stores"); -> what does this do ?

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                presenterLogin.start(getEmail(),getPassword(), getUserType());
            }
        });

    }

    public void registerNewUser(View v)
    {
        Intent intent = new Intent(this, RegisterUser.class);
        startActivity(intent);
    }

    @Override
    public void loginSuccess(String ID) {
        if(getUserType().equals("Store Owner")){
            Intent intent = new Intent(this, OwnerScreen.class);
            intent.putExtra("ID", ID);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, ProfilePage.class);
            intent.putExtra("ID", ID);
            startActivity(intent);
        }
        //startActivity(new Intent(this, ProfilePage.class));
    }

    @Override
    public void loginFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}