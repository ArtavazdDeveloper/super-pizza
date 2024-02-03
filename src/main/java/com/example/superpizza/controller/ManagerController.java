package com.example.superpizza.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.superpizza.entity.productEntity.Product;
import com.example.superpizza.entity.productEntity.ProductType;
import com.example.superpizza.service.ProductsService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ProductsService productsService;


    @GetMapping("")
    public String getProductPage(ModelMap modelMap) {
        ProductType [] productTypes = ProductType.values();
        modelMap.addAttribute("productTypes",productTypes);
        return "/manager/promos";
    }

    @PostMapping("")
    public String saveProductInRepo(@ModelAttribute Product product,
                                    @RequestParam("image") MultipartFile multipartFile) throws Exception {
        productsService.save(product, multipartFile);
        return "redirect:/manager";
    }

    @GetMapping("/delete_product")
    public String deleteProduct(@RequestParam int id) {
        productsService.DeleteById(id);
        return "redirect:/manager";
    }

    @GetMapping("/take_product_by_type/type={type}")
    public String takeByProductType(ModelMap modelMap,
                                    @RequestParam Optional<Integer> size,
                                    @RequestParam Optional<Integer> page,
                                    @PathVariable("type") String productType) {
        Page<Product> pageable = productsService.createPageable(size, page, ProductType.valueOf(productType));
        ProductType [] productTypes = ProductType.values();
        if (pageable.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pageable.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());

            modelMap.addAttribute("pageNumbers", pageNumbers);
            modelMap.addAttribute("productType", productType);
        }
        modelMap.addAttribute("products", pageable);
        modelMap.addAttribute("productTypes",productTypes);

        return "/manager/promos";
    }

}
