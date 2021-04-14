package com.ltnet.shoprocketmqconsumerdemo.entity;

public class MQMessage {
    private String id;
    private String version;
    private Object data;
    private int status;
    private long time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "MQMessage{" +
                "id='" + id + '\'' +
                ", version='" + version + '\'' +
                ", data=" + data +
                ", status=" + status +
                ", time='" + time + '\'' +
                '}';
    }
}
