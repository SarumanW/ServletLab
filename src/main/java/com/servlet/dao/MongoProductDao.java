package com.servlet.dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.servlet.entity.Product;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoProductDao implements ProductDao {

    private MongoCollection<Document> products;

    public MongoProductDao() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("vendingmachine");
        products = database.getCollection("products");
    }

    @Override
    public Product getProductById(int productNumber) {
        Document product = this.products.find(eq("number", (double) productNumber)).first();
        return this.convertDocumentToProduct(product);
    }

    @Override
    public void updateProductQuantity(int productNumber, int quantity) {
        this.products.updateOne(eq("number", productNumber),
                new Document("$set", new Document("quantity", (double) quantity)));
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();

        try (MongoCursor<Document> cursor = this.products.find().iterator()) {
            while (cursor.hasNext()) {
                Document next = cursor.next();

                productList.add(this.convertDocumentToProduct(next));
            }
        }

        return productList;
    }

    private Product convertDocumentToProduct(Document next) {
        Product product = new Product();

        product.setQuantity((next.getDouble("quantity").intValue()));
        product.setName(next.getString("name"));
        product.setProductNumber(next.getDouble("number").intValue());
        product.setPrice(BigDecimal.valueOf(next.getDouble("price")));

        return product;
    }
}
