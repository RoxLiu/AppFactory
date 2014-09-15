package com.ocyd.appfactory.pojo;

import javax.persistence.*;

import java.io.Serializable;

/**
 * 用户表
 */
@Entity
@Table(name = "user_account")
public class TUser implements Serializable {
    public final static int STATUS_NORMAL = 1;
    public final static int STATUS_DELETED = -1;
    public final static int STATUS_LOCKED = -2;

    public static final int TYPE_SUPPER_ADMINISTRATOR = 0;
    public static final int TYPE_APP_ADMINISTRATOR = 1;
    public static final int TYPE_TERMINAL_USER = 2;

    private static final long serialVersionUID = 1L;

    private int id;  // 此处使用id字段，是因为前端js中有些地方固定使用id作标识。
    private int shopId;
    private String accountName;
    private String phone;
    private String email;// 邮箱
    private String password;
    private String sendCode;
    private int status;
    private int type; // 用户类型  1：普通用户，2：商家用户，3:系统管理员
    private String createTime;
    private String online;
    private String loginTime;
    private String offTime;
    private String observe1;
    private String observe2;
    private String observe3;
    private String observe4;
    private String observe5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="user_account_id",unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "user_shopid")
    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    @Column(name = "user_account_name", length = 50, nullable = false)
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Column(name = "user_phone", length = 20)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "user_email", length = 50)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "user_password", length = 50)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "user_sendcode", length = 50)
    public String getSendCode() {
        return sendCode;
    }

    public void setSendCode(String sendCode) {
        this.sendCode = sendCode;
    }

    @Column(name = "user_status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "user_type", length = 20)
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Column(name = "user_createtime")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Column(name = "user_online", length = 20)
    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    @Column(name = "user_logintime")
    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    @Column(name = "user_offtime")
    public String getOffTime() {
        return offTime;
    }

    public void setOffTime(String offTime) {
        this.offTime = offTime;
    }

    @Column(name = "user_observe1", length = 100)
    public String getObserve1() {
        return observe1;
    }

    public void setObserve1(String observe1) {
        this.observe1 = observe1;
    }

    @Column(name = "user_observe2", length = 100)
    public String getObserve2() {
        return observe2;
    }

    public void setObserve2(String observe2) {
        this.observe2 = observe2;
    }

    @Column(name = "user_observe3", length = 100)
    public String getObserve3() {
        return observe3;
    }

    public void setObserve3(String observe3) {
        this.observe3 = observe3;
    }

    @Column(name = "user_observe4", length = 200)
    public String getObserve4() {
        return observe4;
    }

    public void setObserve4(String observe4) {
        this.observe4 = observe4;
    }

    @Column(name = "user_observe5", length = 300)
    public String getObserve5() {
        return observe5;
    }

    public void setObserve5(String observe5) {
        this.observe5 = observe5;
    }
}