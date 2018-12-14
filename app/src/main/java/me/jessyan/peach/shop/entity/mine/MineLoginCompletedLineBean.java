package me.jessyan.peach.shop.entity.mine;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import me.jessyan.peach.shop.entity.MultiItemBean;
import me.jessyan.peach.shop.widget.single.SingleUnit;

/**
 * Created by Administrator on 2017/11/21.
 *
 */

public class MineLoginCompletedLineBean extends MultiItemBean {

    private String canWithdraw;
    private String totalAmount;
    private String yesterdayAmount;
    private List<ResultModel> result;
    private List<SingleUnit> list;

    public List<ResultModel> getResult() {
        return result;
    }

    public void setResult(List<ResultModel> result) {
        this.result = result;
    }

    public List<SingleUnit> getList() {
        return list;
    }

    public void setList(List<SingleUnit> list) {
        this.list = list;
    }

    public String getCanWithdraw() {
        return canWithdraw;
    }

    public void setCanWithdraw(String canWithdraw) {
        this.canWithdraw = canWithdraw;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getYesterdayAmount() {
        return yesterdayAmount;
    }

    public void setYesterdayAmount(String yesterdayAmount) {
        this.yesterdayAmount = yesterdayAmount;
    }

    public static class ResultModel implements Comparable<ResultModel> {
        private String amount;
        private String time;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public int compareTo(@NonNull ResultModel o) {
            long l1 = TimeUtils.string2Millis(this.time, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()));
            long l2 = TimeUtils.string2Millis(o.time, new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()));
            return (int) (l1 - l2);
        }
    }
}
