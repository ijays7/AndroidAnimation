# 属性动画

## 概述

- View动画：可操作范围小、操作结果限制、操作对象限制(View)
- 属性动画：可操作范围大、改变对象属性、直接操作对象(任意对象)

## 属性动画使用

### ValueAnimator

属性动画的核心类。通过传入的起始值和最终值，计算值与值之间的平滑过度。同时负责管理动画的播放次数、播放模式、以及对动画设置监听器等。

ViewAnimator可以设置AnimatorUpdateListener监听器，它会监听整个动画过程。动画每播放一帧，这个回调就会被调用一次，利用此特性可以做一些特殊的事情。

```java
  valueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //获取当前属性值
                int currentValue = (int) valueAnimator.getAnimatedValue();

                Log.wtf("TAG", "current value---->" + currentValue);
                //获取当前进度占整个动画的比例，0～1
                float fraction = valueAnimator.getAnimatedFraction();
            }
        });
```



### ObjectAnimator

继承自ValueAnimator。可以直接对任意对象的任意属性进行动画操作，开发时多数直接使用此类。

ObjectAnimator的使用方法如下，通过ofXX方法传入作用的对象，改变的属性，起始值和终止值来获取Animator对象。

```java
  ObjectAnimator colorAnim = ObjectAnimator.ofInt(mAnimView, "backgroundColor", 0xFFFF8080, 0xFF8080FF);
  colorAnim.setDuration(3000);
  colorAnim.start();
```

我们可以通过AnimatorListener来监听动画的开始、结束、取消和重复播放。

```java
 colorAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
```

可以看到，四个方法较为繁琐。实际上，还提供了一个AnimatorListenerAdapter借口，仅需实现我们需要的接口，如只需在动画结束后监听则仅需实现onAnimationEnd方法。

``` java
colorAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
```



## 属性动画原理

属性动画根据传入的属性初始值和最终值，不断的多次调用属性的set 方法，每次传入的set 方法的值都不一样。最终通过Android 的消息机制去显示动画。即随着时间的推移，所传递的值越来越接近最终值。
如果动画的时候没有传入初始值，则会调用属性的get 方法。  

## 插值器和估值器

### 插值器(Interpolator)

根据时间流逝的百分比来计算出当前属性值改变的百分比。默认使用的是AccelerateDecelerateInterpolator 插值器。通过设置不同的插值器，可以实现很多有趣的动画。

插值器使用了策略模式，我们实现自定义的插值器只需要实现一个timeInterpolator接口即可。

当然一般情况下并不需要自定义插值器

```java
public class MyInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        return (float) Math.sqrt(input);
    }
}
```

### 估值器(TypeEvaluator)

根据当前属性改变的百分比来计算改变后的属性值，系统预知了IntEvaluator、FloatEvaluator以及ArgbEvaluator。