package me.jessyan.peach.shop.entity.goods;

import java.util.List;

/**
 * author: Created by HuiRan on 2018/1/19 22:32
 * E-Mail: 15260828327@163.com
 * description:
 */

public class TbDetailsImageBean {


    private String api;
    private DataBean data;
    private String v;
    private List<String> ret;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public List<String> getRet() {
        return ret;
    }

    public void setRet(List<String> ret) {
        this.ret = ret;
    }

    public static class DataBean {

        private String ID;
        private String key;
        private ParamsBean params;
        private String putID;
        private String type;
        private List<ChildrenBeanX> children;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public ParamsBean getParams() {
            return params;
        }

        public void setParams(ParamsBean params) {
            this.params = params;
        }

        public String getPutID() {
            return putID;
        }

        public void setPutID(String putID) {
            this.putID = putID;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<ChildrenBeanX> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBeanX> children) {
            this.children = children;
        }

        public static class ParamsBean {

            private String shenbiJsonTfsUrl;
            private String requestMap;

            public String getShenbiJsonTfsUrl() {
                return shenbiJsonTfsUrl;
            }

            public void setShenbiJsonTfsUrl(String shenbiJsonTfsUrl) {
                this.shenbiJsonTfsUrl = shenbiJsonTfsUrl;
            }

            public String getRequestMap() {
                return requestMap;
            }

            public void setRequestMap(String requestMap) {
                this.requestMap = requestMap;
            }
        }

        public static class ChildrenBeanX {

            private String ID;
            private String key;
            private ParamsBeanX params;
            private String putID;
            private String type;
            private List<ChildrenBean> children;

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public ParamsBeanX getParams() {
                return params;
            }

            public void setParams(ParamsBeanX params) {
                this.params = params;
            }

            public String getPutID() {
                return putID;
            }

            public void setPutID(String putID) {
                this.putID = putID;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<ChildrenBean> getChildren() {
                return children;
            }

            public void setChildren(List<ChildrenBean> children) {
                this.children = children;
            }

            public static class ParamsBeanX {

                private String childrenStyle;
                private String picUrl;
                private SizeBean size;

                public String getChildrenStyle() {
                    return childrenStyle;
                }

                public void setChildrenStyle(String childrenStyle) {
                    this.childrenStyle = childrenStyle;
                }

                public String getPicUrl() {
                    return picUrl;
                }

                public void setPicUrl(String picUrl) {
                    this.picUrl = picUrl;
                }

                public SizeBean getSize() {
                    return size;
                }

                public void setSize(SizeBean size) {
                    this.size = size;
                }

                public static class SizeBean {

                    private String width;
                    private String height;

                    public String getWidth() {
                        return width;
                    }

                    public void setWidth(String width) {
                        this.width = width;
                    }

                    public String getHeight() {
                        return height;
                    }

                    public void setHeight(String height) {
                        this.height = height;
                    }
                }
            }

            public static class ChildrenBean {

                private String ID;
                private String key;
                private ParamsBeanXX params;
                private String putID;
                private String type;
                private List<ActionsBean> actions;

                public String getID() {
                    return ID;
                }

                public void setID(String ID) {
                    this.ID = ID;
                }

                public String getKey() {
                    return key;
                }

                public void setKey(String key) {
                    this.key = key;
                }

                public ParamsBeanXX getParams() {
                    return params;
                }

                public void setParams(ParamsBeanXX params) {
                    this.params = params;
                }

                public String getPutID() {
                    return putID;
                }

                public void setPutID(String putID) {
                    this.putID = putID;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public List<ActionsBean> getActions() {
                    return actions;
                }

                public void setActions(List<ActionsBean> actions) {
                    this.actions = actions;
                }

                public static class ParamsBeanXX {

                    private String style;
                    private PositionBean position;

                    public String getStyle() {
                        return style;
                    }

                    public void setStyle(String style) {
                        this.style = style;
                    }

                    public PositionBean getPosition() {
                        return position;
                    }

                    public void setPosition(PositionBean position) {
                        this.position = position;
                    }

                    public static class PositionBean {

                        private String endY;
                        private String endX;
                        private String startY;
                        private String startX;

                        public String getEndY() {
                            return endY;
                        }

                        public void setEndY(String endY) {
                            this.endY = endY;
                        }

                        public String getEndX() {
                            return endX;
                        }

                        public void setEndX(String endX) {
                            this.endX = endX;
                        }

                        public String getStartY() {
                            return startY;
                        }

                        public void setStartY(String startY) {
                            this.startY = startY;
                        }

                        public String getStartX() {
                            return startX;
                        }

                        public void setStartX(String startX) {
                            this.startX = startX;
                        }
                    }
                }

                public static class ActionsBean {

                    private String ID;
                    private String key;
                    private ParamsBeanXXX params;
                    private int putID;

                    public String getID() {
                        return ID;
                    }

                    public void setID(String ID) {
                        this.ID = ID;
                    }

                    public String getKey() {
                        return key;
                    }

                    public void setKey(String key) {
                        this.key = key;
                    }

                    public ParamsBeanXXX getParams() {
                        return params;
                    }

                    public void setParams(ParamsBeanXXX params) {
                        this.params = params;
                    }

                    public int getPutID() {
                        return putID;
                    }

                    public void setPutID(int putID) {
                        this.putID = putID;
                    }

                    public static class ParamsBeanXXX {

                        private TrackParamsBean trackParams;
                        private String trackNamePre;
                        private String trackName;
                        private String url;

                        public TrackParamsBean getTrackParams() {
                            return trackParams;
                        }

                        public void setTrackParams(TrackParamsBean trackParams) {
                            this.trackParams = trackParams;
                        }

                        public String getTrackNamePre() {
                            return trackNamePre;
                        }

                        public void setTrackNamePre(String trackNamePre) {
                            this.trackNamePre = trackNamePre;
                        }

                        public String getTrackName() {
                            return trackName;
                        }

                        public void setTrackName(String trackName) {
                            this.trackName = trackName;
                        }

                        public String getUrl() {
                            return url;
                        }

                        public void setUrl(String url) {
                            this.url = url;
                        }

                        public static class TrackParamsBean {

                            private String style;

                            public String getStyle() {
                                return style;
                            }

                            public void setStyle(String style) {
                                this.style = style;
                            }
                        }
                    }
                }
            }
        }
    }
}
