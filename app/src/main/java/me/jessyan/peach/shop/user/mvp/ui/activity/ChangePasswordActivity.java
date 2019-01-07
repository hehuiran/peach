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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.user.di.component.DaggerChangePasswordComponent;
import me.jessyan.peach.shop.user.mvp.contract.ChangePasswordContract;
import me.jessyan.peach.shop.user.mvp.presenter.ChangePasswordPresenter;
import me.jessyan.peach.shop.utils.StringUtils;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 00:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ChangePasswordActivity extends BaseActivity<ChangePasswordPresenter> implements ChangePasswordContract.View {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.edit_verify_code)
    EditText mEditVerifyCode;
    @BindView(R.id.tv_send_code)
    TextView mTvSendCode;
    @BindView(R.id.edit_password)
    EditText mEditPassword;
    @BindView(R.id.edit_password_again)
    EditText mEditPasswordAgain;
    @BindView(R.id.tv_mobile)
    TextView mTvMobile;
    @BindView(R.id.tv_confirm)
    TextView mTvConfirm;
    private CountDownTimer mCountDownTimer;

    public static void launcher(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChangePasswordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_change_password; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTvMobile.setText(UserInfo.getInstance().getMobile());
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .init();
    }

    @OnClick({R.id.iv_back, R.id.tv_send_code, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                backPrevious();
                break;
            case R.id.tv_send_code:
                sendVerifyCode();
                break;
            case R.id.tv_confirm:
                commit();
                break;
        }
    }

    private void commit() {
        String mobile = UserInfo.getInstance().getMobile();
        String verifyCode = checkVerifyCode();
        String password = checkPassword();
        String againPassword = checkAgainPassword();
        if (!StringUtils.isEmpty(mobile, verifyCode, password, againPassword)) {
            if (password.equals(againPassword)) {
                mTvConfirm.setEnabled(false);
                mPresenter.changePassword(mobile, verifyCode, password);
            } else {
                ToastUtils.showShort(R.string.hint_password_twice_error);
            }
        }
    }

    private void sendVerifyCode() {
        String mobile = UserInfo.getInstance().getMobile();
        if (TextUtils.isEmpty(mobile)) {
            return;
        }
        mTvSendCode.setEnabled(false);
        mPresenter.getVerifyCode(mobile);
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

    private void backPrevious() {
        KeyboardUtils.hideSoftInput(this);
        onBackPressed();
    }

    @Override
    public void onGetVerifyCodeSuccess() {
        countDown();
    }

    @Override
    public void onGetVerifyCodeFailed() {
        mTvSendCode.setEnabled(true);
    }

    @Override
    public void onChangePasswordSuccess() {
        backPrevious();
    }

    @Override
    public void onChangePasswordFailed() {
        mTvConfirm.setEnabled(true);
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
    protected void onDestroy() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        super.onDestroy();
    }
}
