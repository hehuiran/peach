package me.jessyan.peach.shop.user.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.EditText;
import android.widget.Space;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.user.di.component.DaggerRegisterComponent;
import me.jessyan.peach.shop.user.mvp.contract.RegisterContract;
import me.jessyan.peach.shop.user.mvp.presenter.RegisterPresenter;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/18/2018 23:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * 手机注册接口： user/mobilelogin
 * 参数：mobile 手机号  verifyCode 验证码 inviterCode 邀请码  passworld 密码
 * ================================================
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.edit_mobile)
    EditText mEditMobile;
    @BindView(R.id.edit_verify_code)
    EditText mEditVerifyCode;
    @BindView(R.id.edit_password)
    EditText mEditPassword;
    @BindView(R.id.edit_password_again)
    EditText mEditPasswordAgain;
    @BindView(R.id.edit_invite_code)
    EditText mEditInviteCode;
    @BindView(R.id.check_box)
    AppCompatCheckBox mCheckBox;
    private int mLoginType;

    public static void launcher(@NonNull Context context, @LoginActivity.LoginType int loginType) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra(IntentExtra.LOGIN_WAY, loginType);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRegisterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_register; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        mLoginType = intent.getIntExtra(IntentExtra.LOGIN_WAY, -1);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar
                .statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .keyboardEnable(true)
                .init();
    }

    @OnClick({R.id.iv_back, R.id.tv_login, R.id.tv_register, R.id.tv_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.tv_login:
                break;
            case R.id.tv_register:
                break;
            case R.id.tv_agreement:
                break;
        }
    }
}
