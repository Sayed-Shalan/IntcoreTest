package com.sayed.intcoretest.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;

import com.sayed.intcoretest.other.OkCancelCallback;

public class AppUtils {

    //Navigation on back-icon
    public static void navigateUp(@NonNull Activity activity) {
        Intent upIntent = NavUtils.getParentActivityIntent(activity);
        if (upIntent == null) {
            activity.finish();
        } else if (NavUtils.shouldUpRecreateTask(activity, upIntent)) {
            // This activity is NOT part of this app's task, so create a new task
            // when navigating up, with a synthesized back stack.
            TaskStackBuilder.create(activity)
                    // Add all of this activity's parents to the back stack
                    .addNextIntentWithParentStack(upIntent)
                    // Navigate up to the closest parent
                    .startActivities();
        } else {
            // This activity is part of this app's task, so simply
            // navigate up to the logical parent activity.
            NavUtils.navigateUpTo(activity, upIntent);
        }
    }

    //open ok cancel Dialog
    public static void buildOkCancelDialog(Activity activity, String title, String okStr, String cancelStr, OkCancelCallback okCancelCallback){
        final AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setMessage(title)
                .setCancelable(false)
                .setPositiveButton(okStr, (dialog, which) -> okCancelCallback.onOkClick())
                .setNegativeButton(cancelStr, (dialog, which) -> {
                    okCancelCallback.onCancelClick();
                    dialog.cancel();
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
