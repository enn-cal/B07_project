package com.example.appsimulator;

public interface Contract {
    public interface Model {
        public boolean userExists(User user);
        public void addUserDB(User user, String spinner);
    }

    public interface View {
        public void displayError(String error, String type);
    }
    public interface Presenter {
        public boolean isValid(String input, String type);
        public void addUser(User user, String spinner);
    }
}
