package me.jessyan.peach.shop.widget.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.callback.OnRefreshDropInListener;

/**
 * Created by Administrator on 2017/4/13.
 *
 */

public class PtrFrameHeader extends FrameLayout implements PtrUIHandler {

    private Context context;
    private static final String TAG = "PtrFrameHeader";
    private RefreshView refreshView;
    private TextView tvTips;
    private static final String PREPARE_TEXT = "下拉刷新";
    private static final String ARRIVE_TEXT = "松手刷新";
    private static final String BEING_TEXT = "正在刷新";
    private static final String COMPLETE_TEXT = "刷新完成";

    public PtrFrameHeader(Context context) {
        this(context, null);
    }

    public PtrFrameHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrFrameHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.refresh_header, this, true);//参数三一定要为true
        refreshView = view.findViewById(R.id.refresh_view);
        tvTips = view.findViewById(R.id.tv_tips);
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
        refreshView.stopRotating();
    }

    /**
     * prepare for loading
     * 准备加载
     */
    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        tvTips.setText(PREPARE_TEXT);
    }

    /**
     * perform refreshing UI
     * 刷新中
     */
    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        tvTips.setText(BEING_TEXT);
        refreshView.setProgress(1.0f);
        refreshView.starRotating();
    }

    /**
     * perform UI after refresh
     * 刷新结束
     */
    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        tvTips.setText(COMPLETE_TEXT);
        refreshView.stopRotating();
    }

    /**
     * perform UI after refresh
     * 下拉过程[逻辑判断对应step0,step4]
     */
    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch,
                                   byte status, PtrIndicator ptrIndicator) {
        //这个值是下拉的系数
        float currentPercent = ptrIndicator.getCurrentPercent();
        float ratio = frame.getRatioOfHeaderToHeightRefresh();
        int currentPosY = ptrIndicator.getCurrentPosY();
        if (mDropInListener != null) {
            mDropInListener.dropIn(currentPercent, currentPosY);
        }

        if (isUnderTouch && !refreshView.isRunning()) {
            if (currentPercent > ratio) {
                currentPercent = ratio;
                tvTips.setText(ARRIVE_TEXT);
            } else {
                tvTips.setText(PREPARE_TEXT);
            }
            refreshView.setProgress(currentPercent / ratio);
        }
    }


    private OnRefreshDropInListener mDropInListener;

    public void setDropInListener(OnRefreshDropInListener dropInListener) {
        mDropInListener = dropInListener;
    }
}
