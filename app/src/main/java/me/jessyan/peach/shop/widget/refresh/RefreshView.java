package me.jessyan.peach.shop.widget.refresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.blankj.utilcode.util.SizeUtils;

import me.jessyan.peach.shop.R;

/**
 * author: Created by HuiRan on 2018/5/31 14:42
 * E-Mail: 15260828327@163.com
 * description:
 */
public class RefreshView extends View {

    private Paint mArcPaint, mPartPaint, mArrowPaint;
    private float mArcStrokeWidth = SizeUtils.dp2px(1);
    private Path mArcPath, mPartPath, mArrowPath, mLeftGapPath, mRightGapPath;
    private PathMeasure mInPathMeasure, mOutPathMeasure;
    private RectF mRectF, mArrowRectF;
    private ValueAnimator mAnimator;
    private boolean isRunning;
    private Interpolator mInterpolator = new LinearInterpolator();
    private float mInLength, mOutLength;
    private int mArcPercent = 12;


    public RefreshView(Context context) {
        this(context, null);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setDither(true);
        mArcPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcPaint.setStrokeWidth(mArcStrokeWidth);

        mPartPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPartPaint.setDither(true);
        mPartPaint.setColor(Color.WHITE);
        mPartPaint.setStyle(Paint.Style.STROKE);
        mPartPaint.setStrokeCap(Paint.Cap.ROUND);
        mPartPaint.setStrokeWidth(mArcStrokeWidth);

        mArrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArrowPaint.setDither(true);
        mArrowPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mArrowPaint.setStyle(Paint.Style.STROKE);
        mArrowPaint.setStrokeCap(Paint.Cap.ROUND);
        mArrowPaint.setStrokeWidth(mArcStrokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;
        width = widthMode == MeasureSpec.EXACTLY ? widthSize : SizeUtils.dp2px(40);
        height = heightMode == MeasureSpec.EXACTLY ? heightSize : width;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF = new RectF(getPaddingLeft() + mArcStrokeWidth / 2,
                getPaddingTop() + mArcStrokeWidth / 2,
                getWidth() - mArcStrokeWidth / 2,
                getHeight() - mArcStrokeWidth / 2);


        initPath();
    }

    private void initPath() {
        float startAngle = -90f;
        float sweepAngle = 359.999f;

        Path inPath = new Path();
        inPath.addArc(mRectF, startAngle, sweepAngle);

        mInPathMeasure = new PathMeasure();
        mInPathMeasure.setPath(inPath, true);
        mInLength = mInPathMeasure.getLength();

        Path outPath = new Path();
        outPath.addArc(mRectF, startAngle, -sweepAngle);

        mOutPathMeasure = new PathMeasure();
        mOutPathMeasure.setPath(outPath, true);
        mOutLength = mOutPathMeasure.getLength();

        mArcPath = new Path();

        mPartPath = new Path();

        mArrowPath = new Path();

        mLeftGapPath = new Path();
        float leftStart = 0;
        float leftEnd = mOutLength / mArcPercent / 2;
        mOutPathMeasure.getSegment(leftStart, leftEnd, mLeftGapPath, true);

        mRightGapPath = new Path();
        float rightStart = 0;
        float rightEnd = mInLength / mArcPercent / 2;
        mInPathMeasure.getSegment(rightStart, rightEnd, mRightGapPath, true);
    }

    /**
     * @param progress 0~1只能传0~1
     */
    public void setProgress(@FloatRange(from = 0f, to = 1.0f) float progress) {
        if (isRunning) {
            return;
        }
        mArcPath.reset();


        initArrow(progress);


        float start = mOutLength / mArcPercent / 2;
        float end = (mOutLength - (mOutLength / mArcPercent / 2)) * progress;

        //兼容android低版本手机，不然path绘制不出来
        mArcPath.lineTo(0, 0);
        mOutPathMeasure.getSegment(start, end, mArcPath, true);

        invalidate();
    }

    private void initArrow(float progress) {
        float fraction = 0f;
        if (progress >= 0.8f && progress <= 1.0f) {
            fraction = (progress - 0.8f) / (1.0f - 0.8f);
        }

        float arrowHeight = mRectF.height() / 3 * (1.0f - fraction);
        float arrowWidth = arrowHeight * 4 / 5;
        mArrowRectF = new RectF((mRectF.width() - arrowWidth) / 2,
                (mRectF.height() - arrowHeight) / 2,
                mRectF.width() - (mRectF.width() - arrowWidth) / 2,
                mRectF.height() - (mRectF.height() - arrowHeight) / 2);

        mArrowPath.reset();
        mArrowPath.moveTo(mArrowRectF.left, mArrowRectF.bottom - mArrowRectF.height() / 2.5f);
        mArrowPath.lineTo(mArrowRectF.centerX(), mArrowRectF.bottom);
        mArrowPath.lineTo(mArrowRectF.right, mArrowRectF.bottom - mArrowRectF.height() / 2.5f);
        mArrowPath.moveTo(mArrowRectF.centerX(), mArrowRectF.top);
        mArrowPath.lineTo(mArrowRectF.centerX(), mArrowRectF.bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mArcPath, mArcPaint);

        if (isRunning) {
            canvas.drawPath(mPartPath, mPartPaint);
        } else {
            canvas.drawPath(mLeftGapPath, mPartPaint);
            canvas.drawPath(mRightGapPath, mPartPaint);
            if (mArrowRectF != null && mArrowRectF.width() > 0) {
                canvas.drawPath(mArrowPath, mArrowPaint);
            }
        }
    }

    public void starRotating() {
        isRunning = true;

        mArcPath.reset();
        mArcPath.addArc(mRectF, -90, -360);

        mAnimator = ValueAnimator.ofFloat(0, mInLength).setDuration(1000);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float start = (float) animation.getAnimatedValue();
                float end = start + mInLength / mArcPercent;
                if (end > mInLength) {
                    end = mInLength;
                }
                mPartPath.reset();
                //兼容android低版本手机，不然path绘制不出来
                mPartPath.lineTo(0, 0);
                mInPathMeasure.getSegment(start, end, mPartPath, true);
                invalidate();
            }
        });
        mAnimator.setInterpolator(mInterpolator);
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.start();
    }

    public void stopRotating() {
        isRunning = false;
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopRotating();
    }
}
