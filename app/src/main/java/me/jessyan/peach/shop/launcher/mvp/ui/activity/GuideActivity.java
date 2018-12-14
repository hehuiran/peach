package me.jessyan.peach.shop.launcher.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.SPKey;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.vp.GuideTransformer;
import me.jessyan.peach.shop.launcher.mvp.ui.adapter.GuidePagerAdapter;
import me.jessyan.peach.shop.user.mvp.ui.activity.LoginActivity;

/**
 * author: Create by HuiRan on 2018/12/9 下午3:57
 * email: 15260828327@163.com
 * description:
 */
public class GuideActivity extends BaseActivity implements GuidePagerAdapter.OnItemClickListener {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.linearCircle)
    LinearLayout mLinearCircle;

    private int mPosition;
    private List<Integer> mList;

    public static void launcher(@NonNull Context context) {
        Intent intent = new Intent(context, GuideActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //do nothing
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_guide;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initViewPager();

        initCircle();
    }

    private void initCircle() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width = 16;
        layoutParams.height = 16;
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < mList.size(); i++) {
            View view = new View(this);
            view.setLayoutParams(layoutParams);
            mLinearCircle.addView(view);
        }
        setLinearCircle(0);
    }

    private ViewPager.SimpleOnPageChangeListener mOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            setLinearCircle(position);
            mPosition = position;
        }
    };

    private void initViewPager() {
        mList = new ArrayList<>();
        mList.add(R.mipmap.img_welcome_1);
        mList.add(R.mipmap.img_welcome_2);
        mList.add(R.mipmap.img_welcome_3);
        mList.add(R.mipmap.img_welcome_4);
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        GuidePagerAdapter pagerAdapter = new GuidePagerAdapter(mList);
        mViewPager.setPageTransformer(false, new GuideTransformer());
        pagerAdapter.setOnItemClickListener(this);
        mViewPager.setAdapter(pagerAdapter);
    }

    private void setLinearCircle(int position) {
        if (mLinearCircle.getChildCount() > position) {
            for (int i = 0; i < mLinearCircle.getChildCount(); i++) {
                View view = mLinearCircle.getChildAt(i);
                if (i == position) {
                    view.setBackgroundResource(R.drawable.shape_white);
                } else {
                    view.setBackgroundResource(R.drawable.shape_white_lucency);
                }
            }
        }
    }

    @Override
    public void onItemClick() {
        if (mPosition == mList.size() - 1) {
            SPUtils.getInstance().put(SPKey.SHOW_GUIDE_IMAGE, AppUtils.getAppVersionCode());
            String token = UserInfo.getInstance().getToken();
            if (TextUtils.isEmpty(token)) {
                LoginActivity.launcher(this, LoginActivity.LOGIN_WAY_SPLASH);
            } else {
                MainActivity.launcher(this,true);
            }
            finish();
        }
    }
}
