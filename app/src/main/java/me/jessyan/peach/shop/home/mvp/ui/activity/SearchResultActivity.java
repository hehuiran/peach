package me.jessyan.peach.shop.home.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.callback.OnSingleClickListener;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.entity.home.GoodsBean;
import me.jessyan.peach.shop.home.di.component.DaggerSearchResultComponent;
import me.jessyan.peach.shop.home.mvp.contract.SearchResultContract;
import me.jessyan.peach.shop.home.mvp.presenter.SearchResultPresenter;
import me.jessyan.peach.shop.home.mvp.ui.adapter.GoodsQuickAdapter;
import me.jessyan.peach.shop.widget.RecyclerLoadMoreView;
import me.jessyan.peach.shop.widget.StickyLayout;
import me.jessyan.peach.shop.widget.refresh.PullRefreshBannerView;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/22/2018 14:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SearchResultActivity extends BaseActivity<SearchResultPresenter> implements SearchResultContract.View {

    @BindView(R.id.status_view)
    View mStatusView;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_composing)
    ImageView mIvComposing;
    @BindView(R.id.sticky_layout)
    StickyLayout mStickyLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.pull_refresh_view)
    PullRefreshBannerView mPullRefreshView;
    private boolean canRefresh, isLinear;
    private String mSort;
    private GoodsQuickAdapter mAdapter;
    private View mNetErrorView;
    private View mEmptyView;
    private String mValue;

    public static void launcher(Context context, String value) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(IntentExtra.VALUE, value);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSearchResultComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search_result; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        mValue = intent.getStringExtra(IntentExtra.VALUE);

        mSort = StickyLayout.getDefaultSort();

        initListener();

        mTvTitle.setText(mValue);

        initRecyclerView();

        refreshData(true);
    }

    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new GoodsQuickAdapter();
        mAdapter.setHasStableIds(true);
        mAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                return isLinear ? 2 : 1;
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getSearchResult(mValue, mSort, false, false);
            }
        }, mRecyclerView);
        mAdapter.setLoadMoreView(new RecyclerLoadMoreView());
        mAdapter.setEnableLoadMore(false);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initListener() {
        mPullRefreshView.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshData(false);
            }

            /**
             * 下拉刷新触发的条件
             */
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return canRefresh && !canScrollVertically() && super.checkCanDoRefresh(frame, content, header);
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                canRefresh = verticalOffset == 0;
            }
        });
        mStickyLayout.setOnStickyTabChangeListener(new StickyLayout.OnStickyTabChangeListener() {
            @Override
            public void onStickyTabChange(int position, String sort) {
                mSort = sort;
                refreshData(false);
            }
        });
    }


    private void refreshData(boolean showLoading) {
        mPresenter.getSearchResult(mValue, mSort, true, showLoading);
    }

    /**
     * recyclerView置顶的判断
     *
     * @return 返回false表示不能往下滑动，即代表到顶部了
     */
    private boolean canScrollVertically() {
        return mRecyclerView.canScrollVertically(-1);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar
                .statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @OnClick({R.id.iv_back, R.id.iv_composing, R.id.tv_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_title:
                SearchGoodsActivity.launcher(this, true);
                break;
            case R.id.iv_composing:
                changeComposing();
                break;
        }
    }

    private void changeComposing() {
        if (mAdapter.getData().isEmpty()) {
            return;
        }
        isLinear = !isLinear;
        mIvComposing.setImageResource(isLinear ? R.mipmap.ic_composing_linear : R.mipmap.ic_composing_grid);
        mAdapter.setLinear(isLinear);
    }

    private void showEmptyView() {
        if (!mAdapter.getData().isEmpty()) {
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
        }
        if (mEmptyView == null) {
            mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, mPullRefreshView, false);
        }
        mAdapter.setEmptyView(mEmptyView);
    }

    private void showNetErrorView() {
        if (mNetErrorView == null) {
            mNetErrorView = LayoutInflater.from(this).inflate(R.layout.net_error_layout, mPullRefreshView, false);
            mNetErrorView.findViewById(R.id.tv_reload).setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onClicked(View view) {
                    refreshData(false);
                }
            });
        }
        mAdapter.setEmptyView(mNetErrorView);
    }

    @Override
    public void onGetSearchResultSuccess(List<GoodsBean> list) {
        boolean isLoadMore = mAdapter.isLoading();
        int size = list == null ? 0 : list.size();
        if (isLoadMore) {
            if (size > 0) {
                mAdapter.addData(list);
            }
            if (size < CommonConstant.PAGE_SIZE) {
                mAdapter.loadMoreEnd();
            } else {
                mAdapter.loadMoreComplete();
            }
        } else {
            if (mPullRefreshView.isRefreshing()) {
                mPullRefreshView.refreshComplete();
            }
            if (size > 0) {
                mAdapter.setNewData(list);
            } else {
                showEmptyView();
            }
            mAdapter.setEnableLoadMore(size >= CommonConstant.PAGE_SIZE);
        }
    }


    @Override
    public void onGetSearchResultFailed() {
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
}
