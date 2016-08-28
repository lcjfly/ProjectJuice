package com.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.luchenjie.projectjuice.R;
import com.serialport.SerialPortUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SerialPortUtil spu = new SerialPortUtil();
        spu.sendCmds("hello");
    }
}
