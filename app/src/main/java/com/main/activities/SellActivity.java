package com.main.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.luchenjie.projectjuice.R;
import com.main.MainActivity;
import com.main.services.SerialPortService;

import java.io.File;

public class SellActivity extends Activity implements ServiceConnection{

    private TextView text;
    private Button btnClickit;
    private VideoView videoView;

    private MyReceiver receiver;

    private SerialPortService.SerialPortBinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        text = (TextView) findViewById(R.id.text);
        videoView = (VideoView) findViewById(R.id.videoview);
        File file=new File(Environment.getExternalStorageDirectory()+"/Download/coke.mp4");
        if(file.exists()){
            videoView.setVideoPath(file.getAbsolutePath());
            //videoView.setMediaController(new MediaController(this));
            //videoView.requestFocus();
            videoView.start();
        }


        btnClickit = (Button) findViewById(R.id.clickit);
        btnClickit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //binder.sendBytes(null);
                gotoMainActivity();
            }
        });

        receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MY_RECEIVER");

        registerReceiver(receiver, intentFilter);

        Intent intent = new Intent(this, SerialPortService.class);
        startService(intent);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    public void gotoMainActivity() {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = (SerialPortService.SerialPortBinder) service;
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
