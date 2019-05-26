package com.servlet.dao;

import com.servlet.entity.Product;

import java.util.List;

public interface ProductDao {
    Product getProductById(int productNumber);

    void updateProductQuantity(int productNumber, int quantity);

    List<Product> getAllProducts();
}
