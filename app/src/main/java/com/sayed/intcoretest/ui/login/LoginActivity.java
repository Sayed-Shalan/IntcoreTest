package com.sayed.intcoretest.ui.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.sayed.intcoretest.R;
import com.sayed.intcoretest.databinding.ActivityLoginBinding;
import com.sayed.intcoretest.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    //Dec Data
    ActivityLoginBinding binding;

    /** START ON CREATE*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_login);
    }
}
