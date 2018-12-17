package me.jessyan.peach.shop.home.mvp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutViewFactory;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.callback.OnSingleClickListener;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import me.jessyan.peach.shop.entity.goods.CouponsChannelSubBean;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryTitleBean;
import me.jessyan.peach.shop.entity.home.GoodsBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailConfigBean;
import me.jessyan.peach.shop.entity.home.HomeMainOptionalBean;
import me.jessyan.peach.shop.entity.home.HomeSectionBean;
import me.jessyan.peach.shop.home.di.component.DaggerHomeMainComponent;
import me.jessyan.peach.shop.home.mvp.contract.HomeMainContract;
import me.jessyan.peach.shop.home.mvp.presenter.HomeMainPresenter;
import me.jessyan.peach.shop.home.mvp.ui.activity.GoodsDetailActivity;
import me.jessyan.peach.shop.home.mvp.ui.adapter.HomeAdvertisingAdapter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.HomeBannerAdapter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.HomeChannelAdapter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.HomeGoodsAdapter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.HomeMainRecommendTitleAdapter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.HomeOrientationAdapter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.HomeOrientationItemAdapter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.HomeSectionAdapter;
import me.jessyan.peach.shop.vlayout.VirtualAdapter;
import me.jessyan.peach.shop.vlayout.callback.OnItemClickListener;
import me.jessyan.peach.shop.widget.DispatchTouchRecyclerView;
import me.jessyan.peach.shop.widget.RecyclerLoadMoreView;
import me.jessyan.peach.shop.widget.refresh.PullRefreshBannerView;
import timber.log.Timber;


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
    private VirtualAdapter mVirtualAdapter;
    private View mNetErrorView;

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
                refreshData();
            }
        });

        initRecyclerView();

        refreshData();
    }

    private void refreshData() {
        mPresenter.getHomeMainData();
    }

    private void initRecyclerView() {
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);

        viewPool.setMaxRecycledViews(VirtualAdapter.LOADING_VIEW, 3);
        viewPool.setMaxRecycledViews(RecyclerViewType.EMPTY_TYPE, 1);
        viewPool.setMaxRecycledViews(RecyclerViewType.NET_ERROR_TYPE, 1);
        viewPool.setMaxRecycledViews(RecyclerViewType.BANNER_TYPE, 3);
        viewPool.setMaxRecycledViews(RecyclerViewType.HOME_CHANNEL_TYPE, 10);
        viewPool.setMaxRecycledViews(RecyclerViewType.HOME_ADVERTISING_TYPE, 3);
        viewPool.setMaxRecycledViews(RecyclerViewType.HOME_SECTION_TYPE, 5);
        viewPool.setMaxRecycledViews(RecyclerViewType.HOME_ORIENTATION_TYPE, 3);
        viewPool.setMaxRecycledViews(RecyclerViewType.HOME_ORIENTATION_ITEM_TYPE, 10);
        viewPool.setMaxRecycledViews(RecyclerViewType.HOME_GOODS_TITLE_TYPE, 1);
        viewPool.setMaxRecycledViews(RecyclerViewType.HOME_GOODS_TYPE, 10);

        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getContext());
        virtualLayoutManager.setLayoutViewFactory(new LayoutViewFactory() {
            @Override
            public View generateLayoutView(@NonNull Context context) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.MATRIX);
                return imageView;
            }
        });
        virtualLayoutManager.setRecycleChildrenOnDetach(true);
        mRecyclerView.setLayoutManager(virtualLayoutManager);

        mVirtualAdapter = new VirtualAdapter(virtualLayoutManager);
        mVirtualAdapter.setOnLoadMoreListener(new VirtualAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadMoreGoods();
            }
        }, mRecyclerView);
        mVirtualAdapter.setLoadMoreView(new RecyclerLoadMoreView());
        mVirtualAdapter.setEnableLoadMore(false);
        mRecyclerView.setAdapter(mVirtualAdapter);

        mAdapterList = new ArrayList<>();

        HomeBannerAdapter bannerAdapter = new HomeBannerAdapter(R.layout.item_home_main_banner);
        mAdapterList.add(bannerAdapter);

        HomeChannelAdapter channelAdapter = new HomeChannelAdapter();
        mAdapterList.add(channelAdapter);

        HomeAdvertisingAdapter advertisingAdapter1 = new HomeAdvertisingAdapter(false);
        mAdapterList.add(advertisingAdapter1);

        HomeSectionAdapter sectionAdapter = new HomeSectionAdapter();
        mAdapterList.add(sectionAdapter);

        HomeAdvertisingAdapter advertisingAdapter2 = new HomeAdvertisingAdapter(true);
        mAdapterList.add(advertisingAdapter2);

        HomeOrientationAdapter orientationAdapter = new HomeOrientationAdapter(viewPool);
        orientationAdapter.setQuickAdapterOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsBean bean = ((HomeOrientationItemAdapter) adapter).getData().get(position);
                launcherGoodsDetail(bean);
            }
        });
        mAdapterList.add(orientationAdapter);

        mAdapterList.add(new HomeMainRecommendTitleAdapter());

        HomeGoodsAdapter goodsAdapter = new HomeGoodsAdapter();
        goodsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View view, int position) {
                GoodsBean bean = ((HomeGoodsAdapter) adapter).getData().get(position);
                if (bean.getItemType() == RecyclerViewType.HOME_GOODS_TYPE) {
                    launcherGoodsDetail(bean);
                }
            }
        });
        mAdapterList.add(goodsAdapter);

        mVirtualAdapter.setAdapters(mAdapterList);
    }

    /**
     * 跳转商品详情
     */
    private void launcherGoodsDetail(GoodsBean bean) {
        Context context = getContext();
        if (context != null) {
            GoodsDetailActivity.launcher(context, GoodsDetailConfigBean.generateConfigBean(bean));
        } else {
            Timber.tag(TAG).w("context is null...");
        }
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
        if (mPullRefreshView.isRefreshing()) {
            mPullRefreshView.refreshComplete();
        }
        HomeBannerAdapter bannerAdapter = getAdapter(0);
        List<CouponsBannerBean.BannerBean> bannerList = homeMainOptionalBean.getBannerData().getBannerList();
        if (bannerList != null && !bannerList.isEmpty() && bannerAdapter != null) {
            bannerAdapter.setData(bannerList);
        }

        HomeChannelAdapter channelAdapter = getAdapter(1);
        List<CouponsChannelSubBean> subList = homeMainOptionalBean.getChannelData().getSubList();
        if (subList != null && !subList.isEmpty() && channelAdapter != null) {
            channelAdapter.setNewData(subList, 1);
        }

        HomeAdvertisingAdapter advertisingAdapter1 = getAdapter(2);
        if (advertisingAdapter1 != null) {
            advertisingAdapter1.setAdvertisingUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544868392232&di=8714b61eb9c1f711d219fdee6035e8cf&imgtype=0&src=http%3A%2F%2Fpic11.photophoto.cn%2F20090603%2F0034034495016977_b.jpg", 2);
        }

        HomeSectionAdapter sectionAdapter = getAdapter(3);
        List<HomeSectionBean> sectionList = homeMainOptionalBean.getHomeSectionList();
        if (sectionList != null && !sectionList.isEmpty() && sectionAdapter != null) {
            sectionAdapter.setNewData(sectionList, 3);
        }

        HomeAdvertisingAdapter advertisingAdapter2 = getAdapter(4);
        if (advertisingAdapter2 != null) {
            advertisingAdapter2.setAdvertisingUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1544868392232&di=8714b61eb9c1f711d219fdee6035e8cf&imgtype=0&src=http%3A%2F%2Fpic11.photophoto.cn%2F20090603%2F0034034495016977_b.jpg", 4);
        }

        HomeOrientationAdapter orientationAdapter = getAdapter(5);
        List<GoodsBean> dataList = homeMainOptionalBean.getOrientationGoodsBean().getData();
        if (dataList != null && !dataList.isEmpty() && orientationAdapter != null) {
            orientationAdapter.setData(dataList, 5);
        }

        List<GoodsBean> goodsList = homeMainOptionalBean.getCouponsCommodityBean().getList();
        int size = goodsList == null ? 0 : goodsList.size();
        if (size > 0) {
            HomeMainRecommendTitleAdapter recommendTitleAdapter = getAdapter(6);
            if (recommendTitleAdapter != null) {
                recommendTitleAdapter.setShown(true, 6);
            }
            HomeGoodsAdapter goodsAdapter = getAdapter(7);
            if (goodsAdapter != null) {
                goodsAdapter.setNewData(goodsList, 7);
            }
        }
        mVirtualAdapter.setEnableLoadMore(size >= CommonConstant.PAGE_SIZE);
    }

    @Override
    public void onGetHomeMainDataFailed() {
        if (mPullRefreshView.isRefreshing()) {
            mPullRefreshView.refreshComplete();
        }
        if (mNetErrorView == null) {
            mNetErrorView = LayoutInflater.from(getContext()).inflate(R.layout.net_error_layout, mPullRefreshView, false);
            mNetErrorView.findViewById(R.id.tv_reload).setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onClicked(View view) {
                    refreshData();
                }
            });
        }
        mVirtualAdapter.setEmptyView(mNetErrorView);
    }

    @Override
    public void onLoadMoreGoodsSuccess(List<GoodsBean> list) {
        final int size = list == null ? 0 : list.size();
        HomeGoodsAdapter goodsAdapter = getAdapter(7);
        if (goodsAdapter != null && size > 0) {
            goodsAdapter.addData(list);
        }
        if (size < CommonConstant.PAGE_SIZE) {
            mVirtualAdapter.loadMoreEnd();
        } else {
            mVirtualAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onLoadMoreGoodsFailed() {
        if (mVirtualAdapter.isLoading()) {
            mVirtualAdapter.loadMoreFail();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Banner banner = getBanner();
        if (banner != null) {
            banner.stopAutoPlay();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Banner banner = getBanner();
        if (banner != null) {
            banner.startAutoPlay();
        }
    }

    private Banner getBanner() {
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager == null) {
            return null;

        }
        View view = layoutManager.findViewByPosition(0);
        if (view == null) {
            return null;
        }
        return view.findViewById(R.id.banner);
    }

    /**
     * 根据position获取相应的adapter
     */
    @SuppressWarnings("unchecked")
    public <T extends DelegateAdapter.Adapter> T getAdapter(int position) {
        if (mAdapterList == null || mAdapterList.isEmpty() || position >= mAdapterList.size()) {
            Timber.tag(TAG).w("mAdapterList is error...");
            return null;
        }
        return (T) mAdapterList.get(position);
    }
}
