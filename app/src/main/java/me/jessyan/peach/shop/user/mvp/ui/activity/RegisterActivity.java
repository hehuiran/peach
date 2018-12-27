package me.jessyan.peach.shop.user.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.telephony.TelephonyManager;
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
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.entity.user.LoginBean;
import me.jessyan.peach.shop.launcher.mvp.ui.activity.MainActivity;
import me.jessyan.peach.shop.user.di.component.DaggerRegisterComponent;
import me.jessyan.peach.shop.user.mvp.contract.RegisterContract;
import me.jessyan.peach.shop.user.mvp.presenter.RegisterPresenter;
import me.jessyan.peach.shop.utils.StringUtils;


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
 * 参数：mobile 手机号  verifyCode 验证码 inviterCode 邀请码  password 密码
 * 测试邀请码输入05ELAM
 * ================================================
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {

    @Inject
    RxPermissions mRxPermissions;
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
    @BindView(R.id.tv_send_code)
    TextView mTvSendCode;
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    private int mLoginType;
    private CountDownTimer mCountDownTimer;
    private String mMobile;
    private String mVerifyCode;
    private String mPassword;
    private String mInviteCode;

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
                .init();
    }

    @OnClick({R.id.iv_back, R.id.tv_login, R.id.tv_send_code, R.id.tv_register, R.id.tv_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_login:
                break;
            case R.id.tv_register:
                registerAccount();
                break;
            case R.id.tv_agreement:
                break;
            case R.id.tv_send_code:
                sendVerifyCode();
                break;
        }
    }

    private void registerAccount() {
        //测试邀请码输入05ELAM
        mMobile = checkMobile();
        mVerifyCode = checkVerifyCode();
        mPassword = checkPassword();
        String againPassword = checkAgainPassword();
        mInviteCode = checkInviteCode();
        if (!StringUtils.isEmpty(mMobile, mVerifyCode, mPassword, againPassword, mInviteCode)) {
            if (!mCheckBox.isChecked()) {
                ToastUtils.showShort(R.string.hint_agreement);
                return;
            }
            if (mPassword.equals(againPassword)) {
                mPresenter.readPhoneState();
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
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    public FragmentActivity getActivity() {
        return this;
    }

    @Override
    public void readPhoneStateSuccess() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager telephonyManager =
                    (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                @SuppressLint("HardwareIds") String deviceId = telephonyManager.getDeviceId();
                if (TextUtils.isEmpty(deviceId)) {
                    deviceId = "unknown";
                }
                //注册
                HashMap<String, Object> map = new HashMap<>();
                map.put("mobile", mMobile);
                map.put("verifyCode", mVerifyCode);
                map.put("inviterCode", mInviteCode);
                map.put("passworld", mPassword);
                map.put("deviceSerialId", deviceId);
                mTvRegister.setEnabled(false);
                mPresenter.registerAccount(map);
            }
        }
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
    public void onRegisterAccountSuccess(LoginBean bean) {
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
    }

    @Override
    public void onRegisterAccountFailed() {
        mTvRegister.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        super.onDestroy();
    }
}
