package com.jess.arms.widget.swipeback;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Created by HuiRan on 2018/6/1 12:20
 * E-Mail: 15260828327@163.com
 * description:
 */
public class PointLoadingView extends View {

    private int mPointDiameter;
    private int mSpace;
    private int count = 3;
    private List<Point> mPointList;
    private List<Paint> mPaintList;
    private int mAreWidth;
    private int mAreHeight;
    private Interpolator mInterpolator = new LinearInterpolator();
    private float mProgress;
    private ValueAnimator mAnimator;
    private static final String TAG = "PointLoadingView";

    public PointLoadingView(Context context) {
        this(context, null);
    }

    public PointLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PointLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPointDiameter = dp2px(9);
        mSpace = dp2px(3);

        mPaintList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {

            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setDither(true);
            paint.setStyle(Paint.Style.FILL);
            if (i == 0) {
                paint.setColor(Color.parseColor("#FFFF664A"));
            } else if (i == 1) {
                paint.setColor(Color.parseColor("#FFFFD100"));
            } else {
                paint.setColor(Color.parseColor("#FF5E8EF4"));
            }
            mPaintList.add(paint);
        }

        mAreWidth = mPointDiameter * count + mSpace * (count - 1);
        mAreHeight = mPointDiameter;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;
        width = widthMode == MeasureSpec.EXACTLY ? widthSize : mAreWidth;
        height = heightMode == MeasureSpec.EXACTLY ? heightSize : mAreHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int paddingHorizontal = (getWidth() - mAreWidth) / 2;
        int paddingVertical = (getHeight() - mAreHeight) / 2;
        Rect rect = new Rect(getPaddingLeft() + paddingHorizontal,
                getPaddingTop() + paddingVertical,
                getWidth() - getPaddingRight() - paddingHorizontal,
                getHeight() - getPaddingBottom() - paddingVertical);

        mPointList = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            Point point = new Point(rect.left + mPointDiameter / 2 * (i + 1) + i * mSpace + i * mPointDiameter / 2, rect.centerY());
            mPointList.add(point);

            /*int red = (int) (Math.random() * 255);
            int green = (int) (Math.random() * 255);
            int blue = (int) (Math.random() * 255);
            int color = 0xff000000 | red << 16 | green << 8 | blue;
            int darkColor = 0xff000000 | red / 4 << 16 | green / 4 << 8 | blue / 4;
            RadialGradient gradient = new RadialGradient(point.x, point.y, mPointDiameter / 2, color, darkColor, Shader.TileMode.CLAMP);
            // 给画笔设置著色器
            mPaintList.get(i).setShader(gradient);*/
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float maxRadius = mPointDiameter / 2.0f;
        float minRadius = maxRadius / count;

        for (int i = 0; i < count; i++) {
            Point point = mPointList.get(i);
            float currentRadius = maxRadius * (i + 1) / count;
//            float targetRadius = maxRadius * (count - i) / count;

            float radius;
            float fraction;

            float percent = 1.0f - (i * 1.0f / (count - 1));
            if (mProgress < percent) {//放大
                fraction = (mProgress - 0.0f) / (percent - 0.0f);
                radius = currentRadius * (1.0f - fraction) + fraction * maxRadius;
            } else {//缩小
                fraction = (mProgress - percent) / (1.0f - percent);
                radius = maxRadius * (1.0f - fraction) + fraction * minRadius;
            }
            canvas.drawCircle(point.x, point.y, radius, mPaintList.get(i));
        }
    }

    public void startLoading() {
        mAnimator = ValueAnimator.ofFloat(0f, 1.0f).setDuration(1000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnimator.setInterpolator(mInterpolator);
        mAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.start();
    }


    public void stopLoading() {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startLoading();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopLoading();
    }

    private int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }
}
