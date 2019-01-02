package me.jessyan.peach.shop.user.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import com.ali.auth.third.core.model.Session;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.constant.SPKey;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.user.LoginBean;
import me.jessyan.peach.shop.entity.user.ThirdPartyInfoBean;
import me.jessyan.peach.shop.launcher.mvp.ui.activity.MainActivity;
import me.jessyan.peach.shop.user.di.component.DaggerLoginComponent;
import me.jessyan.peach.shop.user.mvp.contract.LoginContract;
import me.jessyan.peach.shop.user.mvp.presenter.LoginPresenter;
import me.jessyan.peach.shop.utils.StringUtils;

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


    @Inject
    RxPermissions mRxPermissions;
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
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    private int mLoginType;
    private long mExitTime;
    private SHARE_MEDIA mPlatform;
    private String mDeviceId;
    private UMAuthListener mAuthListener;
    private ThirdPartyInfoBean mThirdPartyInfoBean;
    private String mMobile;
    private String mPassword;

    //手机登录
    private static final int LOGIN_CHANNEL_MOBILE = 1;
    //友盟登录
    private static final int LOGIN_CHANNEL_UM = 2;
    //淘宝登录
    private static final int LOGIN_CHANNEL_TB = 3;

    @IntDef({LOGIN_CHANNEL_MOBILE, LOGIN_CHANNEL_UM, LOGIN_CHANNEL_TB})
    public @interface LoginChannel {

    }

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
                skip();
                break;
            case R.id.tv_register:
                RegisterActivity.launcher(this, mLoginType);
                break;
            case R.id.tv_forget:
                break;
            case R.id.tv_login:
                checkMobileLogin();
                break;
            case R.id.tv_agreement:
                break;
            case R.id.iv_wechat:
                mPlatform = SHARE_MEDIA.WEIXIN;
                mPresenter.readPhoneState(LOGIN_CHANNEL_UM);
                break;
            case R.id.iv_qq:
                mPlatform = SHARE_MEDIA.QQ;
                mPresenter.readPhoneState(LOGIN_CHANNEL_UM);
                break;
            case R.id.iv_taobao:
                mPresenter.readPhoneState(LOGIN_CHANNEL_TB);
                break;
            case R.id.iv_sina:
                mPlatform = SHARE_MEDIA.SINA;
                mPresenter.readPhoneState(LOGIN_CHANNEL_UM);
                break;
        }
    }

    private void skip() {
        if (mLoginType == LoginActivity.LOGIN_WAY_MODULES) {
            //从MainActivity跳转至登录界面,关闭当前页面即可
            finish();
        } else if (mLoginType == LoginActivity.LOGIN_WAY_SPLASH) {
            //从SplashActivity跳转至登录界面,MainActivity还未实例化
            MainActivity.launcher(this, false);
            finish();
        } else if (mLoginType == LoginActivity.LOGIN_WAY_OTHER) {
            //删除无效token 退出应用
            removeTokenAndExitApplication();
        }
    }

    private void checkMobileLogin() {
        mMobile = checkMobile();
        mPassword = checkPassword();
        if (!StringUtils.isEmpty(mMobile, mPassword)) {
            if (!mCheckBox.isChecked()) {
                ToastUtils.showShort(R.string.hint_agreement);
                return;
            }
            mPresenter.readPhoneState(LOGIN_CHANNEL_MOBILE);
        }
    }

    private String checkMobile() {
        Editable text = mEditMobile.getText();
        if (RegexUtils.isMobileSimple(text)) {
            return text.toString();
        }
        ToastUtils.showShort(R.string.hint_mobile_error);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            switch (mLoginType) {
                case LoginActivity.LOGIN_WAY_SPLASH:
                    exitTime();
                    return true;
                case LoginActivity.LOGIN_WAY_OTHER:
                    removeTokenAndExitApplication();
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitTime() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.showShort(R.string.hint_exit);
            mExitTime = System.currentTimeMillis();
        } else {
            ActivityUtils.finishAllActivities();
        }
    }

    private void removeTokenAndExitApplication() {
        SPUtils.getInstance().remove(SPKey.TOKEN);
        ActivityUtils.finishAllActivities();
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
    public void onMobileLoginSuccess(LoginBean bean) {
        if (mLoginType == LoginActivity.LOGIN_WAY_MODULES) {
            //从MainActivity跳转至登录界面
            MainActivity.backToMain(this, MainActivity.BACK_TO_MAIN_DO_NOTHING);
        } else if (mLoginType == LoginActivity.LOGIN_WAY_SPLASH) {
            //从SplashActivity跳转至登录界面,MainActivity还未实例化
            MainActivity.launcher(this, false);
            finish();
        } else if (mLoginType == LoginActivity.LOGIN_WAY_OTHER) {
            //token过期从首页跳转至登录界面
            MainActivity.backToMain(this, MainActivity.BACK_TO_MAIN_RESET_TOKEN);
        }
    }

    @Override
    public void onMobileLoginFailed() {
        mTvLogin.setEnabled(true);
    }

    @SuppressLint("HardwareIds")
    @Override
    public void readPhoneStateSuccess(@LoginChannel int loginChannel) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager telephonyManager =
                    (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                mDeviceId = telephonyManager.getDeviceId();
                if (TextUtils.isEmpty(mDeviceId)) {
                    mDeviceId = "unknown";
                }
                if (loginChannel == LOGIN_CHANNEL_MOBILE) {
                    mobileLogin();
                } else if (loginChannel == LOGIN_CHANNEL_UM) {
                    UMAuth();
                } else if (loginChannel == LOGIN_CHANNEL_TB) {
                    mPresenter.aliLogin();
                }
            }
        }
    }


    @Override
    public void onThirdPartyLoginSuccess(BasicResponse<LoginBean> response) {
        int code = response.getCode();
        if (code == 0) {
            //登陆成功
            if (mLoginType == LoginActivity.LOGIN_WAY_MODULES) {
                //从MainActivity跳转至登录界面
                MainActivity.backToMain(this, MainActivity.BACK_TO_MAIN_DO_NOTHING);
            } else if (mLoginType == LoginActivity.LOGIN_WAY_SPLASH) {
                //从SplashActivity跳转至登录界面,MainActivity还未实例化
                MainActivity.launcher(this, false);
                finish();
            } else if (mLoginType == LoginActivity.LOGIN_WAY_OTHER) {
                //token过期从首页跳转至登录界面
                MainActivity.backToMain(this, MainActivity.BACK_TO_MAIN_RESET_TOKEN);
            }
        } else if (code == 1002) {
            //去绑定手机
            BindMobileActivity.launcher(this, mLoginType, mThirdPartyInfoBean);
        }
    }

    @Override
    public void onAliLoginSuccess() {
        Session session = AlibcLogin.getInstance().getSession();
        thirdPartyLogin(session.openId, session.avatarUrl, -1, session.nick, getAccountTypeByPlatform(null));
    }

    private void mobileLogin() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", mMobile);
        map.put("passworld", mPassword);
        map.put("deviceSerialId", mDeviceId);
        mTvLogin.setEnabled(false);
        mPresenter.mobileLogin(map);
    }

    private void UMAuth() {
        if (mPlatform == null) {
            return;
        }
        if (UMShareAPI.get(this).isInstall(this, mPlatform)) {
            if (mAuthListener == null) {
                mAuthListener = new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        mPresenter.showLoading();
                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            LogUtils.d(TAG, "key= " + entry.getKey() + " and value= "
                                    + entry.getValue());
                        }
                        String uid = map.get("uid");
                        String iconUrl = map.get("iconurl");
                        int gender = map.get("gender").equals("男") ? 0 : 1;
                        String nickname = map.get("name");
                        thirdPartyLogin(uid, iconUrl, gender, nickname, getAccountTypeByPlatform(share_media));
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        mPresenter.hideLoading();

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        mPresenter.hideLoading();
                    }
                };
            }
            UMShareAPI.get(this).getPlatformInfo(this, mPlatform, mAuthListener);
        } else {
            String hint = String.format(getString(R.string.un_install_third_party_app), mPlatform.getName());
            ToastUtils.showShort(hint);
        }
    }

    private int getAccountTypeByPlatform(SHARE_MEDIA platform) {
//        platform ==null为淘宝
        return platform == null ? 3 :
                platform == SHARE_MEDIA.WEIXIN ? 0 :
                        platform == SHARE_MEDIA.QQ ? 1
                                : 2;
    }

    private void thirdPartyLogin(String uid, String iconUrl, int gender, String nickname, int platform) {
        mThirdPartyInfoBean = new ThirdPartyInfoBean();
        mThirdPartyInfoBean.setDeviceId(mDeviceId);
        mThirdPartyInfoBean.setGender(gender);
        mThirdPartyInfoBean.setIconUrl(iconUrl);
        mThirdPartyInfoBean.setOpenId(uid);
        mThirdPartyInfoBean.setPlatform(platform);
        mThirdPartyInfoBean.setNickName(nickname);

        HashMap<String, Object> map = new HashMap<>();
        map.put("wxopenid", uid);
        map.put("accounttype", String.valueOf(platform));
        map.put("deviceSerialId", mDeviceId);
        mPresenter.thirdPartyLogin(map);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        super.onDestroy();
    }
}
