package me.jessyan.peach.shop.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * author: Created by HuiRan on 2017/11/22 10:08
 * E-Mail: 15260828327@163.com
 * description: 处理item里面有横向滑动的view
 */

public class DispatchTouchRecyclerView extends RecyclerView {

    private int touchSlop;
    private Context mContext;
    private int INVALID_POINTER = -1;
    private int scrollPointerId = INVALID_POINTER;
    private int initialTouchX;
    private int initialTouchY;

    public DispatchTouchRecyclerView(Context context) {
        this(context, null);
    }

    public DispatchTouchRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DispatchTouchRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ViewConfiguration vc = ViewConfiguration.get(context);
        touchSlop = vc.getScaledEdgeSlop();
        mContext = context;
    }

    @Override
    public void setScrollingTouchSlop(int slopConstant) {
        super.setScrollingTouchSlop(slopConstant);
        ViewConfiguration vc = ViewConfiguration.get(mContext);
        switch (slopConstant) {
            case TOUCH_SLOP_DEFAULT:
                touchSlop = vc.getScaledTouchSlop();
                break;
            case TOUCH_SLOP_PAGING:
                touchSlop = vc.getScaledPagingTouchSlop();
                break;
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (e == null) {
            return false;
        }

        int action = e.getAction();
        int actionIndex = e.getActionIndex();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                scrollPointerId = e.getPointerId(0);
                initialTouchX = Math.round(e.getX() + 0.5f);
                initialTouchY = Math.round(e.getY() + 0.5f);
                return super.onInterceptTouchEvent(e);
            case MotionEvent.ACTION_POINTER_DOWN:
                scrollPointerId = e.getPointerId(actionIndex);
                initialTouchX = Math.round(e.getX(actionIndex) + 0.5f);
                initialTouchY = Math.round(e.getY(actionIndex) + 0.5f);
                return super.onInterceptTouchEvent(e);
            case MotionEvent.ACTION_MOVE:
                int index = e.findPointerIndex(scrollPointerId);
                if (index < 0) {
                    return false;
                }
                int x = Math.round(e.getX(index) + 0.5f);
                int y = Math.round(e.getY(index) + 0.5f);
                if (getScrollState() != SCROLL_STATE_DRAGGING) {
                    int dx = x - initialTouchX;
                    int dy = y - initialTouchY;
                    boolean startScroll = false;
                    //将斜率添加进来，这样可以减少 startScroll 为true 的机会。这个机会就会给需要这个返回值
                    if (getLayoutManager().canScrollHorizontally() && Math.abs(dx) > touchSlop &&
                            Math.abs(dx) > Math.abs(dy)) {
                        startScroll = true;
                    }
                    if (getLayoutManager().canScrollVertically() && Math.abs(dy) > touchSlop &&
                            Math.abs(dy) > Math.abs(dx)) {
                        startScroll = true;
                    }
                    return startScroll && super.onInterceptTouchEvent(e);
                }
                return super.onInterceptTouchEvent(e);
            default:
                return super.onInterceptTouchEvent(e);
        }
    }
}
