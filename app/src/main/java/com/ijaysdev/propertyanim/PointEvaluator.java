package com.ijaysdev.propertyanim;

import android.animation.TypeEvaluator;

/**
 * 自定义估值器
 * Created by ijaysdev on 2016/10/9.
 */

public class PointEvaluator implements TypeEvaluator{
    @Override
    public Object evaluate(float fraction, Object start, Object end) {
        Point startPoint = (Point) start;
        Point endPoint = (Point) end;
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
        Point point = new Point(x, y);
        return point;
    }
}
