package com.muabe.propose.motion.filter;

import com.muabe.propose.State;
import com.muabe.propose.motion.DragFilter;
import com.muabe.propose.motion.LinkedPoint;
import com.muabe.propose.touch.detector.SingleMotionEvent;
import com.muabe.propose.util.Mlog;

import java.util.ArrayList;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2017-04-07
 */

public class DirectionFilter implements DragFilter, LinkedPoint.OnPointChangeListener{
    private State.MotionState state = State.MotionState.NONE;
    private ArrayList<LinkedPoint> pointList = new ArrayList<>(2);

    public State.MotionState getState() {
        return state;
    }

    @Override
    public void addPoint(LinkedPoint point){
        if(pointList.size()>0){
            point.setLinkPoint(pointList.get(0));
            pointList.get(0).setLinkPoint(point);

        }
        point.setOnPointChangeListener(this);
        pointList.add(point);
    }

    @Override
    public boolean dragFilter(SingleMotionEvent event){
        float distance = event.getDragX();
        if(distance!=0) {
            if (state == State.MotionState.NONE) {
                for (LinkedPoint point : pointList) {
                    if (point.isLikeOrientation(distance)) {
                        point.setPoint(distance);
                        onPointChange(state, point.getState());
                        return true;
                    }
                }
            } else {
                for (LinkedPoint point : pointList) {
                    if (point.getState() == state) {
                        point.setPoint(distance);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void onPointChange(State.MotionState preState, State.MotionState currState) {
        state = currState;
        Mlog.e(this, "바뀜:"+state);
    }
}
