package me.jessyan.peach.shop.widget.refresh;

import android.content.Context;
import android.util.AttributeSet;

import in.srain.cube.views.ptr.PtrFrameLayout;
import me.jessyan.peach.shop.callback.OnRefreshDropInListener;

/**
 * Author: Created by He on 2017/9/22.
 * Description:
 */

public class PullRefreshView extends PtrFrameLayout {

    private static final String TAG = "PullRefreshView";
    private PtrFrameHeader2 mRefreshHeader;

    public PullRefreshView(Context context) {
        super(context);
        addHeaderView();
    }

    public PullRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addHeaderView();
    }

    public PullRefreshView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        addHeaderView();
    }


    private void addHeaderView() {
        setDurationToClose(300);
        setDurationToCloseHeader(200);
        setKeepHeaderWhenRefresh(true);
        setPullToRefresh(false);
        setRatioOfHeaderHeightToRefresh(1.2f);
        setResistance(1.7f);

        mRefreshHeader = new PtrFrameHeader2(getContext());
        setHeaderView(mRefreshHeader);
        addPtrUIHandler(mRefreshHeader);
    }


    public void setOnRefreshDropInListener(OnRefreshDropInListener listener) {
        if (listener == null) {
            throw new NullPointerException("OnRefreshDropInListener is null");
        }
        mRefreshHeader.setDropInListener(listener);
    }
}
