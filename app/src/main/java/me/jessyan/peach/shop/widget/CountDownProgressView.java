package me.jessyan.peach.shop.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.utils.ResourceUtils;

/**
 * author: Created by HuiRan on 2018/1/30 10:49
 * E-Mail: 15260828327@163.com
 * description:
 */

public class CountDownProgressView extends View {

    private Paint mCirclePaint, mTextPaint, mArcPaint;
    private int mStrokeWidth = SizeUtils.dp2px(2);
    private String text = "跳过";
    private float mCircleRadius;
    private int basePadding = 4;
    private int mDuration = 3000;
    private float mDy;
    private RectF mAreaRect, mOvalRect;
    private float mTextWidth;
    private int mAnimatedValue = -1;
    private ValueAnimator mAnimator;
    private static final String TAG = "CountDownProgressView";
    private boolean isCancel;

    public CountDownProgressView(Context context) {
        this(context, null);
    }

    public CountDownProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setDither(true);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(0x44000000);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setTextSize(ResourceUtils.getResourceDimension(R.dimen.textSizeNormal));
        mTextPaint.setColor(ContextCompat.getColor(getContext(),R.color.white));

        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(mStrokeWidth);
        mArcPaint.setColor(ContextCompat.getColor(getContext(),R.color.themeColor));

        mTextWidth = mTextPaint.measureText(text);
        mCircleRadius = (mTextWidth + basePadding * 2) / 2;

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mDy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;
        width = widthMode == MeasureSpec.EXACTLY ? widthSize : (int) (mCircleRadius * 2 + mStrokeWidth * 2);
        height = heightMode == MeasureSpec.EXACTLY ? heightSize : width;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mAreaRect = new RectF(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        mOvalRect = new RectF(mAreaRect.left + mStrokeWidth / 2, mAreaRect.top + mStrokeWidth / 2, mAreaRect.right - mStrokeWidth / 2, mAreaRect.bottom - mStrokeWidth / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(mAreaRect.centerX(), mAreaRect.centerY(), mCircleRadius, mCirclePaint);
        canvas.drawText(text, mAreaRect.centerX() - mTextWidth / 2, mAreaRect.centerY() + mDy, mTextPaint);
        canvas.drawArc(mOvalRect, -90, mAnimatedValue, false, mArcPaint);
    }

    public void startCountDown() {
        isCancel = false;
        post(new Runnable() {
            @Override
            public void run() {
                mAnimator = ValueAnimator.ofInt(-360, 0).setDuration(mDuration);
                mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mAnimatedValue = (int) animation.getAnimatedValue();
                        invalidate();
                    }
                });

                mAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (mOnProgressEndListener != null && !isCancel) {
                            mOnProgressEndListener.onProgressEnd();
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        super.onAnimationCancel(animation);
                        isCancel = true;
                    }

                });
                mAnimator.start();
            }
        });

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancel();
    }

    public void cancel() {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
    }

    private OnProgressEndListener mOnProgressEndListener;

    public void setOnProgressEndListener(OnProgressEndListener onProgressEndListener) {
        mOnProgressEndListener = onProgressEndListener;
    }

    public interface OnProgressEndListener {
        void onProgressEnd();
    }
}
