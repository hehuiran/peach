package me.jessyan.peach.shop.mine.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Space;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.mine.mvp.ui.fragment.OrderParentFragment;

public class OrderActivity extends BaseActivity {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;
    private Fragment[] mFragments = new Fragment[2];
    private FragmentManager mFragmentManager;
    private int lastIndex = -1;

    public static void launcher(Context context) {
        Intent intent = new Intent(context, OrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //do nothing
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mFragmentManager = getSupportFragmentManager();
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_buy:
                        addFragment(0);
                        break;
                    case R.id.rb_reward:
                        addFragment(1);
                        break;
                }
            }
        });
        ((RadioButton) mRadioGroup.getChildAt(0)).setChecked(true);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    private void addFragment(int position) {
        FragmentTransaction transaction =
                mFragmentManager.beginTransaction();
        if (lastIndex >= 0 && mFragments[lastIndex] != null) {
            transaction.hide(mFragments[lastIndex]);
        }
        if (mFragments[position] == null) {
            switch (position) {
                case 0:
                    mFragments[position] = OrderParentFragment.newInstance(position);
                    break;
                case 1:
                    mFragments[position] = OrderParentFragment.newInstance(position);
                    break;
            }
            transaction.add(R.id.frame_layout, mFragments[position]);
        } else {
            transaction.show(mFragments[position]);
        }
        transaction.commit();
        lastIndex = position;
    }

    @Override
    public boolean useFragment() {
        return true;
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
