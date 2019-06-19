package com.sayed.intcoretest.presenter;

import android.os.Bundle;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.sayed.intcoretest.ui.login.LoginView;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenter {

    //declare data
    private LoginView loginView;

    //Constructor
    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    //perform logic for login result
    public void validateCredentials(LoginResult loginResult){
        // App code
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            loginView.navigateToHome(response.getJSONObject().getString("email"));


                        } catch (JSONException e) {
                            e.printStackTrace();
                            loginView.showError();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender");
        request.setParameters(parameters);
        request.executeAsync();

    }

}
