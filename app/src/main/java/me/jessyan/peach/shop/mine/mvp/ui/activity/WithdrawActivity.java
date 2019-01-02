package me.jessyan.peach.shop.mine.mvp.ui.activity;

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
import me.jessyan.peach.shop.mine.di.component.DaggerWithdrawComponent;
import me.jessyan.peach.shop.mine.mvp.contract.WithdrawContract;
import me.jessyan.peach.shop.mine.mvp.presenter.WithdrawPresenter;
import me.jessyan.peach.shop.utils.StringUtils;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 22:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class WithdrawActivity extends BaseActivity<WithdrawPresenter> implements WithdrawContract.View {


    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.tv_mobile)
    TextView mTvMobile;
    @BindView(R.id.edit_verify_code)
    EditText mEditVerifyCode;
    @BindView(R.id.tv_send_code)
    TextView mTvSendCode;
    @BindView(R.id.tv_alipay)
    TextView mTvAlipay;
    @BindView(R.id.edit_money)
    EditText mEditMoney;
    @BindView(R.id.tv_balance)
    TextView mTvBalance;
    private CountDownTimer mCountDownTimer;
    private float mMoney;

    public static void launcher(Context context) {
        Intent intent = new Intent(context, WithdrawActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWithdrawComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_withdraw; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mTvMobile.setText(UserInfo.getInstance().getMobile());
        mTvAlipay.setText(UserInfo.getInstance().getAlipay());

        mPresenter.getBalance();
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

    @OnClick({R.id.iv_back, R.id.tv_withdraw_record, R.id.tv_send_code, R.id.tv_withdraw_all, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                backPrevious();
                break;
            case R.id.tv_withdraw_record:
                WithdrawRecordActivity.launcher(this);
                break;
            case R.id.tv_send_code:
                sendVerifyCode();
                break;
            case R.id.tv_withdraw_all:
                withdrawAll();
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

        Editable moneyText = mEditMoney.getText();
        if (TextUtils.isEmpty(moneyText)) {
            ToastUtils.showShort(R.string.input_withdraw_money);
            return;
        }

        float targetMoney = StringUtils.parseFloat(moneyText.toString());
        if (targetMoney < 1) {
            ToastUtils.showShort(R.string.withdraw_money_low_one);
            return;
        }
        if (mMoney >= targetMoney) {
            //提现
            mPresenter.withdraw(String.valueOf(targetMoney), UserInfo.getInstance().getMobile(), verifyCode);
        } else {
            ToastUtils.showShort(R.string.lack_of_balance);
        }
    }

    private void withdrawAll() {
        if (mMoney >= 1) {
            mEditMoney.setText(String.valueOf(mMoney));
        } else {
            ToastUtils.showShort(R.string.withdraw_money_low_one);
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
    public void onBalanceSuccess(String balance) {
        if (!TextUtils.isEmpty(balance)) {
            String decimal = StringUtils.keepTwoDecimal(balance);
            mMoney = StringUtils.parseFloat(decimal);
            mTvBalance.setText(decimal);
        }
    }

    @Override
    public void onWithDrawSuccess() {
        ToastUtils.showShort(R.string.commit_success);
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
