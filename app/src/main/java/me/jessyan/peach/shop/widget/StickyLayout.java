package me.jessyan.peach.shop.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.CommonConstant;
import timber.log.Timber;

/**
 * author: Create by HuiRan on 2018/12/16 下午1:14
 * email: 15260828327@163.com
 * description:
 */
public class StickyLayout extends FrameLayout {

    private List<TextView> childViews = new ArrayList<>();
    private static final String TAG = "StickyLayout";

    //the default
    public static final int TOP = 1;
    public static final int BOTTOM = 2;
    public static final int NORMAL = 0;

    public StickyLayout(@NonNull Context context) {
        super(context);
        initView();
    }

    public StickyLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public StickyLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        ViewGroup inflate = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.sticky_layout, this, true);
        TextView tvCouponsValue = inflate.findViewById(R.id.tv_coupons_value);
        TextView tvUseCoupons = inflate.findViewById(R.id.tv_use_coupons);
        TextView tvCommission = inflate.findViewById(R.id.tv_commission);
        TextView tvSoldCount = inflate.findViewById(R.id.tv_sold_count);

        childViews.add(tvCouponsValue);
        childViews.add(tvUseCoupons);
        childViews.add(tvCommission);
        childViews.add(tvSoldCount);
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                onChildClick((TextView) v);
            }
        };

        for (int i = 0; i < childViews.size(); i++) {
            TextView view = childViews.get(i);
            view.setOnClickListener(onClickListener);
            view.setTag(R.id.tag_position, i);
            setTagState(view, i == 0 ? TOP : NORMAL);
            view.setSelected(i == 0);
        }
    }


    private void onChildClick(TextView v) {
        Object stateTag = v.getTag(R.id.tag_state);
        Object positionTag = v.getTag(R.id.tag_position);
        if (stateTag == null || positionTag == null) {
            Timber.tag(TAG).w("u must set tag first...");
            return;
        }
        int state = (int) stateTag;
        int position = (int) positionTag;
        int newState;
        if (v.isSelected()) {
            newState = state == TOP ? BOTTOM : TOP;
            setTagState(v, newState);
        } else {
            for (TextView childView : childViews) {
                if (childView.isSelected()) {
                    childView.setSelected(false);
                    setTagState(childView, NORMAL);
                    break;
                }
            }
            v.setSelected(true);
            //the default
            newState = TOP;
            setTagState(v, newState);
        }

        if (mOnStickyTabChangeListener != null) {
            String sort = newState == TOP ? "top" :
                    newState == BOTTOM ? "bot" : CommonConstant.EMPTY_STRING;
            mOnStickyTabChangeListener.onStickyTabChange(position, sort);
        }
    }

    private void setTagState(TextView view, int state) {
        Object tag = view.getTag(R.id.tag_state);
        if (tag != null && (int) tag == state) {
            return;
        }
        view.setTag(R.id.tag_state, state);
        Drawable drawable;
        if (state == TOP) {
            drawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_filter_arrow_up);
        } else if (state == BOTTOM) {
            drawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_filter_arrow_down);
        } else {
            drawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_filter_arrow_normal);
        }
        if (drawable != null) {
            view.setCompoundDrawablePadding(SizeUtils.dp2px(3));
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            view.setCompoundDrawables(null, null, drawable, null);
        }
    }

    private OnStickyTabChangeListener mOnStickyTabChangeListener;

    public void setOnStickyTabChangeListener(OnStickyTabChangeListener onStickyTabChangeListener) {
        mOnStickyTabChangeListener = onStickyTabChangeListener;
    }

    public interface OnStickyTabChangeListener {
        void onStickyTabChange(int position, String sort);
    }
}
