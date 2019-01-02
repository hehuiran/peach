package me.jessyan.peach.shop.user.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Space;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.EventBusManager;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.event.ChangeNicknameEvent;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.user.di.component.DaggerChangeNicknameComponent;
import me.jessyan.peach.shop.user.mvp.contract.ChangeNicknameContract;
import me.jessyan.peach.shop.user.mvp.presenter.ChangeNicknamePresenter;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/29/2018 22:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ChangeNicknameActivity extends BaseActivity<ChangeNicknamePresenter> implements ChangeNicknameContract.View {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.edit_text)
    EditText mEditText;

    public static void launcher(Context context) {
        Intent intent = new Intent(context, ChangeNicknameActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChangeNicknameComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_change_nickname; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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

    private void backPrevious() {
        KeyboardUtils.hideSoftInput(mEditText);
        onBackPressed();
    }

    private void commit() {
        Editable text = mEditText.getText();
        if (!TextUtils.isEmpty(text)) {
            mPresenter.changeNickname(text.toString());
        } else {
            ToastUtils.showShort(R.string.please_input_new_nickname);
        }
    }

    @Override
    public void onChangeNicknameSuccess(String nickname) {
        UserInfo.getInstance().setNickname(nickname);
        EventBusManager.getInstance().post(new ChangeNicknameEvent());
        backPrevious();
    }
}
