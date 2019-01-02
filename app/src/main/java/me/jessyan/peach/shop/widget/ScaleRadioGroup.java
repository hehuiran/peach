package me.jessyan.peach.shop.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * author: Create by HuiRan on 2018/12/29 下午11:10
 * email: 15260828327@163.com
 * description:
 */
public class ScaleRadioGroup extends RadioGroup {
    private static final float FACTOR = 0.3f;
    private final Interpolator mInterpolator = new LinearInterpolator();
    private ValueAnimator mAnimator;

    public ScaleRadioGroup(Context context) {
        super(context);
    }

    public ScaleRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                startAnimation(radioButton);
                if (mOnChildCheckedChangeListener != null) {
                    mOnChildCheckedChangeListener.onChildCheckedChanged(radioButton);
                }
            }
        });
    }

    private void startAnimation(View view) {
        resetAnimation();
        mAnimator = ValueAnimator.ofFloat(0, 1)
                .setDuration(150);
        view.setPivotX(getWidth() / 2);
        view.setPivotY(getHeight() / 2);
        mAnimator.setInterpolator(mInterpolator);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                if (progress < 0.5f) {
                    view.setScaleX(1 + progress * FACTOR);
                    view.setScaleY(1 + progress * FACTOR);
                } else {
                    view.setScaleX(1 + FACTOR / 2 - FACTOR * (progress - 0.5f));
                    view.setScaleY(1 + FACTOR / 2 - FACTOR * (progress - 0.5f));
                }
            }
        });
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                view.setScaleX(1.0f);
                view.setScaleY(1.0f);
            }
        });
        mAnimator.start();
    }

    private void resetAnimation() {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        resetAnimation();
    }

    private OnChildCheckedChangeListener mOnChildCheckedChangeListener;

    public void setOnChildCheckedChangeListener(OnChildCheckedChangeListener onChildCheckedChangeListener) {
        mOnChildCheckedChangeListener = onChildCheckedChangeListener;
    }

    public interface OnChildCheckedChangeListener {
        void onChildCheckedChanged(RadioButton radioButton);
    }
}
