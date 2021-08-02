package com.ltnet.elasticsearchdemo.controller;

import com.ltnet.elasticsearchdemo.entity.ESStockDateK;
import com.ltnet.elasticsearchdemo.service.ESStockDateKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/k")
@RestController
public class TestController {
    @Autowired
    ESStockDateKService stockDateKService;

    @RequestMapping("/{code}")
    public String getDateKList(@PathVariable String code) {
        List<ESStockDateK> lstStockDateK = stockDateKService.getStockDateKListByCode(code);
        String ret = "";

        for (ESStockDateK stockDateK : lstStockDateK) {
            ret += stockDateK.toString();
        }

        return ret;
    }
}
