package me.jessyan.peach.shop.entity.search;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

import me.jessyan.peach.shop.greendao.DaoSession;
import me.jessyan.peach.shop.greendao.SearchRecordBeanDao;
import me.jessyan.peach.shop.greendao.UserSearchRecordBeanDao;

/**
 * author: Create by HuiRan on 2018/12/20 下午11:43
 * email: 15260828327@163.com
 * description:
 */
@Entity
public class UserSearchRecordBean {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "mobile")
    private String mobile;
    @ToMany(referencedJoinProperty = "searchId")//指定与之关联的其他类的id
    private List<SearchRecordBean> searchList;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1847134735)
    private transient UserSearchRecordBeanDao myDao;
    @Generated(hash = 2007175355)
    public UserSearchRecordBean(Long id, String mobile) {
        this.id = id;
        this.mobile = mobile;
    }
    @Generated(hash = 1394173666)
    public UserSearchRecordBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMobile() {
        return this.mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 200062773)
    public List<SearchRecordBean> getSearchList() {
        if (searchList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SearchRecordBeanDao targetDao = daoSession.getSearchRecordBeanDao();
            List<SearchRecordBean> searchListNew = targetDao
                    ._queryUserSearchRecordBean_SearchList(id);
            synchronized (this) {
                if (searchList == null) {
                    searchList = searchListNew;
                }
            }
        }
        return searchList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 998086079)
    public synchronized void resetSearchList() {
        searchList = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 985688424)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserSearchRecordBeanDao() : null;
    }
}
