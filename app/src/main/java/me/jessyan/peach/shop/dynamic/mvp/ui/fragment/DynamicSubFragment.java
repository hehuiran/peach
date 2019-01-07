package me.jessyan.peach.shop.dynamic.mvp.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.callback.OnSingleClickListener;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.dialog.ShareDialogFragment;
import me.jessyan.peach.shop.dynamic.di.component.DaggerDynamicSubComponent;
import me.jessyan.peach.shop.dynamic.mvp.contract.DynamicSubContract;
import me.jessyan.peach.shop.dynamic.mvp.presenter.DynamicSubPresenter;
import me.jessyan.peach.shop.dynamic.mvp.ui.adapter.DynamicSubAdapter;
import me.jessyan.peach.shop.entity.DynamicBean;
import me.jessyan.peach.shop.entity.home.GoodsDetailConfigBean;
import me.jessyan.peach.shop.help.LoginHelper;
import me.jessyan.peach.shop.home.mvp.ui.activity.GoodsDetailActivity;
import me.jessyan.peach.shop.home.mvp.ui.activity.PreviewActivity;
import me.jessyan.peach.shop.utils.ResourceUtils;
import me.jessyan.peach.shop.utils.StringUtils;
import me.jessyan.peach.shop.widget.RecyclerLoadMoreView;
import me.jessyan.peach.shop.widget.refresh.PullRefreshBannerView;
import timber.log.Timber;

import static android.content.Context.CLIPBOARD_SERVICE;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/25/2018 23:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class DynamicSubFragment extends BaseFragment<DynamicSubPresenter> implements DynamicSubContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.pull_refresh_view)
    PullRefreshBannerView mPullRefreshView;
    private DynamicSubAdapter mAdapter;
    private View mEmptyView, mNetErrorView;
    private int mType;

    public static DynamicSubFragment newInstance(int type) {
        DynamicSubFragment fragment = new DynamicSubFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(IntentExtra.TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDynamicSubComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dynamic_sub, container, false);
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
        mAdapter = new DynamicSubAdapter(mType);
        mAdapter.setOnImageClickListener(new DynamicSubAdapter.OnImageClickListener() {
            @Override
            public void onImageClick(View view, String url) {
                int size = ScreenUtils.getScreenWidth();
                launcherPreview(view, url, size, size);
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mType == 0 && LoginHelper.checkLogin(getContext())) {
                    //每日爆款
                    launcherGoodsDetail(position);
                }
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_share:
                        shareImage(position);
                        break;
                    case R.id.tv_copy:
                        copyText(mAdapter.getData().get(position).getComment());
                        break;
                }
            }
        });
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getDynamicData(mType, false, false);
            }
        }, mRecyclerView);
        mAdapter.setLoadMoreView(new RecyclerLoadMoreView());
        mAdapter.setEnableLoadMore(false);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void launcherGoodsDetail(int position) {
        DynamicBean.ListBean bean = mAdapter.getData().get(position);
        GoodsDetailConfigBean configBean = GoodsDetailConfigBean.builder()
                .setRequestDetailsApi(false)
                .setTitle(bean.getTitle())
                .setItemId(bean.getItemId())
                .setCouponMoney(bean.getCouponMoney())
                .setCouponEndTime(StringUtils.parseLong(bean.getCouponEndTime()) * 1000L)
                .setCouponStartTime(StringUtils.parseLong(bean.getCouponStartTime()) * 1000L)
                .setTkMoney(bean.getTkMoney())
                .setDiscountPrice(bean.getDiscountPrice())
                .setOriginalPrice(bean.getOriginalPrice())
                .build();
        GoodsDetailActivity.launcher(getContext(), configBean);
    }

    private void shareImage(int position) {
        if (mType == 0) {
            //每日爆款
            sharePosterImage(position);
        } else if (mType == 1) {
            //宣传素材

        } else if (mType == 2) {
            //小桃学堂
            shareNetImage(position);
        }
    }

    private void shareNetImage(int position) {
        DynamicBean.ListBean bean = mAdapter.getData().get(position);
        List<String> smallImages = bean.getSmallImages();
        if (smallImages != null && !smallImages.isEmpty()) {
            ShareDialogFragment.newInstance(ShareDialogFragment.SHARE_TYPE_NET_IMAGE, smallImages.get(0))
                    .setOnShareListener(new ShareDialogFragment.OnShareListener() {
                        @Override
                        public void onShareSuccess(SHARE_MEDIA platform) {
                            mPresenter.addShareNum(position, String.valueOf(bean.getId()));
                        }
                    })
                    .show(getChildFragmentManager());
        }
    }

    private void sharePosterImage(int position) {
        DynamicBean.ListBean bean = mAdapter.getData().get(position);
        List<String> smallImages = bean.getSmallImages();
        if (smallImages != null && !smallImages.isEmpty()) {
            ShareDialogFragment.ShareGoodsPosterBean posterBean = new ShareDialogFragment.ShareGoodsPosterBean();
            posterBean.setImg(smallImages.get(0));
            posterBean.setCouponMoney(StringUtils.keepTwoDecimal(bean.getCouponMoney()));
            posterBean.setDiscountPrice(StringUtils.keepTwoDecimal(bean.getDiscountPrice()));
            posterBean.setOriginalPrice(StringUtils.keepTwoDecimal(bean.getOriginalPrice()));
            posterBean.setItemId(bean.getItemId());
            posterBean.setTitle(bean.getTitle());
            ShareDialogFragment.newInstance(posterBean)
                    .setOnShareListener(new ShareDialogFragment.OnShareListener() {
                        @Override
                        public void onShareSuccess(SHARE_MEDIA platform) {
                            copyText(bean.getTitle());
                            mPresenter.addShareNum(position, String.valueOf(bean.getId()));
                        }
                    })
                    .show(getChildFragmentManager());
        } else {
            Timber.tag(TAG).w("smallImages is empty...");
        }
    }

    private void copyText(String text) {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            ClipData clipData = ClipData.newPlainText("text", text);
            clipboardManager.setPrimaryClip(clipData);
            ToastUtils.showShort(R.string.copy_success);
        }
    }

    @SuppressWarnings("unchecked")
    private void launcherPreview(View view, String imgUrl, int wight, int height) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            Pair<View, String> pair = Pair.create(view, ResourceUtils.getResourceString(R.string.transition_name_image));
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity, pair);
            PreviewActivity.luncher(getContext(), imgUrl, wight, height, optionsCompat.toBundle());
        } else {
            Timber.tag(TAG).e("getActivity is null...");
        }
    }

    private void refreshData(boolean showLoading) {
        mPresenter.getDynamicData(mType, true, showLoading);
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
    public void onGetDynamicSuccess(List<DynamicBean.ListBean> list) {
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
                mAdapter.setNewData(list);
            } else {
                showEmptyView();
            }
            mAdapter.setEnableLoadMore(size >= CommonConstant.DYNAMIC_PAGE_SIZE);
        }
    }

    @Override
    public void onGetDynamicFailed() {
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
    public void onAddShareNumSuccess(int position) {
        DynamicBean.ListBean bean = mAdapter.getData().get(position);
        bean.setShareNum(bean.getShareNum() + 1);
        TextView tvShare = (TextView) mAdapter.getViewByPosition(position, R.id.tv_share);
        if (tvShare != null) {
            tvShare.setText(String.valueOf(bean.getShareNum()));
        } else {
            mAdapter.notifyItemChanged(position);
        }
    }
}
