package me.jessyan.peach.shop.entity.search;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author: Create by HuiRan on 2018/12/20 下午11:43
 * email: 15260828327@163.com
 * description:
 */
@Entity
public class SearchRecordBean {

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "millis")
    private Long millis;
    @Property(nameInDb = "value")
    private String value;
    @Property(nameInDb = "type")
    private Integer type;
    private Long searchId;
    @Generated(hash = 587804327)
    public SearchRecordBean(Long id, Long millis, String value, Integer type,
            Long searchId) {
        this.id = id;
        this.millis = millis;
        this.value = value;
        this.type = type;
        this.searchId = searchId;
    }
    @Generated(hash = 1260263942)
    public SearchRecordBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getMillis() {
        return this.millis;
    }
    public void setMillis(Long millis) {
        this.millis = millis;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public Integer getType() {
        return this.type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    public Long getSearchId() {
        return this.searchId;
    }
    public void setSearchId(Long searchId) {
        this.searchId = searchId;
    }
}
