package com.sh.shop.service;

import com.sh.shop.dto.ProductDTO;
import com.sh.shop.mapper.ProductMapper;
import com.sh.shop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class ProductServiceImpl implements ProductService{
    private final ProductMapper mapper=ProductMapper.MAPPER;
    private final ProductRepository repository;

    public ProductServiceImpl( ProductRepository repository) {
        this.repository=repository;
    }

    @Override
    public List<ProductDTO> getAll() {
        return mapper.fromProductList(repository.findAll());
    }
}
