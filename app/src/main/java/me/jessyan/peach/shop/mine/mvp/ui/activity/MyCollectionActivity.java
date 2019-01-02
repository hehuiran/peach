package me.jessyan.peach.shop.mine.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Space;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.callback.OnSingleClickListener;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.event.GoodsCollectionStateChangeEvent;
import me.jessyan.peach.shop.entity.home.GoodsBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailConfigBean;
import me.jessyan.peach.shop.home.mvp.ui.activity.GoodsDetailActivity;
import me.jessyan.peach.shop.home.mvp.ui.adapter.GoodsQuickAdapter;
import me.jessyan.peach.shop.mine.di.component.DaggerMyCollectionComponent;
import me.jessyan.peach.shop.mine.mvp.contract.MyCollectionContract;
import me.jessyan.peach.shop.mine.mvp.presenter.MyCollectionPresenter;
import me.jessyan.peach.shop.widget.RecyclerLoadMoreView;
import me.jessyan.peach.shop.widget.refresh.PullRefreshBannerView;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/31/2018 22:19
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MyCollectionActivity extends BaseActivity<MyCollectionPresenter> implements MyCollectionContract.View {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.pull_refresh_view)
    PullRefreshBannerView mPullRefreshView;
    private GoodsQuickAdapter mAdapter;
    private View mNetErrorView;
    private View mEmptyView;

    public static void launcher(Context context) {
        Intent intent = new Intent(context, MyCollectionActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyCollectionComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_collection; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new GoodsQuickAdapter();
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsBean bean = mAdapter.getData().get(position);
                GoodsDetailActivity.launcher(MyCollectionActivity.this, GoodsDetailConfigBean.generateConfigBean(bean));
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getCollectionGoods(false, false);
            }
        }, mRecyclerView);
        mAdapter.setLoadMoreView(new RecyclerLoadMoreView());
        mAdapter.setEnableLoadMore(false);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void refreshData(boolean showLoading) {
        mPresenter.getCollectionGoods(true, showLoading);
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
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(GoodsCollectionStateChangeEvent event) {
        refreshData(false);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onGetCollectionGoodsSuccess(List<GoodsBean> list) {
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
    public void onGetCollectionGoodsFailed() {
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
