package me.jessyan.peach.shop.mine.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Space;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.mine.di.component.DaggerFeedbackComponent;
import me.jessyan.peach.shop.mine.mvp.contract.FeedbackContract;
import me.jessyan.peach.shop.mine.mvp.presenter.FeedbackPresenter;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/07/2019 20:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class FeedbackActivity extends BaseActivity<FeedbackPresenter> implements FeedbackContract.View {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.edit_feedback)
    EditText mEditFeedback;
    @BindView(R.id.tv_count)
    TextView mTvCount;

    public static void launcher(Context context) {
        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFeedbackComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_feedback; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setCount(200);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar
                .statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @OnClick({R.id.iv_back, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                backPrevious();
                break;
            case R.id.tv_confirm:
                commit();
                break;
        }
    }

    private void commit() {
        Editable editable = mEditFeedback.getText();
        if (TextUtils.isEmpty(editable)) {
            ToastUtils.showShort(R.string.input_feedback);
        } else {
            mPresenter.feedback(editable.toString());
        }
    }

    @OnTextChanged(value = {R.id.edit_feedback}, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChanged(Editable editable) {
        int length = TextUtils.isEmpty(editable) ? 0 : editable.length();
        setCount(200 - length);
    }

    private void setCount(int count) {
        String format = String.format(getString(R.string.how_much), String.valueOf(count));
        mTvCount.setText(format);
    }

    private void backPrevious() {
        KeyboardUtils.hideSoftInput(this);
        onBackPressed();
    }

    @Override
    public void onFeedbackSuccess() {
        ToastUtils.showShort(R.string.commit_success);
        backPrevious();
    }
}
