package com.muabe.propose.touch.coords;

import android.view.MotionEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2017-03-24
 */

class AbsolutenessCoordinates {
    private MotionEvent event;

    protected void dispatchTouchEvent(MotionEvent event) {
        this.event = MotionEvent.obtain(event);
    }

    protected MotionEvent convertMotionEvent(MotionEvent event, boolean isRaw) {
        int[] pointerIds = new int[event.getPointerCount()];
        for (int i = 0; i < pointerIds.length; i++) {
            pointerIds[i] = event.getPointerId(i);
        }
        return obtain(this.event, event, isRaw, pointerIds);
    }

    protected MotionEvent convertMotionEvent(MotionEvent event) {
        return this.convertMotionEvent(event, false);
    }

    private MotionEvent obtain(MotionEvent originEvent, MotionEvent cloneEvent, boolean isRaw, int... pointerIds) {
        MotionEvent.PointerCoords[] coords = new MotionEvent.PointerCoords[pointerIds.length];
        MotionEvent.PointerProperties[] properties = new MotionEvent.PointerProperties[pointerIds.length];

        for (int i = 0; i < pointerIds.length; i++) {
            coords[i] = new MotionEvent.PointerCoords();
            properties[i] = new MotionEvent.PointerProperties();

            int index = originEvent.findPointerIndex(pointerIds[i]);
            originEvent.getPointerCoords(index, coords[i]);
            cloneEvent.getPointerProperties(i, properties[i]);
        }

        MotionEvent motionEvent = MotionEvent.obtain(
                cloneEvent.getDownTime(),
                cloneEvent.getEventTime(),
                cloneEvent.getAction(),
                cloneEvent.getPointerCount(),
                properties,
                coords,
                cloneEvent.getMetaState(),
                cloneEvent.getButtonState(),
                cloneEvent.getXPrecision(),
                cloneEvent.getYPrecision(),
                cloneEvent.getDeviceId(),
                cloneEvent.getEdgeFlags(),
                cloneEvent.getSource(),
                cloneEvent.getFlags()
        );
        if(isRaw) {
            motionEvent.offsetLocation(motionEvent.getRawX() - motionEvent.getX(0), motionEvent.getRawY() - motionEvent.getY(0));
        }
        return motionEvent;
    }
}
