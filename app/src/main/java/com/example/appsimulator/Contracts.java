package com.example.appsimulator;

public interface Contracts {
    public interface ModelLogin {
        public void login(String email, String password);
        public boolean loginError(String email, String password);
        public boolean match(String email, String password);
    }

    public interface ViewLogin {
        public void loginSuccess();
        public void loginFailed(String message);
    }
    public interface PresenterLogin {
        public void start (String email, String password);
        public void loginSuccess();
        public void loginFailed(String message);
    }
}
