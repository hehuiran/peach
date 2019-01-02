package me.jessyan.peach.shop.user.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.user.LoginBean;
import me.jessyan.peach.shop.entity.user.ThirdPartyInfoBean;
import me.jessyan.peach.shop.launcher.mvp.ui.activity.MainActivity;
import me.jessyan.peach.shop.user.di.component.DaggerBindMobileComponent;
import me.jessyan.peach.shop.user.mvp.contract.BindMobileContract;
import me.jessyan.peach.shop.user.mvp.presenter.BindMobilePresenter;
import me.jessyan.peach.shop.utils.StringUtils;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/20/2018 21:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class BindMobileActivity extends BaseActivity<BindMobilePresenter> implements BindMobileContract.View {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.edit_mobile)
    EditText mEditMobile;
    @BindView(R.id.edit_verify_code)
    EditText mEditVerifyCode;
    @BindView(R.id.tv_send_code)
    TextView mTvSendCode;
    @BindView(R.id.edit_password)
    EditText mEditPassword;
    @BindView(R.id.edit_password_again)
    EditText mEditPasswordAgain;
    @BindView(R.id.edit_invite_code)
    EditText mEditInviteCode;
    @BindView(R.id.tv_confirm)
    TextView mTvConfirm;
    private CountDownTimer mCountDownTimer;
    private ThirdPartyInfoBean mInfoBean;
    private int mLoginType;

    public static void launcher(@NonNull Context context, @LoginActivity.LoginType int loginType, ThirdPartyInfoBean infoBean) {
        Intent intent = new Intent(context, BindMobileActivity.class);
        intent.putExtra(IntentExtra.LOGIN_WAY, loginType);
        intent.putExtra(IntentExtra.THIRD_PARTY_INFO, infoBean);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBindMobileComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bind_mobile; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        mLoginType = intent.getIntExtra(IntentExtra.LOGIN_WAY, -1);
        mInfoBean = intent.getParcelableExtra(IntentExtra.THIRD_PARTY_INFO);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar
                .statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @OnClick({R.id.iv_back, R.id.tv_send_code, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_send_code:
                sendVerifyCode();
                break;
            case R.id.tv_confirm:
                bindMobile();
                break;
        }
    }

    private void bindMobile() {
        //测试邀请码输入05ELAM
        String mobile = checkMobile();
        String verifyCode = checkVerifyCode();
        String password = checkPassword();
        String againPassword = checkAgainPassword();
        String inviteCode = checkInviteCode();
        if (!StringUtils.isEmpty(mobile, verifyCode, password, againPassword, inviteCode)) {
            if (password.equals(againPassword)) {
                //绑定手机
                HashMap<String, Object> map = new HashMap<>();
                map.put("mobile", mobile);
                map.put("verifyCode", verifyCode);
                map.put("inviterCode", inviteCode);
                map.put("password", password);

                map.put("wxopenid", mInfoBean.getOpenId());
                map.put("accounttype", String.valueOf(mInfoBean.getPlatform()));
                map.put("deviceSerialId", mInfoBean.getDeviceId());
                map.put("gender", mInfoBean.getGender());
                map.put("headimgurl", mInfoBean.getIconUrl());
                map.put("nickname", mInfoBean.getNickName());
                mTvConfirm.setEnabled(false);
                mPresenter.bindMobile(map);
            } else {
                ToastUtils.showShort(R.string.hint_password_twice_error);
            }
        }
    }


    private void sendVerifyCode() {
        String mobile = checkMobile();
        if (TextUtils.isEmpty(mobile)) {
            return;
        }
        mTvSendCode.setEnabled(false);
        mPresenter.getVerifyCode(mobile);
    }

    private String checkMobile() {
        Editable text = mEditMobile.getText();
        if (RegexUtils.isMobileSimple(text)) {
            return text.toString();
        }
        ToastUtils.showShort(R.string.hint_mobile_error);
        return null;
    }

    private String checkVerifyCode() {
        Editable text = mEditVerifyCode.getText();
        if (!TextUtils.isEmpty(text) && text.length() == 6) {
            return text.toString();
        }
        ToastUtils.showShort(R.string.hint_verify_code_error);
        return null;
    }

    private String checkPassword() {
        Editable text = mEditPassword.getText();
        if (!TextUtils.isEmpty(text) && text.length() >= 6 && text.length() <= 12) {
            return text.toString();
        }
        ToastUtils.showShort(R.string.hint_password_error);
        return null;
    }

    private String checkAgainPassword() {
        Editable text = mEditPasswordAgain.getText();
        if (!TextUtils.isEmpty(text) && text.length() >= 6 && text.length() <= 12) {
            return text.toString();
        }
        ToastUtils.showShort(R.string.hint_password_error);
        return null;
    }

    private String checkInviteCode() {
        Editable text = mEditInviteCode.getText();
        if (!TextUtils.isEmpty(text)) {
            return text.toString();
        }
        ToastUtils.showShort(R.string.hint_invite_code_error);
        return null;
    }

    @Override
    public void onGetVerifyCodeSuccess() {
        countDown();
    }

    private void countDown() {
        if (mCountDownTimer == null) {
            mCountDownTimer = new CountDownTimer(60000L, 1000L) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String text = millisUntilFinished / 1000 + "s";
                    mTvSendCode.setText(text);
                }

                @Override
                public void onFinish() {
                    mTvSendCode.setEnabled(true);
                    mTvSendCode.setText(R.string.send_verify_code);
                }
            };
        }
        mCountDownTimer.start();
    }

    @Override
    public void onGetVerifyCodeFailed() {
        mTvSendCode.setEnabled(true);
    }

    @Override
    public void onBindMobileSuccess(BasicResponse<LoginBean> response) {
        int code = response.getCode();
        if (code == 0) {
            //登陆成功
            if (mLoginType == LoginActivity.LOGIN_WAY_MODULES) {
                //从MainActivity跳转至登录界面
                MainActivity.backToMain(this, MainActivity.BACK_TO_MAIN_DO_NOTHING);
            } else if (mLoginType == LoginActivity.LOGIN_WAY_SPLASH) {
                //从SplashActivity跳转至登录界面,MainActivity还未实例化
                ActivityUtils.finishActivity(LoginActivity.class);
                MainActivity.launcher(this, false);
                finish();
            } else if (mLoginType == LoginActivity.LOGIN_WAY_OTHER) {
                //token过期从首页跳转至登录界面
                MainActivity.backToMain(this, MainActivity.BACK_TO_MAIN_RESET_TOKEN);
            }
        } else if (code == 1030) {
            //邀请码不存在
            mTvConfirm.setEnabled(true);
            ToastUtils.showShort(R.string.invite_code_is_not_exist);
        }
    }

    @Override
    public void onBindMobileFailed() {
        mTvConfirm.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        super.onDestroy();
    }
}
