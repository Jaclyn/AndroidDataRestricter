package com.jaclyn.dataalert.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jaclyn.dataalert.R;
import com.jaclyn.dataalert.services.MainService;
import com.jaclyn.dataalert.services.MainService.MainBinder;


public class MainActivity extends Activity {

    private static MainActivity instance;

    private MainService mService;

    private Button button;
    private TextView txtCounter;

    boolean mBound = false;

    public static MainActivity getInstance(){
        return instance;
    }

    private Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.btn_on);
        txtCounter = (TextView) findViewById(R.id.txt_counter);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (!mService.run()) {
                    Toast.makeText(getContext(), "Data Restricter Is Off.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        instance = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        instance = null;
    }

    @Override
    protected void onStart() {
        Intent intent = new Intent(this, MainService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        super.onStart();
    }

    public void setTxtCounter(int input){
        txtCounter.setText(input);
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
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
