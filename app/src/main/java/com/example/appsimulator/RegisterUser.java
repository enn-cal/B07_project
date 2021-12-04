package com.example.appsimulator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class RegisterUser extends AppCompatActivity{
    private EditText Name, password, dob, PostalCode, email;
    private Spinner accountSpinner;


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
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean userExists = false;
                Toast toast = Toast.makeText(RegisterUser.this, "", Toast.LENGTH_LONG);

                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.getKey().equals(Integer.toString(user.hashCode()))) { // user exists will output error
                        userExists = true;
                        break;
                    }
                }
                if (!userExists) {
                    ref.child(Integer.toString(user.hashCode())).setValue(user); // adds in database
                } else {
                    toast.setText("User Already Exists, Please Login");
                    toast.show();
                    toast.setText("");
                }
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

                /*
                  following code is to manually enter data in database
                  feel free to remove it or keep it if you want to add data manually again.
                 */
//                String em = email.getText().toString();
//                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Users").child("Store Owner").child("1902570695").child("Customers");
//                Products products = new Products("Pie", "Pizza Hut", "$12.5", "30");
//                Products products2 = new Products("Pizza", "Pizza Hut", "$50", "1");
//                Orders o = new Orders("5059");
//                o.addProduct(products);
//                o.addProduct(products2);
//                customerStore cs = new customerStore(em,o.storeID);
//                cs.setOrder(o);
//                String key_path = ref1.push().getKey();
//                ref1.child(key_path).setValue(cs);
//                int i = 0;
//                for (Products p : o.order)
//                    ref1.child(key_path).child("order").child(Integer.toString(++i)).setValue(p);




    }

    public void clickRegister(View v){
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


        User user = new User(name, em, pwd, pCode, bday);
        addToFirebase(user,spinnerString);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");

        ChildEventListener childLister = ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Intent intent;
                if (spinnerString.equals("Store Owner")) {
                    intent = new Intent(RegisterUser.this, OwnerScreen.class);
                } else {
                    intent = new Intent(RegisterUser.this, CustomerScreen.class);
                }
                intent.putExtra("ID", Integer.toString(user.hashCode()));
                startActivity(intent);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}