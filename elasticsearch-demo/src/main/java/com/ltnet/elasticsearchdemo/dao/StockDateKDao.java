package com.ltnet.elasticsearchdemo.dao;

import com.ltnet.elasticsearchdemo.entity.StockDateK;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

public interface StockDateKDao {
    List<StockDateK> getStockDateKList();
    List<StockDateK> getStockDateKListByCode(String code);
}
