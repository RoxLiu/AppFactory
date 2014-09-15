package com.ocyd.appfactory.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户表
 */
@Entity
@Table(name = "production_list")
public class TProduct implements Serializable {
    public final static int STATUS_NORMAL = 1;
    public final static int STATUS_DELETED = -1;
    public final static int STATUS_LOCKED = -2;

    public static final int TYPE_SUPPER_ADMINISTRATOR = 0;
    public static final int TYPE_APP_ADMINISTRATOR = 1;
    public static final int TYPE_TERMINAL_USER = 2;

    private static final long serialVersionUID = 1L;

    private int id;  // 此处使用id字段，是因为前端js中有些地方固定使用id作标识。
    private int shopAccountId;
    private int connectId;
//    private int categoryId;
    private String name;
    private String brandId;
    private String color;
    private String size;
    private String sexual;
    private int status;// 正常状态：1，删除：-1，热卖：2，新品：8，预售：16，售罄：32，暂时只会用到正常状态和删除状态两种，不可为空
    private String picture;
    private String icon;
    private String normalPrice;
    private String nowPrice;
    private String brief;
    private String description;
    private String storeAmount;
    private String salesVolume;
    private String secondTitle;
    private String sn;
    private String startDiscountTime;
    private String endDiscountTime;
    private String addTime;
    private String lastUpdateTime;
    private String orderable;
    private String webLink;
    private String observe1;
    private String observe2;
    private String observe3;
    private String observe4;
    private String observe5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="production_id",unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "production_shop_account_id")
    public int getShopAccountId() {
        return shopAccountId;
    }

    public void setShopAccountId(int shopId) {
        this.shopAccountId = shopId;
    }

    @Column(name = "production_connectid")
    public int getConnectId() {
        return connectId;
    }

    public void setConnectId(int connectId) {
        this.connectId = connectId;
    }

//    @Column(name = "shop_account_id")
//    public int getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(int categoryId) {
//        this.categoryId = categoryId;
//    }

    @Column(name = "production_name", length = 50)
    public String getName() {
        return name;
    }

    public void setName(String phone) {
        this.name = phone;
    }

    @Column(name = "production_brandid", length = 200)
    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    @Column(name = "production_color", length = 100)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "production_size", length = 100)
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Column(name = "production_sex", length = 10)
    public String getSexual() {
        return sexual;
    }

    public void setSexual(String sex) {
        this.sexual = sex;
    }

    @Column(name = "production_status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "production_picture", length = 1000)
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Column(name = "production_icon", length = 100)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name = "production_notmal_price", length = 12)
    public String getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice;
    }

    @Column(name = "production_now_price", length = 12)
    public String getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(String nowPrice) {
        this.nowPrice = nowPrice;
    }

    @Column(name = "production_brief", length = 100)
    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    @Column(name = "production_description", length = 1000)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String email) {
        this.description = email;
    }

    @Column(name = "production_storeamount", length = 20)
    public String getStoreAmount() {
        return storeAmount;
    }

    public void setStoreAmount(String storeAmount) {
        this.storeAmount = storeAmount;
    }

    @Column(name = "production_salesvolume", length = 20)
    public String getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(String salesVolume) {
        this.salesVolume = salesVolume;
    }

    @Column(name = "production_secondtitle", length = 100)
    public String getSecondTitle() {
        return secondTitle;
    }

    public void setSecondTitle(String secondTitle) {
        this.secondTitle = secondTitle;
    }

    @Column(name = "production_sn", length = 100)
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Column(name = "production_starttodiscount")
    public String getStartDiscountTime() {
        return startDiscountTime;
    }

    public void setStartDiscountTime(String startDiscountTime) {
        this.startDiscountTime = startDiscountTime;
    }

    @Column(name = "production_timetodiscount")
    public String getEndDiscountTime() {
        return endDiscountTime;
    }

    public void setEndDiscountTime(String endDiscountTime) {
        this.endDiscountTime = endDiscountTime;
    }

    @Column(name = "production_addtime")
    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    @Column(name = "production_lastupdate")
    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Column(name = "production_tobeordered", length = 20)
    public String getOrderable() {
        return orderable;
    }

    public void setOrderable(String orderable) {
        this.orderable = orderable;
    }

    @Column(name = "production_webLink", length = 100)
    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    @Column(name = "production_observe1", length = 100)
    public String getObserve1() {
        return observe1;
    }

    public void setObserve1(String observe1) {
        this.observe1 = observe1;
    }

    @Column(name = "production_observe2", length = 100)
    public String getObserve2() {
        return observe2;
    }

    public void setObserve2(String observe2) {
        this.observe2 = observe2;
    }

    @Column(name = "production_observe3", length = 100)
    public String getObserve3() {
        return observe3;
    }

    public void setObserve3(String observe3) {
        this.observe3 = observe3;
    }

    @Column(name = "production_observe4", length = 200)
    public String getObserve4() {
        return observe4;
    }

    public void setObserve4(String observe4) {
        this.observe4 = observe4;
    }

    @Column(name = "production_observe5", length = 300)
    public String getObserve5() {
        return observe5;
    }

    public void setObserve5(String observe5) {
        this.observe5 = observe5;
    }
}