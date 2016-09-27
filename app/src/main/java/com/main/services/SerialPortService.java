package com.main.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.dwin.navy.serialportapi.SerailPortOpt;
import com.serialport.MySerialPort;

import java.io.FileDescriptor;
import java.io.InputStream;

public class SerialPortService extends Service {

    private SerialPortBinder binder = new SerialPortBinder();

    private SerailPortOpt serialportopt;// = new SerailPortOpt();
    private InputStream mInputStream;
    private ReadThread mReadThread;
    public boolean isOpen = false;

    private int rate = 0;

    public SerialPortService() {
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        //this.openSerial("S2", 9600, 8, 1, 'n');

        /*
        new Thread() {
          public void run() {
              startDownload();
          }
        }.start();
        */
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.closeSerial();
    }

    public void startDownload() {
        while(rate < 100) {
            try {
                Thread.sleep(500);
                rate += 5;
            } catch (Exception e) {
                e.printStackTrace();
            }
            sendBroadcast("android.intent.action.MY_RECEIVER", "progress", String.valueOf(rate));

        }
    }

    public void sendBroadcast(String sIntentAction, String sKey, String sValue) {
        Intent intent = new Intent();
        intent.setAction(sIntentAction);//"android.intent.action.MY_RECEIVER"
        intent.putExtra(sKey, sValue);
        sendBroadcast(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class SerialPortBinder extends Binder {

        public void sendBytes(byte[] bytes) {
            rate = 0;
            sendData(bytes);
            Log.d("tt", "SerialPortBinder:sendBytes");
        }
    }

    private boolean openSerial(String devNum, int speed, int dataBits, int stopBits, int parity) {
        this.serialportopt.mDevNum = devNum;
        this.serialportopt.mDataBits = dataBits;
        this.serialportopt.mSpeed = speed;
        this.serialportopt.mStopBits = stopBits;
        this.serialportopt.mParity = parity;
        FileDescriptor fd = this.serialportopt.openDev(this.serialportopt.mDevNum);
        if(fd == null) {
            return false;
        } else {
            this.serialportopt.setSpeed(fd, speed);
            this.serialportopt.setParity(fd, dataBits, stopBits, parity);
            this.mInputStream = this.serialportopt.getInputStream();
            mReadThread = new ReadThread();
            mReadThread.start();
            this.isOpen = true;
            return true;
        }
    }

    public void closeSerial() {
        if(this.serialportopt.mFd != null) {
            this.serialportopt.closeDev(this.serialportopt.mFd);
            this.isOpen = false;
        }

    }

    public void sendData(byte[] bytes) {
        try {
            this.serialportopt.writeBytes(bytes);
        } catch (Exception var4) {
            ;
        }

    }

    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (isOpen && !isInterrupted()) {
                int size;
                try {
                    if (mInputStream == null)
                        return;
                    byte[] buffer = new byte[512];
                    size = mInputStream.read(buffer);
                    if (size > 0) {
                        System.out.println("length is:"+size+",data is:"+new String(buffer, 0, size));
                        sendBroadcast("android.intent.action.MY_RECEIVER", "serial", new String(buffer, 0, size));
                    }
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}
