package me.jessyan.peach.shop.greendao;

import android.database.sqlite.SQLiteDatabase;

import com.blankj.utilcode.util.Utils;

/**
 * author: Created by HuiRan on 2017/12/9 11:08
 * E-Mail: 15260828327@163.com
 * description:
 */

public class GreenDaoManager {
    // 是否加密
    public static final boolean ENCRYPTED = true;

    private static final String DB_NAME = "searchRecord.db";
    private static GreenDaoManager sGreenDaoManager;
    private static DaoMaster.DevOpenHelper sDevOpenHelper;
    private static DaoMaster sDaoMaster;
    private static DaoSession sDaoSession;

    private GreenDaoManager() {
        // 初始化数据库信息
        sDevOpenHelper = new DaoMaster.DevOpenHelper(Utils.getApp(), DB_NAME);
        getDaoMaster();
        getDaoSession();
    }

    public static GreenDaoManager getInstance() {
        if (null == sGreenDaoManager) {
            synchronized (GreenDaoManager.class) {
                if (null == sGreenDaoManager) {
                    sGreenDaoManager = new GreenDaoManager();
                }
            }
        }
        return sGreenDaoManager;
    }

    /**
     * 获取可读数据库
     */
    public static SQLiteDatabase getReadableDatabase() {
        if (null == sDevOpenHelper) {
            getInstance();
        }
        return sDevOpenHelper.getReadableDatabase();
    }

    /**
     * 获取可写数据库
     */
    public static SQLiteDatabase getWritableDatabase() {
        if (null == sDevOpenHelper) {
            getInstance();
        }
        return sDevOpenHelper.getWritableDatabase();
    }

    /**
     * 获取DaoMaster
     */
    public static DaoMaster getDaoMaster() {
        if (null == sDaoMaster) {
            synchronized (GreenDaoManager.class) {
                if (null == sDaoMaster) {
                    sDaoMaster = new DaoMaster(getWritableDatabase());
                }
            }
        }
        return sDaoMaster;
    }

    /**
     * 获取DaoSession
     */
    public static DaoSession getDaoSession() {
        if (null == sDaoSession) {
            synchronized (GreenDaoManager.class) {
                sDaoSession = getDaoMaster().newSession();
            }
        }
        return sDaoSession;
    }

    public static UserSearchRecordBeanDao getUserSearchRecordBeanDao() {
        return getDaoSession().getUserSearchRecordBeanDao();
    }

    public static SearchRecordBeanDao getSearchRecordBeanDao() {
        return getDaoSession().getSearchRecordBeanDao();
    }

}
