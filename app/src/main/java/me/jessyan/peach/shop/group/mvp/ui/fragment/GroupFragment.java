package me.jessyan.peach.shop.group.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;

import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.callback.OnSingleClickListener;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.BannerCategoryClickBean;
import me.jessyan.peach.shop.entity.goods.CouponsBannerBean;
import me.jessyan.peach.shop.entity.group.GroupBean;
import me.jessyan.peach.shop.entity.group.GroupOptionalBean;
import me.jessyan.peach.shop.group.di.component.DaggerGroupComponent;
import me.jessyan.peach.shop.group.mvp.contract.GroupContract;
import me.jessyan.peach.shop.group.mvp.presenter.GroupPresenter;
import me.jessyan.peach.shop.group.mvp.ui.adapter.GroupAdapter;
import me.jessyan.peach.shop.help.AliTradeHelper;
import me.jessyan.peach.shop.help.BannerCategoryClickHelper;
import me.jessyan.peach.shop.help.BannerImageLoader;
import me.jessyan.peach.shop.help.LoginHelper;
import me.jessyan.peach.shop.widget.RecyclerLoadMoreView;
import me.jessyan.peach.shop.widget.refresh.PullRefreshBannerView;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 21:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class GroupFragment extends BaseFragment<GroupPresenter> implements GroupContract.View {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.pull_refresh_view)
    PullRefreshBannerView mPullRefreshView;
    private GroupAdapter mAdapter;
    private View mEmptyView, mNetErrorView;
    private Banner mBanner;
    private View mHeadView;

    public static GroupFragment newInstance() {
        GroupFragment fragment = new GroupFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerGroupComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group, container, false);
    }

    @Override
    public void initData() {

        mPullRefreshView.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshData(false);
            }
        });

        initRecyclerView();

        refreshData(true);
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new GroupAdapter();
        mAdapter.setHeaderAndEmpty(true);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (LoginHelper.checkLogin(getContext())) {
                    String clickUrl = mAdapter.getData().get(position).getClickUrl();
                    launcherTaoBao(clickUrl);
                }
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getGroupData(false, false);
            }
        }, mRecyclerView);
        mAdapter.setLoadMoreView(new RecyclerLoadMoreView());
        mAdapter.setEnableLoadMore(false);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    private void launcherTaoBao(String url) {
        FragmentActivity activity = getActivity();
        if (activity == null || TextUtils.isEmpty(url)) {
            return;
        }
        AlibcPage page = new AlibcPage(url);
        AliTradeHelper.getDefault().show(activity, page, OpenType.Native);
    }

    private void refreshData(boolean showLoading) {
        mPresenter.getGroupData(true, showLoading);
    }

    private void showEmptyView() {
        if (!mAdapter.getData().isEmpty()) {
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
        }
        if (mEmptyView == null) {
            mEmptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_layout, ((ViewGroup) mPullRefreshView.getParent()), false);
        }
        mAdapter.setEmptyView(mEmptyView);
    }

    private void showNetErrorView() {
        if (mNetErrorView == null) {
            mNetErrorView = LayoutInflater.from(getContext()).inflate(R.layout.net_error_layout, ((ViewGroup) mPullRefreshView.getParent()), false);
            mNetErrorView.findViewById(R.id.tv_reload).setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onClicked(View view) {
                    refreshData(false);
                }
            });
        }
        mAdapter.setEmptyView(mNetErrorView);
    }

    private void addHeadView(List<CouponsBannerBean.BannerBean> bannerList) {
        if (bannerList == null || bannerList.isEmpty()) {
            return;
        }
        if (mHeadView == null) {
            mHeadView = LayoutInflater.from(getContext()).inflate(R.layout.item_group_banner, ((ViewGroup) mPullRefreshView.getParent()), false);
            mBanner = mHeadView.findViewById(R.id.banner);
        }
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new BannerImageLoader<CouponsBannerBean.BannerBean, String>() {
                    @Override
                    protected String getPath(CouponsBannerBean.BannerBean bean) {
                        return bean.getDataBean().getImg();
                    }
                })
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        if (LoginHelper.checkLogin(getContext())) {
                            handleBannerClick(bannerList.get(position));
                        }
                    }
                })
                .setIndicatorGravity(BannerConfig.CENTER)
                .setImages(bannerList)
                .start();
        if (mAdapter.getHeaderLayoutCount() == 0) {
            mAdapter.addHeaderView(mHeadView);
        }
    }

    private void handleBannerClick(CouponsBannerBean.BannerBean bannerBean) {
        CouponsBannerBean.BannerBean.DataBean data = bannerBean.getDataBean();

        BannerCategoryClickBean bannerCategoryClickBean = new BannerCategoryClickBean();
        bannerCategoryClickBean.setAction(data.getAction());
        bannerCategoryClickBean.setExtraBean(data.getExtra());
        if (data.getItems() != null && !data.getItems().isEmpty()) {
            bannerCategoryClickBean.setGiveGoodsDetailBean(data.getItems().get(0));
        }
        bannerCategoryClickBean.setTitle(data.getTitle());
        bannerCategoryClickBean.setUrl(data.getImageUrl());
        bannerCategoryClickBean.setType(bannerBean.getType());

        new BannerCategoryClickHelper().handleClick(getContext(), bannerCategoryClickBean);
    }

    @Override
    public void onGetGroupDataSuccess(GroupOptionalBean optionalBean) {
        GroupBean groupBean = optionalBean.getGroupBean();
        List<GroupBean.DataBean> list = groupBean == null ? null : groupBean.getData();
        boolean isLoadMore = mAdapter.isLoading();
        int size = list == null ? 0 : list.size();
        if (isLoadMore) {
            if (size > 0) {
                mAdapter.addData(list);
            }
            if (size < CommonConstant.DYNAMIC_PAGE_SIZE) {
                mAdapter.loadMoreEnd();
            } else {
                mAdapter.loadMoreComplete();
            }
        } else {
            if (mPullRefreshView.isRefreshing()) {
                mPullRefreshView.refreshComplete();
            }
            if (size > 0) {
                addHeadView(optionalBean.getBannerList());
                mAdapter.setNewData(list);
            } else {
                showEmptyView();
            }
            mAdapter.setEnableLoadMore(size >= CommonConstant.DYNAMIC_PAGE_SIZE);
        }
    }


    @Override
    public void onGetGroupDataFailed() {
        boolean isLoadMore = mAdapter.isLoading();
        if (isLoadMore) {
            mAdapter.loadMoreFail();
        } else {
            if (mPullRefreshView.isRefreshing()) {
                mPullRefreshView.refreshComplete();
            }
            showNetErrorView();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mBanner != null) {
            mBanner.startAutoPlay();
        }
    }

    @Override
    public void onDestroyView() {
        AliTradeHelper.getDefault().release();
        super.onDestroyView();
    }
}
