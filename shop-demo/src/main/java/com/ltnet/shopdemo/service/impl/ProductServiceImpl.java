package com.ltnet.shopdemo.service.impl;

import com.ltnet.shopdemo.dao.ProductDao;
import com.ltnet.shopdemo.entity.Product;
import com.ltnet.shopdemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;

    @Override
    public List<Product> getProductList() {
        return productDao.getProductList();
    }

    @Override
    public Product getProductById(String id) {
        return productDao.getProductById(Integer.parseInt(id));
    }

    @Override
    public boolean updateProduct(Product product) {
        return productDao.updateProduct(product) > 0 ? true : false;
    }
}
