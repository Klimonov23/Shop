package com.sh.shop.controller;

import com.sh.shop.dto.BucketDTO;
import com.sh.shop.service.BucketService;
import com.sh.shop.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
@Controller
public class BucketController {
    private final BucketService bucketService;
    private final ProductService productService;

    public BucketController(BucketService bucketService, ProductService productService) {
        this.bucketService = bucketService;
        this.productService = productService;
    }
    @GetMapping("/bucket")
    public String aboutBucket(Model model, Principal principal){
        if (principal==null){
            model.addAttribute("bucket",new BucketDTO());
        }
        else {
            BucketDTO dto=bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket",dto);
        }
        return "bucket";
    }
    @GetMapping("/bucket/{id}/product")
    public String removeBucket(@PathVariable Long id, Principal principal){
        if(principal == null){
            return "redirect:/bucket";
        }
        productService.removeFromUserBucket(id, principal.getName());
        return "redirect:/bucket";
    }
}
