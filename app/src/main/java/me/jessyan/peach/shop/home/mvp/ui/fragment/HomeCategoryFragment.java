package me.jessyan.peach.shop.home.mvp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
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
import me.jessyan.peach.shop.entity.goods.GoodsCategoryGridBean;
import me.jessyan.peach.shop.entity.home.GoodsBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailConfigBean;
import me.jessyan.peach.shop.entity.home.HomeCategoryOptionalBean;
import me.jessyan.peach.shop.home.di.component.DaggerHomeCategoryComponent;
import me.jessyan.peach.shop.home.mvp.contract.HomeCategoryContract;
import me.jessyan.peach.shop.home.mvp.presenter.HomeCategoryPresenter;
import me.jessyan.peach.shop.home.mvp.ui.activity.GoodsDetailActivity;
import me.jessyan.peach.shop.home.mvp.ui.adapter.HomeBannerAdapter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.HomeCategoryChannelAdapter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.HomeCategoryStickyAdapter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.HomeGoodsAdapter;
import me.jessyan.peach.shop.vlayout.VirtualAdapter;
import me.jessyan.peach.shop.vlayout.callback.OnItemClickListener;
import me.jessyan.peach.shop.widget.DispatchTouchRecyclerView;
import me.jessyan.peach.shop.widget.RecyclerLoadMoreView;
import me.jessyan.peach.shop.widget.StickyLayout;
import me.jessyan.peach.shop.widget.refresh.PullRefreshBannerView;
import timber.log.Timber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 23:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeCategoryFragment extends BaseFragment<HomeCategoryPresenter> implements HomeCategoryContract.View {

    @BindView(R.id.recycler_view)
    DispatchTouchRecyclerView mRecyclerView;
    @BindView(R.id.pull_refresh_view)
    PullRefreshBannerView mPullRefreshView;
    private List<DelegateAdapter.Adapter> mAdapterList;
    private VirtualAdapter mVirtualAdapter;
    private View mNetErrorView;
    private int[] mStickySelectTypes;
    private int mTypeId;
    private String mOneType;
    private String mTwoType;
    private int mSelectType;
    private String mSort = CommonConstant.EMPTY_STRING;

    public static HomeCategoryFragment newInstance(int typeId, String oneType, String twoType) {
        HomeCategoryFragment fragment = new HomeCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IntentExtra.ID, typeId);
        bundle.putString(IntentExtra.ONE_TYPE, oneType);
        bundle.putString(IntentExtra.TWO_TYPE, twoType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeCategoryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_category, container, false);
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTypeId = bundle.getInt(IntentExtra.ID);
            mOneType = bundle.getString(IntentExtra.ONE_TYPE);
            mTwoType = bundle.getString(IntentExtra.TWO_TYPE);
        }

        mPullRefreshView.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshData();
            }
        });

        mStickySelectTypes = getResources().getIntArray(R.array.category_tag_array);
        mSelectType = mStickySelectTypes[0];

        initRecyclerView();

        refreshData();
    }

    private void refreshData() {
        mPresenter.getHomeCategoryData(mTypeId, mOneType, mTwoType, mSelectType, mSort);
    }

    private void getGoods(boolean isLoadMore) {
        mPresenter.getGoods(mOneType, mTwoType, mSelectType, mSort, isLoadMore);
    }

    private void initRecyclerView() {
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);

        viewPool.setMaxRecycledViews(VirtualAdapter.LOADING_VIEW, 3);
        viewPool.setMaxRecycledViews(RecyclerViewType.EMPTY_TYPE, 1);
        viewPool.setMaxRecycledViews(RecyclerViewType.NET_ERROR_TYPE, 1);
        viewPool.setMaxRecycledViews(RecyclerViewType.STICKY_TYPE, 1);
        viewPool.setMaxRecycledViews(RecyclerViewType.BANNER_TYPE, 3);
        viewPool.setMaxRecycledViews(RecyclerViewType.HOME_CHANNEL_TYPE, 10);
        viewPool.setMaxRecycledViews(RecyclerViewType.HOME_GOODS_TYPE, 10);

        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(getContext());
        mRecyclerView.setLayoutManager(virtualLayoutManager);

        mVirtualAdapter = new VirtualAdapter(virtualLayoutManager);
        mVirtualAdapter.setOnLoadMoreListener(new VirtualAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                //load more
                getGoods(true);
            }
        }, mRecyclerView);
        mVirtualAdapter.setLoadMoreView(new RecyclerLoadMoreView());
        mVirtualAdapter.setEnableLoadMore(false);
        mRecyclerView.setAdapter(mVirtualAdapter);

        mAdapterList = new ArrayList<>();

        HomeBannerAdapter bannerAdapter = new HomeBannerAdapter(R.layout.item_home_category_banner);
        mAdapterList.add(bannerAdapter);

        HomeCategoryChannelAdapter channelAdapter = new HomeCategoryChannelAdapter();
        mAdapterList.add(channelAdapter);

        HomeCategoryStickyAdapter stickyAdapter = new HomeCategoryStickyAdapter();
        stickyAdapter.setOnStickyTabChangeListener(new StickyLayout.OnStickyTabChangeListener() {
            @Override
            public void onStickyTabChange(int position, String sort) {
                mSelectType = mStickySelectTypes[position];
                mSort = sort;
                getGoods(false);
            }
        });
        mAdapterList.add(stickyAdapter);

        HomeGoodsAdapter goodsAdapter = new HomeGoodsAdapter();
        goodsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View view, int position) {
                GoodsBean bean = ((HomeGoodsAdapter) adapter).getData().get(position);
                launcherGoodsDetail(bean);
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
    public void onGetHomeCategoryDataSuccess(HomeCategoryOptionalBean optionalBean) {
        if (mPullRefreshView.isRefreshing()) {
            mPullRefreshView.refreshComplete();
        }
        HomeBannerAdapter bannerAdapter = getAdapter(0);
        List<CouponsBannerBean.BannerBean> bannerList = optionalBean.getBannerData().getBannerList();
        if (bannerList != null && !bannerList.isEmpty() && bannerAdapter != null) {
            bannerAdapter.setData(bannerList);
        }

        HomeCategoryChannelAdapter channelAdapter = getAdapter(1);
        List<GoodsCategoryGridBean.DataBean> channelList = optionalBean.getChannelData().getData();
        int channelSize = channelList == null ? 0 : channelList.size();
        if (channelAdapter != null && channelSize > 0) {
            if (channelSize > 5 && channelSize < 10) {
                channelList = channelList.subList(0, 5);
            }
            channelAdapter.setNewData(channelList, 1);
        }

        HomeCategoryStickyAdapter stickyAdapter = getAdapter(2);
        if (stickyAdapter != null) {
            stickyAdapter.setShown(true, 2);
        }

        List<GoodsBean> goodsList = optionalBean.getCouponsCommodityBean().getList();
        int goodsSize = goodsList == null ? 0 : goodsList.size();
        HomeGoodsAdapter goodsAdapter = getAdapter(3);
        if (goodsSize > 0 && goodsAdapter != null) {
            goodsAdapter.setNewData(goodsList, 3);
        }
        mVirtualAdapter.setEnableLoadMore(goodsSize >= CommonConstant.PAGE_SIZE);
    }

    @Override
    public void onGetHomeCategoryDataFailed() {
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
        HomeGoodsAdapter goodsAdapter = getAdapter(3);
        if (goodsAdapter != null && size > 0) {
            goodsAdapter.addData(list);
        }
        loadMoreComplete(size, false);
    }

    @Override
    public void onLoadMoreGoodsFailed() {
        if (mVirtualAdapter.isLoading()) {
            mVirtualAdapter.loadMoreFail();
        }
    }

    @Override
    public void onGoodsRefreshCompleted(List<GoodsBean> list) {
        int size = list == null ? 0 : list.size();
        refreshGoods(list);
        loadMoreComplete(size, true);
    }

    /**
     * 商品更新
     */
    private void refreshGoods(List<GoodsBean> list) {
        int size = list == null ? 0 : list.size();
        HomeGoodsAdapter goodsAdapter = getAdapter(3);
        if (size > 0 && goodsAdapter != null) {
            //适配器需全局更新，不然会数组越界
            goodsAdapter.setNewData(list);
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    scrollToSticky();
                }
            });
        }
    }

    /**
     * 滚动到吸顶处
     */
    private void scrollToSticky() {
        HomeCategoryStickyAdapter stickyAdapter = getAdapter(2);
        if (stickyAdapter == null) {
            Timber.tag(TAG).w("warning!!! HomeCategoryStickyAdapter is null");
            return;
        }
        int absolutePosition = stickyAdapter.getAbsolutePosition();
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager == null) {
            Timber.tag(TAG).w("warning!!! RecyclerView.LayoutManager is null");
            return;
        }
        View view = layoutManager.findViewByPosition(absolutePosition);
        if (view != null) {
            int top = view.getTop();
            if (top > 0) {
                mRecyclerView.scrollBy(0, top);
            }
        } else {
            mRecyclerView.scrollToPosition(absolutePosition);
        }
    }

    /**
     * 加载更多完成
     */
    private void loadMoreComplete(int size, boolean gone) {
        if (size < CommonConstant.PAGE_SIZE) {
            mVirtualAdapter.loadMoreEnd(gone);
        } else {
            mVirtualAdapter.loadMoreComplete();
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
