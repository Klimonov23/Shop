package com.sh.shop.service;

import com.sh.shop.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;
public interface ProductService {
    List<ProductDTO> getAll();
    void addToUserBucket(Long productId,String username);
}
