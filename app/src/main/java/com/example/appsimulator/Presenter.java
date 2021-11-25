package com.example.appsimulator;

import android.util.Patterns;

import java.util.regex.Pattern;

public class Presenter implements Contract.Presenter{

    private Contract.Model model;
    private Contract.View view;

    public Presenter(Contract.Model model, Contract.View view){
        this.model = model;
        this.view = view;
    }

    /**
     * Checks if user input is valid or not and prompts user to
     * resubmit info if invalid.
     * @param input : The user's input in the EditText
     * @param type : The type of info the user inputted
     */
    @Override
    public boolean isValid(String input, String type) {
        //Consider the type to compare corresponding regex with
        boolean v = true;
        switch(type){
            case "name":
                if(input.isEmpty()){
                    view.displayError("Empty Name. Try Again", "name");
                    v = false;
                }
                if(!(Pattern.compile("[A-Za-z]+( [A-Za-z]* | )[A-Za-z]+").matcher(input).matches())) {
                    view.displayError("Invalid Name. Try Again", "name");
                    v = false;
                }
                break;
            case "email":
                if(input.isEmpty()){
                    view.displayError("Empty Email. Try Again", "email");
                    v = false;
                }
                if(!(Patterns.EMAIL_ADDRESS.matcher(input).matches())){
                    view.displayError("Invalid Email. Try Again", "email");
                    v = false;
                }
            case "pwd":
                if(input.isEmpty()){
                    view.displayError("Empty Password. Try Again", "pwd");
                    v = false;
                }
                break;

            case "dob":
                if(input.isEmpty()){
                    view.displayError("Empty DOB. Try Again", "dob");
                    v = false;
                }
                if(!(Pattern.compile("(0[1-9]|1[012])[/](0[1-9]|[12][0-9]|3[01])[/](19|20)\\d\\d")).matcher(input).matches()) {
                    view.displayError("Invalid DOB. Try Again", "dob");
                    v = false;
                }
                break;
            case "postal":
                if(input.isEmpty()){
                    view.displayError("Empty Postal Code. Try Again", "postal");
                    v = false;
                }
                if(!(Pattern.compile("[ABCEGHJ-NPRSTVXY]\\d[ABCEGHJ-NPRSTV-Z][ -]\\d[ABCEGHJ-NPRSTV-Z]\\d")).matcher(input).matches()){
                    view.displayError("Invalid Postal Code. Try Again", "postal");
                    v = false;
                }
                break;
        }
        return v;
    }

    @Override
    public void addUser(User user, String spinner) {
       model.addUserDB(user, spinner);
    }
}
