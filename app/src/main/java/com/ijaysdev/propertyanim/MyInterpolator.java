package com.ijaysdev.propertyanim;

import android.animation.TimeInterpolator;

/**
 * Created by ijaysdev on 2016/10/9.
 */

public class MyInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
//        return (float) Math.sin(input*Math.PI);
//        float result;
//        if (input < 0.5) {
//            result = (float) (Math.sin(Math.PI * input) / 2);
//        } else {
//            result = (float) (2 - Math.sin(Math.PI * input) / 2);
//        }
//        return result;
        return (float) Math.sqrt(input);
    }
}
