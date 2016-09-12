package com.main;

import android.view.View;
import android.widget.TextView;

/**
 * Created by luchenjie on 9/12/16.
 */
public class BinaryTextViewOnClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        TextView textView = (TextView) v;

        int value = Integer.parseInt(textView.getText().toString());
        if(value == 0) {
            textView.setText("1");
        } else {
            textView.setText("0");
        }
    }
}
