package com.sh.shop.controller;

import com.sh.shop.dto.BucketDTO;
import com.sh.shop.service.BucketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
@Controller
public class BucketController {
    private final BucketService bucketService;

    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
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
}
