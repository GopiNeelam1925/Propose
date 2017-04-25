package com.muabe.propose.motion.filter;

import com.muabe.propose.State;
import com.muabe.propose.motion.DragFilter;
import com.muabe.propose.motion.Motion;
import com.muabe.propose.util.ObservableMap;

import java.util.List;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2017-04-25
 */

public class Filter {
    private static ObservableMap<State.MotionState, DragFilter> singleFilters = new ObservableMap<>();
    static {
        singleFilters.clear();
        DirectionFilter directionFilter = new DirectionFilter();

        Filter.addSingle(State.MotionState.LEFT, directionFilter);
        Filter.addSingle(State.MotionState.RIGHT, directionFilter);
    }

    public static void addSingle(State.MotionState state, DragFilter filter){
        Filter.singleFilters.put(state, filter);
    }

    public static void addSingleMotion(Motion motion){
        Filter.singleFilters.get(motion.getMotionState()).addMotion(motion);
    }

    public static List<DragFilter> getSingleValues(){
        return singleFilters.getValues();
    }
}
