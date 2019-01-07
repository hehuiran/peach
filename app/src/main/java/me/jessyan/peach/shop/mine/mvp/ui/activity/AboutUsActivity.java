package me.jessyan.peach.shop.mine.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Space;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.BuildConfig;
import me.jessyan.peach.shop.R;

public class AboutUsActivity extends BaseActivity {


    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.tv_version)
    TextView mTvVersion;

    public static void launcher(Context context) {
        Intent intent = new Intent(context, AboutUsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_about_us;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String versionName = "V" + BuildConfig.VERSION_NAME;
        mTvVersion.setText(versionName);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar
                .statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @OnClick({R.id.iv_back, R.id.fl_update, R.id.fl_peach})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.fl_update:
                break;
            case R.id.fl_peach:
                break;
        }
    }
}
