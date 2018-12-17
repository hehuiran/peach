package me.jessyan.peach.shop.home.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.callback.OnSingleClickListener;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.constant.RecyclerViewType;
import me.jessyan.peach.shop.entity.home.GoodsBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailConfigBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailImageBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailInfoBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailOptionalBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailSellerBean;
import me.jessyan.peach.shop.home.di.component.DaggerGoodsDetailComponent;
import me.jessyan.peach.shop.home.mvp.contract.GoodsDetailContract;
import me.jessyan.peach.shop.home.mvp.presenter.GoodsDetailPresenter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.GoodsDetailBannerAdapter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.GoodsDetailDecorationAdapter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.GoodsDetailImageAdapter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.GoodsDetailInfoAdapter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.GoodsDetailSellerAdapter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.HomeGoodsAdapter;
import me.jessyan.peach.shop.vlayout.VirtualAdapter;
import me.jessyan.peach.shop.widget.DispatchTouchRecyclerView;
import me.jessyan.peach.shop.widget.refresh.PullRefreshBannerView;
import timber.log.Timber;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/16/2018 22:44
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class GoodsDetailActivity extends BaseActivity<GoodsDetailPresenter> implements GoodsDetailContract.View {


    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.recycler_view)
    DispatchTouchRecyclerView mRecyclerView;
    @BindView(R.id.pull_refresh_view)
    PullRefreshBannerView mPullRefreshView;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.group_bottom_bar)
    Group mGroupBottomBar;
    private List<DelegateAdapter.Adapter> mAdapterList;
    private VirtualAdapter mVirtualAdapter;
    private View mNetErrorView;
    private GoodsDetailConfigBean mDetailConfigBean;

    public static void launcher(@NonNull Context context, GoodsDetailConfigBean bean) {
        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra(IntentExtra.GOODS_DETAIL_CONFIG_BEAN, bean);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGoodsDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_goods_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        mDetailConfigBean = intent.getParcelableExtra(IntentExtra.GOODS_DETAIL_CONFIG_BEAN);
        mTvTitle.setText(mDetailConfigBean.getTitle());

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
        mPresenter.getGoodsDetailData(mDetailConfigBean);
    }

    private void initRecyclerView() {
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);

        viewPool.setMaxRecycledViews(RecyclerViewType.BANNER_TYPE, 3);
        viewPool.setMaxRecycledViews(RecyclerViewType.GOODS_DETAIL_INFO_TYPE, 3);
        viewPool.setMaxRecycledViews(RecyclerViewType.GOODS_DETAIL_SELLER_TYPE, 3);
        viewPool.setMaxRecycledViews(RecyclerViewType.GOODS_DETAIL_DECORATION_TYPE, 3);
        viewPool.setMaxRecycledViews(RecyclerViewType.GOODS_DETAIL_IMAGE_TYPE, 10);
        viewPool.setMaxRecycledViews(RecyclerViewType.HOME_GOODS_TYPE, 10);

        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(this);
        mRecyclerView.setLayoutManager(virtualLayoutManager);

        mVirtualAdapter = new VirtualAdapter(virtualLayoutManager);
        mRecyclerView.setAdapter(mVirtualAdapter);

        mAdapterList = new ArrayList<>();

        GoodsDetailBannerAdapter bannerAdapter = new GoodsDetailBannerAdapter();
        mAdapterList.add(bannerAdapter);

        GoodsDetailInfoAdapter infoAdapter = new GoodsDetailInfoAdapter();
        mAdapterList.add(infoAdapter);

        GoodsDetailSellerAdapter sellerAdapter = new GoodsDetailSellerAdapter();
        mAdapterList.add(sellerAdapter);

        GoodsDetailDecorationAdapter decorationAdapter1 = new GoodsDetailDecorationAdapter();
        mAdapterList.add(decorationAdapter1);

        GoodsDetailImageAdapter imageAdapter = new GoodsDetailImageAdapter();
        mAdapterList.add(imageAdapter);

        GoodsDetailDecorationAdapter decorationAdapter2 = new GoodsDetailDecorationAdapter();
        mAdapterList.add(decorationAdapter2);

        HomeGoodsAdapter goodsAdapter = new HomeGoodsAdapter();
        mAdapterList.add(goodsAdapter);

        mVirtualAdapter.setAdapters(mAdapterList);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar
                .statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @OnClick({R.id.iv_back, R.id.v_shape, R.id.v_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.v_shape:
                break;
            case R.id.v_buy:
                break;
        }
    }

    @Override
    public void onGetGoodsDetailDataSuccess(GoodsDetailOptionalBean optionalBean) {
        if (mPullRefreshView.isRefreshing()) {
            mPullRefreshView.refreshComplete();
        }
        GoodsDetailBannerAdapter bannerAdapter = getAdapter(0);
        List<String> bannerList = optionalBean.getBannerList();
        if (bannerAdapter != null && !bannerList.isEmpty()) {
            bannerAdapter.setData(bannerList);
        }
        GoodsDetailInfoAdapter infoAdapter = getAdapter(1);
        GoodsDetailInfoBean infoBean = optionalBean.getGoodsDetailInfoBean();
        if (infoAdapter != null && infoBean != null) {
            infoAdapter.setData(infoBean, 1);
        }
        GoodsDetailSellerAdapter sellerAdapter = getAdapter(2);
        GoodsDetailSellerBean sellerBean = optionalBean.getGoodsDetailSellerBean();
        if (sellerAdapter != null && sellerBean != null) {
            sellerAdapter.setData(sellerBean, 2);
        }
        GoodsDetailDecorationAdapter decorationAdapter1 = getAdapter(3);
        GoodsDetailImageAdapter imageAdapter = getAdapter(4);
        List<GoodsDetailImageBean> imageList = optionalBean.getImageList();
        if (imageList != null && !imageList.isEmpty()) {
            if (decorationAdapter1 != null) {
                decorationAdapter1.setTitle(getString(R.string.goods_detail), 3);
            }
            if (imageAdapter != null) {
                imageAdapter.setNewData(imageList, 4);
            }
        }
        GoodsDetailDecorationAdapter decorationAdapter2 = getAdapter(5);
        HomeGoodsAdapter goodsAdapter = getAdapter(6);
        List<GoodsBean> recommendList = optionalBean.getRecommendList();
        if (recommendList != null && !recommendList.isEmpty()) {
            if (decorationAdapter2 != null) {
                decorationAdapter2.setTitle(getString(R.string.guess_you_like), 5);
            }
            if (goodsAdapter != null) {
                goodsAdapter.setNewData(recommendList, 6);
            }
        }
    }

    @Override
    public void onGetGoodsDetailDataFailed() {
        if (mPullRefreshView.isRefreshing()) {
            mPullRefreshView.refreshComplete();
        }
        if (mNetErrorView == null) {
            mNetErrorView = LayoutInflater.from(this).inflate(R.layout.net_error_layout, ((ViewGroup) mPullRefreshView.getParent()), false);
            mNetErrorView.findViewById(R.id.tv_reload).setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onClicked(View view) {
                    refreshData();
                }
            });
        }
        mVirtualAdapter.setEmptyView(mNetErrorView);
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
