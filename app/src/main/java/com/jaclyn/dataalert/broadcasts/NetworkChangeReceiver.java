package com.jaclyn.dataalert.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jaclyn.dataalert.activities.MainActivity;
import com.jaclyn.dataalert.utilities.Common;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private static boolean isFirstConnect = true;

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (Common.isMobileData(context)) {
            if (isFirstConnect) {
                MainActivity.getInstance().setTxtCounter();
                isFirstConnect = false;
            }
        } else {
            isFirstConnect = true;
        }
    }
}