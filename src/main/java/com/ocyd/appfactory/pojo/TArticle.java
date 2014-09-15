package com.ocyd.appfactory.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 文章表
 */
@Entity
@Table(name = "article_list")
public class TArticle implements Serializable {
    public final static int STATUS_NORMAL = 1;
    public final static int STATUS_DELETED = -1;
    public final static int STATUS_LOCKED = -2;

    public static final int TYPE_SUPPER_ADMINISTRATOR = 0;
    public static final int TYPE_APP_ADMINISTRATOR = 1;
    public static final int TYPE_TERMINAL_USER = 2;

    private static final long serialVersionUID = 1L;

    private int id;  // 此处使用id字段，是因为前端js中有些地方固定使用id作标识。
    private String shopId;
    private int connectId;
    private int categoryId;
    private String icon;
    private String title;
    private String description;
    private String content;
    private String keyWords;
    private int status;
    private String createUserId;
    private String publishUserId;
    private String createTime;
    private String publishTime;
    private String lastUpdate;
    private String webLink;
    private String observe1;
    private String observe2;
    private String observe3;
    private String observe4;
    private String observe5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="article_id",unique = true, nullable = false)
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

    @Column(name = "article_connect_id")
    public int getConnectId() {
        return connectId;
    }

    public void setConnectId(int connectId) {
        this.connectId = connectId;
    }

    @Column(name = "article_cat_id")
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Column(name = "article_icon", length = 200)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name = "article_title", length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "article_description", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String email) {
        this.description = email;
    }

    @Column(name = "article_content", length = 2000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "article_keywords", length = 200)
    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    @Column(name = "article_status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "article_create_userid")
    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    @Column(name = "article_publish_userid")
    public String getPublishUserId() {
        return publishUserId;
    }

    public void setPublishUserId(String publishUserId) {
        this.publishUserId = publishUserId;
    }

    @Column(name = "article_create_time")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Column(name = "article_publish_time")
    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    @Column(name = "article_lastupdate")
    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String updateTime) {
        this.lastUpdate = updateTime;
    }

    @Column(name = "article_web_url", length = 200)
    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    @Column(name = "article_observe1", length = 200)
    public String getObserve1() {
        return observe1;
    }

    public void setObserve1(String observe1) {
        this.observe1 = observe1;
    }

    @Column(name = "article_observe2", length = 200)
    public String getObserve2() {
        return observe2;
    }

    public void setObserve2(String observe2) {
        this.observe2 = observe2;
    }

    @Column(name = "article_observe3", length = 200)
    public String getObserve3() {
        return observe3;
    }

    public void setObserve3(String observe3) {
        this.observe3 = observe3;
    }

    @Column(name = "article_observe4", length = 200)
    public String getObserve4() {
        return observe4;
    }

    public void setObserve4(String observe4) {
        this.observe4 = observe4;
    }

    @Column(name = "article_observe5", length = 200)
    public String getObserve5() {
        return observe5;
    }

    public void setObserve5(String observe5) {
        this.observe5 = observe5;
    }
}