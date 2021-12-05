package com.example.appsimulator;

import android.util.Patterns;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class PresenterLogin implements Contracts.PresenterLogin {

    private Contracts.ModelLogin model;
    private Contracts.ViewLogin view;

    public PresenterLogin(Contracts.ViewLogin view){
        model = new ModelLogin(this);
        this.view = view;
    }

    @Override
    public void start(String email, String password, String userType) {
        model.login(email, password, userType);
    }

    @Override
    public void loginSuccess(String ID) {
        view.loginSuccess(ID);
    }

    @Override
    public void loginFailed(String message) {
        view.loginFailed(message);
    }
}
