package com.ltnet.elasticsearchdemo.service;

import com.ltnet.elasticsearchdemo.entity.ESStockDateK;

import java.util.List;

public interface ESStockDateKService {
    int importAllFromMysql();
    int getMaxStockDateKId();
    List<ESStockDateK> getStockDateKList(String code);
    List<ESStockDateK> getStockDateKListByCode(String code);
}
