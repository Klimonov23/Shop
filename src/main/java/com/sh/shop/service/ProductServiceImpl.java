package com.sh.shop.service;

import com.sh.shop.domain.Bucket;
import com.sh.shop.domain.User;
import com.sh.shop.dto.ProductDTO;
import com.sh.shop.mapper.ProductMapper;
import com.sh.shop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service

public class ProductServiceImpl implements ProductService{
    private final ProductMapper mapper=ProductMapper.MAPPER;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final BucketService bucketService;

    public ProductServiceImpl(ProductRepository productRepository,
                              UserService userService,
                              BucketService bucketService
                             ) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;

    }

    @Override
    public List<ProductDTO> getAll() {
        return mapper.fromProductList(productRepository.findAll());
    }

    @Override
    public void addToUserBucket(Long productId, String username) {
        User user = userService.findByName(username);
        if(user == null){
            throw new RuntimeException("User not found. " + username);
        }

        Bucket bucket = user.getBucket();
        if(bucket == null){
            Bucket newBucket = bucketService.createBucket(user, Collections.singletonList(productId));
            user.setBucket(newBucket);
            userService.save(user);
        }
        else {
            bucketService.addProducts(bucket, Collections.singletonList(productId));
        }
    }
}
