package me.jessyan.peach.shop.launcher.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.socialize.UMShareAPI;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.callback.OnSingleClickListener;
import me.jessyan.peach.shop.category.mvp.ui.fragment.CategoryFragment;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.dynamic.mvp.ui.fragment.DynamicFragment;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.ConfigBean;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.group.mvp.ui.fragment.GroupFragment;
import me.jessyan.peach.shop.home.mvp.ui.fragment.HomeFragment;
import me.jessyan.peach.shop.launcher.di.component.DaggerMainComponent;
import me.jessyan.peach.shop.launcher.mvp.contract.MainContract;
import me.jessyan.peach.shop.launcher.mvp.presenter.MainPresenter;
import me.jessyan.peach.shop.mine.mvp.ui.fragment.MineFragment;
import me.jessyan.peach.shop.user.mvp.ui.activity.LoginActivity;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/10/2018 21:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @Inject
    RxPermissions mRxPermissions;
    @BindView(R.id.root)
    FrameLayout mRoot;
    @BindView(R.id.view_stub)
    ViewStub mViewStub;
    @BindView(R.id.view_stub_error)
    ViewStub mViewStubError;
    private boolean mIsNeedAutoLogin;
    private BasicResponse<ConfigBean> configBeanBasicResponse;
    private View mErrorView;
    private Fragment[] mFragments;
    private FragmentManager mFragmentManager;
    private int lastIndex = -1;
    private RadioGroup mRadioGroup;
    private RadioButton lastView;


    //返回首页用新token重新请求配置信息
    public static final int BACK_TO_MAIN_RESET_TOKEN = 1;
    //仅仅是返回首页,不做任何事情
    public static final int BACK_TO_MAIN_DO_NOTHING = 2;
    //退出登录
    public static final int BACK_TO_MAIN_LOGOUT = 3;

    @IntDef({BACK_TO_MAIN_RESET_TOKEN, BACK_TO_MAIN_DO_NOTHING, BACK_TO_MAIN_LOGOUT})
    public @interface BackToMain {

    }

    public static void launcher(@NonNull Context context, boolean isNeedAutoLogin) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(IntentExtra.IS_NEED_AUTO_LOGIN, isNeedAutoLogin);
        context.startActivity(intent);
    }

    public static void backToMain(@NonNull Context context, @BackToMain int type) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(IntentExtra.BACK_TO_MAIN, type);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public boolean useFragment() {
        return true;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        mIsNeedAutoLogin = intent.getBooleanExtra(IntentExtra.IS_NEED_AUTO_LOGIN, true);

        requestNetData();
    }

    /**
     * 请求网络
     */
    private void requestNetData() {
        mPresenter.getConfigInfo(mIsNeedAutoLogin, UserInfo.getInstance().getToken());
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
    public void onConfigSuccess(BasicResponse<ConfigBean> configBeanBasicResponse) {
        this.configBeanBasicResponse = configBeanBasicResponse;
        loadContentView();
    }

    @Override
    public void onConfigFailed() {
        loadContentView();
    }

    /**
     * 加载视图
     */
    private void loadContentView() {
        if (configBeanBasicResponse != null) {
            if (mIsNeedAutoLogin && configBeanBasicResponse.getCode() != 0) {
                //token过期，跳转登录页
                ToastUtils.showShort(R.string.login_expiration);
                LoginActivity.launcher(this, LoginActivity.LOGIN_WAY_OTHER);
            } else {
                //网络请求成功，加载内容视图
                initSuccessView();
            }
        } else {
            initErrorView();
        }
    }

    private void initSuccessView() {
        if (mErrorView != null) {
            mRoot.removeView(mErrorView);
        }
        mViewStub.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                initView(inflated);
            }
        });
        if (mViewStub != null) {
            mViewStub.inflate();
        }
    }

    private void initView(View view) {
        mRadioGroup = view.findViewById(R.id.radio_group);
        RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                switch (checkedId) {
                    case R.id.rb_main:
                        addFragment(0, radioButton);
                        break;
                    case R.id.rb_category:
                        addFragment(1, radioButton);
                        break;
                    case R.id.rb_spell_group:
                        addFragment(2, radioButton);
                        break;
                    case R.id.rb_dynamic:
                        addFragment(3, radioButton);
                        break;
                    case R.id.rb_mine:
                        checkLogin(4, radioButton);
                        break;
                }
            }
        };
        mRadioGroup.setOnCheckedChangeListener(listener);

        ConfigBean configBean = configBeanBasicResponse.getData();
        initConfig(configBean);
    }

    private void initConfig(ConfigBean configBean) {
        mFragments = new Fragment[5];
        mFragmentManager = getSupportFragmentManager();
        ((RadioButton) mRadioGroup.getChildAt(0)).setChecked(true);
    }

    private void initErrorView() {
        if (mViewStubError != null && mErrorView == null) {
            //网络请求出错，加载error视图
            mViewStubError.setOnInflateListener(new ViewStub.OnInflateListener() {
                @Override
                public void onInflate(ViewStub stub, View inflated) {
                    //重新加载
                    inflated.findViewById(R.id.tv_reload).setOnClickListener(new OnSingleClickListener() {
                        @Override
                        public void onClicked(View view) {
                            requestNetData();
                        }
                    });
                }
            });
            mErrorView = mViewStubError.inflate();
        }
    }

    /**
     * 显示对应的fragment
     */
    private void addFragment(int position, RadioButton view) {
        FragmentTransaction transaction =
                mFragmentManager.beginTransaction();
        if (lastIndex >= 0 && mFragments[lastIndex] != null) {
            transaction.hide(mFragments[lastIndex]);
        }
        if (mFragments[position] == null) {
            switch (position) {
                case 0:
                    mFragments[position] = HomeFragment.newInstance();
                    break;
                case 1:
                    mFragments[position] = CategoryFragment.newInstance();
                    break;
                case 2:
                    mFragments[position] = GroupFragment.newInstance();
                    break;
                case 3:
                    mFragments[position] = DynamicFragment.newInstance();
                    break;
                case 4:
                    mFragments[position] = MineFragment.newInstance();
                    break;
                default:
                    break;
            }
            transaction.add(R.id.frame_layout, mFragments[position]);
        } else {
            transaction.show(mFragments[position]);
        }
        transaction.commitAllowingStateLoss();
        lastIndex = position;
        lastView = view;
    }

    private void logout() {
        ((RadioButton) mRadioGroup.getChildAt(0)).setChecked(true);
        FragmentTransaction transaction =
                mFragmentManager.beginTransaction();
        for (int i = 0; i < mFragments.length; i++) {
            if (mFragments[i] != null) {
                if (mFragments[i] instanceof MineFragment) {
                    transaction.remove(mFragments[i]);
                    mFragments[i] = null;
                }
            }
        }
        transaction.commit();
    }

    /**
     * 检查是否登录
     */
    @SuppressWarnings("SameParameterValue")
    private void checkLogin(int position, RadioButton radioButton) {
        String token = UserInfo.getInstance().getToken();
        if (TextUtils.isEmpty(token)) {
            radioButton.setChecked(false);
            if (lastView != null) {
                lastView.setChecked(true);
            }
            //未登录跳转至登录页面
            LoginActivity.launcher(this, LoginActivity.LOGIN_WAY_MODULES);
            return;
        }
        //已经登录显示相对应的fragment
        addFragment(position, radioButton);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int type = intent.getIntExtra(IntentExtra.BACK_TO_MAIN, -1);
        if (type == BACK_TO_MAIN_RESET_TOKEN) {
            //重新请求配置信息
            mIsNeedAutoLogin = false;
            requestNetData();
        } else if (type == BACK_TO_MAIN_LOGOUT) {
            logout();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
