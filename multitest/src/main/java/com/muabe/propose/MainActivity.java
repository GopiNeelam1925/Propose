package com.muabe.propose;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.muabe.propose.touch.coords.window.WindowCoordinates;

public class MainActivity extends Activity {
    TextView text, select1, select2;

    public static float den;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WindowCoordinates.bindWindow(this);


        text = (TextView)findViewById(R.id.text);
        select1 = (TextView)findViewById(R.id.select1);
        select2 = (TextView)findViewById(R.id.select2);

        den = getResources().getDisplayMetrics().density;

        text.setClickable(true);


        final TouchDetector detector = new TouchDetector(this);
        text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return detector.onTouchEvent(v, event);
            }
        });


    }


}
