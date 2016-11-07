package com.ijaysdev.propertyanim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.anim_view)
    View mAnimView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }


    /**
     * @param view
     */
    @OnClick(R.id.bt_normal)
    void normalClick(View view) {
        doAnim();
    }

    @OnClick(R.id.bt_no_effct)
    void noEffectClick(View view) {
        noEffectAnim();
    }

    @OnClick(R.id.bt_length)
    void lengthenAnim(View view) {
        performAnimate(mAnimView.getWidth(), 800);
    }

    @OnClick(R.id.anim_view)
    void onCl(View view) {
        Toast.makeText(this, "ss", Toast.LENGTH_SHORT).show();
    }

    private void performAnimate(final int start, final int end) {
        ValueAnimator valueAnim = ValueAnimator.ofInt(1, 100);

        valueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //获取当前属性值
                int currentValue = (int) valueAnimator.getAnimatedValue();

                Log.wtf("TAG", "current value---->" + currentValue);
                //获取当前进度占整个动画的比例，0～1
                float fraction = valueAnimator.getAnimatedFraction();

                mAnimView.getLayoutParams().width = mEvaluator.evaluate(fraction, start, end);

                mAnimView.requestLayout();
            }
        });
        valueAnim.setInterpolator(new AccelerateDecelerateInterpolator());

        valueAnim.setDuration(4000).start();
        valueAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAnimView.getLayoutParams().width = 200;
                mAnimView.requestLayout();
            }
        });
    }

    //没有效果
    private void noEffectAnim() {
        ObjectAnimator.ofFloat(mAnimView, "width", 500).setDuration(500).start();
    }

    private void doAnim() {
        //从红色渐变到蓝色
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(mAnimView, "backgroundColor", 0xFFFF8080, 0xFF8080FF);
        colorAnim.setDuration(3000);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ValueAnimator.INFINITE);
        colorAnim.setRepeatMode(ValueAnimator.REVERSE);
        colorAnim.start();

    }

    private void chainApproach() {
        mAnimView.animate()
                .scaleX(2f)
                .scaleY(2f)
                .setDuration(350)
                .setInterpolator(new BounceInterpolator())
                .start();
    }
}
