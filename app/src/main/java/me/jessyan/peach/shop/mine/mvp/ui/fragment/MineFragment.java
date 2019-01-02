package me.jessyan.peach.shop.mine.mvp.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageConfigImpl;
import com.jess.arms.http.imageloader.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.entity.event.ChangeAvatarEvent;
import me.jessyan.peach.shop.entity.event.ChangeNicknameEvent;
import me.jessyan.peach.shop.entity.mine.MineOptionalBean;
import me.jessyan.peach.shop.entity.user.UserAccountBean;
import me.jessyan.peach.shop.entity.user.UserInfo;
import me.jessyan.peach.shop.help.ImageLoaderHelper;
import me.jessyan.peach.shop.help.UserGradeHelper;
import me.jessyan.peach.shop.help.glide.GlideRoundTransform;
import me.jessyan.peach.shop.mine.di.component.DaggerMineComponent;
import me.jessyan.peach.shop.mine.mvp.contract.MineContract;
import me.jessyan.peach.shop.mine.mvp.presenter.MinePresenter;
import me.jessyan.peach.shop.mine.mvp.ui.activity.CommonProblemActivity;
import me.jessyan.peach.shop.mine.mvp.ui.activity.InviteActivity;
import me.jessyan.peach.shop.mine.mvp.ui.activity.MyCollectionActivity;
import me.jessyan.peach.shop.mine.mvp.ui.activity.MyFansActivity;
import me.jessyan.peach.shop.mine.mvp.ui.activity.MyIncomeActivity;
import me.jessyan.peach.shop.mine.mvp.ui.activity.OrderActivity;
import me.jessyan.peach.shop.mine.mvp.ui.activity.ServiceProviderActivity;
import me.jessyan.peach.shop.mine.mvp.ui.activity.WithdrawActivity;
import me.jessyan.peach.shop.user.mvp.ui.activity.BindAlipayActivity;
import me.jessyan.peach.shop.user.mvp.ui.activity.MessageActivity;
import me.jessyan.peach.shop.user.mvp.ui.activity.SettingActivity;
import me.jessyan.peach.shop.utils.StringUtils;
import me.jessyan.peach.shop.widget.refresh.PullRefreshBannerView;

import static android.content.Context.CLIPBOARD_SERVICE;


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
        updateAvatarImage();

        mTvMembers.setText(UserGradeHelper.getUserGradeNameByLevel(UserInfo.getInstance().getIdentity()));
        mTvNickname.setText(UserInfo.getInstance().getNickname());

        String inviteCodeText = String.format(getString(R.string.invite_code), UserInfo.getInstance().getInviteCode());
        mTvInviteCode.setText(inviteCodeText);

    }

    private void updateAvatarImage() {
        mImageLoader.loadImage(getContext(), ImageConfigImpl.builder()
                .imageView(mIvAvatar)
                .isCircle(true)
                .url(UserInfo.getInstance().getHeadImgUrl())
                .build());
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

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(Object event) {
        if (event instanceof ChangeAvatarEvent) {
            updateAvatarImage();
        } else if (event instanceof ChangeNicknameEvent) {
            mTvNickname.setText(UserInfo.getInstance().getNickname());
        }
    }

    @OnClick({R.id.iv_setting, R.id.iv_message, R.id.iv_avatar, R.id.tv_copy, R.id.card_income,
            R.id.card_balance, R.id.tv_my_order, R.id.tv_integral_order, R.id.tv_my_fans,
            R.id.tv_invite_friend, R.id.iv_advertising, R.id.tv_service_provider,
            R.id.tv_new_user_guide, R.id.tv_my_collection, R.id.tv_common_problem,
            R.id.tv_contract_customer, R.id.tv_platform_notice, R.id.tv_feedback, R.id.tv_about_us})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_setting:
            case R.id.iv_avatar:
                SettingActivity.launcher(getContext());
                break;
            case R.id.iv_message:
                MessageActivity.launcher(getContext());
                break;
            case R.id.tv_copy:
                copyInviteCode();
                break;
            case R.id.card_income:
                MyIncomeActivity.launcher(getContext());
                break;
            case R.id.card_balance:
                launcherWithdraw();
                break;
            case R.id.tv_my_order:
                OrderActivity.launcher(getContext());
                break;
            case R.id.tv_integral_order:
                break;
            case R.id.tv_my_fans:
                MyFansActivity.launcher(getContext());
                break;
            case R.id.tv_invite_friend:
                InviteActivity.launcher(getContext());
                break;
            case R.id.iv_advertising:
                break;
            case R.id.tv_service_provider:
                ServiceProviderActivity.launcher(getContext());
                break;
            case R.id.tv_new_user_guide:
                break;
            case R.id.tv_my_collection:
                MyCollectionActivity.launcher(getContext());
                break;
            case R.id.tv_common_problem:
                CommonProblemActivity.launcher(getContext());
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

    private void copyInviteCode() {
        String inviteCode = UserInfo.getInstance().getInviteCode();
        FragmentActivity activity = getActivity();
        if (activity == null || TextUtils.isEmpty(inviteCode)) {
            return;
        }
        ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            ClipData clipData = ClipData.newPlainText("text", inviteCode);
            clipboardManager.setPrimaryClip(clipData);
            ToastUtils.showShort(R.string.copy_success);
        }
    }

    private void launcherWithdraw() {
        String alipay = UserInfo.getInstance().getAlipay();
        if (TextUtils.isEmpty(alipay)) {
            BindAlipayActivity.launcher(getContext(), BindAlipayActivity.PURPOSE_WITHDRAW);
        } else {
            WithdrawActivity.launcher(getContext());
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
