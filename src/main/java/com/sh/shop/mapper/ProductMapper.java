package com.sh.shop.mapper;


import com.sh.shop.domain.Product;
import com.sh.shop.dto.ProductDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    Product toDTO(ProductDTO dto);

    @InheritInverseConfiguration
    ProductDTO fromProduct(Product product);

    List<Product> toProductList(List<ProductDTO> list);

    List<ProductDTO> fromProductList(List<Product> list);


}
