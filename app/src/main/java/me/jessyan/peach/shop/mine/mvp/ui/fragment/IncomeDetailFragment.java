package me.jessyan.peach.shop.mine.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;

import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.callback.OnSingleClickListener;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.entity.mine.IncomeReportsListBean;
import me.jessyan.peach.shop.mine.di.component.DaggerIncomeDetailComponent;
import me.jessyan.peach.shop.mine.mvp.contract.IncomeDetailContract;
import me.jessyan.peach.shop.mine.mvp.presenter.IncomeDetailPresenter;
import me.jessyan.peach.shop.mine.mvp.ui.adapter.IncomeDetailAdapter;
import me.jessyan.peach.shop.widget.RecyclerLoadMoreView;
import me.jessyan.peach.shop.widget.refresh.PullRefreshBannerView;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 18:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class IncomeDetailFragment extends BaseFragment<IncomeDetailPresenter> implements IncomeDetailContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.pull_refresh_view)
    PullRefreshBannerView mPullRefreshView;
    private IncomeDetailAdapter mAdapter;
    private View mEmptyView, mNetErrorView;
    private int mType;

    public static IncomeDetailFragment newInstance(int type) {
        IncomeDetailFragment fragment = new IncomeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IntentExtra.TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerIncomeDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_income_detail, container, false);
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getInt(IntentExtra.TYPE);
        }

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
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new IncomeDetailAdapter();
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getIncomeDetail(mType, false, false);
            }
        }, mRecyclerView);
        mAdapter.setLoadMoreView(new RecyclerLoadMoreView());
        mAdapter.setEnableLoadMore(false);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void refreshData(boolean showLoading) {
        mPresenter.getIncomeDetail(mType, true, showLoading);
    }

    private void showEmptyView() {
        if (!mAdapter.getData().isEmpty()) {
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
        }
        if (mEmptyView == null) {
            mEmptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_layout, mPullRefreshView, false);
        }
        mAdapter.setEmptyView(mEmptyView);
    }

    private void showNetErrorView() {
        if (mNetErrorView == null) {
            mNetErrorView = LayoutInflater.from(getContext()).inflate(R.layout.net_error_layout, mPullRefreshView, false);
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
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    public boolean isLazyLoad() {
        return true;
    }

    @Override
    public void onGetIncomeDetailSuccess(List<IncomeReportsListBean.DataBean> list) {
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
    public void onGetIncomeDetailFailed() {
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
