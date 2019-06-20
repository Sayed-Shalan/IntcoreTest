package com.sayed.intcoretest.ui.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.sayed.intcoretest.R;
import com.sayed.intcoretest.databinding.ActivityLoginBinding;
import com.sayed.intcoretest.presenter.LoginPresenter;
import com.sayed.intcoretest.repositories.LoginInteractor;
import com.sayed.intcoretest.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity implements LoginView, FacebookCallback<LoginResult> {

    //Dec Data
    ActivityLoginBinding binding;
    CallbackManager callbackManager;
    private LoginPresenter presenter;

    /** ON ACTIVITY RESULT **/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    /** START ON CREATE*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_login);

        initData();//init data
    }

    //Init data
    private void initData() {
        callbackManager = CallbackManager.Factory.create();
        binding.btnFacebookLogin.registerCallback(callbackManager,this);
        presenter=new LoginPresenter(this,new LoginInteractor());
        checkIfUserLoggedIn();
    }

    //remove session
    private void checkIfUserLoggedIn() {
        AccessToken accessToken=AccessToken.getCurrentAccessToken();
        if (accessToken!=null) AccessToken.setCurrentAccessToken(null);
    }

    /** Handle update view methods **/
    @Override
    public void navigateToHome(String email) {
        showSnackMsg(email);
    }

    @Override
    public void showError() {
        showError(getResources().getString(R.string.error_occurred));
    }

    /** Facebook Login result methods **/
    @Override
    public void onSuccess(LoginResult loginResult) {
        presenter.validateCredentials(loginResult);
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onError(FacebookException error) {
        showError(error.getMessage());
    }
}
