package com.ltnet.elasticsearchdemo.service.impl;

import com.ltnet.elasticsearchdemo.dao.StockDateKDao;
import com.ltnet.elasticsearchdemo.entity.ESStockDateK;
import com.ltnet.elasticsearchdemo.entity.ESStockInfo;
import com.ltnet.elasticsearchdemo.repository.ESStockDateKRepository;
import com.ltnet.elasticsearchdemo.repository.ESStockRepository;
import com.ltnet.elasticsearchdemo.service.ESStockDateKService;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.suggest.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class ESStockDateKServiceImpl implements ESStockDateKService {
    @Autowired
    StockDateKDao stockDateKDao;

    @Autowired
    ESStockDateKRepository esStockDateKRepository;

    @Autowired
    ESStockRepository esStockRepository;

    @Override
    public int importAllFromMysql() {
        Map<String, Object> page = new HashMap<>();
        int ret = 0;
        int count = 0;

        int start = getMaxStockDateKId();
        if (start < 0) {
            return 0;
        }

        int size = 10000;
        page.put("start", start);
        page.put("size", size);
        List<ESStockDateK> listStockDateK = null;

        while ((listStockDateK = stockDateKDao.getStockDateKList(page)) != null && listStockDateK.size() > 0) {
            count = 0;
            start += size;
            page.put("start", start);

            Iterable<ESStockDateK> esStockDateKIterable = esStockDateKRepository.saveAll(listStockDateK);

            for (ESStockDateK esStockDateK : esStockDateKIterable) {
                count++;
            }
            ret += count;
            System.out.println("=== 导入数据 === \n 查询列表总数：" + listStockDateK.size() + " 导入总数：" + count);
        }

        System.out.println("=== 完成导入数据 === \n 导入总数：" + ret);
        return ret;
    }

    @Override
    public int getMaxStockDateKId() {
        int maxId = -1;

        try {
            ESStockDateK data = esStockDateKRepository.findFirstByOrderByIdDesc();
            maxId = data.getId();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ElasticsearchStatusException statusException = (ElasticsearchStatusException) e.getCause();
            // 该索引还没有数据
            if (statusException.status() == RestStatus.BAD_REQUEST) {
                maxId = 0;
            }
        }
        return maxId;
    }

    @Override
    public List<ESStockDateK> getStockDateKList(String code) {
        Long startTime = System.currentTimeMillis();
        List<ESStockDateK> list = esStockDateKRepository.findESStockDateKSByCodeOrderByDateDesc(code);
        Long used = System.currentTimeMillis() - startTime;
        System.out.println("=== 查询用时： " + used + "ms");
        return list;
    }

    @Override
    public List<ESStockDateK> getStockDateKListByCode(String code) {
        return stockDateKDao.getStockDateKListByCode(code);
    }

}
