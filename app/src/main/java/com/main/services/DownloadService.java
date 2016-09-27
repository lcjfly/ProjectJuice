package com.main.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class DownloadService extends Service {

    private DownloadBinder binder = new DownloadBinder();

    private int rate = 0;

    public DownloadService() {
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        new Thread() {
          public void run() {
              startDownload();
          }
        }.start();
    }

    public void startDownload() {
        while(rate < 100) {
            try {
                Thread.sleep(500);
                rate += 5;
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent intent = new Intent();
            intent.setAction("android.intent.action.MY_RECEIVER");
            intent.putExtra("progress", rate);
            sendBroadcast(intent);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class DownloadBinder extends Binder {

        public void sendBytes(byte[] bytes) {
            rate = 0;
            Log.d("tt", "ttt");
        }
    }
}
