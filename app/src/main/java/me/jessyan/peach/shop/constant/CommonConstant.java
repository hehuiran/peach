package me.jessyan.peach.shop.constant;

/**
 * author: Create by HuiRan on 2018/12/8 下午9:49
 * email: 15260828327@163.com
 * description:
 */
public interface CommonConstant {

    String EMPTY_STRING = "";

    String HTTPS = "https:";

    String TAOKE_APP_KEY = "24712221";

    /**
     * 商户ID
     */
    String ALIPAY_UID = "2088221462381933";
    /**
     * 三段PID
     */
    String PID = "mm_52605298_40122705_180780979";
    /**
     * appID(蚂蚁金服)
     */
    String ALIPAY_APP_ID = "2017111609968179";
    /**
     * 支付宝网关
     */
    String ALIPAY_GATEWAY = "https://openapi.alipaydev.com/gateway.do";
    /**
     * 商户私钥
     */
    String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCFpCqvSvHXFVOoN3qZ6rX1hIdN36ZaUfm+TrArLAJDcB7uXExJzulBH6Q5gnjzhXuPjl2OK28dGVMxR+3dwPpXk1O2QW58giGMyRG+/sMcCnmjGO0B5iR5jGzaOfu8prHOYflJM9YoJvlGFtJRDG/g6Q824QPx1WYNIY5zZIKjsp/1Rkb5qcrSZtKRWR/k4aErgpBo5lZ2OLvcM2d+2EwZwPoRDUtAkpJZ+0GzFXBP6/JoNFfNxVS7xESvFotWDzwGh5QA+UkwRHp0L1zpj3PrmqaxaZBLfTpioGvjnCTVgyIuox2XW4pcjSQkLdbeOtW1tb9Whr2i5wjBAQskQVFHAgMBAAECggEAbNGEQEASNESVXf/uwK2xhD1pvytBVYIyjZedM0oefbMqPITDScZFgFW3b2yh+AjU18vgugb77wHUD+i18z9TmUPJgjo9MPz1PKHs5UykqoyNBxUgx88SHDcP25IjPTWuypJUXhrrYr55c2zZzE0IPGcJkHIguCY/dqnASI2rIob+/lQY5v7u7F+1u65lbTkZhGtzokMKBsIe/cQvG8vVIYYawcMsza6K4QbqlhEplgK+LyD7fJGb9/iYjv+OlJtfV48u3vOpJ5+TNzApFsZYgiiPXfVGCT/aBhSXhihxrwbsA4lP1AyKsz2SqaqvEENFcyUnxwG3lwLk6X2Lat4Y4QKBgQC/HWUaHW1Ew3Fx6rIUUoRcNKo2KtmE2gXqR+DjebXey728Jv1goAVvtH5K4oEMqWsSeViM7j3fd7FzhSMnIfjHa8D52/QxU0OKSZzSf9t9Rp8KxKQYn/tQM+dw/QX+CPdU/j5POD1Mc9v8PKxz5znql2zUc7v1ActRMTmCjbpaowKBgQCzA4AfAp157zAplOIG2mM5WP32bq5oXoZDWe8rlAVGRJNKxYRhmDn3uQh4QkGcFtCanQ9cIE5Ko786NMq92oEWvL9RXqusEChvQsDmXx0Gx6vfIbrWOJhK56JUU8qE3ajlhy3aG9IWOirEM7yZqJEsQNTn/NMKrJwXcnJoLQTdDQKBgQCdyIv+6oqa+e0ASu7pHlNOuOBKRkg9ia7WhK9jvslrfrRP0bThAFxcpBycgLJgnMLchriX6d3FUFF9+U5TcZWuNwUkg/bwfjjFxA6oL40ruZF213BPME3RInkS7KL037wrPJh+2QSqyQC3F/Sc4CsNxiTApihomCmfB8/ePFq9lwKBgHAeliVWBvmy9LBmfnaaW6E0esPZKMVXxrHyZxkdOXx8QOCI+1QA+vWL36Eqplsz8u2Tx2YFGeQn0QKMxBrK+yUZjJ5TNCStbhXZcmYXVl/DuYMkZhf8FG59y68TV/mFt14jH3XHDPs87UI7wTlgWaSk6nRuZmP+HjmVRdC3uZ8VAoGAWa2e5+UX8cBorJLzJ31SlNxaP5dzM7zmG+y1NOabMOTQrK0p6p6nForg9RAwAC5Db7YSSR6VYQYeN+TLnhg5eMBG5LfUiysasAm82zihjTjWqOuEIXFAFhtuK0/VjZoyjna1vEIF4Q3LpDTpfe21wVyZzO+NeEUQ6j00Jc1OB1Q=";

    int PAGE_SIZE_ORIENTATION = 10;
    int PAGE_INITIAL = 1;
    int PAGE_SIZE = 20;
    int DYNAMIC_PAGE_SIZE = 10;

    /**
     * 分类下面的二级分类数据
     */
    int TYPE_CATEGORY_SECOND_TEN = 0;       //只查询10条
    int TYPE_CATEGORY_SECOND_ALL = 1;       //查询全部子分类


    String SHARE_POSTER_BASE_URL ="https://hzcangyu.com/goods/goodsDetails.html?itemId=%s&invitercode=%s&userPid=%s";
}
