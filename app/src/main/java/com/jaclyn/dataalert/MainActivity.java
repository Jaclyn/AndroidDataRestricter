package com.jaclyn.dataalert;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jaclyn.dataalert.MainService.MainBinder;


public class MainActivity extends Activity {

    private Button button;
    private MainService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        button = (Button) findViewById(R.id.btn_on);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(! mService.run()){
                    Toast.makeText(getContext(), "Data Restricter Is Off.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Context getContext(){
        return this;
    }

    @Override
    protected void onStart() {
        Intent intent = new Intent(this, MainService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        super.onStart();
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MainBinder binder = (MainBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

}
