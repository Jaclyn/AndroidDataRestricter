package com.jaclyn.dataalert;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.lang.Override;
import java.lang.reflect.Method;
import java.util.Random;

/**
 * Created by Owner on 23-May-15.
 */
public class MainService extends Service {

    // Binder given to clients
    private final IBinder mBinder = new MainBinder();
    // Random number generator
    private final Random mGenerator = new Random();
    private ConnectivityManager connectManager;
    private Method setMobileDataDisabledMethod;
    private boolean serviceRun = false;

    public MainService(){
       super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceRun = true;
        return super.onStartCommand(intent,flags,startId);
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        serviceRun = false;
        return super.onUnbind(intent);
    }

    /** method for clients */
    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class MainBinder extends Binder {
        MainService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MainService.this;
        }
    }

    public boolean run(){
        long interval = System.currentTimeMillis();

//        Thread thread = new Runnable(){
//
//        }
        while (true && serviceRun) {
            synchronized (this) {
                try {
                    if(System.currentTimeMillis() == interval) {
                        connectManager = (ConnectivityManager) getApplicationContext()
                                .getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo mobile = connectManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                        if(mobile.isConnected()){
                            setMobileDataDisabledMethod = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
                            setMobileDataDisabledMethod.setAccessible(true);
                            setMobileDataDisabledMethod.invoke(connectManager, false);
                        }
                    }
                    //check every 30 seconds
                    interval = System.currentTimeMillis() + 30*1000;

                } catch (Exception e) {
                }
            }
        }

        return false;
    }

}

