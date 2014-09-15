package com.ocyd.appfactory.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 广告表
 */
@Entity
@Table(name = "ad_list")
public class TAdvert implements Serializable {
    public final static int STATUS_NORMAL = 1;
    public final static int STATUS_DELETED = -1;
    public final static int STATUS_LOCKED = -2;

    public static final int TYPE_SUPPER_ADMINISTRATOR = 0;
    public static final int TYPE_APP_ADMINISTRATOR = 1;
    public static final int TYPE_TERMINAL_USER = 2;

    private static final long serialVersionUID = 1L;

    private int id;  // 此处使用id字段，是因为前端js中有些地方固定使用id作标识。
    private String shopId;
    private String icon;
    private String name;
    private int status;
    private String lastUpdate;
    private String webLink;
//    private String observe1;
//    private String observe2;
//    private String observe3;
//    private String observe4;
//    private String observe5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="ad_id",unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "shop_id")
    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    @Column(name = "ad_icon", length = 100)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name = "ad_name", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ad_status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "ad_lastupdate")
    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String updateTime) {
        this.lastUpdate = updateTime;
    }

    @Column(name = "ad_weburl", length = 200)
    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

//    @Column(name = "article_observe1", length = 200)
//    public String getObserve1() {
//        return observe1;
//    }
//
//    public void setObserve1(String observe1) {
//        this.observe1 = observe1;
//    }
//
//    @Column(name = "article_observe2", length = 200)
//    public String getObserve2() {
//        return observe2;
//    }
//
//    public void setObserve2(String observe2) {
//        this.observe2 = observe2;
//    }
//
//    @Column(name = "article_observe3", length = 200)
//    public String getObserve3() {
//        return observe3;
//    }
//
//    public void setObserve3(String observe3) {
//        this.observe3 = observe3;
//    }
//
//    @Column(name = "article_observe4", length = 200)
//    public String getObserve4() {
//        return observe4;
//    }
//
//    public void setObserve4(String observe4) {
//        this.observe4 = observe4;
//    }
//
//    @Column(name = "article_observe5", length = 200)
//    public String getObserve5() {
//        return observe5;
//    }
//
//    public void setObserve5(String observe5) {
//        this.observe5 = observe5;
//    }
}