package com.example.appsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

//    private FirebaseDatabase f_db;
//    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        f_db = FirebaseDatabase.getInstance();
//        ref = f_db.getReference();
    }

//    public void sendMessage(View view) {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }

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

}