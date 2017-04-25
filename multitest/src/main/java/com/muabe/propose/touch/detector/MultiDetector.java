package com.muabe.propose.touch.detector;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2017-04-06
 */

class MultiDetector implements ScaleGestureDetector.OnScaleGestureListener{
    private MultiMotionEvent multiEvent;
    private MultiGestureDetector gestureDetector;
    private TouchDetectAdapter.OnTouchDetectListener listener;

    public MultiDetector(Context context, TouchDetectAdapter.OnTouchDetectListener listener){
        multiEvent = new MultiMotionEvent();
        gestureDetector = new MultiGestureDetector(context, this);
        this.listener = listener;
    }

    public boolean onTouchEvent(MotionEvent e){
        multiEvent.setMotionEvent(e);
        return gestureDetector.onTouchEvent(e);
    }


    boolean onMultiUp(){
        return listener.onMultiUp(multiEvent);
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        multiEvent.initMultiTouch(detector);
        return listener.onMulitBegin(multiEvent);
    }


    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        multiEvent.setMultiDragProperty(detector);
        return listener.onMultiDrag(multiEvent);
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        listener.onMultiEnd(multiEvent);
        multiEvent.initMultiTouch(null);
    }

    private class MultiGestureDetector extends android.view.ScaleGestureDetector {
        private MultiDetector adapter;

        public MultiGestureDetector(Context context, MultiDetector adapter) {
            super(context, adapter);
            this.adapter = adapter;
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if(event.getPointerCount()<=1){
                return false;
            }
            boolean result = super.onTouchEvent(event);
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP) {
                result = adapter.onMultiUp() || result;
            }
            return result;
        }
    }
}
