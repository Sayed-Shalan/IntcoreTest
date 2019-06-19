package com.sayed.intcoretest.ui.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sayed.intcoretest.R;
import com.sayed.intcoretest.utils.AppUtils;

/**
 * Base activity - common methods
 */
public abstract class BaseActivity extends AppCompatActivity {

    //Dec Data
    private Loading loading;
    private ConnectivityManager connectivityManager;
    private Snackbar snackbar;
    private boolean noInternetConnection;

    //receiver for network status change
    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            noInternetConnection = connectivityManager.getActiveNetworkInfo() == null;
            showHideSnackBar();
        }
    };


    /** On Activity created*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //register network change receiver
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);

        loading = new Loading(this);//init loading dialog

        initSnackBar();//init snackBar

        ViewGroup viewGroup = (ViewGroup) ((ViewGroup) (findViewById(android.R.id.content))).getChildAt(0);
        if (viewGroup != null) viewGroup.setBackgroundColor(Color.YELLOW);

    }

    //On Resume
    @Override
    protected void onResume() {
        super.onResume();
        if (noInternetConnection && snackbar != null && !snackbar.isShown()) { //check network status
            snackbar.show();
        }
    }

    //on destroy
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);//unregister receivers
    }

    //on items - bck handling
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (getSupportActionBar() != null) {
                if ((getSupportActionBar().getDisplayOptions() & ActionBar.DISPLAY_HOME_AS_UP) > 0) {
                    AppUtils.navigateUp(this);
                    return true;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //init network status snackBar
    private void initSnackBar() {
        int white = ContextCompat.getColor(this, R.color.white);

        ViewGroup rootLayout = findViewById(android.R.id.content);
        if(rootLayout == null) rootLayout = getWindow().getDecorView().findViewById(android.R.id.content);

        if (rootLayout != null) {
            snackbar = Snackbar.make(rootLayout, getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE)
                    .setActionTextColor(white)
                    .setAction("x", v -> snackbar.dismiss());
            TextView textView = snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
            if (textView != null){
                textView.setTextColor(white);
            }

        }
    }

    //show and hide snack depend on network status
    private void showHideSnackBar() {
        if (snackbar != null) {
            if (noInternetConnection) snackbar.show();
            else snackbar.dismiss();
        }
    }

    //show loading dialog with loading str
    public void showLoading(boolean show) {
        loading.show(show);
    }

    //show loading dialog with msg
    public void setLoadingMsg(String msg){
        loading.setLoadingMsg(msg);
    }

    public void showError(String error) {
        try {
                new AlertDialog.Builder(getBaseContext())
                        .setTitle(R.string.title_error)
                        .setMessage(error)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();

        } catch (Exception ignored) {
        }
    }

    //toolbar with back and title
    protected void initToolbar(Toolbar toolbar) {
        if (toolbar != null) setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (toolbar != null) getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    //toolbar with back only
    protected void initToolbarWithNoBack(Toolbar toolbar) {
        if (toolbar != null) setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        if (toolbar != null) getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    //print snack msg
    public void showSnackMsg(String msg){
        ViewGroup rootLayout = findViewById(android.R.id.content);
        if(rootLayout == null) rootLayout = getWindow().getDecorView().findViewById(android.R.id.content);
        if (rootLayout!=null){
            Snackbar.make(rootLayout, msg, Snackbar.LENGTH_SHORT)
                    .setActionTextColor(getResources().getColor(R.color.white))
                    .setAction("Action",null).show();

        }
    }
}
