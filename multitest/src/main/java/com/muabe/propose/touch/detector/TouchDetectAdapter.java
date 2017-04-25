package com.muabe.propose.touch.detector;

import android.content.Context;
import android.view.MotionEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2017-02-24
 */

public class TouchDetectAdapter {
    public interface OnTouchDetectListener {
        boolean onDown(SingleMotionEvent event);
        boolean onUp(SingleMotionEvent event);
        boolean onDrag(SingleMotionEvent event);

        boolean onMulitBegin(MultiMotionEvent event);
        boolean onMultiEnd(MultiMotionEvent event);
        boolean onMultiDrag(MultiMotionEvent event);
        boolean onMultiUp(MultiMotionEvent multiEvent);
    }

    private MultiDetector multiAdater;
    private SingleDetector singleAdapter;

    public TouchDetectAdapter(Context context, OnTouchDetectListener listener){
        this.multiAdater = new MultiDetector(context, listener);
        this.singleAdapter = new SingleDetector(context, listener);
    }

    public boolean onTouchEvent(MotionEvent event){
        return multiAdater.onTouchEvent(event) || singleAdapter.onTouchEvent(event);
    }


}
