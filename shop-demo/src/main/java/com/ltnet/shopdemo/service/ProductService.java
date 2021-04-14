package com.ltnet.shopdemo.service;

import com.ltnet.shopdemo.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductList();
    Product getProductById(String id);
    boolean updateProduct(Product product);
}
