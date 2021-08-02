package com.ltnet.elasticsearchdemo.entity;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "test_index", shards = 2)
public final class ESEntity<T> {
    private String id;
    private T data;

    public ESEntity(String id, T data) {
        this.id = id;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
