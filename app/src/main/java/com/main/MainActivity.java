package com.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.luchenjie.projectjuice.R;
import com.serialport.MySerialPort;

public class MainActivity extends AppCompatActivity {

    private MySerialPort sp;

    private int[] valveBtnIds = {
            R.id.valveBtn1, R.id.valveBtn2, R.id.valveBtn3, R.id.valveBtn4, R.id.valveBtn5, R.id.valveBtn6,
            R.id.valveBtn7, R.id.valveBtn8, R.id.valveBtn9, R.id.valveBtn10, R.id.valveBtn11, R.id.valveBtn12
    };
    private int[] valveTimeIds = {
            R.id.valveTime1, R.id.valveTime2, R.id.valveTime3, R.id.valveTime4, R.id.valveTime5, R.id.valveTime6,
            R.id.valveTime7, R.id.valveTime8, R.id.valveTime9, R.id.valveTime10, R.id.valveTime11, R.id.valveTime12
    };
    private int[] valveSubIds = {
            R.id.valveSub1, R.id.valveSub2, R.id.valveSub3, R.id.valveSub4, R.id.valveSub5, R.id.valveSub6,
            R.id.valveSub7, R.id.valveSub8, R.id.valveSub9, R.id.valveSub10, R.id.valveSub11, R.id.valveSub12
    };
    private int[] valveAddIds = {
            R.id.valveAdd1, R.id.valveAdd2, R.id.valveAdd3, R.id.valveAdd4, R.id.valveAdd5, R.id.valveAdd6,
            R.id.valveAdd7, R.id.valveAdd8, R.id.valveAdd9, R.id.valveAdd10, R.id.valveAdd11, R.id.valveAdd12
    };
    private int[] valveToggleIds = {
            R.id.valveToggle1, R.id.valveToggle2, R.id.valveToggle3, R.id.valveToggle4, R.id.valveToggle5, R.id.valveToggle6,
            R.id.valveToggle7, R.id.valveToggle8, R.id.valveToggle9, R.id.valveToggle10, R.id.valveToggle11, R.id.valveToggle12
    };
    private Button[] valveBtns = new Button[12];
    private Button[] valveAdds = new Button[12];
    private Button[] valveSubs = new Button[12];
    private TextView[] valveTimes = new TextView[12];
    private ToggleButton[] valveToggles = new ToggleButton[12];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {

        }


        init();

        //sp = new MySerialPort("S2", 9600, 8, 1, 'n');


        /*
        valve1 = (Button)findViewById(R.id.valve1);
        valve1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp.sendData("1", "HEX");

            }
        });
        */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sp.closeSerial();
    }

    private void init() {
        for(int i=0;i<valveBtnIds.length;i++) {
            valveBtns[i] = (Button) findViewById(valveBtnIds[i]);
            valveBtns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sp.sendData(new byte[]{(byte) 0xff});
                    //sp.sendData("07", "HEX");

                    Intent it = new Intent(MainActivity.this, LogoActivity.class);
                    startActivity(it);
                }
            });
        }

        for(int i=0;i<valveTimeIds.length;i++) {
            valveTimes[i] = (TextView) findViewById(valveTimeIds[i]);
        }

        for(int i=0;i<valveSubIds.length;i++) {
            valveSubs[i] = (Button) findViewById(valveSubIds[i]);
            valveSubs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int viewId = v.getId();
                    int index = getIndexById(viewId);
                    if(index != -1) {
                        int val = Integer.parseInt(valveTimes[index].getText().toString());
                        if(val > 1) {
                            valveTimes[index].setText(String.valueOf(val - 1));
                        }
                    }
                }

                private int getIndexById(int subBtnId) {
                    for(int i=0;i<valveSubIds.length;i++) {
                        if(valveSubIds[i] == subBtnId) {
                            return i;
                        }
                    }
                    return -1;
                }
            });
        }

        for(int i=0;i<valveAddIds.length;i++) {
            valveAdds[i] = (Button) findViewById(valveAddIds[i]);
            valveAdds[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int viewId = v.getId();
                    int index = getIndexById(viewId);
                    if(index != -1) {
                        int val = Integer.parseInt(valveTimes[index].getText().toString());
                        if(val < 20) {
                            valveTimes[index].setText(String.valueOf(val + 1));
                        }
                    }
                }

                private int getIndexById(int addBtnId) {
                    for(int i=0;i<valveAddIds.length;i++) {
                        if(valveAddIds[i] == addBtnId) {
                            return i;
                        }
                    }
                    return -1;
                }
            });
        }

        for(int i=0;i<valveToggleIds.length;i++) {
            valveToggles[i] = (ToggleButton) findViewById(valveToggleIds[i]);
        }
    }

}
