package me.jessyan.peach.shop.mine.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.mine.MineOptionalBean;
import me.jessyan.peach.shop.entity.user.UserAccountBean;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.help.ImageLoaderHelper;
import me.jessyan.peach.shop.help.glide.GlideRoundTransform;
import me.jessyan.peach.shop.mine.di.component.DaggerMineComponent;
import me.jessyan.peach.shop.mine.mvp.contract.MineContract;
import me.jessyan.peach.shop.mine.mvp.presenter.MinePresenter;
import me.jessyan.peach.shop.utils.StringUtils;
import me.jessyan.peach.shop.widget.refresh.PullRefreshBannerView;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/12/2018 21:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.View {

    @BindView(R.id.status_view)
    View mStatusView;
    @BindView(R.id.pull_refresh_view)
    PullRefreshBannerView mPullRefreshView;
    @BindView(R.id.iv_avatar)
    RoundedImageView mIvAvatar;
    @BindView(R.id.tv_members)
    TextView mTvMembers;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;
    @BindView(R.id.tv_invite_code)
    TextView mTvInviteCode;
    @BindView(R.id.tv_fans)
    TextView mTvFans;
    @BindView(R.id.tv_integral)
    TextView mTvIntegral;
    @BindView(R.id.tv_income)
    TextView mTvIncome;
    @BindView(R.id.tv_balance)
    TextView mTvBalance;
    @BindView(R.id.iv_advertising)
    ImageView mIvAdvertising;
    private ImageLoader mImageLoader;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMineComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void initData() {
        mPullRefreshView.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshData(false);
            }
        });
        mImageLoader = ImageLoaderHelper.getImageLoader();

        setUserInfo();

        refreshData(true);
    }

    private void setUserInfo() {
        mImageLoader.loadImage(getContext(), ImageConfigImpl.builder()
                .imageView(mIvAvatar)
                .url(UserInfo.getInstance().getHeadImgUrl())
                .build());

        mTvNickname.setText(UserInfo.getInstance().getNickname());

        String inviteCodeText = String.format(getString(R.string.invite_code), UserInfo.getInstance().getInviteCode());
        mTvInviteCode.setText(inviteCodeText);

    }

    private void refreshData(boolean showLoading) {
        mPresenter.getMineData(showLoading);
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarView(mStatusView)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @OnClick({R.id.iv_setting, R.id.iv_message, R.id.iv_avatar, R.id.tv_copy, R.id.card_income,
            R.id.card_balance, R.id.tv_my_order, R.id.tv_integral_order, R.id.tv_my_fans,
            R.id.tv_invite_friend, R.id.iv_advertising, R.id.tv_service_provider,
            R.id.tv_new_user_guide, R.id.tv_my_collection, R.id.tv_common_problem,
            R.id.tv_contract_customer, R.id.tv_platform_notice, R.id.tv_feedback, R.id.tv_about_us})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_setting:
                break;
            case R.id.iv_message:
                break;
            case R.id.iv_avatar:
                break;
            case R.id.tv_copy:
                break;
            case R.id.card_income:
                break;
            case R.id.card_balance:
                break;
            case R.id.tv_my_order:
                break;
            case R.id.tv_integral_order:
                break;
            case R.id.tv_my_fans:
                break;
            case R.id.tv_invite_friend:
                break;
            case R.id.iv_advertising:
                break;
            case R.id.tv_service_provider:
                break;
            case R.id.tv_new_user_guide:
                break;
            case R.id.tv_my_collection:
                break;
            case R.id.tv_common_problem:
                break;
            case R.id.tv_contract_customer:
                break;
            case R.id.tv_platform_notice:
                break;
            case R.id.tv_feedback:
                break;
            case R.id.tv_about_us:
                break;
        }
    }

    @Override
    public void onGetMineDataSuccess(MineOptionalBean optionalBean) {
        if (mPullRefreshView.isRefreshing()) {
            mPullRefreshView.refreshComplete();
        }

        String advertising = optionalBean.getAdvertising();
        if (!TextUtils.isEmpty(advertising)) {
            mImageLoader.loadImage(getContext(), ImageConfigImpl.builder()
                    .imageView(mIvAdvertising)
                    .url(optionalBean.getAdvertising())
                    .transformation(new GlideRoundTransform())
                    .build());
        }

        UserAccountBean accountBean = optionalBean.getAccountBean();
        if (accountBean != null) {
            mTvIncome.setText(StringUtils.keepTwoDecimal(accountBean.getIncome()));
            mTvBalance.setText(StringUtils.keepTwoDecimal(accountBean.getBalance()));
        }

    }

    @Override
    public void onGetMineDataFailed() {
        if (mPullRefreshView.isRefreshing()) {
            mPullRefreshView.refreshComplete();
        }
    }
}
