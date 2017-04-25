package com.muabe.propose.touch.coords;

import android.view.MotionEvent;
import android.view.View;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2017-04-05
 */

public class MetrixCordinates {
    public static MotionEvent convertMotionEvent(MotionEvent event, View touchView, boolean isRaw) {
        if(isRaw) {
            event.offsetLocation(event.getRawX() - event.getX(0), event.getRawY() - event.getY(0));
        }
        MotionEvent motionEvent = MotionEvent.obtain(event);
        motionEvent.transform(touchView.getMatrix());
        return motionEvent;
    }

    public static MotionEvent convertMotionEvent(MotionEvent event, View touchView) {
        return MetrixCordinates.convertMotionEvent(event, touchView, false);
    }
}
