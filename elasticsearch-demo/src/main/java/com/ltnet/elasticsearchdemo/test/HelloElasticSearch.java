package com.ltnet.elasticsearchdemo.test;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HelloElasticSearch {
    @Resource
    private RestHighLevelClient restHighLevelClient;

    public void add() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", "20210305");
        map.put("name", "test");

        try {
            IndexRequest indexRequest = new IndexRequest("content", "doc", map.get("id").toString()).source(map);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
