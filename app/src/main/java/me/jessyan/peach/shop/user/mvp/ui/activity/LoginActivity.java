package me.jessyan.peach.shop.user.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Space;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;
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
 * 账号密码登陆：接口：user/login
 * 参数：mobile  账号
 * 参数：password  密码
 * 返回参数：code 1022   meg 手机号不存在或密码错误  0 登录成功
 * ================================================
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.iv_bg)
    ImageView mIvBg;
    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.edit_mobile)
    EditText mEditMobile;
    @BindView(R.id.edit_password)
    EditText mEditPassword;
    @BindView(R.id.check_box)
    AppCompatCheckBox mCheckBox;
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

        ImageLoader imageLoader = ArmsUtils.getImageLoaderInstance(this);
        imageLoader.loadImage(this, ImageConfigImpl.builder()
                .imageView(mIvBg)
                .url(R.mipmap.ic_login_bg)
                .placeholder(R.mipmap.ic_login_bg)
                .build());
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar
                .statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @OnClick({R.id.tv_skip, R.id.tv_register, R.id.tv_forget, R.id.tv_login,
            R.id.tv_agreement, R.id.iv_wechat, R.id.iv_qq, R.id.iv_taobao, R.id.iv_sina})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_skip:
                break;
            case R.id.tv_register:
                RegisterActivity.launcher(this, mLoginType);
                break;
            case R.id.tv_forget:
                break;
            case R.id.tv_login:
                break;
            case R.id.tv_agreement:
                break;
            case R.id.iv_wechat:
                break;
            case R.id.iv_qq:
                break;
            case R.id.iv_taobao:
                break;
            case R.id.iv_sina:
                break;
        }
    }
}
