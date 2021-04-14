package com.ltnet.shopdemo.dao;

import com.ltnet.shopdemo.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductDao {
    List<Product> getProductList();
    Product getProductById(Integer id);
    int updateProduct(Product product);
    int saleProduct(@Param("id")int id, @Param("count")int count);
    int saleProductByLock(@Param("id")int id, @Param("count")int count, @Param("version")int version);
}
