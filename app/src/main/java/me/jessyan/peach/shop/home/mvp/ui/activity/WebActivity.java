package me.jessyan.peach.shop.home.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.umeng.socialize.UMShareAPI;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.dialog.ShareDialogFragment;
import me.jessyan.peach.shop.entity.WebShipBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailConfigBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailInfoBean;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.help.AliTradeHelper;
import me.jessyan.peach.shop.home.di.component.DaggerWebComponent;
import me.jessyan.peach.shop.home.mvp.contract.WebContract;
import me.jessyan.peach.shop.home.mvp.presenter.WebPresenter;
import me.jessyan.peach.shop.utils.StringUtils;
import me.jessyan.peach.shop.widget.web.ProgressWebView;
import me.jessyan.peach.shop.widget.web.WebChromeClientImpl;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/05/2019 21:22
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class WebActivity extends BaseActivity<WebPresenter> implements WebContract.View {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.ll_toolbar)
    LinearLayout mLlToolbar;
    @BindView(R.id.preview_web_view)
    ProgressWebView mPreviewWebView;
    @BindView(R.id.tv_commission)
    TextView mTvCommission;
    @BindView(R.id.space_ship)
    Space mSpaceShip;
    @BindView(R.id.tv_roll)
    TextView mTvRoll;
    @BindView(R.id.ll_ship_bottom)
    LinearLayout mLlShipBottom;
    @BindView(R.id.root)
    RelativeLayout mRoot;
    private String mUrl;
    private WebView mWebView;
    private String mTitle;
    private String shipId;
    private WebShipBean webShipBean;

    public static void launcher(Context context, String title, String url) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(IntentExtra.TITLE, title);
        intent.putExtra(IntentExtra.URL, url);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWebComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_web; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra(IntentExtra.TITLE);
        mUrl = intent.getStringExtra(IntentExtra.URL);

        mWebView = mPreviewWebView.getWebView();

        setBar();

        configWebView();

        if (!TextUtils.isEmpty(mUrl)) {
            mWebView.loadUrl(mUrl);
        }
    }

    private void setBar() {
        if (!mUrl.contains("istm=")) {
            //title不透明
            mImmersionBar.statusBarDarkFont(true, 0.2f).init();
            mLlToolbar.setBackgroundColor(Color.WHITE);
            mIvBack.setImageResource(R.mipmap.ic_black_back);
            mTvTitle.setTextColor(ContextCompat.getColor(this, R.color.black));

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mPreviewWebView.getLayoutParams();
            params.addRule(RelativeLayout.BELOW, R.id.ll_toolbar);
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(mStatusView)
                .init();
    }

    @SuppressWarnings("setJavaScriptEnabled")
    private void configWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        //webView自适应手机屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //支持手势缩放，并且隐藏缩放按钮
        settings.setDisplayZoomControls(false);
        //https图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        WebChromeClientImpl webChromeClient = new WebChromeClientImpl();
        webChromeClient.setOnWebChromeListener(new WebChromeClientImpl.OnWebChromeListener() {
            @Override
            public void onReceivedTitle(WebView view, String title) {

            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                ProgressBar progressbar = mPreviewWebView.getProgressbar();
                if (progressbar == null) {
                    return;
                }
                if (newProgress == 100) {
                    progressbar.setVisibility(View.GONE);
                } else {
                    if (progressbar.getVisibility() == View.GONE) {
                        progressbar.setVisibility(View.VISIBLE);
                    }
                    progressbar.setProgress(newProgress);
                }
            }
        });
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String title = view.getTitle();
                if (!TextUtils.isEmpty(title) && !title.startsWith("http")) {
                    mTvTitle.setText(title);
                } else {
                    mTvTitle.setText(mTitle);
                }
                if (title.endsWith("- 天猫Tmall.com")) {
                    mTvRoll.setText(R.string.find_coupon);
                    mTvRoll.setEnabled(true);
                    mTvCommission.setVisibility(View.GONE);
                    mSpaceShip.setVisibility(View.GONE);
                    mLlShipBottom.setVisibility(View.VISIBLE);
                    if (mWebView.getProgress() == 100) {
                        if (url.contains("&id=") || url.contains("?id=")) {
                            String id;
                            if (url.contains("&id="))
                                id = url.substring(url.lastIndexOf("&id=") + 4, url.length());
                            else
                                id = url.substring(url.lastIndexOf("?id=") + 4, url.length());
                            if (id.contains("&"))
                                shipId = id.substring(0, id.indexOf("&"));
                        }
                    }
                } else {
                    mLlShipBottom.setVisibility(View.GONE);
                    shipId = null;
                }
            }
        };
        mWebView.setWebViewClient(webViewClient);
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.addJavascriptInterface(new WebTools(this), "android");
    }

    @OnClick({R.id.iv_back, R.id.tv_commission, R.id.tv_roll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_commission:
                handleCommissionClick();
                break;
            case R.id.tv_roll:
                handleRollClick();
                break;
        }
    }

    private void handleCommissionClick() {
        if (webShipBean != null) {
            GoodsDetailInfoBean infoBean = new GoodsDetailInfoBean();
            infoBean.setTitle(webShipBean.getTitle());
            infoBean.setCouponMoney(String.valueOf(webShipBean.getCoupon_money()));
            infoBean.setDiscountPrice(webShipBean.isIs_coupon() ? String.valueOf(webShipBean.getEnd_price())
                    : webShipBean.getFinal_price());
            infoBean.setOriginalPrice(webShipBean.getFinal_price());
            infoBean.setTkMoney(webShipBean.getCommission_rate());
            infoBean.setItemId(shipId);
            CreateShareActivity.launcher(this, infoBean, ((ArrayList<String>) webShipBean.getSmall_images()));
        }
    }

    private void handleRollClick() {
        if (mSpaceShip.getVisibility() == View.GONE) {
            if (!TextUtils.isEmpty(shipId)) {
                mPresenter.getWebShip(shipId);
            }
        } else {
            if (webShipBean != null && !TextUtils.isEmpty(webShipBean.getCoupon_click_url())) {
                AlibcPage alibcPage = new AlibcPage(webShipBean.getCoupon_click_url());
                AliTradeHelper.getDefault().show(this, alibcPage, OpenType.Native);
            }
        }
    }

    @Override
    public void obtainBeanSuccess(WebShipBean webShipBean) {
        this.webShipBean = webShipBean;
        mTvRoll.setEnabled(true);
        mTvCommission.setVisibility(View.VISIBLE);
        mSpaceShip.setVisibility(View.VISIBLE);
        String makeMoney = String.format(getString(R.string.share_make_money), webShipBean.getCommission_rate());
        mTvCommission.setText(makeMoney);
        if (webShipBean.isIs_coupon()) {
            String roll = String.format(getString(R.string.receive_coupon), String.valueOf(webShipBean.getCoupon_money()));
            mTvRoll.setText(roll);
        } else {
            String roll = String.format(getString(R.string.buy_receive_money), webShipBean.getCommission_rate());
            mTvRoll.setText(roll);
        }
    }

    @Override
    public void obtainBeanFail(String msg) {
        mTvRoll.setText(msg);
        mTvRoll.setEnabled(false);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        AliTradeHelper.getDefault().release();
        try {
            if (mWebView != null) {
                mRoot.removeAllViews();
                mWebView.removeAllViews();
                mWebView.destroy();
                mWebView = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    private static class WebTools {

        private WeakReference<WebActivity> mReference;

        private WebTools(WebActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        @JavascriptInterface
        public void redirectInterface(String title, String content, String img, String url) {
            WebActivity activity = mReference.get();
            if (!StringUtils.isEmpty(title, img, url, content) && activity != null) {
                url = StringUtils.getShareUrl(url, "");
                ShareDialogFragment.newInstance(img, title, content, url)
                        .show(activity.getSupportFragmentManager());
            }
        }


        @JavascriptInterface
        public void sharePop(String actJson) {
            WebActivity activity = mReference.get();
            JSONObject objShare = JSON.parseObject(actJson);
            if (objShare == null || activity == null) {
                return;
            }
            String link = objShare.getString("act_url") + "?invitercode=" + UserInfo.getInstance().getInviteCode();
            ShareDialogFragment.newInstance(objShare.getString("act_img"),
                    objShare.getString("act_title"), objShare.getString("act_sketch"),
                    link);
        }


        @JavascriptInterface
        public void redirectToItemInfo(String item) {
            WebActivity activity = mReference.get();
            JSONObject object = JSON.parseObject(item);
            if (object == null || activity == null) {
                return;
            }
            GoodsDetailConfigBean configBean = GoodsDetailConfigBean.builder()
                    .setRequestDetailsApi(false)
                    .setItemId(object.getString("itemid"))
                    .setCouponMoney(object.getString("couponmoney"))
                    .setTkMoney(object.getString("tkmoney"))
                    .setCouponStartTime(StringUtils.parseLong(object.getString("couponstarttime")))
                    .setCouponEndTime(StringUtils.parseLong(object.getString("couponendtime")))
                    .setTitle(object.getString("itemtitle"))
                    .build();
            GoodsDetailActivity.launcher(activity, configBean);
        }

        /*@JavascriptInterface
        public void openItemDetail(String itemId) {
            LogUtils.di("JS : openItemDetail 跳转商品页面 -> " + itemId);
            EventProductDetailsModel model = new EventProductDetailsModel.Builder()
                    .setRequestDetailsApi(false)
                    .setItemId(itemId)
                    .build();
            ActivityUtils.startProductActivity(model);
        }


        @JavascriptInterface
        public void redirectShareInterface() {

            String title = (String) SPUtils.get(WebActivity.this, ConstantUtils.WX_SHARE_TITLE, "");
            String icon = (String) SPUtils.get(WebActivity.this, ConstantUtils.WX_SHARE_ICON, "");
            String url = (String) SPUtils.get(WebActivity.this, ConstantUtils.WX_SHARE_URL, "");
            String description = (String) SPUtils.get(WebActivity.this, ConstantUtils.WX_SHARE_DESCRIPTION, "");
            if (!StringUtils.isEmpty(title, icon, url, description)) {
                ShareDataModel shareDataModel = new ShareDataModel();
                shareDataModel.setTitle(title);
                shareDataModel.setLinkUrl(url);
                shareDataModel.setIcon(icon);
                shareDataModel.setDescription(description);
                mPresenter.thirdPartyToShare(BasePresenterImpl.SHARE_LINK, shareDataModel);
            }

        }

        @JavascriptInterface
        public void copyText(String content) {
            if (mClipboardManager == null) {
                mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            }
            ClipData clipData = ClipData.newPlainText("text", content);
            mClipboardManager.setPrimaryClip(clipData);
            mPresenter.copyText(content);
            ToastUtils.showLong("复制成功");

        }

        @JavascriptInterface
        public void copyTextAndToWX(String content) {
            mPresenter.copyText(content);
            ToastUtils.showLong("复制成功");

            ActivityUtils.jumpToWeiXin();
        }

        @JavascriptInterface
        public void redirectToTaobaoURL(String url) {
            mPresenter.openTB(url);

        }*/

    }
}
