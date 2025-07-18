package com.harry.indentity_service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.harry.indentity_service.dto.request.ProductCreationRequest;
import com.harry.indentity_service.dto.request.ProductUpdateRequest;
import com.harry.indentity_service.dto.response.ProductResponse;
import com.harry.indentity_service.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductCreationRequest request);

    List<ProductResponse> toListProductResponse(List<Product> products);

    ProductResponse toProductResponse(Product product);

    void updateProduct(@MappingTarget Product product, ProductUpdateRequest productUpdateRequest);
}
