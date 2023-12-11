package com.example.superpizza.repository;

import com.example.superpizza.entity.productEntity.Product;
import com.example.superpizza.entity.productEntity.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;



public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT * from products where product_type != 'FAST_FOOD'order by rand() limit 8",nativeQuery = true)
    List<Product> takeRandomProduct();

    @Query(value = "SELECT * from products order by rand() limit 9",nativeQuery = true)
    List<Product> randomProduct();

    List<Product> findProductsByProductType(ProductType productType);

    Product findProductsById(int id);

    Page<Product> findProductsByProductType(ProductType productType, Pageable pageable);
    @Query(value = "SELECT COUNT(*) AS size FROM products", nativeQuery = true)
    int listSize();

}
