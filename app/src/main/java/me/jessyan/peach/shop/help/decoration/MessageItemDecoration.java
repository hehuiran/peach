package me.jessyan.peach.shop.help.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;

import me.jessyan.peach.shop.constant.RecyclerViewType;

/**
 * author: Created by HuiRan on 2018/5/11 15:36
 * E-Mail: 15260828327@163.com
 * description:
 */

public class MessageItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;
    private Paint mPaint;
    private static final String TAG = "MessageItemDecoration";

    public MessageItemDecoration() {
        this.mSpace = SizeUtils.dp2px(15);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        RecyclerView.Adapter adapter = parent.getAdapter();
        int itemViewType = adapter.getItemViewType(position);
        if (itemViewType == RecyclerViewType.ACTIVITY_TYPE ||
                itemViewType == RecyclerViewType.ALLIANCE_TYPE ||
                itemViewType == RecyclerViewType.SYSTEM_TYPE ||
                itemViewType == RecyclerViewType.INCOME_TYPE) {
            outRect.set(0, position == 0 ? mSpace : 0, 0, mSpace);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        boolean first = true;
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view
                    .getLayoutParams();
            RecyclerView.Adapter adapter = parent.getAdapter();
            int itemViewType = adapter.getItemViewType(i);
            if (itemViewType == RecyclerViewType.ACTIVITY_TYPE ||
                    itemViewType == RecyclerViewType.ALLIANCE_TYPE ||
                    itemViewType == RecyclerViewType.SYSTEM_TYPE ||
                    itemViewType == RecyclerViewType.INCOME_TYPE) {
                final int top = view.getBottom() + params.bottomMargin;
                final int bottom = top + mSpace;
                c.drawRect(left, top, right, bottom, mPaint);
                if (first) {
                    int parentTop = parent.getPaddingTop();
                    c.drawRect(left, parentTop, right, parentTop + mSpace, mPaint);
                    first = false;
                }
            }


        }
    }
}
