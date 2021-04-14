package com.ltnet.elasticsearchdemo.service.impl;

import com.ltnet.elasticsearchdemo.service.ESIndexService;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ESIndexServiceImpl implements ESIndexService {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    public boolean checkIndex(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest();
        request.humanReadable(true);
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }
}
