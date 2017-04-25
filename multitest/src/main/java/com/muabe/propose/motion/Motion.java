package com.muabe.propose.motion;

import com.muabe.propose.State;
import com.muabe.propose.util.Mlog;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2017-04-25
 */

public class Motion implements Point.OnPointListener {
    private State.MotionState motionState;

    public Motion(State.MotionState motionState){
        this.motionState = motionState;
    }

    @Override
    public void onPoint(float prePoint, float point) {
        Mlog.d(this, motionState+" : "+point);
    }

    @Override
    public void onMin(float currentPoint, float distance) {
        Mlog.d(this, motionState+" : Min");
    }

    @Override
    public void onMax(float currentPoint, float distance) {
        Mlog.d(this, motionState+" : Max");
    }

    public float getMaxPoint(){
        return 100*3;
    }

    public float getMinPoint(){
        return 0;
    }

    public State.MotionState getMotionState(){
        return this.motionState;
    }
}
