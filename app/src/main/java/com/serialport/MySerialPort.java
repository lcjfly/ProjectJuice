package com.serialport;

import android.content.Context;

import com.dwin.dwinapi.SerialPort;
import com.dwin.navy.serialportapi.SerailPortOpt;

import java.io.FileDescriptor;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by luchenjie on 9/8/16.
 */
public class MySerialPort {

    Context context;
    private static SerialPort serialPort;
    private SerailPortOpt serialportopt = new SerailPortOpt();
    private InputStream mInputStream;
    private ReadThread mReadThread;
    private OnDataReceiveListener onDataReceiveListener = new OnDataReceiveListener();
    public boolean isOpen = false;
    String data;

    public MySerialPort(String devNum, int speed, int dataBits, int stopBits, int parity) {
        this.openSerial(devNum, speed, dataBits, stopBits, parity);
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

    public void sendData(String data, String type) {
        try {
            this.serialportopt.writeBytes(type.equals("HEX")?HexString2Bytes(data.length() % 2 == 1?data + "0":data.replace(" ", "")):HexString2Bytes(this.toHexString(data)));
        } catch (Exception var4) {
            ;
        }

    }

    public String receiveData(String type) {
        byte[] buf = new byte[1024];
        if(this.mInputStream == null) {
            return null;
        } else {
            int size = this.serialportopt.readBytes(buf);
            if(size > 0) {
                try {
                    this.data = type.equals("HEX")?bytesToHexString(buf, size):(new String(buf, 0, size, "gb2312")).trim().toString();
                } catch (UnsupportedEncodingException var5) {
                    var5.printStackTrace();
                }

                return this.data;
            } else {
                return null;
            }
        }
    }

    public class OnDataReceiveListener {
        public void onDataReceive(byte[] buffer, int size) {
            System.out.println(buffer);
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
                        if (null != onDataReceiveListener) {
                            onDataReceiveListener.onDataReceive(buffer, size);
                        }
                    }
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    private String toHexString(String s) {
        String str = "";

        for(int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }

        return str;
    }

    private static byte[] HexString2Bytes(String src) {
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();

        for(int i = 0; i < tmp.length / 2; ++i) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }

        return ret;
    }

    public static String bytesToHexString(byte[] src, int size) {
        String ret = "";
        if(src != null && size > 0) {
            for(int i = 0; i < size; ++i) {
                String hex = Integer.toHexString(src[i] & 255);
                if(hex.length() < 2) {
                    hex = "0" + hex;
                }

                hex = hex + " ";
                ret = ret + hex;
            }

            return ret.toUpperCase();
        } else {
            return null;
        }
    }

    private static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        _b0 = (byte)(_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        byte ret = (byte)(_b0 ^ _b1);
        return ret;
    }
}
