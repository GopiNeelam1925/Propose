package com.muabe.propose.guesture;

import com.muabe.propose.touch.detector.single.SingleTouchEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-18
 */
public class SingleTouchRightGesture extends GesturePlugin<SingleTouchEvent> {

    public SingleTouchRightGesture(float maxPoint) {
        super(maxPoint);
    }

    @Override
    public float preemp(SingleTouchEvent event) {
        return increase(event);
    }

    @Override
    public float increase(SingleTouchEvent event) {
        return event.getX() - event.getPreX();
    }
}
