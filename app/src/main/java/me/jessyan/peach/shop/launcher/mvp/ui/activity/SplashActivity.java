package me.jessyan.peach.shop.launcher.mvp.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.SPKey;
import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.launcher.di.component.DaggerSplashComponent;
import me.jessyan.peach.shop.launcher.mvp.contract.SplashContract;
import me.jessyan.peach.shop.launcher.mvp.presenter.SplashPresenter;
import me.jessyan.peach.shop.user.mvp.ui.activity.LoginActivity;
import me.jessyan.peach.shop.widget.CountDownProgressView;
import me.jessyan.peach.shop.widget.RecyclerImageView;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/08/2018 21:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    @BindView(R.id.iv_ad)
    RecyclerImageView mIvAd;
    @BindView(R.id.progress_view)
    CountDownProgressView mProgressView;
    private int mLastVersionCode;
    private String mToken;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            receiveMessage();
        }
    };

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSplashComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_splash; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getWindow().setBackgroundDrawable(null);
//        SPUtils.getInstance().put(SPKey.TOKEN,"fc2c7c47b91830c29792a719dfb602ef");

        mProgressView.setOnProgressEndListener(() -> toNextPage());

        mToken = UserInfo.getInstance().getToken();
        mLastVersionCode = SPUtils.getInstance().getInt(SPKey.SHOW_GUIDE_IMAGE, -1);

        //获取广告图片
        mPresenter.getAdvertising();
        Message message = mHandler.obtainMessage();
        mHandler.sendMessageDelayed(message, 3000);
    }

    @OnClick({R.id.iv_ad, R.id.progress_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_ad:
                //广告点击
                break;
            case R.id.progress_view:
                toNextPage();
                break;
        }
    }

    private void receiveMessage() {
        //取消网络请求
        mPresenter.cancelTask();
        toNextPage();
    }

    @Override
    public void onGetAdvertisingSuccess(CouponsBannerBean.BannerBean bannerBean) {
        removeHandlerMessage();

        CouponsBannerBean.BannerBean.DataBean dataBean = bannerBean.getDataBean();
        if (dataBean != null && !TextUtils.isEmpty(dataBean.getImg())) {
            ImageLoader imageLoader = ArmsUtils.getImageLoaderInstance(this);
            imageLoader.loadImage(this,
                    ImageConfigImpl.builder()
                            .imageView(mIvAd)
                            .url(dataBean.getImg())
                            .placeholder(R.mipmap.ic_splash)
                            .errorPic(R.mipmap.ic_splash)
                            .build());
        }

        mProgressView.setVisibility(View.VISIBLE);
        mProgressView.startCountDown();
    }

    @Override
    public void onGetAdvertisingFailed() {
        removeHandlerMessage();
        toNextPage();
    }

    private void toNextPage() {
        if (mLastVersionCode == -1 || mLastVersionCode < AppUtils.getAppVersionCode()) {
            //跳转引导页面
            GuideActivity.launcher(this);
        } else {
            if (TextUtils.isEmpty(mToken)) {
//                MainActivity.launcher(this, false);
                LoginActivity.launcher(this, LoginActivity.LOGIN_WAY_SPLASH);
            } else {
                MainActivity.launcher(this, true);
            }
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        removeHandlerMessage();
        super.onDestroy();
    }

    private void removeHandlerMessage() {
        mHandler.removeCallbacksAndMessages(null);
    }
}
