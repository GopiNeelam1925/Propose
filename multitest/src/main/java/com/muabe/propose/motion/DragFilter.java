package com.muabe.propose.motion;

import com.muabe.propose.touch.detector.SingleMotionEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2017-04-25
 */

public interface DragFilter {
    void addMotion(Motion motion);
    boolean dragFilter(SingleMotionEvent event);
}
