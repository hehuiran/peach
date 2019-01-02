package me.jessyan.peach.shop.user.mvp.model;

import com.blankj.utilcode.util.Utils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import me.jessyan.peach.shop.entity.ResultBean;
import me.jessyan.peach.shop.user.mvp.contract.SettingContract;
import me.jessyan.peach.shop.netconfig.function.ResponseFunction;
import me.jessyan.peach.shop.netconfig.temporary.PersonalApiService;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/28/2018 22:15
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SettingModel extends BaseModel implements SettingContract.Model {

    @Inject
    public SettingModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<ResultBean> changeAvatarImage(String originalPath) {
        return Observable.just(originalPath)
                .map(new Function<String, File>() {
                    @Override
                    public File apply(String s) throws Exception {
                        return Luban.with(Utils.getApp()).get(s);
                    }
                })
                .flatMap(new Function<File, ObservableSource<ResultBean>>() {
                    @Override
                    public ObservableSource<ResultBean> apply(File file) throws Exception {
                        RequestBody fileBody = RequestBody.create(MultipartBody.FORM, file);
                        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), fileBody);
                        return mRepositoryManager.obtainRetrofitService(PersonalApiService.class)
                                .changeIcon(filePart)
                                .map(new ResponseFunction<>(ResultBean.class));
                    }
                });
    }
}