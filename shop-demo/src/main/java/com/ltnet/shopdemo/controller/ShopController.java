package com.ltnet.shopdemo.controller;

import com.ltnet.shopdemo.service.ProductService;
import com.ltnet.shopdemo.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/shop")
@RestController
public class ShopController {
    @Autowired
    ProductService productService;

    @Autowired
    ShopService shopService;

    @RequestMapping("/get/{id}")
    public String get(@PathVariable String id) {
        System.out.println("Query product id: " + id);
        return productService.getProductById(id).toString();
    }

    @RequestMapping("/buy/{id}")
    public String buy(@PathVariable String id) {
        System.out.println("Buy product id: " + id);
        return shopService.buyProduct(id);
    }
}
