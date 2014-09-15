package com.ocyd.appfactory.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * ID抽象类
 * Created by Rox on 2014/9/2.
 */
@MappedSuperclass
public abstract class IdEntity {
    private int id;  // 此处使用id字段，是因为前端js中有些地方固定使用id作标识。

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="user_account_id",unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
