package com.muabe.propose.touch.coords;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.Window;

/**
 * <br>捲土重來<br>
 * 윈도우 기반 좌표 변환을 담당한다.
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2017-03-22
 */

public class WindowCoordinates {
    private static Window window;
    private static AbsolutenessCoordinates absolutenessCoordinates = new AbsolutenessCoordinates();

    private WindowCoordinates(){}

    public static void bindWindow(Window window){
        WindowTouchEventAdapter.dispatchTouchEvent(window, new WindowTouchEventAdapter.OnDispatchTouchListener() {
            @Override
            public void onDispatchTouchEvent(MotionEvent event) {
                WindowCoordinates.absolutenessCoordinates.dispatchTouchEvent(event);
            }
        });
        WindowCoordinates.window = window;
    }

    public static void bindWindow(Activity activity){
        WindowCoordinates.bindWindow(activity.getWindow());
    }

    public static MotionEvent convertMotionEvent(MotionEvent event) {
        return WindowCoordinates.absolutenessCoordinates.convertMotionEvent(event);
    }

    public static boolean isBindWindow(){
        if(WindowCoordinates.window==null){
            return false;
        }
        return WindowTouchEventAdapter.isRegistry(WindowCoordinates.window);
    }
}
