package me.jessyan.peach.shop.category.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.callback.OnSingleClickListener;
import me.jessyan.peach.shop.category.di.component.DaggerCategoryComponent;
import me.jessyan.peach.shop.category.mvp.contract.CategoryContract;
import me.jessyan.peach.shop.category.mvp.presenter.CategoryPresenter;
import me.jessyan.peach.shop.category.mvp.ui.activity.CategorySubActivity;
import me.jessyan.peach.shop.category.mvp.ui.adapter.CategoryChildAdapter;
import me.jessyan.peach.shop.category.mvp.ui.adapter.CategoryParentAdapter;
import me.jessyan.peach.shop.entity.ExtraBean;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryGridBean;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryTitleBean;
import me.jessyan.peach.shop.help.LoginHelper;
import me.jessyan.peach.shop.home.mvp.ui.activity.SearchGoodsActivity;
import me.jessyan.peach.shop.home.mvp.ui.fragment.HomeFragment;
import me.jessyan.peach.shop.user.mvp.ui.activity.MessageActivity;
import me.jessyan.peach.shop.utils.ResourceUtils;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 21:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CategoryFragment extends BaseFragment<CategoryPresenter> implements CategoryContract.View {

    @BindView(R.id.status_view)
    View mStatusView;
    @BindView(R.id.recycler_view_parent)
    RecyclerView mRecyclerViewParent;
    @BindView(R.id.recycler_view_child)
    RecyclerView mRecyclerViewChild;
    @BindView(R.id.ll_empty_root)
    LinearLayout mLlEmptyRoot;
    private CategoryParentAdapter mParentAdapter;
    private List<GoodsCategoryTitleBean.DataBean> mParentData;
    private CategoryChildAdapter mChildAdapter;
    private int lastPosition, currentPosition = -1;
    private View mNetErrorView, mEmptyView;
    private TextView mTvChildHeadTitle;
    private View mHeadView;

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCategoryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void initData() {

        initChildHeadView();

        initParentRecyclerView();

        initChildRecyclerView();

        fetchParentData();
    }

    private void initChildHeadView() {
        mHeadView = LayoutInflater.from(getContext()).inflate(R.layout.item_category_child_head, ((ViewGroup) mRecyclerViewChild.getParent()), false);
        mTvChildHeadTitle = mHeadView.findViewById(R.id.tv_name);
    }

    private void fetchParentData() {
        mParentData = getTabCategory();
        if (mParentData != null && !mParentData.isEmpty()) {
            if (mLlEmptyRoot.getVisibility() != View.GONE) {
                mLlEmptyRoot.setVisibility(View.GONE);
            }
            currentPosition = 0;
            mParentData.get(currentPosition).setChecked(true);
            fetchChildData();
            mParentAdapter.setNewData(mParentData);
        } else {
            if (mLlEmptyRoot.getVisibility() != View.VISIBLE) {
                mLlEmptyRoot.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initChildRecyclerView() {
        mRecyclerViewChild.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mChildAdapter = new CategoryChildAdapter();
        mChildAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (LoginHelper.checkLogin(getContext())) {
                    GoodsCategoryGridBean.DataBean dataBean = mChildAdapter.getData().get(position);
                    ExtraBean extra = dataBean.getExtra();
                    CategorySubActivity.launcher(getContext(), dataBean.getTitle(), extra.getOneType(), extra.getTwoType());
                }
            }
        });
        mRecyclerViewChild.setAdapter(mChildAdapter);
    }

    private void initParentRecyclerView() {
        mRecyclerViewParent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewParent.setHasFixedSize(true);

        mParentAdapter = new CategoryParentAdapter();
        mParentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsCategoryTitleBean.DataBean data = mParentAdapter.getData().get(position);
                if (!data.isChecked()) {
                    currentPosition = position;
                    fetchChildData();
                    changeParentItemState(position, true);
                    changeParentItemState(lastPosition, false);
                    lastPosition = currentPosition;
                }
            }
        });
        mParentAdapter.bindToRecyclerView(mRecyclerViewParent);
    }

    private void changeParentItemState(int position, boolean isChecked) {
        mParentData.get(position).setChecked(isChecked);
        View currentView = mParentAdapter.getViewByPosition(position, R.id.fl_root);
        if (currentView != null) {
            currentView.setBackgroundColor(ResourceUtils.getResourceColor(isChecked ? R.color.white : R.color.color_f5f5f5));
            currentView.findViewById(R.id.v_decoration).setVisibility(isChecked ? View.VISIBLE : View.GONE);
            ((TextView) currentView.findViewById(R.id.tv_title)).setTextColor(ResourceUtils.getResourceColor(isChecked ? R.color.black : R.color.color_202020));
        } else {
            mParentAdapter.notifyItemChanged(position);
        }
    }

    private void fetchChildData() {
        if (currentPosition != -1) {
            GoodsCategoryTitleBean.DataBean data = mParentData.get(currentPosition);
            mTvChildHeadTitle.setText(data.getTypename());
            mPresenter.getSubCategoryData(data.getTypeid());
        }
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(mStatusView).init();
    }

    private List<GoodsCategoryTitleBean.DataBean> getTabCategory() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) {
            return null;
        }
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments.isEmpty()) {
            return null;
        }
        for (Fragment fragment : fragments) {
            if (fragment instanceof HomeFragment) {
                return ((HomeFragment) fragment).getTabCategory();
            }
        }
        return null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && (mParentData == null || mParentData.isEmpty())) {
            fetchParentData();
        }
    }

    @OnClick({R.id.tv_search, R.id.iv_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                if (LoginHelper.checkLogin(getContext())) {
                    SearchGoodsActivity.launcher(getContext());
                }
                break;
            case R.id.iv_msg:
                if (LoginHelper.checkLogin(getContext())) {
                    MessageActivity.launcher(getContext());
                }
                break;
        }
    }

    private void clearChildData() {
        if (!mChildAdapter.getData().isEmpty()) {
            mChildAdapter.getData().clear();
            mChildAdapter.notifyDataSetChanged();
        }
    }

    private void showEmptyView() {
        clearChildData();
        if (mEmptyView == null) {
            mEmptyView = LayoutInflater.from(getContext()).inflate(R.layout.empty_layout, ((ViewGroup) mRecyclerViewChild.getParent()), false);
        }
        mChildAdapter.setEmptyView(mEmptyView);
    }

    private void showErrorView() {
        clearChildData();
        if (mNetErrorView == null) {
            mNetErrorView = LayoutInflater.from(getContext()).inflate(R.layout.net_error_layout, ((ViewGroup) mRecyclerViewChild.getParent()), false);
            mNetErrorView.findViewById(R.id.tv_reload).setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onClicked(View view) {
                    fetchChildData();
                }
            });
        }
        mChildAdapter.setEmptyView(mNetErrorView);
    }


    private void addHeadView() {
        if (mChildAdapter.getHeaderLayoutCount() == 0) {
            mChildAdapter.addHeaderView(mHeadView);
        }
    }

    @Override
    public void onGetSubCategoryDataSuccess(List<GoodsCategoryGridBean.DataBean> list) {
        int size = list == null ? 0 : list.size();
        if (size > 0) {
            mChildAdapter.setNewData(list);
            addHeadView();
        } else {
            showEmptyView();
        }
    }


    @Override
    public void onGetSubCategoryDataFailed() {
        showErrorView();
    }
}
