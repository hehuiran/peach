package me.jessyan.peach.shop.user.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.MessageBean;
import me.jessyan.peach.shop.netconfig.temporary.CollectionApiService;
import me.jessyan.peach.shop.user.mvp.contract.MessageContract;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/23/2018 23:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MessageModel extends BaseModel implements MessageContract.Model {

    @Inject
    public MessageModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<MessageBean> getMessageList(int page) {
        return mRepositoryManager.obtainRetrofitService(CollectionApiService.class)
                .getMessageList(page, CommonConstant.PAGE_SIZE)
                .map(new Function<BasicResponse<MessageBean>, MessageBean>() {
                    @Override
                    public MessageBean apply(BasicResponse<MessageBean> messageBeanBasicResponse) throws Exception {
                        MessageBean messageBean = messageBeanBasicResponse.getData();
                        List<MessageBean.DataBean> data = messageBean.getData();
                        for (MessageBean.DataBean bean : data) {
                            int itemType;
                            int type = bean.getType();
                            if (type == 0) {
                                itemType = RecyclerViewType.ACTIVITY_TYPE;
                            } else if (type == 1) {
                                itemType = RecyclerViewType.SYSTEM_TYPE;
                            } else if (type == 2) {
                                itemType = RecyclerViewType.ALLIANCE_TYPE;
                            } else {
                                itemType = RecyclerViewType.INCOME_TYPE;
                            }
                            bean.setItemType(itemType);
                        }

                        return messageBean;
                    }
                });
    }

    @Override
    public Observable<BasicResponse<String>> deleteMessage(String id) {
        return mRepositoryManager.obtainRetrofitService(CollectionApiService.class)
                .deleteMessage(id);
    }
}