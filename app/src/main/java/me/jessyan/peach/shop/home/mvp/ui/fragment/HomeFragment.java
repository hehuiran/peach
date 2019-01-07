package me.jessyan.peach.shop.home.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.flyco.tablayout.SlidingTabLayout;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.callback.OnSingleClickListener;
import me.jessyan.peach.shop.entity.event.ChangeTabEvent;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryTitleBean;
import me.jessyan.peach.shop.entity.goods.GoodsCategoryTitleMainBean;
import me.jessyan.peach.shop.help.LoginHelper;
import me.jessyan.peach.shop.home.di.component.DaggerHomeComponent;
import me.jessyan.peach.shop.home.mvp.contract.HomeContract;
import me.jessyan.peach.shop.home.mvp.presenter.HomePresenter;
import me.jessyan.peach.shop.home.mvp.ui.activity.SearchGoodsActivity;
import me.jessyan.peach.shop.home.mvp.ui.adapter.HomePagerAdapter;
import me.jessyan.peach.shop.home.mvp.ui.window.MoreChannelWindow;
import me.jessyan.peach.shop.user.mvp.ui.activity.MessageActivity;
import me.jessyan.peach.shop.utils.ResourceUtils;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 21:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.root)
    ConstraintLayout mRoot;
    @BindView(R.id.status_view)
    View mStatusView;
    @BindView(R.id.v_target)
    View mTargetView;
    @BindView(R.id.tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.view_stub_error)
    ViewStub mViewStubError;
    @BindView(R.id.view_stub_success)
    ViewStub mViewStubSuccess;
    private View mErrorView;
    private List<GoodsCategoryTitleBean.DataBean> mData;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(mStatusView)
                .init();
    }

    @Override
    public void initData() {

        requestNetData();
    }

    private void requestNetData() {
        mPresenter.getTabCategory();
    }

    @OnClick({R.id.tv_search, R.id.iv_msg, R.id.iv_category})
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
            case R.id.iv_category:
                showMoreChannelWindow();
                break;
        }
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(ChangeTabEvent event) {
        int position = event.getPosition();
        if (position < mTabLayout.getTabCount()) {
            mTabLayout.setCurrentTab(position);
        }
    }

    private void showMoreChannelWindow() {
        MoreChannelWindow moreChannelWindow = new MoreChannelWindow(getContext(), mData);
        moreChannelWindow.setOnMoreChannelItemClickListener(new MoreChannelWindow.OnMoreChannelItemClickListener() {
            @Override
            public void onMoreChannelItemClick(int position) {
                if (position < mTabLayout.getTabCount() - 1) {
                    mTabLayout.setCurrentTab(position);
                }
            }
        });
        moreChannelWindow.show(mTargetView);
    }

    @Override
    public void onGetTabCategorySuccess(GoodsCategoryTitleBean bean) {
        mData = bean.getData();
        if (mData != null && !mData.isEmpty()) {
            initSuccessView();
        } else {
            initErrorView();
        }
    }

    public List<GoodsCategoryTitleBean.DataBean> getTabCategory() {
        return mData;
    }

    @Override
    public void onGetTabCategoryFailed() {
        initErrorView();
    }

    private void initSuccessView() {
        if (mViewStubError == null && mErrorView != null) {
            mRoot.removeView(mErrorView);
        }
        mViewStubSuccess.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, View inflated) {
                initViewPager(inflated);
            }
        });
        if (mViewStubSuccess != null) {
            mViewStubSuccess.inflate();
        }
    }

    private void initViewPager(View view) {
        ViewPager viewPager = view.findViewById(R.id.view_pager);

        List<GoodsCategoryTitleBean.DataBean> list = generateTabList();
        FragmentManager childFragmentManager = getChildFragmentManager();
        HomePagerAdapter pagerAdapter = new HomePagerAdapter(childFragmentManager, list);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(list.size());
        mTabLayout.setViewPager(viewPager);
    }

    private List<GoodsCategoryTitleBean.DataBean> generateTabList() {
        List<GoodsCategoryTitleBean.DataBean> list = new ArrayList<>();
        GoodsCategoryTitleMainBean mainBean = new GoodsCategoryTitleMainBean();
        mainBean.setTypename(ResourceUtils.getResourceString(R.string.today_selection));
        ArrayList<GoodsCategoryTitleBean.DataBean> dataBeans = new ArrayList<>(mData);
        mainBean.setGoodsTypeList(dataBeans);
        list.add(mainBean);
        list.addAll(mData);
        return list;
    }

    private void initErrorView() {
        if (mViewStubError != null && mErrorView == null) {
            //网络请求出错，加载error视图
            mViewStubError.setOnInflateListener(new ViewStub.OnInflateListener() {
                @Override
                public void onInflate(ViewStub stub, View inflated) {
                    //重新加载
                    inflated.findViewById(R.id.tv_reload).setOnClickListener(new OnSingleClickListener() {
                        @Override
                        public void onClicked(View view) {
                            requestNetData();
                        }
                    });
                }
            });
            mErrorView = mViewStubError.inflate();
        }
    }
}
