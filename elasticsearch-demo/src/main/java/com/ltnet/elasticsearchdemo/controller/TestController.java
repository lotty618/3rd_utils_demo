package com.ltnet.elasticsearchdemo.controller;

import com.ltnet.elasticsearchdemo.entity.StockDateK;
import com.ltnet.elasticsearchdemo.service.StockDateKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/k")
@RestController
public class TestController {
    @Autowired
    StockDateKService stockDateKService;

    @RequestMapping("/{code}")
    public String getDateKList(@PathVariable String code) {
        List<StockDateK> lstStockDateK = stockDateKService.getStockDateKListByCode(code);
        String ret = "";

        for (StockDateK stockDateK : lstStockDateK) {
            ret += stockDateK.toString();
        }

        return ret;
    }
}
