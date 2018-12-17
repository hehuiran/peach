package me.jessyan.peach.shop.entity.home;


import java.util.List;

/**
 * Created by dxp on 2018/8/24.
 * Describe : 首页横向滚动商品
 */

public class OrientationGoodsBean {

    //"returnModel":{"endtime":"\"null\"","rightcolor":"FF5CAD","leftcolor":"705AFF","imgs":"https://hzcangyu.com/title_jinribiqiang@2x.png”}

    private String endtime;
    private String rightcolor;
    private String leftcolor;
    private String imgs;
    private List<GoodsBean> data;

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getRightcolor() {
        return rightcolor;
    }

    public void setRightcolor(String rightcolor) {
        this.rightcolor = rightcolor;
    }

    public String getLeftcolor() {
        return leftcolor;
    }

    public void setLeftcolor(String leftcolor) {
        this.leftcolor = leftcolor;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public List<GoodsBean> getData() {
        return data;
    }

    public void setData(List<GoodsBean> data) {
        this.data = data;
    }

}
