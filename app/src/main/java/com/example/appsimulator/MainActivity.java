package com.example.appsimulator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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