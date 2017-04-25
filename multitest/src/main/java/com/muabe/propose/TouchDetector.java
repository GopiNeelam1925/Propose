package com.muabe.propose;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.muabe.propose.motion.filter.DirectionFilter;
import com.muabe.propose.motion.LinkedPoint;
import com.muabe.propose.motion.Point;
import com.muabe.propose.touch.coords.MetrixCordinates;
import com.muabe.propose.touch.coords.WindowCoordinates;
import com.muabe.propose.touch.detector.MultiMotionEvent;
import com.muabe.propose.touch.detector.SingleMotionEvent;
import com.muabe.propose.touch.detector.TouchDetectAdapter;
import com.muabe.propose.util.Mlog;

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


    DirectionFilter directionFilter = new DirectionFilter();
    public TouchDetector(Context context) {
        touchDetectAdapter = new TouchDetectAdapter(context, this);
        isWindow = WindowCoordinates.isBindWindow();
        Mlog.e(this, "WindowCoordinates:"+isWindow);
        float density = context.getResources().getDisplayMetrics().density;
        directionFilter.addPoint(new LinkedPoint(State.MotionState.LEFT, 100*density, new Point.onPointListener() {
            @Override
            public void onPoint(float prePoint, float point) {
                Mlog.d(TouchDetector.this, directionFilter.getState()+"/LEFT:"+point);
            }

            @Override
            public void onMin(float currentPoint, float distance) {

            }

            @Override
            public void onMax(float currentPoint, float distance) {
                Mlog.i(TouchDetector.this, "left max");
            }
        }));

        directionFilter.addPoint(new LinkedPoint(State.MotionState.RIGHT, 100*density, new Point.onPointListener() {
            @Override
            public void onPoint(float prePoint, float point) {
                Mlog.d(TouchDetector.this, directionFilter.getState()+"/RIGHT:"+point);
            }

            @Override
            public void onMin(float currentPoint, float distance) {

            }

            @Override
            public void onMax(float currentPoint, float distance) {
                Mlog.i(TouchDetector.this, "right max");
            }
        }));
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

//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//
//        }else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_MOVE) {
//            if(event.getPointerCount()>1){
//                touchView.setRotationY(touchView.getRotationY()+5);
//            }
//        }else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
//            touchView.setRotation(0);
//            touchView.setRotationY(0);
//            touchView.setRotationX(0);
//        }
//        else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN) {
//            if(event.getPointerCount() ==2){
//
//            }
//            return true;
//        }

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
        directionFilter.dragFilter(event);
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
