package com.sh.shop.controller;

import com.sh.shop.dto.ProductDTO;
import com.sh.shop.service.ProductService;
import com.sh.shop.service.SessionObjectHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final SessionObjectHolder sessionObjectHolder;

    public ProductController(ProductService productService, SessionObjectHolder sessionObjectHolder){
        this.productService=productService;
        this.sessionObjectHolder = sessionObjectHolder;
    }
    @GetMapping
    public String list(Model model){
        sessionObjectHolder.addClicks();
        List<ProductDTO> list=productService.getAll();
        model.addAttribute("products",list);
        return "products";
    }
    @GetMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal){
        sessionObjectHolder.addClicks();
        if(principal == null){
            return "redirect:/products";
        }
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/products";
    }

}
