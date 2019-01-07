package me.jessyan.peach.shop.category.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import me.jessyan.peach.shop.category.di.component.DaggerCategorySubComponent;
import me.jessyan.peach.shop.category.mvp.contract.CategorySubContract;
import me.jessyan.peach.shop.category.mvp.presenter.CategorySubPresenter;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.entity.home.GoodsBean;
import me.jessyan.peach.shop.home.mvp.ui.adapter.GoodsQuickAdapter;
import me.jessyan.peach.shop.widget.RecyclerLoadMoreView;
import me.jessyan.peach.shop.widget.StickyLayout;
import me.jessyan.peach.shop.widget.refresh.PullRefreshBannerView;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/23/2018 21:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CategorySubActivity extends BaseActivity<CategorySubPresenter> implements CategorySubContract.View {

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
    private String mOneType;
    private String mTwoType;

    public static void launcher(Context context, String title, String oneType, String twoType) {
        Intent intent = new Intent(context, CategorySubActivity.class);
        intent.putExtra(IntentExtra.TITLE, title);
        intent.putExtra(IntentExtra.ONE_TYPE, oneType);
        intent.putExtra(IntentExtra.TWO_TYPE, twoType);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCategorySubComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_category_sub; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        String title = intent.getStringExtra(IntentExtra.TITLE);
        mOneType = intent.getStringExtra(IntentExtra.ONE_TYPE);
        mTwoType = intent.getStringExtra(IntentExtra.TWO_TYPE);

        if (TextUtils.isEmpty(title)) {
            title = TextUtils.isEmpty(mTwoType) ? mOneType : mTwoType;
        }
        mTvTitle.setText(title);

        mSort = StickyLayout.getDefaultSort();

        initListener();

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
                mPresenter.fetchCategorySubData(false, false, mOneType, mTwoType, mSort);
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
        mPresenter.fetchCategorySubData(true, showLoading, mOneType, mTwoType, mSort);
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
        mImmersionBar.statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @OnClick({R.id.iv_back, R.id.iv_composing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
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
    public void onFetchCategorySubDataSuccess(List<GoodsBean> list) {
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
    public void onFetchCategorySubDataFailed() {
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
