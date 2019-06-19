package com.sayed.intcoretest.ui.base;

import android.app.ProgressDialog;
import android.content.Context;

import com.sayed.intcoretest.R;

import java.util.concurrent.atomic.AtomicInteger;

public class Loading {

    private ProgressDialog loadingDialog;
    private AtomicInteger count = new AtomicInteger();

    public Loading(Context context) {
        loadingDialog = new ProgressDialog(context);
        loadingDialog.setMessage(context.getResources().getString(R.string.loading));
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);
    }

    public void show(boolean show) {
        if (show) {
            if (loadingDialog != null && count.getAndIncrement() == 0) {
                loadingDialog.show();
            }
        } else {
            if (loadingDialog != null && count.decrementAndGet() == 0 && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            if (count.get() < 0) count.set(0);
        }
    }

    public void setLoadingMsg(String msg){
        loadingDialog.setMessage(msg);
    }
}