package com.jaclyn.dataalert.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jaclyn.dataalert.activities.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private Context context;
    private Timer timer;
    private int counter;

    @Override
    public void onReceive(final Context context, final Intent intent) {

        this.context = context;

        if (isNetworkAvailable() && isMobileData()) {
            if (counter == 0) {
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        MainActivity.getInstance().setTxtCounter(counter++);
                    }
                }, 1000, 1000);
            }
        } else {
            counter = 0;
        }
    }

    private NetworkInfo getNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    private boolean isNetworkAvailable() {
        NetworkInfo networkInfo = getNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private boolean isMobileData() {
        return getNetworkInfo().getType() == ConnectivityManager.TYPE_MOBILE;
    }
}