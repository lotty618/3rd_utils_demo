package com.ltnet.elasticsearchdemo.service;

import com.ltnet.elasticsearchdemo.entity.StockDateK;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StockDateKService {
    List<StockDateK> getStockDateKList();
    List<StockDateK> getStockDateKListByCode(String code);
}
