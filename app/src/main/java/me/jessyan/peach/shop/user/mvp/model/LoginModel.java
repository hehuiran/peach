package me.jessyan.peach.shop.user.mvp.model;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.user.LoginBean;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.netconfig.temporary.PersonalApiService;
import me.jessyan.peach.shop.user.mvp.contract.LoginContract;


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
@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.Model {

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<LoginBean> mobileLogin(HashMap<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(PersonalApiService.class)
                .mobileLogin(map)
                .map(new Function<BasicResponse<LoginBean>, LoginBean>() {
                    @Override
                    public LoginBean apply(BasicResponse<LoginBean> loginBeanBasicResponse) throws Exception {
                        if (loginBeanBasicResponse.getCode() != 0) {
                            ToastUtils.showShort(loginBeanBasicResponse.getMsg());
                            return null;
                        }
                        //登录成功，保存用户信息
                        LoginBean loginBean = loginBeanBasicResponse.getData();
                        UserInfo.getInstance().setData(loginBean);
                        return loginBean;
                    }
                });
    }

    @Override
    public Observable<BasicResponse<LoginBean>> thirdPartyLogin(HashMap<String, Object> map) {
        return mRepositoryManager.obtainRetrofitService(PersonalApiService.class)
                .thirdPartyLogin(map)
                .map(new Function<BasicResponse<LoginBean>, BasicResponse<LoginBean>>() {
                    @Override
                    public BasicResponse<LoginBean> apply(BasicResponse<LoginBean> loginBeanBasicResponse) throws Exception {
                        if (loginBeanBasicResponse.getCode() == 0) {
                            //登录成功，保存用户信息
                            LoginBean loginBean = loginBeanBasicResponse.getData();
                            UserInfo.getInstance().setData(loginBean);

                        }
                        return loginBeanBasicResponse;
                    }
                });
    }
}