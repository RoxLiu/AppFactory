package com.ocyd.appfactory.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户表
 */
@Entity
@Table(name = "shop_module")
public class TShopModule implements Serializable {
    public final static int STATUS_NORMAL = 1;
    public final static int STATUS_DELETED = -1;
    private static final long serialVersionUID = 1L;

    private int id;  // 此处使用id字段，是因为前端js中有些地方固定使用id作标识。
    private String shopId;
    private String moduleId;
    private String moduleName;
    private String createTime;
    private String lastUpdate;
    private int status;//正常：1, 删除:-1
    private String description;
    private String observe1;
    private String observe2;
    private String observe3;
    private String observe4;
    private String observe5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="shop_module_id",unique = true, nullable = false)
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

    @Column(name = "shop_module_list_id")
    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    @Column(name = "shop_module_list_name", length = 20)
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Column(name = "shop_module_createtime")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Column(name = "shop_module_lastupdate", length = 50)
    public String getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(String updateTime) {
        this.lastUpdate = updateTime;
    }

    @Column(name = "shop_module_status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Column(name = "shop_module_desc", length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "shop_module_observe1", length = 100)
    public String getObserve1() {
        return observe1;
    }

    public void setObserve1(String observe1) {
        this.observe1 = observe1;
    }

    @Column(name = "shop_module_observe2", length = 100)
    public String getObserve2() {
        return observe2;
    }

    public void setObserve2(String observe2) {
        this.observe2 = observe2;
    }

    @Column(name = "shop_module_observe3", length = 100)
    public String getObserve3() {
        return observe3;
    }

    public void setObserve3(String observe3) {
        this.observe3 = observe3;
    }

    @Column(name = "shop_module_observe4", length = 200)
    public String getObserve4() {
        return observe4;
    }

    public void setObserve4(String observe4) {
        this.observe4 = observe4;
    }

    @Column(name = "shop_module_observe5", length = 300)
    public String getObserve5() {
        return observe5;
    }

    public void setObserve5(String observe5) {
        this.observe5 = observe5;
    }
}