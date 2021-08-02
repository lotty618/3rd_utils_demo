package com.ltnet.elasticsearchdemo.repository;

import com.ltnet.elasticsearchdemo.entity.ESStockDateK;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Optional;

public interface ESStockDateKRepository extends ElasticsearchRepository<ESStockDateK, String> {
    ESStockDateK findFirstByOrderByIdDesc();
    List<ESStockDateK> findESStockDateKSByCodeOrderByDateDesc(String code);
}
