package com.sh.shop.service;

import com.sh.shop.domain.Bucket;
import com.sh.shop.domain.User;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productIds);
     void addProducts(Bucket bucket,List<Long> productIds);
}
