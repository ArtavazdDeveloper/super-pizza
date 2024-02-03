package com.example.superpizza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    
   @GetMapping("/path")
   public String FindPage() {
       return "search";
   }

   
}
