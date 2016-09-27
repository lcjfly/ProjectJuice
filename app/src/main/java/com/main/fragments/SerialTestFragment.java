package com.main.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.luchenjie.projectjuice.R;
import com.main.BinaryTextViewOnClickListener;
import com.main.FragmentCallback;
import com.main.MainActivity;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SerialTestFragment extends Fragment {

    private int[] bytesIds = {
            R.id.byte1, R.id.byte2, R.id.byte3, R.id.byte4,
            R.id.byte5, R.id.byte6, R.id.byte7, R.id.byte8,
            R.id.byte9, R.id.byte10, R.id.byte11, R.id.byte12,
            R.id.byte13, R.id.byte14, R.id.byte15, R.id.byte16,
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_serial_test, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        Button sendBtn = (Button) getView().findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fragmentCallback.sendSerialByte(null);
                TextView sendLogsText = (TextView) getView().findViewById(R.id.sendLogsText);
                sendLogsText.append(getTime()+"-201010101010101010\n");
                scrollToBottom();
            }
        });

        for(int i=0;i<bytesIds.length;i++) {
            TextView byteTextView = (TextView) getView().findViewById(bytesIds[i]);
            byteTextView.setOnClickListener(new BinaryTextViewOnClickListener());
        }

        Button clearReceivedLogsBtn = (Button) getView().findViewById(R.id.clearReceivedLogsBtn);
        clearReceivedLogsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView receivedLogsText = (TextView) getView().findViewById(R.id.receivedLogsText);
                receivedLogsText.setText("");
            }
        });

        Button clearSendLogsBtn = (Button) getView().findViewById(R.id.clearSendLogsBtn);
        clearSendLogsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView sendLogsText = (TextView) getView().findViewById(R.id.sendLogsText);
                sendLogsText.setText("");
            }
        });
    }

    public void scrollToBottom() {
        Handler mHandler = new Handler();

        mHandler.post(new Runnable() {
            public void run() {
                ScrollView sendLogsSV = (ScrollView) getView().findViewById(R.id.sendLogs);
                sendLogsSV.fullScroll(ScrollView.FOCUS_DOWN);
                ScrollView receiveLogsSV = (ScrollView) getView().findViewById(R.id.receivedLogs);
                receiveLogsSV.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        return formatter.format(new Date());
    }

}
