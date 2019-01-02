package me.jessyan.peach.shop.callback;

import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.trade.biz.alipay.AliPayResult;
import com.alibaba.baichuan.trade.biz.context.AlibcResultType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.blankj.utilcode.util.LogUtils;

import java.util.List;

/**
 * Created by fenghaoxiu on 16/8/23.
 *
 * 阿里百川的回调接口，只有通过h5打开页面才会有回调
 */
public class TradeCallback implements AlibcTradeCallback {
    private static final String TAG = "TradeCallback";

    @Override
    public void onTradeSuccess(AlibcTradeResult tradeResult) {
        //当addCartPage加购成功和其他page支付成功的时候会回调

        AliPayResult payResult = tradeResult.payResult;
        List payFailedOrders = payResult.payFailedOrders;
        for (Object failedOrder : payFailedOrders) {
            LogUtils.d(TAG, "失败的订单号=" + failedOrder.toString());
        }

        List paySuccessOrders = payResult.paySuccessOrders;
        for (Object paySuccessOrder : paySuccessOrders) {
            LogUtils.d(TAG, "成功的订单号=" + paySuccessOrder.toString());
        }

        if (tradeResult.resultType.equals(AlibcResultType.TYPECART)) {
            //加购成功
            LogUtils.d(TAG, "加购成功");
        } else if (tradeResult.resultType.equals(AlibcResultType.TYPEPAY)) {
            //支付成功
            LogUtils.d(TAG, "支付成功,成功订单号为" + tradeResult.payResult.paySuccessOrders);
        }
    }

    @Override
    public void onFailure(int errCode, String errMsg) {
        LogUtils.e(TAG, "电商SDK出错,错误码=" + errCode + " / 错误消息=" + errMsg);
    }
}
