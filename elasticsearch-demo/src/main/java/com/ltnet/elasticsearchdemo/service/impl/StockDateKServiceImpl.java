package com.ltnet.elasticsearchdemo.service.impl;

import com.ltnet.elasticsearchdemo.dao.StockDateKDao;
import com.ltnet.elasticsearchdemo.entity.StockDateK;
import com.ltnet.elasticsearchdemo.service.StockDateKService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockDateKServiceImpl implements StockDateKService {
    @Autowired
    StockDateKDao stockDateKDao;

    @Override
    public List<StockDateK> getStockDateKList() {
        return stockDateKDao.getStockDateKList();
    }

    @Override
    public List<StockDateK> getStockDateKListByCode(String code) {
        return stockDateKDao.getStockDateKListByCode(code);
    }

}
