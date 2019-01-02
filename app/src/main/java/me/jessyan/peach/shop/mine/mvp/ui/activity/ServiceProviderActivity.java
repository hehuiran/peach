package me.jessyan.peach.shop.mine.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Space;

import com.blankj.utilcode.util.KeyboardUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.mine.di.component.DaggerServiceProviderComponent;
import me.jessyan.peach.shop.mine.mvp.contract.ServiceProviderContract;
import me.jessyan.peach.shop.mine.mvp.presenter.ServiceProviderPresenter;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/01/2019 22:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ServiceProviderActivity extends BaseActivity<ServiceProviderPresenter> implements ServiceProviderContract.View {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.edit_real_name)
    EditText mEditRealName;
    @BindView(R.id.edit_mobile)
    EditText mEditMobile;

    public static void launcher(Context context) {
        Intent intent = new Intent(context, ServiceProviderActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerServiceProviderComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_service_provider; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(mStatusView)
                .init();
    }

    @OnClick({R.id.iv_back, R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                backPrevious();
                break;
            case R.id.tv_commit:
                break;
        }
    }

    private void backPrevious() {
        KeyboardUtils.hideSoftInput(this);
        onBackPressed();
    }
}
