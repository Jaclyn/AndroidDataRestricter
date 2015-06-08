package com.jaclyn.dataalert.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jaclyn.dataalert.R;
import com.jaclyn.dataalert.utilities.Common;

public class MainActivity extends Activity {

    private static MainActivity instance;

    private Button button;
    private TextView txtCounter;
    private int count;

    public static MainActivity getInstance() {
        return instance;
    }

    private Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        count = 0;

        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btn_on);
        txtCounter = (TextView) findViewById(R.id.txt_counter);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean isMobileConnected = Common.isMobileData(getContext());
                if (Common.setMobileDataEnabled(getContext(), !isMobileConnected)) {
                    Toast.makeText(getContext(), "Turn " + (isMobileConnected ? "off" : "on") + " Mobile Data", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Fail to switch Mobile Data", Toast.LENGTH_SHORT).show();
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

    public void setTxtCounter() {
        count++;
        Log.d("asd", String.valueOf(count));
        txtCounter.setText(String.valueOf(count));
    }
}