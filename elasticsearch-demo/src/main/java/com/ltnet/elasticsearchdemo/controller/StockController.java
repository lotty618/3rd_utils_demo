package com.ltnet.elasticsearchdemo.controller;

import com.ltnet.elasticsearchdemo.entity.ESStockDateK;
import com.ltnet.elasticsearchdemo.service.ESStockDateKService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    ESStockDateKService esStockDateKService;

    @RequestMapping("/import")
    public String importAll() {
        int ret = esStockDateKService.importAllFromMysql();
        return ret > 0 ? ("成功导入" + ret + "条数据！") : "导入失败！";
    }

    @RequestMapping("/get/maxid")
    public String getFirst() {
        return "Max ID: " + String.valueOf(esStockDateKService.getMaxStockDateKId());
    }

    @RequestMapping("/get/{code}")
    public String getDateKByCode(@PathVariable String code) {
        List<ESStockDateK> list = esStockDateKService.getStockDateKList(code);
        StringBuilder ret = new StringBuilder();
        for (ESStockDateK esStockDateK : list) {
            ret.append(esStockDateK.toString()).append("\n");
        }
        return ret.toString();
    }
}
