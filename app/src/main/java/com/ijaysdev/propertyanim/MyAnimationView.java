package com.ijaysdev.propertyanim;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by ijaysdev on 2016/10/10.
 */

public class MyAnimationView extends View {
    public static final float RADIUS = 50f;

    private Point mCurrentPoint;
    private Paint mPaint;

    public MyAnimationView(Context context) {
        this(context, null);
    }

    public MyAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mCurrentPoint == null) {
            mCurrentPoint = new Point(RADIUS, RADIUS);
            drawCirCle(canvas);

            startAnimation();
        } else {
            drawCirCle(canvas);
        }
    }

    private void drawCirCle(Canvas canvas) {
        canvas.drawCircle(mCurrentPoint.getX(), mCurrentPoint.getY(), RADIUS, mPaint);
    }

    private void startAnimation() {
        Point startPoint = new Point(getWidth() / 2, RADIUS);
        Point endPoint = new Point(getWidth() / 2, getHeight() - RADIUS);

        final ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);

        anim.setInterpolator(new MyInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentPoint = (Point) anim.getAnimatedValue();
                invalidate();
            }
        });
        anim.setDuration(2000);
        anim.start();
    }
}
