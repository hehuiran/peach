package me.jessyan.peach.shop.user.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import io.reactivex.Observable;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.ResultBean;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 01/03/2019 21:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface ChangeMobileContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void onGetVerifyCodeSuccess();

        void onGetVerifyCodeFailed();

        void onVerifyMobileSuccess();

        void onVerifyMobileFailed();

        void onModifyMobileSuccess();

        void onModifyMobileFailed();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BasicResponse<ResultBean>> getVerifyCode(String mobile);

        Observable<BasicResponse<ResultBean>> verifyMobile(String mobile, String verifyCode);

        Observable<BasicResponse<ResultBean>> modifyMobile(String oldMobile, String newMobile, String verifyCode);
    }
}
