package me.jessyan.peach.shop.widget.single;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.EdgeEffect;
import android.widget.Scroller;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.TimeUtils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.utils.StringUtils;

/**
 * author: Created by HuiRan on 2017/12/19 17:42
 * E-Mail: 15260828327@163.com
 * description:
 */

public class SingleLineView extends View {

    /**
     * y轴起始点位置，改为0就是在最底下(一般为0)
     */
    private static final int OFFSET_UPWARD = SizeUtils.dp2px(30);

    private List<SingleUnit> mData;
    /**
     * y轴的最大刻度值，保留一位小数
     */
    private float maxValueOfY;

    /**
     * 根据可见点数计算出的两点之间的距离
     */
    private float realBetween;
    /**
     * 手指/fling的上次位置
     */
    private float lastX;
    /**
     * 滚动当前偏移量
     */
    private float offset;
    /**
     * 滚动偏移量的边界
     */
    private float maxOffset;
    /**
     * fling最大速度
     */
    private int maxVelocity;
    /**
     * lines在当前可见区域的边缘点
     */
    private int[] suitEdge;
    /**
     * 实际的点击位置，0为x索引，1为某条line
     */
    private int clickIndex = -1;
    private float firstX, firstY;
    /**
     * y轴的缓存，提高移动效率
     */
    private Bitmap yAreaBuffer;
    /**
     * 一组数据在可见区域中的最大可见点数，至少>=2
     */
    private int maxOfVisible = 10;
    /**
     * 文本之间/图表之间的间距
     */
    private float basePadding = 4;
    /**
     * 是否正在整体动画中
     */
    private boolean isAniming;
    /**
     * 判断左/右方向，当在边缘就不触发fling，以优化性能
     */
    float orientationX;
    private VelocityTracker velocityTracker;
    private Scroller scroller;
    private EdgeEffect edgeEffectLeft, edgeEffectRight;
    // 对于fling，仅吸收到达边缘时的速度
    private boolean hasAbsorbLeft, hasAbsorbRight;
    /**
     * 是否需要边缘反馈效果
     */
    private boolean needEdgeEffect = true;
    private int edgeEffectColor = ContextCompat.getColor(getContext(), R.color.colorThemePrimaryLight);

    /**
     * 曲线的画笔
     */
    private Paint mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 填充的画笔
     */
    private Paint mFillPaint;
    /**
     * XY轴对应的画笔
     */
    private Paint xyPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint xDatePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 点击提示rect的画笔
     */
    private Paint clickRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Paint clickTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint clickOutCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint clickCenterCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint clickInCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float clickCircleRadius = SizeUtils.dp2px(8);
    private float clickWidthPadding = SizeUtils.dp2px(3);
    private float clickHeightPadding = SizeUtils.dp2px(3);
    private float mPromptRectWidth;
    private float mTriangleWidth = SizeUtils.dp2px(3);
    private float mHintCircleRectSpace = 0;
    private float mPromptTotalHeight;
    private int mLineColor;

    private RectF linesArea, xArea, yArea, canvasRect;

    private float defaultXyStrokeWidth = SizeUtils.dp2px(1);


    private Path mPath, mDst;
    private float measureLength;
    private PathMeasure mPathMeasure = new PathMeasure();
    private float mProgress;

    /**
     * 默认画笔的颜色，索引0位置为画笔颜色，整个数组为shader颜色
     */
    private int[] defaultLineColor = new int[3];
    private ValueAnimator mAnimator;
    private boolean mIsFill;
    private int mXyColor;
    private boolean mNeedXAxis;
    private int mDateColor;
    private int mHintColor;
    private SimpleDateFormat mSimpleDateFormat;

    public SingleLineView(Context context) {
        this(context, null);
    }

    public SingleLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SingleLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initOptionalState(context, attrs);

        mSimpleDateFormat = new SimpleDateFormat("dd", Locale.getDefault());

        basePadding = SizeUtils.dp2px(basePadding);


        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(defaultXyStrokeWidth);
        if (mIsFill) {
            mFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mFillPaint.setStyle(Paint.Style.FILL);
            mLinePaint.setColor(mLineColor);
        }


        xyPaint.setColor(mXyColor);
        xyPaint.setStrokeWidth(SizeUtils.dp2px(0.5f));
        xyPaint.setTextAlign(Paint.Align.CENTER);

        /*
         *xy轴文字颜色和大小
         */
        float defaultXySize = 12;
        xDatePaint.setTextSize(SizeUtils.sp2px(defaultXySize));
        //xDatePaint.setColor(mDateColor);
        xDatePaint.setTextAlign(Paint.Align.CENTER);

        clickRectPaint.setColor(mHintColor);
        clickRectPaint.setStyle(Paint.Style.FILL);
        clickRectPaint.setPathEffect(new CornerPathEffect(SizeUtils.dp2px(5)));
        clickRectPaint.setStrokeJoin(Paint.Join.ROUND);

        clickTextPaint.setTextSize(SizeUtils.sp2px(12));
        clickTextPaint.setColor(Color.WHITE);

        clickOutCirclePaint.setColor(mHintColor);
        clickOutCirclePaint.setAlpha(100);
        clickOutCirclePaint.setStyle(Paint.Style.FILL);

        clickCenterCirclePaint.setColor(mHintColor);
        clickCenterCirclePaint.setAlpha(150);
        clickCenterCirclePaint.setStyle(Paint.Style.FILL);

        clickInCirclePaint.setColor(mHintColor);
        clickInCirclePaint.setAlpha(255);
        clickInCirclePaint.setStyle(Paint.Style.FILL);

        maxVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        scroller = new Scroller(context);
        edgeEffectLeft = new EdgeEffect(context);
        edgeEffectRight = new EdgeEffect(context);
        setEdgeEffectColor(edgeEffectColor);
    }

    private void initOptionalState(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SingleLineView);
        int startColor = typedArray.getColor(R.styleable.SingleLineView_startColor, ContextCompat.getColor(context, R.color.colorThemePrimary));
        int centerColor = typedArray.getColor(R.styleable.SingleLineView_centerColor, ContextCompat.getColor(context, R.color.colorThemePrimary));
        int endColor = typedArray.getColor(R.styleable.SingleLineView_endColor, ContextCompat.getColor(context, R.color.colorThemePrimaryLight));
        mLineColor = typedArray.getColor(R.styleable.SingleLineView_lineColor, ContextCompat.getColor(context, R.color.colorThemePrimary));
        mXyColor = typedArray.getColor(R.styleable.SingleLineView_xyColor, ContextCompat.getColor(context, R.color.colorThemePrimary));
        mDateColor = typedArray.getColor(R.styleable.SingleLineView_dateColor, ContextCompat.getColor(context, R.color.colorThemePrimary));
        mHintColor = typedArray.getColor(R.styleable.SingleLineView_hintColor, ContextCompat.getColor(context, R.color.colorThemePrimary));
        mIsFill = typedArray.getBoolean(R.styleable.SingleLineView_fill, true);
        mNeedXAxis = typedArray.getBoolean(R.styleable.SingleLineView_needXAxis, true);
        defaultLineColor[0] = startColor;
        defaultLineColor[1] = centerColor;
        defaultLineColor[2] = endColor;
        typedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        calcAreas();
        if (mData != null && !mData.isEmpty()) {
            calcUnitXY();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mData == null || isAniming) {
            recycleVelocityTracker();
            return false;
        }
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                firstX = lastX = event.getX();
                firstY = event.getY();
                scroller.abortAnimation();
                initOrResetVelocityTracker();
                velocityTracker.addMovement(event);
                super.onTouchEvent(event);
                return true;
            case MotionEvent.ACTION_POINTER_DOWN:
                lastX = event.getX(0);
                break;
            case MotionEvent.ACTION_MOVE:
                orientationX = event.getX() - lastX;
                onScroll(orientationX);
                lastX = event.getX();
                velocityTracker.addMovement(event);
                if (needEdgeEffect && mData.size() > maxOfVisible) {
                    if (isArriveAtLeftEdge()) {
                        edgeEffectLeft.onPull(Math.abs(orientationX) / canvasRect.height());
                    } else if (isArriveAtRightEdge()) {
                        edgeEffectRight.onPull(Math.abs(orientationX) / canvasRect.height());
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP: // 计算出正确的追踪手指
                int minID = event.getPointerId(0);
                for (int i = 0; i < event.getPointerCount(); i++) {
                    if (event.getPointerId(i) <= minID) {
                        minID = event.getPointerId(i);
                    }
                }
                if (event.getPointerId(event.getActionIndex()) == minID) {
                    minID = event.getPointerId(event.getActionIndex() + 1);
                }
                lastX = event.getX(event.findPointerIndex(minID));
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    boolean canCallTap = Math.abs(event.getX() - firstX) < 3
                            && Math.abs(event.getY() - firstY) < 3;
                    if (canCallTap) {
                        onTap(event.getX(), event.getY());
                    }
                }
                velocityTracker.addMovement(event);
                velocityTracker.computeCurrentVelocity(1000, maxVelocity);
                int initialVelocity = (int) velocityTracker.getXVelocity();
                velocityTracker.clear();
                if (!isArriveAtLeftEdge() && !isArriveAtRightEdge()) {
                    scroller.fling((int) event.getX(), (int) event.getY(), initialVelocity / 2,
                            0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
                    invalidate();
                } else {
                    edgeEffectLeft.onRelease();
                    edgeEffectRight.onRelease();
                }
                lastX = event.getX();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            onScroll(scroller.getCurrX() - lastX);
            lastX = scroller.getCurrX();
            if (needEdgeEffect) {
                if (!hasAbsorbLeft && isArriveAtLeftEdge()) {
                    hasAbsorbLeft = true;
                    edgeEffectLeft.onAbsorb((int) scroller.getCurrVelocity());
                } else if (!hasAbsorbRight && isArriveAtRightEdge()) {
                    hasAbsorbRight = true;
                    edgeEffectRight.onAbsorb((int) scroller.getCurrVelocity());
                }
            }
            postInvalidate();
        } else {
            hasAbsorbLeft = false;
            hasAbsorbRight = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mData == null) return;
        if (!needEdgeEffect) return;

        if (!edgeEffectLeft.isFinished()) {
            canvas.save();
            canvas.rotate(-90);
            canvas.translate(-canvasRect.bottom, canvasRect.left);
            edgeEffectLeft.setSize((int) canvasRect.height(), (int) canvasRect.height());
            if (edgeEffectLeft.draw(canvas)) {
                postInvalidate();
            }
            canvas.restore();
        }

        if (!edgeEffectRight.isFinished()) {
            canvas.save();
            canvas.rotate(90);
            canvas.translate(canvasRect.top, -canvasRect.right);
            edgeEffectRight.setSize((int) canvasRect.height(), (int) canvasRect.height());
            if (edgeEffectRight.draw(canvas)) {
                postInvalidate();
            }
            canvas.restore();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mData == null || mDst == null) return;
        canvas.save();
        canvas.clipRect(linesArea.left, linesArea.top - mPromptTotalHeight, linesArea.right, canvasRect.bottom);

        canvas.translate(offset, 0);

        suitEdge = findSuitEdgeInVisual2();
        drawXY(canvas, suitEdge[0], suitEdge[1]);
        drawLine(canvas);

        if (!isAniming && clickIndex != -1) {
            drawClickHint(canvas);
        }
        canvas.restore();
//        lastOffset = offset;
    }

    private void drawLine(Canvas canvas) {
        if (isAniming) {
            mDst.reset();
            mDst.lineTo(0, 0);
            float stop = measureLength * mProgress;
            mPathMeasure.getSegment(0, stop, mDst, true);
            canvas.drawPath(mDst, mLinePaint);
            if (mIsFill) {
                float curX = mData.get(suitEdge[1]).getPointF().x * mProgress + (1.0f - mProgress) * mData.get(suitEdge[0]).getPointF().x;
                mDst.lineTo(curX, yArea.bottom);
                mDst.lineTo(mData.get(suitEdge[0]).getPointF().x, yArea.bottom);
                mDst.close();
                canvas.drawPath(mDst, mFillPaint);
            }

        } else {
            initPath();
            canvas.drawPath(mPath, mLinePaint);
            if (mIsFill) {
                mPath.lineTo(mData.get(suitEdge[1]).getPointF().x, yArea.bottom);
                mPath.lineTo(mData.get(suitEdge[0]).getPointF().x, yArea.bottom);
                mPath.close();
                canvas.drawPath(mPath, mFillPaint);
            }
        }
    }

    /**
     * 画x轴,默认取第一条线的值
     */
    private void drawXY(Canvas canvas, int startIndex, int endIndex) {
        if (mNeedXAxis) {
            canvas.drawLine(mData.get(startIndex).getPointF().x, yArea.bottom,
                    mData.get(endIndex).getPointF().x, yArea.bottom, xyPaint);
        }

        for (int i = startIndex; i <= endIndex; i++) {
            if (i != 0 && i != mData.size() - 1) {
                String extX = TimeUtils.millis2String(mData.get(i).getTimeMillis(), mSimpleDateFormat);
                canvas.drawLine(mData.get(i).getPointF().x, yArea.bottom - SizeUtils.dp2px(5), mData.get(i).getPointF().x, yArea.top, xyPaint);
                if(i==mData.size()-2){
                    xDatePaint.setColor(mDateColor);
                } else xDatePaint.setColor(Color.parseColor("#cccccc"));
                canvas.drawText(extX, mData.get(i).getPointF().x, calcTextSuitBaseY(xArea, xDatePaint), xDatePaint);
            }
        }
    }

    /**
     * 画提示文本和辅助线
     */
    private void drawClickHint(Canvas canvas) {
        SingleUnit cur = mData.get(clickIndex);
        float circleX = cur.getPointF().x;
        float circleY = cur.getPointF().y;

        canvas.drawCircle(circleX, circleY, clickCircleRadius, clickOutCirclePaint);
        canvas.drawCircle(circleX, circleY, clickCircleRadius / 3 * 2, clickCenterCirclePaint);
        canvas.drawCircle(circleX, circleY, clickCircleRadius / 3, clickInCirclePaint);

        float curValue = cur.getValue();
        String value = StringUtils.filterAccurateDecimal(String.valueOf(curValue));
//        String value = String.valueOf(((int) cur.getValue()));
        float textWidth = clickTextPaint.measureText(value);
        float promptWidth = Math.max(mPromptRectWidth, textWidth + clickWidthPadding * 2);

        Path path = new Path();
        path.moveTo(circleX - mTriangleWidth / 2, circleY - (mTriangleWidth + mHintCircleRectSpace + clickCircleRadius));
        path.lineTo(circleX - promptWidth / 2, circleY - (mTriangleWidth + mHintCircleRectSpace + clickCircleRadius));
        path.lineTo(circleX - promptWidth / 2, circleY - mPromptTotalHeight);
        path.lineTo(circleX + promptWidth / 2, circleY - mPromptTotalHeight);
        path.lineTo(circleX + promptWidth / 2, circleY - (mTriangleWidth + mHintCircleRectSpace + clickCircleRadius));
        path.lineTo(circleX + mTriangleWidth / 2, circleY - (mTriangleWidth + mHintCircleRectSpace + clickCircleRadius));

        path.lineTo(circleX, circleY - clickCircleRadius - mHintCircleRectSpace);
        path.close();

        canvas.drawPath(path, clickRectPaint);

        RectF rectF = new RectF(circleX - promptWidth / 2, circleY - mPromptTotalHeight + defaultXyStrokeWidth / 2, circleX + promptWidth / 2, circleY - (mTriangleWidth + mHintCircleRectSpace + clickCircleRadius));

        canvas.drawText(value, circleX - textWidth / 2, calcTextSuitBaseY(rectF, clickTextPaint), clickTextPaint);

//        canvas.drawLine(circleX, circleY, circleX, yArea.bottom - xArea.height(), clickLinePaint);
    }

    private void onTap(float upX, float upY) {

        upX -= offset;
        RectF bak = new RectF(linesArea.left, yArea.top, linesArea.right, yArea.bottom);
        bak.offset(-offset, 0);
        if (mData == null || !bak.contains(upX, upY)) {
            return;
        }
        float index = (upX - linesArea.left) / realBetween;
        int realIndex;
        if ((index - (int) index) > 0.5f) {
            realIndex = (int) index + 1;
        } else {
            realIndex = (int) index;
        }

        if (realIndex != 0 && realIndex != mData.size() - 1) {

            invalidate();
            clickIndex = realIndex;
        }
    }

    private void initOrResetVelocityTracker() {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
    }

    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    private void calcAreas() {

        float clickHeight = getTextHeight(clickTextPaint);
        float promptrectHeight = clickHeight + clickHeightPadding * 2;
        mPromptRectWidth = promptrectHeight + promptrectHeight / 2;
        mPromptTotalHeight = promptrectHeight + mTriangleWidth + mHintCircleRectSpace + clickCircleRadius;

        canvasRect = new RectF(getPaddingLeft() + basePadding,
                getPaddingTop() + basePadding,
                getMeasuredWidth() - getPaddingRight() - basePadding,
                getMeasuredHeight() - getPaddingBottom());

        RectF validArea = new RectF(canvasRect.left, canvasRect.top,
                canvasRect.right,
                canvasRect.bottom - (mNeedXAxis ? defaultXyStrokeWidth / 2 : 0));

        xArea = new RectF(validArea.left, validArea.bottom - getTextHeight(xDatePaint) - basePadding * 2,
                validArea.right, validArea.bottom);

        yArea = new RectF(validArea.left, validArea.top,
                validArea.left + basePadding,
                xArea.top);

        linesArea = new RectF(yArea.left, yArea.top + mPromptTotalHeight, validArea.right, yArea.bottom - OFFSET_UPWARD);
    }

    public void feedWithAnim(final List<SingleUnit> list) {
        if (list == null || list.isEmpty()) {
            throw new NullPointerException("list is null");
        }
        if (mData != null && mData == list) {
            return;
        }
        post(new Runnable() {
            @Override
            public void run() {
                cancelAnim();
                reset();
                mData = list;
                mPath = mPath == null ? new Path() : mPath;
                mDst = mDst == null ? new Path() : mDst;
                calcMaxUnit();
                calcAreas();
                calcUnitXY();
                offset = -maxOffset;
                if (mIsFill) {
                    mFillPaint.setShader(buildPaintColor(defaultLineColor));
                } else {
                    mLinePaint.setShader(buildPaintColor(defaultLineColor));
                }
                suitEdge = findSuitEdgeInVisual();
                initPath();
                showWithAnims();
            }
        });

    }

    private void showWithAnims() {
        clickIndex = mData.size() - 2;
        mPathMeasure.setPath(mPath, false);
        measureLength = mPathMeasure.getLength();
        isAniming = true;
        mAnimator = ValueAnimator.ofFloat(0, 1.0f).setDuration(1500);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAniming = false;
            }
        });
        mAnimator.start();
    }

    private void initPath() {

        mPath.reset();

        for (int i = suitEdge[0]; i <= suitEdge[1]; i++) {
            SingleUnit current = mData.get(i);
            if (i == suitEdge[0]) {
                mPath.moveTo(current.getPointF().x, current.getPointF().y);
                continue;
            }
            SingleUnit previous = mData.get(i - 1);
            // 两个锚点的坐标x为中点的x，y分别是两个连接点的y
            mPath.cubicTo((previous.getPointF().x + current.getPointF().x) / 2,
                    previous.getPointF().y,
                    (previous.getPointF().x + current.getPointF().x) / 2, current.getPointF().y,
                    current.getPointF().x, current.getPointF().y);
        }

    }

    /**
     * 计算所有点的坐标
     * <br>同时得到了realBetween，maxOffset
     */
    private void calcUnitXY() {
        int realNum = Math.min(mData.size(), maxOfVisible);
        realBetween = linesArea.width() / (realNum - 1);
        for (int i = 0; i < mData.size(); i++) {
            mData.get(i).setPointF(new PointF(linesArea.left + realBetween * i,
                    linesArea.top + linesArea.height() * (1 - (maxValueOfY == 0 ? 0 : mData.get(i).getValue() / maxValueOfY))));
            if (i == mData.size() - 1) {
                maxOffset = Math.abs(mData.get(i).getPointF().x) - linesArea.width() - linesArea.left;
            }
        }
    }

    /**
     * 得到maxValueOfY
     */
    private void calcMaxUnit() {
        // 先“扁平”
        List<SingleUnit> allUnits = new ArrayList<>(mData);
//        allUnits.addAll(mData);

        // 再拷贝，防止引用问题
        List<SingleUnit> bakUnits = new ArrayList<>();
        for (int i = 0; i < allUnits.size(); i++) {
            bakUnits.add(allUnits.get(i).clone());
        }
        // 最后排序，得到最大值
        Collections.sort(bakUnits, new SingleValueComparator());
        SingleUnit maxUnit = bakUnits.get(bakUnits.size() - 1);
        maxValueOfY = getCeil5(maxUnit.getValue());
    }

    /**
     * 取消动画
     */
    private void cancelAnim() {
        scroller.abortAnimation();
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
            mAnimator = null;
        }
    }


    public void release() {
        cancelAnim();
        reset();
    }

    /**
     * 重置相关状态
     */
    private void reset() {
        invalidateYBuffer();
        offset = 0;
        realBetween = 0;
        suitEdge = null;
        clickIndex = -1;
        if (mData != null) {
            mData.clear();
            mData = null;
        }
    }

    private void invalidateYBuffer() {
        if (yAreaBuffer != null) {
            yAreaBuffer.recycle();
            yAreaBuffer = null;
        }
    }

    /**
     * 是否滑动到了左边缘，注意，并非指可视区域的边缘，下同
     */
    private boolean isArriveAtLeftEdge() {
        return offset == 0 && orientationX > 0;
    }

    /**
     * 是否滑动到了右边缘
     */
    private boolean isArriveAtRightEdge() {
        return Math.abs(offset) == Math.abs(maxOffset) && orientationX < 0;
    }

    /**
     * 指定边缘效果的颜色
     *
     * @param color 默认为Color.GRAY
     */
    public void setEdgeEffectColor(int color) {
        needEdgeEffect = true;
        edgeEffectColor = color;
        trySetColorForEdgeEffect(edgeEffectLeft, edgeEffectColor);
        trySetColorForEdgeEffect(edgeEffectRight, edgeEffectColor);
        postInvalidate();
    }

    /**
     * 滑动方法，同时检测边缘条件
     */
    private void onScroll(float deltaX) {
        offset += deltaX;
        offset = offset > 0 ? 0 : (Math.abs(offset) > maxOffset) ? -maxOffset : offset;
        invalidate();
    }

    /**
     * @param color 不能为null
     */
    private LinearGradient buildPaintColor(int[] color) {
        int[] bakColor = color;
        if (color != null && color.length < 2) {
            bakColor = new int[2];
            bakColor[0] = color[0];
            bakColor[1] = color[0];
        }
        return new LinearGradient(linesArea.left, linesArea.top,
                linesArea.left + realBetween * (mData.size() - 1), linesArea.top, bakColor, null, Shader.TileMode.CLAMP);
    }

    /**
     * 找到当前可见区间内合适的两个边缘点，注意如果边缘点不在可见区间的边缘，则需要包含下一个不可见的点
     */
    private int[] findSuitEdgeInVisual() {
        int startIndex = 0, endIndex = mData.size() - 1;
        if (offset == 0) {// 不可滑动或当前位于最左边
            startIndex = 0;
            endIndex = Math.min(mData.size() - 1, maxOfVisible - 1);
        } else if (Math.abs(offset) == maxOffset) {// 可滑动且当前位于最右边
            endIndex = mData.size() - 1;
            startIndex = endIndex - maxOfVisible + 1;
        } else {
            float startX = linesArea.left - offset;
            float endX = linesArea.right - offset;
            if (mData.size() > maxOfVisible) {
                // 找到指定区间的第一个被发现的点
                int suitKey = 0;
                int low = 0;
                int high = mData.size() - 1;
                while (low <= high) {
                    int mid = (low + high) >>> 1;
                    SingleUnit midVal = mData.get(mid);
                    if (midVal.getPointF().x < startX) {
                        low = mid + 1;
                    } else if (midVal.getPointF().x > endX) {
                        high = mid - 1;
                    } else {
                        suitKey = mid;
                        break;
                    }
                }
                int bakKey = suitKey;
                // 先左边
                while (suitKey >= 0) {
                    startIndex = suitKey;
                    if (mData.get(suitKey).getPointF().x <= startX) {
                        break;
                    }
                    suitKey--;
                }
                suitKey = bakKey;
                // 再右边
                while (suitKey < mData.size()) {
                    endIndex = suitKey;
                    if (mData.get(suitKey).getPointF().x >= endX) {
                        break;
                    }
                    suitKey++;
                }
            }
        }
        return new int[]{startIndex, endIndex};
    }

    /**
     * 1. ax+b >= y
     * 2. a(x+1)+b <= y
     * 得到： (int)x = (y-b) / a
     * 由于 y = b - offset
     * 所以：(int)x = |offset| / a
     */
    private int[] findSuitEdgeInVisual2() {
        int startIndex, endIndex;
        if (offset == 0) {// 不可滑动或当前位于最左边
            startIndex = 0;
            endIndex = Math.min(mData.size() - 1, maxOfVisible - 1);
        } else if (Math.abs(offset) == maxOffset) {// 可滑动且当前位于最右边
            endIndex = mData.size() - 1;
            startIndex = endIndex - maxOfVisible + 1;
        } else {
            startIndex = (int) (Math.abs(offset) / realBetween);
            endIndex = startIndex + maxOfVisible;
        }
        return new int[]{startIndex, endIndex};
    }

    private  float calcTextSuitBaseY(RectF rectF, Paint paint) {
        return rectF.top + rectF.height() / 2 -
                (paint.getFontMetrics().ascent + paint.getFontMetrics().descent) / 2;
    }

    private float getTextHeight(Paint textPaint) {
        return -textPaint.ascent() - textPaint.descent();
    }

    private int getCeil5(float num) {
        return ((int) (num + 4.9f)) / 5 * 5;
    }

    private void trySetColorForEdgeEffect(EdgeEffect edgeEffect, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            edgeEffect.setColor(color);
            return;
        }
        try {
            Field edgeField = EdgeEffect.class.getDeclaredField("mEdge");
            edgeField.setAccessible(true);
            Drawable mEdge = (Drawable) edgeField.get(edgeEffect);
            mEdge.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            mEdge.setCallback(null);
            Field glowField = EdgeEffect.class.getDeclaredField("mGlow");
            glowField.setAccessible(true);
            Drawable mGlow = (Drawable) glowField.get(edgeEffect);
            mGlow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            mGlow.setCallback(null);
        } catch (Exception ignored) {
        }
    }
}
