package com.muabe.propose.motion.filter;

import com.muabe.propose.State;
import com.muabe.propose.motion.DragFilter;
import com.muabe.propose.motion.LinkedPoint;
import com.muabe.propose.motion.Motion;
import com.muabe.propose.motion.Point;
import com.muabe.propose.touch.detector.SingleMotionEvent;
import com.muabe.propose.util.Mlog;
import com.muabe.propose.util.ObservableMap;

import java.util.List;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2017-04-07
 */

public class DirectionFilter implements DragFilter, LinkedPoint.OnPointChangeListener{
    private State.MotionState state = State.MotionState.NONE;
    private ObservableMap<State.MotionState, PointObserver> pointObservable = new ObservableMap<>();


    public State.MotionState getState() {
        return state;
    }

    @Override
    public void addMotion(Motion motion){
        LinkedPoint point = new LinkedPoint(motion.getMotionState(), motion.getMaxPoint(), motion);
        PointObserver observer = new PointObserver(point);
        point.setOnPointChangeListener(this);
        pointObservable.put(motion.getMotionState(), observer);

        if(pointObservable.size() > 1){
            List<State.MotionState> keyList = pointObservable.getKeys();
            pointObservable.get(keyList.get(0)).getPoint().setLinkPoint(pointObservable.get(keyList.get(1)).getPoint());
            pointObservable.get(keyList.get(1)).getPoint().setLinkPoint(pointObservable.get(keyList.get(0)).getPoint());
        }
    }

    @Override
    public boolean dragFilter(SingleMotionEvent event){
        if(pointObservable.size()>0) {
            float distance = event.getDragX();
            if (distance != 0) {
                if (state == State.MotionState.NONE) {
                    for (PointObserver observer : pointObservable.getValues()) {
                        if (observer.getPoint().isLikeOrientation(distance)) {
                            observer.getPoint().setPoint(distance);
                            onPointChange(state, observer.getPoint().getState());
                            return true;
                        }
                    }
                } else {
                    if (pointObservable.containsKey(state)) {
                        LinkedPoint point = pointObservable.get(state).getPoint();
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

    private class PointObserver{
        LinkedPoint point;
        Point.OnPointListener pointListener;

        public PointObserver(LinkedPoint point){
            this.point = point;
        }

        public LinkedPoint getPoint(){
            return point;
        }

        public Point.OnPointListener getPointListener(){
            return pointListener;
        }

        public void setPointListener(Point.OnPointListener pointListener){
            this.pointListener = pointListener;
        }
    }
}
