package com.sh.shop.repositories;

import com.sh.shop.domain.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<Bucket,Long> {
}
