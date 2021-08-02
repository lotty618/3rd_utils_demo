package com.ltnet.elasticsearchdemo.utils;

import com.alibaba.fastjson.JSON;
import com.ltnet.elasticsearchdemo.common.SysConst;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.Metadata;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.List;

@Component
public class ESUtils {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * Create Index
     * @param name              index name
     * @return
     * @throws IOException
     */
    public boolean createIndex(String name) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(name);
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        return response.isAcknowledged();
    }

    /**
     * Check the index if exist
     * @param name              index name
     * @return
     * @throws IOException
     */
    public boolean checkIndexExist(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    public void createDocument(String index, Object obj) throws IOException {
        IndexRequest request = new IndexRequest(index);
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.source(JSON.toJSON(obj), XContentType.JSON);
        // 客户端发送请求
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
        System.out.println(response.status());
    }

    public String getDocument(String index) throws IOException {
        GetRequest request = new GetRequest(index, "1");
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        return response.toString();
    }

    public List<String> matchAllQuery(String index, int from, int size, String sortField, SysConst.SortOrder order) throws IOException {
        List<String> lstRet = new ArrayList<>();

        // 构建查询条件
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        // 创建查询源构造器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchAllQueryBuilder);
        // 设置分页
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);
        // 设置排序
        if (StringUtils.isNotEmpty(sortField)) {
            searchSourceBuilder.sort(sortField, order == SysConst.SortOrder.ASC ? SortOrder.ASC : SortOrder.DESC);
        }
        // 创建查询请求对象，将查询对象配置到其中
        SearchRequest request = new SearchRequest(index);
        request.source(searchSourceBuilder);

        // 执行查询，然后处理响应结果
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        // 根据状态和数据条数验证是否返回了数据
        if (RestStatus.OK.equals(response.status()) && response.getHits().getTotalHits().value > 0) {
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits) {
                lstRet.add(hit.getSourceAsString());
            }
        }

        return lstRet;
    }
}
