package me.jessyan.peach.shop.user.mvp.contract;

import android.support.v4.app.FragmentActivity;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.user.LoginBean;
import me.jessyan.peach.shop.user.mvp.ui.activity.LoginActivity;


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
public interface LoginContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        //申请权限
        RxPermissions getRxPermissions();

        FragmentActivity getActivity();

        void onMobileLoginSuccess(LoginBean bean);

        void onMobileLoginFailed();

        void readPhoneStateSuccess(@LoginActivity.LoginChannel int loginChannel);

        void onThirdPartyLoginSuccess(BasicResponse<LoginBean> response);

        void onAliLoginSuccess();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<LoginBean> mobileLogin(HashMap<String, Object> map);

        Observable<BasicResponse<LoginBean>> thirdPartyLogin(HashMap<String, Object> map);
    }
}
