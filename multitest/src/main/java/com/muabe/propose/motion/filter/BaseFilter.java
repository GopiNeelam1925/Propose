package com.muabe.propose.motion.filter;

import com.muabe.propose.State;
import com.muabe.propose.motion.DragFilter;
import com.muabe.propose.motion.LinkedPoint;
import com.muabe.propose.motion.Point;
import com.muabe.propose.util.Mlog;
import com.muabe.propose.util.ObservableMap;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2017-04-27
 */

public abstract class BaseFilter implements DragFilter, LinkedPoint.OnPointChangeListener{
    private State.MotionState state = State.MotionState.NONE;
    private ObservableMap<State.MotionState, BaseFilter.PointObserver> pointObservable = new ObservableMap<>();
    public State.MotionState getState() {
        return state;
    }

    @Override
    public void onPointChange(State.MotionState preState, State.MotionState currState) {
        state = currState;
        Mlog.e(this, "바뀜:" + state);
    }

    private class PointObserver {
        LinkedPoint point;
        Point.OnPointListener pointListener;

        public PointObserver(LinkedPoint point) {
            this.point = point;
        }

        public LinkedPoint getPoint() {
            return point;
        }

        public Point.OnPointListener getPointListener() {
            return pointListener;
        }

        public void setPointListener(Point.OnPointListener pointListener) {
            this.pointListener = pointListener;
        }
    }
}
