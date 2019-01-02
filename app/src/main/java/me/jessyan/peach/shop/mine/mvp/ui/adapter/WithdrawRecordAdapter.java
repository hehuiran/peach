package me.jessyan.peach.shop.mine.mvp.ui.adapter;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import me.jessyan.peach.shop.R;
import me.jessyan.peach.shop.constant.CommonConstant;
import me.jessyan.peach.shop.entity.mine.WithdrawRecordBean;
import me.jessyan.peach.shop.utils.StringUtils;

/**
 * author: Create by HuiRan on 2018/12/30 下午7:34
 * email: 15260828327@163.com
 * description:
 */
public class WithdrawRecordAdapter extends BaseQuickAdapter<WithdrawRecordBean.DataBean, BaseViewHolder> {

    public WithdrawRecordAdapter() {
        super(R.layout.item_income_detail);
    }

    @Override
    protected void convert(BaseViewHolder helper, WithdrawRecordBean.DataBean item) {
        String money = StringUtils.keepTwoDecimal(item.getAmount());
        float moneyFloat = StringUtils.parseFloat(money);
        String moneyText = (moneyFloat > 0 ? "+" : CommonConstant.EMPTY_STRING) + money;

        helper.setText(R.id.tv_money, moneyText)
                .setText(R.id.tv_channel, item.getRemark())
                .setText(R.id.tv_time, TimeUtils.millis2String(item.getAddTime()));
    }
}
