package com.example.superpizza.controller;

import com.example.superpizza.entity.productEntity.Product;
import com.example.superpizza.entity.productEntity.ProductType;
import com.example.superpizza.entity.userEntity.User;
import com.example.superpizza.entity.userEntity.UserRole;
import com.example.superpizza.service.ContactDataService;
import com.example.superpizza.service.ProductsService;
import com.example.superpizza.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductsService productsService;

    private final UserService userService;

    private final ContactDataService contactDataService;

    @GetMapping("")
    public String adminMainPage(ModelMap modelMap) {
        modelMap.addAttribute("productListSize", productsService.sizeListOfProduct());
        modelMap.addAttribute("userListSize", userService.userListSize());
        return "/admin/adminPage";
    }

    @GetMapping("/product")
    public String adminProductPage(ModelMap modelMap) {
        ProductType[] productTypes = ProductType.values();
        modelMap.addAttribute("productTypes", productTypes);
        return "/admin/adminPromos";
    }

    @GetMapping("/take_product_by_type/type={type}")
    public String takeByProductType(ModelMap modelMap,
                                    @RequestParam("size") Optional<Integer> size,
                                    @RequestParam("page") Optional<Integer> page,
                                    @PathVariable("type") String productType) {
        Page<Product> pageable = productsService.createPageable(size, page, ProductType.valueOf(productType));
        ProductType[] productTypes = ProductType.values();
        if (pageable.getTotalPages() > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pageable.getTotalPages())
                    .boxed()
                    .collect(Collectors.toList());

            modelMap.addAttribute("pageNumbers", pageNumbers);
            modelMap.addAttribute("productType", productType);
        }
        modelMap.addAttribute("products", pageable);
        modelMap.addAttribute("productTypes", productTypes);

        return "/admin/adminPromos";
    }

    @GetMapping("/delete_product")
    public String deleteProduct(@RequestParam("id") int id) {
        productsService.DeleteById(id);
        return "redirect:/admin/product";
    }

    @GetMapping("/user_setting")
    public String userSettingPage() {
        return "/admin/userSettingPage";
    }

    @PostMapping("/user_by_email")
    public String takeUserByEmail(@RequestParam("email") String email,ModelMap modelMap) {
        if (userService.getUserByEmail(email).isEmpty()){
            modelMap.addAttribute("user",null);
        }else {
            modelMap.addAttribute("user",userService.getUserByEmail(email).get());
            UserRole [] userRoles = UserRole.values();
            modelMap.addAttribute("userRoles",userRoles);
        }

        return "/admin/userSettingPage";

    }
    @PostMapping("/change_user_role")
    public String changeUserRole(@RequestParam("userRole") String userRole,@RequestParam("id") int id){
        User user = userService.getUserById(id);
        user.setUserRole(UserRole.valueOf(userRole));
        userService.saveUser(user);
        return "/admin/userSettingPage";
    }
    @GetMapping("/delete_user/{id}")
    public String deleteUser(@PathVariable("id") int id){
        User  user = userService.getUserById(id);
        userService.deleteUserById(id);
        contactDataService.deleteById(user.getContactData().getId());
        return "/admin/userSettingPage";
    }
}
