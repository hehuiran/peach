package me.jessyan.peach.shop.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;

/**
 * author: Created by HuiRan on 2018/4/25 16:42
 * E-Mail: 15260828327@163.com
 * description:
 */

public class BottomMenuLayout extends LinearLayout implements BottomMenu.OnBottomMenuCheckedListener {

    private BottomMenu mBmLast;
    private boolean isRunning;
    private static final String TAG = "BottomMenuLayout";
    private SparseIntArray mPositions;

    public BottomMenuLayout(Context context) {
        super(context);
    }

    public BottomMenuLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomMenuLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void setLastBottomMenu(BottomMenu bottomMenu) {
        this.mBmLast = bottomMenu;
    }

    private void init() {
        int childCount = getChildCount();
        int position = 0;
        if (mPositions == null) {
            mPositions = new SparseIntArray();
        }
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if (!(view instanceof BottomMenu)) {
                continue;
            }
            mPositions.put(position, i);
            BottomMenu bottomMenu = (BottomMenu) view;
            bottomMenu.setOnBottomMenuCheckedListener(this);
            position++;
        }
    }

    public void checkedChild(int position) {
        position = mPositions.get(position);
        View child = getChildAt(position);
        if (child == null) {
            throw new NullPointerException("the child view can not be null reference");
        }
        if (!(child instanceof BottomMenu)) {
            throw new ClassCastException("类型转换出错了");
        }
        if (mBmLast != null) {
            mBmLast.unSelectMenu();
        }
        BottomMenu bottomMenu = (BottomMenu) child;
        bottomMenu.selectMenu(true, false);
        mBmLast = bottomMenu;
    }

    public void release() {
        if (mPositions != null) {
            mPositions.clear();
        }
    }

    public void clear()
    {
        if (mBmLast!=null)
            mBmLast.unSelectMenu();
    }

    @Override
    public void onCheckedStateChange(View view, boolean isRunning) {
        BottomMenu bottomMenu = (BottomMenu) view;
        this.isRunning = isRunning;
        if (mOnBottomMenuCheckedChangeListener != null && !isRunning) {
            mOnBottomMenuCheckedChangeListener.onBottomMenuCheckedChange(bottomMenu);
        }
    }

    @Override
    public void onSingleTap(View view) {
        //新增单击事件  处理首页快速切换需求
        if (mOnBottomMenuCheckedChangeListener != null) {
            mOnBottomMenuCheckedChangeListener.onSingleClick((BottomMenu) view);
        }
        if (isRunning) {
            return;
        }
        BottomMenu menu = (BottomMenu) view;
        if (!menu.isChecked) {
            if (mBmLast != null) {
                mBmLast.unSelectMenu();
            }
            menu.selectMenu(true, true);
            mBmLast = menu;
        }
    }

    @Override
    public void onDoubleTap(View view) {
        if (isRunning) {
            return;
        }
        BottomMenu menu = (BottomMenu) view;
        if (!menu.isChecked) {
            if (mBmLast != null) {
                mBmLast.unSelectMenu();
            }
            menu.selectMenu(true, true);
            mBmLast = menu;
        } else {
            if (mOnBottomMenuCheckedChangeListener != null) {
                mOnBottomMenuCheckedChangeListener.onBottomMenuDoubleTap(menu);
            }
        }
    }

    private OnBottomMenuCheckedChangeListener mOnBottomMenuCheckedChangeListener;

    public void setOnBottomMenuCheckedChangeListener(OnBottomMenuCheckedChangeListener onBottomMenuCheckedChangeListener) {
        mOnBottomMenuCheckedChangeListener = onBottomMenuCheckedChangeListener;
    }

    public interface OnBottomMenuCheckedChangeListener {
        void onBottomMenuCheckedChange(BottomMenu view);

        default void onBottomMenuDoubleTap(BottomMenu view){}

        default void onSingleClick(BottomMenu view){}
    }
}
