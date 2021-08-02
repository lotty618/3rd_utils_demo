package com.ltnet.elasticsearchdemo.repository;

import com.ltnet.elasticsearchdemo.entity.ESStockInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ESStockRepository extends ElasticsearchRepository<ESStockInfo, String> {

}
