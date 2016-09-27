package com.main.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.luchenjie.projectjuice.R;
import com.main.services.DownloadService;

public class SellActivity extends Activity implements ServiceConnection{

    private TextView text;
    private Button btnClickit;

    private MyReceiver receiver;

    private DownloadService.DownloadBinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        text = (TextView) findViewById(R.id.text);
        btnClickit = (Button) findViewById(R.id.clickit);
        btnClickit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binder.sendBytes(null);
            }
        });

        receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MY_RECEIVER");

        registerReceiver(receiver, intentFilter);

        Intent intent = new Intent(this, DownloadService.class);
        startService(intent);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = (DownloadService.DownloadBinder) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            int progress = bundle.getInt("progress");
            text.setText("received: " + progress);
        }
    }
}
