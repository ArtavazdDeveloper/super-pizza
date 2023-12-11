package com.example.superpizza.controller;

import com.example.superpizza.entity.productEntity.Product;
import com.example.superpizza.entity.productEntity.ProductType;
import com.example.superpizza.service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductsService productsService;


    @GetMapping("/single_page/{id}")
    public String productSinglePage(@PathVariable int id, ModelMap modelMap) {
        Product product = productsService.searchProductById(id);
        modelMap.addAttribute("product", product);
        modelMap.addAttribute("productList", productsService.randomProduct());
        return "product_details";
    }

    @GetMapping("/get_product_by_type/type={type}")
    public String getProductDetailPage(@PathVariable("type") String type, ModelMap modelMap,
                                       @RequestParam("size") Optional<Integer> size,
                                       @RequestParam("page") Optional<Integer> page) {

        ProductType[] values = ProductType.values();
        modelMap.addAttribute("productTypes", values);

        Page<Product> productsByProductType =
                productsService.createPageable(size, page, ProductType.valueOf(type));

        if (productsByProductType.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, productsByProductType.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
            modelMap.addAttribute("productType", type);
        }
        modelMap.addAttribute("products", productsByProductType);

        return "picks_today";
    }

    @GetMapping("/byProductType")
    public String takeByProductType(@RequestParam("type") String productType, ModelMap modelMap) {
        modelMap.addAttribute("productType", productType);
        modelMap.addAttribute("productsByType", productsService.searchProductByProductType(productType));
        return "picks_today";
    }


}
