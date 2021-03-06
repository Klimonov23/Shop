package com.sh.shop.service;

import com.sh.shop.domain.Bucket;
import com.sh.shop.domain.User;
import com.sh.shop.dto.BucketDTO;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productIds);
     void addProducts(Bucket bucket,List<Long> productIds);
     void remoteProducts(Bucket bucket,List<Long> productIds);
     BucketDTO getBucketByUser(String name);
}
