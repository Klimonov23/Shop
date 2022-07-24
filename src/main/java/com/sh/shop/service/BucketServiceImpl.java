package com.sh.shop.service;

import com.sh.shop.domain.Bucket;
import com.sh.shop.domain.Product;
import com.sh.shop.domain.User;
import com.sh.shop.dto.BucketDTO;
import com.sh.shop.dto.BucketDetailDTO;
import com.sh.shop.repositories.BucketRepository;
import com.sh.shop.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    @Transactional
    public void remoteProducts(Bucket bucket, List<Long> productIds) {
        List<Product> products = bucket.getProducts();
        List<Product> newProductsList = new ArrayList<>();
        List<Product> remoteProducts = new ArrayList<>(getCollectRefProductsByIds(productIds));
        for (Product p: products) {
            if (!remoteProducts.contains(p)) {
                newProductsList.add(p);
            }
        }
        bucket.setProducts(newProductsList);
        bucketRepository.save(bucket);
    }

    @Override
    public BucketDTO getBucketByUser(String name) {
        User user=userService.findByName(name);
        if (user==null||user.getBucket()==null){
            return  new BucketDTO();
        }
        BucketDTO bucketDTO=new BucketDTO();
        Map<Long, BucketDetailDTO> mapByProductId=new HashMap<>();
        List<Product> products=user.getBucket().getProducts();
        for (Product p: products){
            BucketDetailDTO detail=mapByProductId.get(p.getId());
            if (detail==null){
                mapByProductId.put(p.getId(),new BucketDetailDTO(p));
            }
            else{
                detail.setAmount(detail.getAmount().add(new BigDecimal(1.0)));
                detail.setSum(detail.getSum()+Double.valueOf(p.getPrice().toString()));
            }

        }
        bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDTO.aggregate();
        return bucketDTO;
    }

}
