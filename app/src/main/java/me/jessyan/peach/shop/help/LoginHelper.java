package me.jessyan.peach.shop.help;

import android.content.Context;
import android.text.TextUtils;

import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.user.mvp.ui.activity.LoginActivity;

/**
 * author: Create by HuiRan on 2018/12/24 下午11:27
 * email: 15260828327@163.com
 * description:
 */
public class LoginHelper {

    private LoginHelper() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static boolean checkLogin(Context context) {
        String token = UserInfo.getInstance().getToken();
        if (TextUtils.isEmpty(token)) {
            LoginActivity.launcher(context, LoginActivity.LOGIN_WAY_MODULES);
            return false;
        }
        return true;
    }
}
