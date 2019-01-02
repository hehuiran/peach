package me.jessyan.peach.shop.user.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
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
import me.jessyan.peach.shop.entity.user.MessageBean;
import me.jessyan.peach.shop.help.decoration.MessageItemDecoration;
import me.jessyan.peach.shop.user.di.component.DaggerMessageComponent;
import me.jessyan.peach.shop.user.mvp.contract.MessageContract;
import me.jessyan.peach.shop.user.mvp.presenter.MessagePresenter;
import me.jessyan.peach.shop.user.mvp.ui.adapter.MessageAdapter;
import me.jessyan.peach.shop.utils.ResourceUtils;
import me.jessyan.peach.shop.widget.RecyclerLoadMoreView;
import me.jessyan.peach.shop.widget.refresh.PullRefreshBannerView;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/23/2018 23:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MessageActivity extends BaseActivity<MessagePresenter> implements MessageContract.View {

    @BindView(R.id.status_view)
    Space mStatusView;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.pull_refresh_view)
    PullRefreshBannerView mPullRefreshView;
    private MessageAdapter mAdapter;
    private View mNetErrorView;
    private View mEmptyView;

    public static void launcher(Context context) {
        Intent intent = new Intent(context, MessageActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMessageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_message; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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
        mPresenter.getMessageList(true);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new MessageItemDecoration());
        mAdapter = new MessageAdapter();

        //recyclerView item侧滑
        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
        new ItemTouchHelper(itemDragAndSwipeCallback).attachToRecyclerView(mRecyclerView);
        itemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START);
        mAdapter.enableSwipeItem();
        mAdapter.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
                String id = mAdapter.getData().get(pos).getId();
                if (TextUtils.isEmpty(id)) {
                    mAdapter.notifyItemChanged(pos);
                } else {
                    mPresenter.deleteMessage(id, pos);
                }
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
                canvas.drawColor(ResourceUtils.getResourceColor(R.color.color_ff1673));
            }
        });

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getMessageList(false);
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

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        onBackPressed();
    }

    private void clearData(){
        if (!mAdapter.getData().isEmpty()) {
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
        }
    }

    private void showEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, ((ViewGroup) mPullRefreshView.getParent()), false);
        }
        mAdapter.setEmptyView(mEmptyView);
    }

    private void showNetErrorView() {
        if (mNetErrorView == null) {
            mNetErrorView = LayoutInflater.from(this).inflate(R.layout.net_error_layout, ((ViewGroup) mPullRefreshView.getParent()), false);
            mNetErrorView.findViewById(R.id.tv_reload).setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onClicked(View view) {
                    refreshData();
                }
            });
        }
        mAdapter.setEmptyView(mNetErrorView);
    }

    @Override
    public void onGetMessageListSuccess(List<MessageBean.DataBean> list) {
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
                clearData();
                showEmptyView();
            }
            mAdapter.setEnableLoadMore(size >= CommonConstant.PAGE_SIZE);
        }
    }

    @Override
    public void onGetMessageListFailed() {
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
    public void onDeleteMessageSuccess(int position) {
        mAdapter.remove(position);
        if (mAdapter.getData().isEmpty()) {
            showEmptyView();
        }
    }

    @Override
    public void onDeleteMessageFailed(int position) {
        mAdapter.notifyItemChanged(position);
    }
}
