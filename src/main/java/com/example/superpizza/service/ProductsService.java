package com.example.superpizza.service;

import com.example.superpizza.entity.productEntity.Product;
import com.example.superpizza.entity.productEntity.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


public interface ProductsService {

    Product save(Product product, MultipartFile multipartFile) throws Exception;

    void DeleteById(int id);

    List<Product> searchProductByProductType(String productType);

    Product searchProductById(int id);

    List<Product> takeRandomProducts();
    int sizeListOfProduct();

    List<Product> randomProduct();
    Page<Product> createPageable(Optional<Integer> size, Optional<Integer> page, ProductType productType);

}
