package com.example.appsimulator;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    public void setEmail(){
        this.email = findViewById(R.id.editTextTextEmailAddress);
    }

    public String getEmail(){
        return this.email.getText().toString();
    }

    public void setPassword(){
        this.password = findViewById(R.id.editTextTextPassword);
    }

    public String getPassword(){
        return this.password.getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenterLogin = new PresenterLogin(FirebaseDatabase.getInstance(), FirebaseDatabase.getInstance().getReference(), this);


        //Log.i("TestMessage", "HELLO");

        login = (Button) findViewById(R.id.button);
        setEmail();
        setPassword();
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                presenterLogin.start(getEmail(),getPassword());
            }
        });

    }

    public void registerNewUser(View v)
    {
        Intent intent = new Intent(this, RegisterUser.class);
        startActivity(intent);
//        TextView tv= (TextView) findViewById(R.id.text_view);
//
//        //alter text of textview widget
//        tv.setText("This text view is clicked");
//
//        //assign the textview forecolor
//        tv.setTextColor(Color.GREEN);
    }

    @Override
    public void loginSuccess() {
        startActivity(new Intent(this, ProfilePage.class));
    }

    @Override
    public void loginFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}