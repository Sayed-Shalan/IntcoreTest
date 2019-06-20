package com.sayed.intcoretest.repositories;

import android.os.Bundle;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

public class LoginInteractor {


    //get graph response data
    public void getGraphResponseData(LoginResult loginResult,OnLoginFinishedListener listener){
        // Application code
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                listener::onSuccess);
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    /**
     * Login interactor interface
     */
    public interface OnLoginFinishedListener{
        void onSuccess(JSONObject object, GraphResponse response);
    }
}
