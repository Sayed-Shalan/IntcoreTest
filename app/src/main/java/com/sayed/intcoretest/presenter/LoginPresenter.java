package com.sayed.intcoretest.presenter;

import com.facebook.AccessToken;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.sayed.intcoretest.repositories.LoginInteractor;
import com.sayed.intcoretest.ui.login.LoginView;
import com.sayed.intcoretest.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenter implements LoginInteractor.OnLoginFinishedListener {

    //declare data
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    //Constructor
    public LoginPresenter(LoginView loginView,LoginInteractor loginInteractor) {
        this.loginView = loginView;
        this.loginInteractor=loginInteractor;
    }

    //perform logic for login result
    public void validateCredentials(LoginResult loginResult){
        // App code
        loginInteractor.getGraphResponseData(loginResult,this);

    }

    //check if user logged in
    public void checkIfUserLoggedIn(SPUtils spUtils){
        if (AccessToken.getCurrentAccessToken()!=null&&spUtils.getUser()!=null){
            loginView.startHomeActivity();
        }
    }

    @Override
    public void onSuccess(JSONObject object, GraphResponse response) {
        try {
            loginView.navigateToHome(object.getString("first_name"),object.getString("last_name"));
        } catch (JSONException e) {
            e.printStackTrace();
            loginView.showError();
        }
    }
}
