package me.jessyan.peach.shop.widget.refresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.callback.OnRefreshDropInListener;

/**
 * Created by Administrator on 2017/4/13.
 */

public class PtrFrameHeader2 extends FrameLayout implements PtrUIHandler {

    private Context context;
    private static final String TAG = "PtrFrameHeader2";
    private AnimationDrawable refreshAnim;

    public PtrFrameHeader2(Context context) {
        this(context, null);
    }

    public PtrFrameHeader2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrFrameHeader2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.refresh_header2, this, true);//参数三一定要为true
        ImageView ivRefreshView = view.findViewById(R.id.refreshView);

        ivRefreshView.setBackgroundResource(R.drawable.anim_refresh);
        refreshAnim = (AnimationDrawable) ivRefreshView.getBackground();

    }

    /**
     * When the content view has reached top and refresh has been completed, view will be reset.
     * 当内容视图已达到顶部和更新已经完成,视图将被重新设置
     * 刷新结束隐藏hederView
     *
     * @param frame
     */
    @Override
    public void onUIReset(PtrFrameLayout frame) {
        resetView();
    }

    private void resetView() {
        if (refreshAnim != null && refreshAnim.isRunning()) {
            refreshAnim.stop();
        }
    }

    /**
     * prepare for loading
     * 准备加载
     */
    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    /**
     * perform refreshing UI
     * 刷新中
     */
    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        if (refreshAnim != null && !refreshAnim.isRunning()) {
            refreshAnim.start();
        }
    }

    /**
     * perform UI after refresh
     * 刷新结束
     */
    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        resetView();
    }

    /**
     * perform UI after refresh
     * 下拉过程[逻辑判断对应step0,step4]
     */
    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch,
                                   byte status, PtrIndicator ptrIndicator) {
        if (refreshAnim == null) {
            return;
        }
        //这个值是下拉的系数
        float currentPercent = ptrIndicator.getCurrentPercent();
        //float ratio = frame.getRatioOfHeaderToHeightRefresh();
        int currentPosY = ptrIndicator.getCurrentPosY();
        if (mDropInListener != null) {
            mDropInListener.dropIn(currentPercent, currentPosY);
        }
    }


    private OnRefreshDropInListener mDropInListener;

    public void setDropInListener(OnRefreshDropInListener dropInListener) {
        mDropInListener = dropInListener;
    }
}
