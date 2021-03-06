package me.jessyan.peach.shop.user.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.IntDef;
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

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.entity.event.BindAlipayEvent;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.mine.mvp.ui.activity.WithdrawActivity;
import me.jessyan.peach.shop.user.di.component.DaggerBindAlipayComponent;
import me.jessyan.peach.shop.user.mvp.contract.BindAlipayContract;
import me.jessyan.peach.shop.user.mvp.presenter.BindAlipayPresenter;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 00:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class BindAlipayActivity extends BaseActivity<BindAlipayPresenter> implements BindAlipayContract.View {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.edit_real_name)
    EditText mEditRealName;
    @BindView(R.id.edit_alipay_account)
    EditText mEditAlipayAccount;
    @BindView(R.id.tv_mobile)
    TextView mTvMobile;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.edit_verify_code)
    EditText mEditVerifyCode;
    @BindView(R.id.tv_send_code)
    TextView mTvSendCode;
    private CountDownTimer mCountDownTimer;

    public static final int PURPOSE_NORMAL = 11;
    public static final int PURPOSE_WITHDRAW = 22;
    private int mPurpose;
    private String mAlipay;
    private String mRealName;

    @IntDef({PURPOSE_NORMAL, PURPOSE_WITHDRAW})
    public @interface Purpose {

    }

    public static void launcher(Context context, @Purpose int purpose) {
        Intent intent = new Intent(context, BindAlipayActivity.class);
        intent.putExtra(IntentExtra.PURPOSE, purpose);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBindAlipayComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_bind_alipay; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        mPurpose = intent.getIntExtra(IntentExtra.PURPOSE, PURPOSE_NORMAL);

        boolean isBind = TextUtils.isEmpty(UserInfo.getInstance().getAlipay());
        mTvTitle.setText(isBind ? R.string.bind_alipay : R.string.modify_alipay);
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
        Editable realNameText = mEditRealName.getText();
        if (TextUtils.isEmpty(realNameText)) {
            ToastUtils.showShort(R.string.input_real_name);
            return;
        }
        Editable alipayAccountText = mEditAlipayAccount.getText();
        if (TextUtils.isEmpty(alipayAccountText)) {
            ToastUtils.showShort(R.string.input_alipay_account);
            return;
        }
        String verifyCode = checkVerifyCode();
        if (TextUtils.isEmpty(verifyCode)) {
            return;
        }
        mAlipay = alipayAccountText.toString();
        mRealName = realNameText.toString();
        mPresenter.bindAlipay(mAlipay, mRealName, verifyCode);
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
    public void onBindAlipaySuccess() {
        UserInfo.getInstance().setAlipay(mAlipay);
        UserInfo.getInstance().setRealName(mRealName);
        EventBus.getDefault().post(new BindAlipayEvent());
        if (mPurpose == PURPOSE_WITHDRAW) {
            WithdrawActivity.launcher(this);
        }
        finish();
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
