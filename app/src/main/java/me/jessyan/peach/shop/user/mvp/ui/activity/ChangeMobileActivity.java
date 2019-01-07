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
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.user.di.component.DaggerChangeMobileComponent;
import me.jessyan.peach.shop.user.mvp.contract.ChangeMobileContract;
import me.jessyan.peach.shop.user.mvp.presenter.ChangeMobilePresenter;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/03/2019 21:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ChangeMobileActivity extends BaseActivity<ChangeMobilePresenter> implements ChangeMobileContract.View {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_mobile)
    TextView mTvMobile;
    @BindView(R.id.ll_modify)
    LinearLayout mLlModify;
    @BindView(R.id.edit_mobile)
    EditText mEditMobile;
    @BindView(R.id.ll_bind)
    LinearLayout mLlBind;
    @BindView(R.id.edit_verify_code)
    EditText mEditVerifyCode;
    @BindView(R.id.tv_send_code)
    TextView mTvSendCode;
    @BindView(R.id.tv_confirm)
    TextView mTvConfirm;
    private boolean isBind;
    private CountDownTimer mCountDownTimer;


    public static void launcher(Context context) {
        Intent intent = new Intent(context, ChangeMobileActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChangeMobileComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_change_mobile; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String mobile = UserInfo.getInstance().getMobile();
        mTvMobile.setText(mobile);

        setViews();
    }

    private void setViews() {
        mLlBind.setVisibility(isBind ? View.VISIBLE : View.GONE);
        mLlModify.setVisibility(isBind ? View.GONE : View.VISIBLE);
        mTvTitle.setText(isBind ? R.string.bind_mobile : R.string.modify_mobile);
        mTvConfirm.setText(isBind ? R.string.confirm : R.string.verify_success_bind_new_mobile);
        mEditVerifyCode.setText(null);
        mEditVerifyCode.clearFocus();
        if (isBind) {
            mEditMobile.requestFocus();
        }
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer.onFinish();
        }
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
        String verifyCode = checkVerifyCode();
        if (TextUtils.isEmpty(verifyCode)) {
            return;
        }
        String oldMobile = UserInfo.getInstance().getMobile();

        if (isBind) {
            String newMobile = checkNewMobile();
            if (TextUtils.isEmpty(newMobile)) {
                return;
            }
            mTvConfirm.setEnabled(false);
            mPresenter.modifyMobile(oldMobile, newMobile, verifyCode);
        } else {
            mTvConfirm.setEnabled(false);
            mPresenter.verifyMobile(oldMobile, verifyCode);
        }
    }

    private void sendVerifyCode() {
        String mobile = isBind ? checkNewMobile() : UserInfo.getInstance().getMobile();
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

    private String checkNewMobile() {
        Editable text = mEditMobile.getText();
        if (RegexUtils.isMobileSimple(text)) {
            return text.toString();
        }
        ToastUtils.showShort(R.string.hint_mobile_error);
        return null;
    }

    private void backPrevious() {
        KeyboardUtils.hideSoftInput(this);
        onBackPressed();
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

    @Override
    public void onBackPressed() {
        if (isBind) {
            isBind = false;
            setViews();
        } else {
            super.onBackPressed();
        }
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
    public void onVerifyMobileSuccess() {
        mTvConfirm.setEnabled(true);
        isBind = true;
        setViews();
    }

    @Override
    public void onVerifyMobileFailed() {
        mTvConfirm.setEnabled(true);
    }

    @Override
    public void onModifyMobileSuccess() {
        //关闭当前界面
        finish();
    }

    @Override
    public void onModifyMobileFailed() {
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
