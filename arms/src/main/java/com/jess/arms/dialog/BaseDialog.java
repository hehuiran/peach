package com.jess.arms.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.R;

/**
 *
 */

public class BaseDialog extends Dialog {
    private BaseController mAlert;

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        mAlert = new BaseController(this, getWindow());
    }

    //设置文本
    public BaseDialog setText(int viewId, CharSequence text) {
        mAlert.setText(viewId, text);
        return this;
    }

    //设置点击事件
    public BaseDialog setOnClick(int viewId, View.OnClickListener listener) {
        mAlert.setOnClick(viewId, listener);
        return this;
    }

    //减少findVIewById的次数
    public <T extends View> T getView(int viewId) {
        return mAlert.getView(viewId);
    }

    public static class Builder {
        private final BaseController.BaseParams P;

        public Builder(Context context) {
            this(context, R.style.alert_dialog);
        }

        public Builder(Context context, int themeResId) {
            P = new BaseController.BaseParams(context, themeResId);
        }

        public BaseDialog create() {
            final BaseDialog dialog = new BaseDialog(P.mContext, P.mThemeResId);
            P.apply(dialog.mAlert);
            if (!P.mCancelable) {
                dialog.setCancelable(false);
            }
            dialog.setCanceledOnTouchOutside(P.mCancel);
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }

        //设置布局
        public BaseDialog show() {
            final BaseDialog dialog = create();
            dialog.show();
            return dialog;
        }

        public Builder setContentView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        public Builder setContentView(int layoutId) {
            P.mView = null;
            P.mViewLayoutResId = layoutId;
            return this;
        }

        //设置文字
        public Builder setText(int viewId, CharSequence text) {
            P.mTextArray.put(viewId, text);
            return this;
        }

        //设置点击事件
        public Builder setOnClickListener(int viewId, View.OnClickListener listener) {
            P.mClickArray.put(viewId, listener);
            return this;
        }

        //配置一些万能的参数

        //是否宽度填充
        public Builder fullWidth() {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        public Builder needSoft(boolean needSoft) {
            P.needSoft = needSoft;
            return this;
        }

        //是否高度填充
        public Builder fullHeight() {
            P.mHeight = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        //是否从底部弹出
        public Builder fromBottom(boolean isAnimation) {
            if (isAnimation) {
                P.mAnimation = R.style.dialog_from_bottom_anim;
            }
            P.mGravity = Gravity.BOTTOM;
            return this;
        }

        public Builder fromTop(boolean isAnimation) {
            if (isAnimation) {
                P.mAnimation = R.style.dialog_from_top_anim;
            }
            P.mGravity = Gravity.TOP;
            return this;
        }

        //设置宽高
        public Builder setWidthAndHeight(int width, int height) {
            P.mWidth = width;
            P.mHeight = height;
            return this;
        }

        public Builder setWidth(int width) {
            P.mWidth = width;
            return this;
        }

        public Builder setHeight(int height) {
            P.mHeight = height;
            return this;
        }

        public Builder setWindowAlpha(float alpha) {
            P.mWindowAlpha = alpha;
            return this;
        }

        //默认动画
        public Builder addDefaultAnimation() {
            P.mAnimation = R.style.dialog_from_alpha_anim;
            return this;
        }

        //自定义动画
        public Builder addAnimation(int styleAnimation) {
            P.mAnimation = styleAnimation;
            return this;
        }


        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean cancel) {
            P.mCancel = cancel;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }
    }

}
