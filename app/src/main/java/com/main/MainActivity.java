package com.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.luchenjie.projectjuice.R;
import com.main.fragments.CleanFragment;
import com.main.fragments.DebugFragment;
import com.main.fragments.DeviceInfoFragment;
import com.main.fragments.LogoFragment;
import com.main.fragments.MaintainceFragment;
import com.main.fragments.PayFragment;
import com.main.fragments.SaleFragment;
import com.main.fragments.SettingFragment;
import com.main.fragments.SupportFragment;
import com.main.fragments.VideoFileFragment;
import com.serialport.MySerialPort;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();


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
    }

    private void initView() {
        // 提示getActionBar方法一定在setContentView后面
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // 添加Tab选项
        ActionBar.Tab tab = actionBar.newTab().setText("调试界面")
                .setTabListener(new TabListener<DebugFragment>(this, "info", DebugFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("设备信息")
                .setTabListener(new TabListener<DeviceInfoFragment>(this, "info", DeviceInfoFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("销售数据")
                .setTabListener(new TabListener<SaleFragment>(this, "info", SaleFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("保养")
                .setTabListener(new TabListener<CleanFragment>(this, "info", CleanFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("视频文件")
                .setTabListener(new TabListener<VideoFileFragment>(this, "info", VideoFileFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Logo")
                .setTabListener(new TabListener<LogoFragment>(this, "info", LogoFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("维护")
                .setTabListener(new TabListener<MaintainceFragment>(this, "info", MaintainceFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("支付")
                .setTabListener(new TabListener<PayFragment>(this, "info", PayFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("设置")
                .setTabListener(new TabListener<SettingFragment>(this, "info", SettingFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("系统")
                .setTabListener(new TabListener<SupportFragment>(this, "info", SupportFragment.class));
        actionBar.addTab(tab);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else {
                    upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
