package me.jessyan.peach.shop.home.mvp.model;

import com.blankj.utilcode.util.LogUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import me.jessyan.peach.shop.entity.BasicResponse;
import me.jessyan.peach.shop.entity.search.SearchHotBean;
import me.jessyan.peach.shop.entity.search.SearchOptionalBean;
import me.jessyan.peach.shop.entity.search.SearchRecordBean;
import me.jessyan.peach.shop.entity.search.UserSearchRecordBean;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.greendao.GreenDaoManager;
import me.jessyan.peach.shop.greendao.SearchRecordBeanDao;
import me.jessyan.peach.shop.greendao.UserSearchRecordBeanDao;
import me.jessyan.peach.shop.home.mvp.contract.SearchGoodsContract;
import me.jessyan.peach.shop.netconfig.temporary.NewApi;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/20/2018 23:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SearchGoodsModel extends BaseModel implements SearchGoodsContract.Model {

    /**
     * mType: 1 淘宝
     * 2 京东
     */
    private final int mType = 1;

    @Inject
    public SearchGoodsModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<SearchOptionalBean> getSearchData() {
        return mRepositoryManager
                .obtainRetrofitService(NewApi.class)
                .getHotSearchWords()
                .map(new Function<BasicResponse<SearchHotBean>, SearchOptionalBean>() {
                    @Override
                    public SearchOptionalBean apply(BasicResponse<SearchHotBean> searchHotBeanBasicResponse) throws Exception {
                        SearchOptionalBean optionalBean = new SearchOptionalBean();
                        List<SearchRecordBean> recordList = querySearchRecord();
                        optionalBean.setRecordList(recordList);
                        if (searchHotBeanBasicResponse.getCode() == 0) {
                            optionalBean.setHotList(searchHotBeanBasicResponse.getData().getData());
                        }
                        return optionalBean;
                    }
                });
    }

    @Override
    public Observable<List<SearchRecordBean>> saveSearchRecord(String value) {
        return Observable.just(value)
                .map(new Function<String, List<SearchRecordBean>>() {
                    @Override
                    public List<SearchRecordBean> apply(String s) throws Exception {
                        UserSearchRecordBeanDao userSearchRecordBeanDao = GreenDaoManager.getUserSearchRecordBeanDao();
                        SearchRecordBeanDao searchRecordBeanDao = GreenDaoManager.getSearchRecordBeanDao();

                        UserSearchRecordBean userSearchRecordBean = userSearchRecordBeanDao.queryBuilder()
                                .where(UserSearchRecordBeanDao.Properties.Mobile.eq(UserInfo.getInstance().getMobile()))
                                .build().unique();
                        if (userSearchRecordBean == null) {
                            //表中没有该用户的信息
                            LogUtils.d(TAG, "搜索用户表中没有该用户");
                            UserSearchRecordBean bean = new UserSearchRecordBean();
                            bean.setMobile(UserInfo.getInstance().getMobile());
                            userSearchRecordBeanDao.insert(bean);
                            //插入数据库
                            insertSearchRecord(bean, s);
                            return querySearchRecord();
                        }

                        QueryBuilder<SearchRecordBean> queryBuilder = searchRecordBeanDao.queryBuilder();

                        SearchRecordBean searchRecordBean = queryBuilder.where(SearchRecordBeanDao.Properties.SearchId.eq(userSearchRecordBean.getId()),
                                SearchRecordBeanDao.Properties.Type.eq(mType),
                                SearchRecordBeanDao.Properties.Value.eq(s)).build().unique();
                        if (searchRecordBean != null) {
                            //有该用户的历史搜索记录，该记录关键词已经存在表中
                            LogUtils.d(TAG, "有该用户的历史搜索记录，该记录关键词已经存在表中");
                            searchRecordBean.setMillis(System.currentTimeMillis());
                            searchRecordBeanDao.update(searchRecordBean);
                        } else {
                            //有该用户的历史搜索记录，该记录是新的关键词
                            LogUtils.d(TAG, "有该用户的历史搜索记录，该记录是新的关键词");
                            insertSearchRecord(userSearchRecordBean, s);
                        }

                        return querySearchRecord();
                    }
                });
    }

    /**
     * 插入历史搜索记录
     */
    private void insertSearchRecord(UserSearchRecordBean bean, String value) {
        SearchRecordBean searchRecordBean = new SearchRecordBean();
        searchRecordBean.setMillis(System.currentTimeMillis());
        searchRecordBean.setValue(value);
        searchRecordBean.setType(mType);
        searchRecordBean.setSearchId(bean.getId());
        GreenDaoManager.getSearchRecordBeanDao().insert(searchRecordBean);
    }

    /**
     * 数据库中查询历史搜索记录
     */
    private List<SearchRecordBean> querySearchRecord() {
        UserSearchRecordBeanDao userSearchRecordBeanDao = GreenDaoManager.getUserSearchRecordBeanDao();
        SearchRecordBeanDao searchRecordBeanDao = GreenDaoManager.getSearchRecordBeanDao();

        UserSearchRecordBean userSearchRecordBean = userSearchRecordBeanDao.queryBuilder()
                .where(UserSearchRecordBeanDao.Properties.Mobile.eq(UserInfo.getInstance().getMobile())).build().unique();
        if (userSearchRecordBean == null) {
            return null;
        }
        return searchRecordBeanDao.queryBuilder()
                .where(SearchRecordBeanDao.Properties.SearchId.eq(userSearchRecordBean.getId()),
                        SearchRecordBeanDao.Properties.Type.eq(mType))
                .orderDesc(SearchRecordBeanDao.Properties.Millis)
                .limit(10)
                .build().list();
    }
}