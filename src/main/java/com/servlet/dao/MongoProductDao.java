package com.servlet.dao;

import com.servlet.entity.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MongoProductDao implements ProductDao {

    private List<Product> products = new ArrayList<>();

    public MongoProductDao() {
        products.add(new Product(1, new BigDecimal(5.5), 5, "Milkslice"));
        products.add(new Product(2, new BigDecimal(2), 5, "Roshen"));
        products.add(new Product(3, new BigDecimal(1), 5, "Kinder"));
        products.add(new Product(4, new BigDecimal(3), 5, "Milka"));
        products.add(new Product(5, new BigDecimal(2.25), 6, "Korona"));
    }
    @Override
    public Product getProductById(int productNumber) {
        for(Product p : products){
            if(p.getProductNumber() == productNumber) {
                return p;
            }
        }

        return null;
    }

    @Override
    public void updateProductQuantity(int productNumber, int quantity) {
        for(Product p : products){
            if(p.getProductNumber() == productNumber) {
                p.setQuantity(quantity);
            }
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }
}
