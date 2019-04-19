package com.huojumu.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class NativeOrders {

    @Id
    private long id;
    @NotNull
    private String orderJson;

    @Generated(hash = 534052346)
    public NativeOrders(long id, @NotNull String orderJson) {
        this.id = id;
        this.orderJson = orderJson;
    }

    @Generated(hash = 141707955)
    public NativeOrders() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderJson() {
        return orderJson;
    }

    public void setOrderJson(String orderJson) {
        this.orderJson = orderJson;
    }
}
