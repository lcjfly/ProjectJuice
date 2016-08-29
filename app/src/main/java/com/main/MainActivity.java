package com.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.dwin.dwinapi.SerialPort;
import com.example.luchenjie.projectjuice.R;

public class MainActivity extends AppCompatActivity {

    private SerialPort sp;
    private Button sendBtn1;
    private Button sendBtn2;
    private Button sendBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setStretchAllColumns(true);
        tableLayout.setLayoutParams();
        */

        sp = new SerialPort("S1", 9600, 8, 1, 'n');

        sendBtn1 = (Button)findViewById(R.id.sendBtn1);
        sendBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.sendData("1", "HEX");

            }
        });

        sendBtn2 = (Button)findViewById(R.id.sendBtn2);
        sendBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.sendData("2", "HEX");

            }
        });

        sendBtn3 = (Button)findViewById(R.id.sendBtn3);
        sendBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.sendData("3", "HEX");

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sp.closeSerial();
    }
}
