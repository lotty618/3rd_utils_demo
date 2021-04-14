package com.ltnet.shopdemo.entity;

public class Order {
    private Integer id;
    private Integer pid;
    private String name;
    private Integer status;
    private Integer count;
    private String addtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", pid=" + pid +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", count=" + count +
                ", addtime='" + addtime + '\'' +
                '}';
    }
}
