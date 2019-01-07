package me.jessyan.peach.shop.mine.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.IntentExtra;
import me.jessyan.peach.shop.entity.mine.IncomeReportsDetailsBean;
import me.jessyan.peach.shop.mine.di.component.DaggerIncomeComponent;
import me.jessyan.peach.shop.mine.mvp.contract.IncomeContract;
import me.jessyan.peach.shop.mine.mvp.presenter.IncomePresenter;
import me.jessyan.peach.shop.mine.mvp.ui.activity.IncomeDetailActivity;
import me.jessyan.peach.shop.utils.StringUtils;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/30/2018 14:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class IncomeFragment extends BaseFragment<IncomePresenter> implements IncomeContract.View {

    @BindView(R.id.tv_total_income)
    TextView mTvTotalIncome;
    @BindView(R.id.tv_current_income)
    TextView mTvCurrentIncome;
    @BindView(R.id.tv_last_income)
    TextView mTvLastIncome;
    @BindView(R.id.tv_current_settlement)
    TextView mTvCurrentSettlement;
    @BindView(R.id.tv_last_settlement)
    TextView mTvLastSettlement;
    @BindView(R.id.tv_today_count)
    TextView mTvTodayCount;
    @BindView(R.id.tv_today_commission)
    TextView mTvTodayCommission;
    @BindView(R.id.tv_yesterday_count)
    TextView mTvYesterdayCount;
    @BindView(R.id.tv_yesterday_commission)
    TextView mTvYesterdayCommission;
    @BindView(R.id.tv_buy_money)
    TextView mTvBuyMoney;
    @BindView(R.id.tv_service_provider_money)
    TextView mTvServiceProviderMoney;
    private String mShopType;

    public static IncomeFragment newInstance(String shopType) {
        IncomeFragment fragment = new IncomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IntentExtra.SHOP_TYPE, shopType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerIncomeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_income, container, false);
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mShopType = bundle.getString(IntentExtra.SHOP_TYPE);
        }
        if (!TextUtils.isEmpty(mShopType)) {
            mPresenter.getIncomeReportsDetails(mShopType);
        }
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    public boolean isLazyLoad() {
        return true;
    }

    @OnClick(R.id.tv_look_detail)
    public void onViewClicked() {
        IncomeDetailActivity.launcher(getContext());
    }

    @Override
    public void onGetIncomeReportsDetailsSuccess(IncomeReportsDetailsBean.DataBean data) {
        mTvTotalIncome.setText(StringUtils.keepTwoDecimal(data.getTotal()));
        String currentIncomeFormat = String.format(getString(R.string.rmb), StringUtils.keepTwoDecimal(data.getCurrentForecast()));
        mTvCurrentIncome.setText(currentIncomeFormat);

        String lastIncomeFormat = String.format(getString(R.string.rmb), StringUtils.keepTwoDecimal(data.getLastForecast()));
        mTvLastIncome.setText(lastIncomeFormat);

        String currentSettlementFormat = String.format(getString(R.string.rmb), StringUtils.keepTwoDecimal(data.getCurrentSettlement()));
        mTvCurrentSettlement.setText(currentSettlementFormat);

        String lastSettlementFormat = String.format(getString(R.string.rmb), StringUtils.keepTwoDecimal(data.getLastSettlement()));
        mTvLastSettlement.setText(lastSettlementFormat);

        mTvTodayCount.setText(data.getTodayCount());

        String todayCommission = String.format(getString(R.string.rmb), StringUtils.keepTwoDecimal(data.getTodayCommission()));
        mTvTodayCommission.setText(todayCommission);

        mTvYesterdayCount.setText(data.getYesterdayCount());

        String yesterdayCommission = String.format(getString(R.string.rmb), StringUtils.keepTwoDecimal(data.getYesterdayCommission()));
        mTvYesterdayCommission.setText(yesterdayCommission);

        String buyMoney = String.format(getString(R.string.rmb), StringUtils.keepTwoDecimal(data.getBuyMoney()));
        mTvBuyMoney.setText(buyMoney);

        String serviceProviderMoney = String.format(getString(R.string.rmb), StringUtils.keepTwoDecimal(data.getServiceProviderMoney()));
        mTvServiceProviderMoney.setText(serviceProviderMoney);
    }
}
