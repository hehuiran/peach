package me.jessyan.peach.shop.help;

import me.jessyan.peach.shop.utils.StringUtils;

/**
 * author: Create by HuiRan on 2018/12/31 下午9:58
 * email: 15260828327@163.com
 * description:
 */
public final class UserGradeHelper {

    private UserGradeHelper() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static String getUserGradeNameByLevel(String level) {
        return getUserGradeNameByLevel(StringUtils.parseInt(level));
    }

    public static String getUserGradeNameByLevel(int level) {
        String name;
        switch (level) {
            case 1:
                name = "普通会员";
                break;
            case 2:
                name = "超级粉丝";
                break;
            case 3:
                name = "个体服务商";
                break;
            case 4:
                name = "区县级服务商";
                break;
            case 5:
                name = "市级服务商";
                break;
            case 6:
                name = "省级服务商";
                break;
            case 98:
                name = "直接粉丝";
                break;
            case 99:
                name = "间接粉丝";
                break;
            default:
                name = "unknown";
                break;
        }
        return name;
    }
}
