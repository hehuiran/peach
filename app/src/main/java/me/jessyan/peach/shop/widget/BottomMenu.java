package me.jessyan.peach.shop.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.utils.ClickUtils;


/**
 * Created by Administrator on 2016/10/25 0025.
 */
public class BottomMenu extends RelativeLayout {

    private ImageView ivMenu;
    private TextView tvMenu, tvNumber;
    private int resNormalId;
    private int resSelectedId;
    private String imgNormal, imgSeleced;
    private String menuText;

    private float FACTOR = 0.3f;
    private final int mNormalColor;
    private final int mSelectColor;
    public boolean isChecked;
    private final GestureDetector mGestureDetector;
    private final Interpolator mInterpolator;
    private final ImageLoader mImageLoader;

    public BottomMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        mImageLoader = ArmsUtils.getImageLoaderInstance(context);
        View view = View.inflate(context, R.layout.bottom_menu, this);
        ivMenu = view.findViewById(R.id.iv_menu);
        tvMenu = view.findViewById(R.id.tv_menu);
        tvNumber = view.findViewById(R.id.tv_num);
        tvNumber.setVisibility(GONE);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BottomMenu);
        menuText = typedArray.getString(R.styleable.BottomMenu_menu_text);
        resNormalId = typedArray.getResourceId(R.styleable.BottomMenu_menu_icon_normal, -1);
        resSelectedId = typedArray.getResourceId(R.styleable.BottomMenu_menu_icon_selected, -1);
        int textSize = typedArray.getInteger(R.styleable.BottomMenu_menu_text_size, 12);
        mNormalColor = typedArray.getColor(R.styleable.BottomMenu_menu_text_normal_color, Color.GRAY);
        mSelectColor = typedArray.getColor(R.styleable.BottomMenu_menu_text_selected_color, Color.GRAY);
        typedArray.recycle();

        ivMenu.setImageResource(resNormalId);
        tvMenu.setText(menuText);
        tvMenu.setTextSize(textSize);

        mInterpolator = new LinearInterpolator();
        //mInterpolator = new AccelerateInterpolator();

        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener());
        mGestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (mOnBottomMenuCheckedListener != null && ClickUtils.isNormalClick(BottomMenu.this)) {
                    mOnBottomMenuCheckedListener.onSingleTap(BottomMenu.this);
                }
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (mOnBottomMenuCheckedListener != null && ClickUtils.isNormalClick(BottomMenu.this)) {
                    mOnBottomMenuCheckedListener.onDoubleTap(BottomMenu.this);
                }
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                return false;
            }
        });
    }

    /**
     * 动态设置导航栏图标
     *
     * @param imgs 下标：0 默认图标； 1 被选中图标
     */
    public void setMenuIcon(String[] imgs) {
        if (imgs == null || imgs.length < 2) {
            return;
        }
        //LogUtils.di(imgs[0]+" / /   "+imgs[1]);
        imgNormal = imgs[0];
        imgSeleced = imgs[1];

        mImageLoader.loadImage(getContext(),
                ImageConfigImpl.builder()
                        .imageView(ivMenu)
                        .url(isChecked ? imgSeleced : imgNormal)
                        .build());
    }

    /**
     * 动态设置导航栏文字
     *
     * @param name 文字
     */
    public void setMenuName(String name) {
        if (TextUtils.isEmpty(name)) {
            return;
        }
        menuText = name;
        tvMenu.setText(name);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    public void selectMenu(boolean isListener, boolean isAnim) {
        isChecked = true;
        if (isListener && mOnBottomMenuCheckedListener != null) {
            mOnBottomMenuCheckedListener.onCheckedStateChange(BottomMenu.this, true);
        }
        tvMenu.setTextColor(mSelectColor);
        if (TextUtils.isEmpty(imgSeleced)) {
            ivMenu.setImageResource(resSelectedId);
        } else {
            mImageLoader.loadImage(getContext(),
                    ImageConfigImpl.builder()
                            .imageView(ivMenu)
                            .url(imgSeleced)
                            .build());
        }

        if (isAnim) {
            updateView(isListener);
        } else {
            if (isListener && mOnBottomMenuCheckedListener != null) {
                mOnBottomMenuCheckedListener.onCheckedStateChange(BottomMenu.this, false);
            }
        }
    }

    public void unSelectMenu() {
        isChecked = false;
        tvMenu.setTextColor(mNormalColor);

        if (TextUtils.isEmpty(imgSeleced)) {
            ivMenu.setImageResource(resNormalId);
        } else {
            mImageLoader.loadImage(getContext(),
                    ImageConfigImpl.builder()
                            .imageView(ivMenu)
                            .url(imgNormal)
                            .build());
        }

    }

    @SuppressLint("SetTextI18n")
    public void showMsg(int msgCount) {
        if (msgCount <= 0) {
            tvNumber.setVisibility(GONE);
            return;
        }
        tvNumber.setVisibility(VISIBLE);
        if (msgCount < 100) {
            tvNumber.setText(String.valueOf(msgCount));
        } else {
            tvNumber.setText("99+");
        }
    }

    public boolean isMsgCountShowing() {
        return tvNumber.isShown();
    }

    private void updateView(final boolean isListener) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1)
                .setDuration(200);
        setPivotX(getWidth() / 2);
        setPivotY(getHeight() / 2);
        animator.setInterpolator(mInterpolator);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                /*if (progress < 0.5f) {
                    setScaleX(1 + progress * FACTOR);
                    setScaleY(1 + progress * FACTOR);
                } else {
                    setScaleX(1 + FACTOR / 2 - FACTOR * (progress - 0.5f));
                    setScaleY(1 + FACTOR / 2 - FACTOR * (progress - 0.5f));
                }*/
                if (progress < 0.3f) {
                    setScaleX(1 - progress * 0.2f / 0.3f);
                    setScaleY(1 - progress * 0.2f / 0.3f);
                } else {
                    setScaleX(0.7f + progress * 0.3f);
                    setScaleY(0.7f + progress * 0.3f);
                }


            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isListener && mOnBottomMenuCheckedListener != null) {
                    mOnBottomMenuCheckedListener.onCheckedStateChange(BottomMenu.this, false);
                }
            }
        });
        animator.start();
    }

    private OnBottomMenuCheckedListener mOnBottomMenuCheckedListener;

    public void setOnBottomMenuCheckedListener(OnBottomMenuCheckedListener onBottomMenuCheckedListener) {
        mOnBottomMenuCheckedListener = onBottomMenuCheckedListener;
    }

    public interface OnBottomMenuCheckedListener {
        void onCheckedStateChange(View view, boolean isRunning);

        void onSingleTap(View view);

        void onDoubleTap(View view);
    }
}
