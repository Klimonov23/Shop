package com.sh.shop.service;

import com.sh.shop.domain.Bucket;
import com.sh.shop.domain.Product;
import com.sh.shop.domain.User;
import com.sh.shop.repositories.BucketRepository;
import com.sh.shop.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements BucketService{
        private final BucketRepository bucketRepository;
        private final ProductRepository productRepository;
        private final UserService userService;

        public BucketServiceImpl(BucketRepository bucketRepository,
                                 ProductRepository productRepository,
                                 UserService userService
                                 ) {
            this.bucketRepository = bucketRepository;
            this.productRepository = productRepository;
            this.userService = userService;
        }

    @Override
    @javax.transaction.Transactional
    public Bucket createBucket(User user, List<Long> productIds) {

            Bucket bucket = new Bucket();
            bucket.setUser(user);
            List<Product> productList = getCollectRefProductsByIds(productIds);
            bucket.setProducts(productList);
            return bucketRepository.save(bucket);
    }

    private List<Product> getCollectRefProductsByIds(List<Long> productIds) {
        return productIds.stream()
                .map(productRepository::getOne)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addProducts(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProducts();
        List<Product> newProductsList = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProductsList.addAll(getCollectRefProductsByIds(productIds));
        bucket.setProducts(newProductsList);
        bucketRepository.save(bucket);
    }
}
