package com.jaclyn.dataalert.activities;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jaclyn.dataalert.R;
import com.jaclyn.dataalert.utilities.Common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
        instance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.btn_on);
        txtCounter = (TextView) findViewById(R.id.txt_counter);
        count = 0;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean isMobileConnected = Common.isMobileData(getContext());
                if (setMobileDataEnabled(!isMobileConnected)) {
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

    private boolean setMobileDataEnabled(boolean enabled) {
        try {
            ConnectivityManager connectManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            Method setMobileDataDisabledMethod = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataDisabledMethod.setAccessible(true);
            setMobileDataDisabledMethod.invoke(connectManager, enabled);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}