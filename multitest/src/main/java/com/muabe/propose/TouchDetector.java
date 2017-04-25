package com.muabe.propose;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.muabe.propose.motion.DragFilter;
import com.muabe.propose.motion.Motion;
import com.muabe.propose.motion.filter.Filter;
import com.muabe.propose.touch.coords.MetrixCordinates;
import com.muabe.propose.touch.coords.WindowCoordinates;
import com.muabe.propose.touch.detector.MultiMotionEvent;
import com.muabe.propose.touch.detector.SingleMotionEvent;
import com.muabe.propose.touch.detector.TouchDetectAdapter;
import com.muabe.propose.util.Mlog;

import java.util.List;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2017-02-21
 */

public class TouchDetector implements TouchDetectAdapter.OnTouchDetectListener {

    private TouchDetectAdapter touchDetectAdapter;
    private boolean isWindow = false;

    public TouchDetector(Context context) {
        touchDetectAdapter = new TouchDetectAdapter(context, this);
        isWindow = WindowCoordinates.isBindWindow();
        Mlog.e(this, "WindowCoordinates:"+isWindow);

        test();
    }

    //TODO REMOVE
    private void test(){
        Motion left = new Motion(State.MotionState.LEFT);
        Motion right = new Motion(State.MotionState.RIGHT);
        Filter.addSingleMotion(left);
        Filter.addSingleMotion(right);
    }


    public boolean onTouchEvent(View touchView, MotionEvent originEvent) {
        //좌표로 변환
        touchView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

            }
        });
        MotionEvent event;
        if(isWindow) {
            event = WindowCoordinates.convertMotionEvent(originEvent);
        }else{
            event =MetrixCordinates.convertMotionEvent(originEvent, touchView, true);
        }

        return touchDetectAdapter.onTouchEvent(event);
    }

    @Override
    public boolean onDown(SingleMotionEvent event) {
        Log.i("MultiTouchDetector", "onDown");
        return true;
    }

    @Override
    public boolean onUp(SingleMotionEvent event) {
        return true;
    }

    @Override
    public boolean onDrag(SingleMotionEvent event) {
        List<DragFilter> dragFilterList = Filter.getSingleValues();
        for(DragFilter filter : dragFilterList){
            filter.dragFilter(event);
        }
        return true;
    }

    @Override
    public boolean onMulitBegin(MultiMotionEvent event) {
        Log.e("MultiTouchDetector", "onMulitBegin");
        return true;
    }

    @Override
    public boolean onMultiEnd(MultiMotionEvent event) {
        Log.e("MultiTouchDetector", "onMultiEnd");
        return true;
    }

    @Override
    public boolean onMultiDrag(MultiMotionEvent event) {
        return true;
    }

    @Override
    public boolean onMultiUp(MultiMotionEvent multiEvent) {
        Log.e("MultiTouchDetector", "onMultiUp");
        return true;
    }




}
