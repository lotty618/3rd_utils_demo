package com.ltnet.elasticsearchdemo.dao;

import com.ltnet.elasticsearchdemo.entity.ESStockDateK;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

//@Mapper
public interface StockDateKDao {
    List<ESStockDateK> getStockDateKList(Map<String, Object> page);
    List<ESStockDateK> getStockDateKListByCode(String code);
}
