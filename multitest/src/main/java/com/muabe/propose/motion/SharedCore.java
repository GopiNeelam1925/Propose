package com.muabe.propose.motion;

import com.muabe.propose.motion.filter.DirectionFilter;

import java.util.ArrayList;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2017-04-25
 */

public class SharedCore {
    private static ArrayList<DragFilter> dragFilters = new ArrayList<>();
    static {
        dragFilters.clear();
        DirectionFilter directionFilter = new DirectionFilter();
        dragFilters.add(directionFilter);
    }
}
