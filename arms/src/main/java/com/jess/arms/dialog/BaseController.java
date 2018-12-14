package com.jess.arms.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 *
 */

public class BaseController {
    private BaseDialog mBaseDialog;
    private Window mWindow;
    private DialogHelper mDialogHelper;

    public BaseController(BaseDialog dialog, Window window) {
        this.mBaseDialog = dialog;
        this.mWindow = window;
    }

    /**
     * 获取dialog
     */
    public BaseDialog getBaseDialog() {
        return mBaseDialog;
    }

    /**
     * 获取dialog所在的window
     */
    public Window getWindow() {
        return mWindow;
    }

    public void setDialogHelper(DialogHelper dialogHelper) {
        mDialogHelper = dialogHelper;
    }

    //设置文本
    public void setText(int viewId, CharSequence text) {
        mDialogHelper.setText(viewId, text);
    }

    //设置点击事件
    public void setOnClick(int viewId, View.OnClickListener listener) {
        mDialogHelper.setOnClick(viewId, listener);
    }

    //减少findVIewById的次数
    public <T extends View> T getView(int viewId) {
        return mDialogHelper.getView(viewId);
    }

    public static class BaseParams {

        public Context mContext;
        public int mThemeResId;
        public boolean mCancelable = true;
        //点击空白是否可以取消
        public boolean mCancel = false;
        //dialog展示隐藏监听
        public DialogInterface.OnCancelListener mOnCancelListener;
        //DIsmiss监听
        public DialogInterface.OnDismissListener mOnDismissListener;
        //key按键监听
        public DialogInterface.OnKeyListener mOnKeyListener;
        //布局的View
        public View mView;
        //布局的layout id
        public int mViewLayoutResId;
        //如果hashMap的key是Integer，那么SparseArray会更高效
        //存放文本信息,和字体修改
        public SparseArray<CharSequence> mTextArray = new SparseArray<>();
        //存放dialog里面的所有点击事件
        public SparseArray<View.OnClickListener> mClickArray = new SparseArray<>();
        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        //进出动画
        public int mAnimation = 0;
        //位置默认在中间
        public int mGravity = Gravity.CENTER;
        //高度
        public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
        //窗口透明度
        public float mWindowAlpha = 0.6f;
        //是否需要软键盘
        public boolean needSoft = false;

        public BaseParams(Context context, int themeResId) {

            this.mContext = context;
            this.mThemeResId = themeResId;
        }

        /**
         * 绑定和设置参数
         */
        public void apply(BaseController alert) {
            //1、设置布局
            DialogHelper viewHelper = null;
            if (mViewLayoutResId != 0) {
                viewHelper = new DialogHelper(mContext, mViewLayoutResId);
            }

            if (mView != null) {
                viewHelper = new DialogHelper();
                viewHelper.setContentView(mView);
            }

            if (viewHelper == null) {
                throw new IllegalArgumentException("请设置布局setContentView()");
            }
            alert.getBaseDialog().setContentView(viewHelper.getContentView());

            //设置Controller辅助类
            alert.setDialogHelper(viewHelper);

            //2、设置文本
            int textArraySize = mTextArray.size();
            for (int i = 0; i < textArraySize; i++) {
                alert.setText(mTextArray.keyAt(i), mTextArray.valueAt(i));
            }

            //3、设置点击
            int clickArraySize = mClickArray.size();
            for (int i = 0; i < clickArraySize; i++) {
                alert.setOnClick(mClickArray.keyAt(i), mClickArray.valueAt(i));
            }

            //配置自定义效果，动画
            Window window = alert.getWindow();
            if (needSoft) {
                //让dialog可以弹出软键盘
                window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            }

            //设置位置
            window.setGravity(mGravity);
            //设置动画
            if (mAnimation != 0) {
                window.setWindowAnimations(mAnimation);
            }

            //设置宽高
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = mWidth;
            params.height = mHeight;
            params.dimAmount = mWindowAlpha;
//            params.alpha = mWindowAlpha;
            window.setAttributes(params);

        }
    }
}
