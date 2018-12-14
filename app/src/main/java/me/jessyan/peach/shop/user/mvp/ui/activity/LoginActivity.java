package me.jessyan.peach.shop.user.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.user.di.component.DaggerLoginComponent;
import me.jessyan.peach.shop.user.mvp.contract.LoginContract;
import me.jessyan.peach.shop.user.mvp.presenter.LoginPresenter;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/09/2018 16:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


    private int mLoginType;


    //从模块
    public static final int LOGIN_WAY_MODULES = 1;
    //从启动页
    public static final int LOGIN_WAY_SPLASH = 2;
    //token失效等
    public static final int LOGIN_WAY_OTHER = 3;

    @IntDef({LOGIN_WAY_MODULES, LOGIN_WAY_SPLASH, LOGIN_WAY_OTHER})
    public @interface LoginType {

    }

    public static void launcher(@NonNull Context context, @LoginType int loginType) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(IntentExtra.LOGIN_WAY, loginType);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        mLoginType = intent.getIntExtra(IntentExtra.LOGIN_WAY, -1);
    }
}
