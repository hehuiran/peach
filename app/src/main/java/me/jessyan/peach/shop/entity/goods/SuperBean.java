package me.jessyan.peach.shop.entity.goods;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by yuwenchao on 2018/11/8.
 */

public class SuperBean implements MultiItemEntity {


    //是否没有数据
    private boolean noData;

    public void setNoData(boolean noData) {
        this.noData = noData;
    }

    @Override
    public int getItemType() {
        return 0;
        /*return  noData?SuperListAdapter.NOT_DATA:child!=null&&child.size()>0
                &&child.get(0)!=null&&child.get(0).getChild()!=null
                &&child.get(0).getChild().size()>0? SuperListAdapter.ITEM_TWO:
                SuperListAdapter.ITEM_ONE;*/
    }
    /**
     * title : å¤©çŒ«æ·˜å®å®˜æ–¹
     * child : [{"id":2,"icon":"https://hzcangyu.com/BTN_SUPER_NZ@2x.png","title":"å¤©çŒ«å¥³è£\u2026","desc":"å¹³å\u009d\u2021è¿\u201dåˆ©5%","link":"https://s.click.taobao.com/t?e=m%3D2%26s%3D%2BTOHHFmhLcgcQipKwQzePCperVdZeJviK7Vc7tFgwiFRAdhuF14FMWwR7bI%2FErz2xq3IhSJN6GSFDcmJ6sM8taHVl%2FEpIVZdCkUYCsoI6Zv60%2B%2BBfSW5iZ3PLI70IiRVfkwHTg4TQaeOJ2XZVrYPybh9YVht%2FlWEjPobR%2B9Wl%2BrZWChgySy8t%2FJX60D4hjctJPwiig1bxLPGDF1NzTQoPw%3D%3D","child":[]},{"id":3,"icon":"https://hzcangyu.com/BTN_SUPER_MY@2x.png","title":"å¤©çŒ«æ¯\u008då©´","desc":"å¹³å\u009d\u2021è¿\u201dåˆ©5%","child":[]},{"id":4,"icon":"https://hzcangyu.com/BTN_SUPER_MEIZHUANG@2x.png","title":"å¤©çŒ«ç¾Žè£\u2026","desc":"å¹³å\u009d\u2021è¿\u201dåˆ©5%","child":[]},{"id":5,"icon":"https://hzcangyu.com/BTN_SUPER_GUOJI@2x.png","title":"å¤©çŒ«å\u203a½é™\u2026","desc":"å¹³å\u009d\u2021è¿\u201dåˆ©5%","link":"https://s.click.taobao.com/t?e=m%3D2%26s%3DbxVex0ictVccQipKwQzePCperVdZeJviK7Vc7tFgwiFRAdhuF14FMXUmSuYYNhi9lovu%2FCElQOuFDcmJ6sM8taHVl%2FEpIVZdCkUYCsoI6Zv60%2B%2BBfSW5iZ3PLI70IiRVfkwHTg4TQaeOJ2XZVrYPybh9YVht%2FlWEjPobR%2B9Wl%2BobZtumn06mYREMeoFd7VavDSlIgwOUCuE%3D","child":[]},{"id":6,"icon":"https://hzcangyu.com/BTN_SUPER_BAIHUO@2x.png","title":"å¤©çŒ«ç™¾è´§","desc":"å¹³å\u009d\u2021è¿\u201dåˆ©5%","child":[]},{"id":7,"icon":"https://hzcangyu.com/BTN_SUPER_SHENGXIAN@2x.png","title":"å¤©çŒ«ç\u201dŸé²œ","desc":"å¹³å\u009d\u2021è¿\u201dåˆ©5%","link":"https://s.click.taobao.com/t?e=m%3D2%26s%3D%2FuTifD3qT00cQipKwQzePCperVdZeJviK7Vc7tFgwiFRAdhuF14FMb6nt%2FFqTaOPt4hWD5k2kjOFDcmJ6sM8taHVl%2FEpIVZdCkUYCsoI6Zv60%2B%2BBfSW5iZ3PLI70IiRVfkwHTg4TQaeOJ2XZVrYPybh9YVht%2FlWEjPobR%2B9Wl%2BoDZZ8w6LKUEUtRIy3Y8lGD8HTPyqWu%2F6NwnwUIa4QiSBhgUpsznC0eGUCDbD2uUlbGDF1NzTQoPw%3D%3D","child":[]},{"id":8,"icon":"https://hzcangyu.com/BTN_SUPER_YIYAO@2x.png","title":"å¤©çŒ«åŒ»è\u008d¯","desc":"å¹³å\u009d\u2021è¿\u201dåˆ©5%","child":[]},{"id":9,"title":"æ·˜å®\u009då¿ƒé\u20ac\u2030","desc":"å¹³å\u009d\u2021è¿\u201dåˆ©5%","child":[]}]
     */

    private String title;
    private List<ChildBean> child;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    public static class ChildBean implements MultiItemEntity {
        /**
         * id : 2
         * icon : https://hzcangyu.com/BTN_SUPER_NZ@2x.png
         * title : å¤©çŒ«å¥³è£…
         * desc : å¹³å‡è¿”åˆ©5%
         * link : https://s.click.taobao.com/t?e=m%3D2%26s%3D%2BTOHHFmhLcgcQipKwQzePCperVdZeJviK7Vc7tFgwiFRAdhuF14FMWwR7bI%2FErz2xq3IhSJN6GSFDcmJ6sM8taHVl%2FEpIVZdCkUYCsoI6Zv60%2B%2BBfSW5iZ3PLI70IiRVfkwHTg4TQaeOJ2XZVrYPybh9YVht%2FlWEjPobR%2B9Wl%2BrZWChgySy8t%2FJX60D4hjctJPwiig1bxLPGDF1NzTQoPw%3D%3D
         * child : []
         */

        private int id;
        private String icon;
        private String title;
        private String desc;
        private String link;
        private List<ChildTwoBean> child;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public List<ChildTwoBean> getChild() {
            return child;
        }

        public void setChild(List<ChildTwoBean> child) {
            this.child = child;
        }

        @Override
        public int getItemType() {
            return 0;
        }


        public class ChildTwoBean implements MultiItemEntity {
            private String id;

            private String title;

            private String icon;


            private String desc;

            private String link;


            public String getcoid() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }


            @Override
            public int getItemType() {
                return 0;
            }
        }

    }

    @Override
    public String toString() {
        return "SuperBean{" +
                "noData=" + noData +
                ", title='" + title + '\'' +
                ", child=" + child +
                '}';
    }
}
