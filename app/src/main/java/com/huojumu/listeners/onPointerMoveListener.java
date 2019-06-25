package com.huojumu.listeners;

import com.huojumu.model.Production;
import com.huojumu.model.ScaleBean;

public interface onPointerMoveListener {
    void onPointerMoved(Production production, int direction, ScaleBean scaleBean);
}
