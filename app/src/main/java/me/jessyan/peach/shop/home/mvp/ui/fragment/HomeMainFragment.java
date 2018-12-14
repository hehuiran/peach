package me.jessyan.peach.shop.home.mvp.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.entity.HomeMainOptionalBean;
import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryTitleBean;
import me.jessyan.peach.shop.home.di.component.DaggerHomeMainComponent;
import me.jessyan.peach.shop.home.mvp.contract.HomeMainContract;
import me.jessyan.peach.shop.home.mvp.presenter.HomeMainPresenter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.HomeBannerAdapter;
import me.jessyan.peach.shop.vlayout.VirtualAdapter;
import me.jessyan.peach.shop.widget.DispatchTouchRecyclerView;
import me.jessyan.peach.shop.widget.RecyclerLoadMoreView;
import me.jessyan.peach.shop.widget.refresh.PullRefreshBannerView;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 23:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeMainFragment extends BaseFragment<HomeMainPresenter> implements HomeMainContract.View {

    @BindView(R.id.recycler_view)
    DispatchTouchRecyclerView mRecyclerView;
    @BindView(R.id.pull_refresh_view)
    PullRefreshBannerView mPullRefreshView;
    private ArrayList<GoodsCategoryTitleBean.DataBean> mGoodsTypeList;
    private List<DelegateAdapter.Adapter> mAdapterList;

    public static HomeMainFragment newInstance(ArrayList<? extends Parcelable> list) {
        HomeMainFragment fragment = new HomeMainFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(IntentExtra.HOME_CATEGORY_LIST, list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_main, container, false);
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mGoodsTypeList = bundle.getParcelableArrayList(IntentExtra.HOME_CATEGORY_LIST);
        }

        mPullRefreshView.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

            }
        });

        initRecyclerView();

        mPresenter.getHomeMainData();
    }

    private void initRecyclerView() {
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);

        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getContext());
        virtualLayoutManager.setRecycleChildrenOnDetach(true);
        mRecyclerView.setLayoutManager(virtualLayoutManager);

        VirtualAdapter virtualAdapter = new VirtualAdapter(virtualLayoutManager);
        virtualAdapter.setOnLoadMoreListener(new VirtualAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

            }
        }, mRecyclerView);
        virtualAdapter.setLoadMoreView(new RecyclerLoadMoreView());
        virtualAdapter.setEnableLoadMore(false);
        mRecyclerView.setAdapter(virtualAdapter);

        mAdapterList = new ArrayList<>();

        HomeBannerAdapter bannerAdapter = new HomeBannerAdapter();
        mAdapterList.add(bannerAdapter);

        virtualAdapter.setAdapters(mAdapterList);
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    public boolean isLazyLoad() {
        return true;
    }

    @Override
    public void onGetHomeMainDataSuccess(HomeMainOptionalBean homeMainOptionalBean) {
        HomeBannerAdapter bannerAdapter = getAdapter(0);
        List<CouponsBannerBean.BannerBean> bannerList = homeMainOptionalBean.getBannerData().getBannerList();
        if (bannerList != null && !bannerList.isEmpty() && bannerAdapter != null) {
            bannerAdapter.setData(bannerList);
        }
    }

    @Override
    public void onGetHomeMainDataFailed() {

    }

    /**
     * 根据position获取相应的adapter
     */
    @SuppressWarnings("unchecked")
    public <T extends DelegateAdapter.Adapter> T getAdapter(int position) {
        if (mAdapterList == null || mAdapterList.isEmpty()) {
            return null;
        }
        return (T) mAdapterList.get(position);
    }
}
