package com.sayed.intcoretest.ui.login;

public interface LoginView {

    void navigateToHome(String first_name,String last_name);//if login success
    void showError();//if login failed
    void startHomeActivity();
}
