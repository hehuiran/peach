package me.jessyan.peach.shop.help;

import android.app.Activity;

import com.ali.auth.third.core.model.Session;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.trade.biz.AlibcConstants;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.callback.TradeCallback;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.user.UserInfo;

/**
 * author: Created by HuiRan on 2018/6/15 14:13
 * E-Mail: 15260828327@163.com
 * description:
 */
public class AliTradeHelper {

    private static volatile AliTradeHelper sInstance;
    private WeakCallback mWeakCallback;
    private static final String TAG = "AliTradeHelper";

    private AliTradeHelper() {

    }

    public static AliTradeHelper getDefault() {
        if (sInstance == null) {
            synchronized (AliTradeHelper.class) {
                if (sInstance == null) {
                    sInstance = new AliTradeHelper();
                }
            }
        }
        return sInstance;
    }

    /**
     * 拉起手机淘宝
     */
    public void show(Activity activity, AlibcBasePage basePage, OpenType openType) {
        mWeakCallback = new WeakCallback(activity, basePage, openType);
        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        if (!alibcLogin.isLogin()) {
            alibcLogin.showLogin(mWeakCallback);
        } else {
            mWeakCallback.onSuccess(0);
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mWeakCallback != null) {
            clear();
        }
        AlibcTradeSDK.destory();
    }

    private void clear() {
        if (mWeakCallback.mActivityWeakReference != null) {
            mWeakCallback.mActivityWeakReference.clear();
        }
    }

    private static class WeakCallback implements AlibcLoginCallback {

        private WeakReference<Activity> mActivityWeakReference;
        private AlibcBasePage mAlibcBasePage;
        private OpenType mOpenType;

        private WeakCallback(Activity activity, AlibcBasePage basePage, OpenType openType) {
            mActivityWeakReference = new WeakReference<>(activity);
            mAlibcBasePage = basePage;
            mOpenType = openType;
        }

        @Override
        public void onSuccess(int i) {
            LogUtils.d(TAG, "淘宝授权成功");
            if (mActivityWeakReference == null) {
                return;
            }
            Activity activity = mActivityWeakReference.get();
            if (activity == null) {
                return;
            }
            Session session = AlibcLogin.getInstance().getSession();
            LogUtils.d(TAG, "openId=" + session.openId + " openSid=" + session.openSid + " nick=" + session.nick + " img=" + session.avatarUrl);
            AlibcShowParams alibcShowParams = new AlibcShowParams(mOpenType, false);//页面打开方式，默认，H5，Native
            //打开淘宝
            alibcShowParams.setClientType("taobao_scheme");
            //打开天猫
//                alibcShowParams.setClientType("tmall_scheme");
            AlibcTaokeParams taokeParams = new AlibcTaokeParams();
            taokeParams.adzoneid = UserInfo.getInstance().getAdZoneId();
            taokeParams.pid = UserInfo.getInstance().getPid();
            taokeParams.extraParams = new HashMap<>();
            taokeParams.extraParams.put("taokeAppkey", CommonConstant.TAOKE_APP_KEY);
            HashMap<String, String> map = new HashMap<>();
            map.put(AlibcConstants.ISV_CODE, "appisvcode");
            map.put("alibaba", "阿里巴巴");
            AlibcTrade.show(activity, mAlibcBasePage, alibcShowParams, taokeParams, map, new TradeCallback());
        }

        @Override
        public void onFailure(int i, String s) {
            LogUtils.d(TAG, "淘宝授权失败i=" + i + "->s=" + s);
            ToastUtils.showShort(R.string.tb_auth_failed);
        }
    }
}
