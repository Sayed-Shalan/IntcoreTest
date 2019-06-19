package com.sayed.intcoretest.ui.login;

public interface LoginView {

    void navigateToHome(String email);//if login success
    void showError();//if login failed
}
