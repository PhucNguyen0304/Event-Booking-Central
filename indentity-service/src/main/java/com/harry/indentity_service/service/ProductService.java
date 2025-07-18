package com.harry.indentity_service.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.harry.indentity_service.dto.request.ProductCreationRequest;
import com.harry.indentity_service.dto.request.ProductUpdateRequest;
import com.harry.indentity_service.dto.response.ProductResponse;
import com.harry.indentity_service.entity.Product;
import com.harry.indentity_service.exception.AppException;
import com.harry.indentity_service.exception.ErrorCode;
import com.harry.indentity_service.mapper.ProductMapper;
import com.harry.indentity_service.repository.ProductRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;
    CloudinaryService cloudinaryService;

    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse createProduct(ProductCreationRequest request, MultipartFile file) {
        log.info("Create product service");
        String imageUrl = cloudinaryService.uploadImage(file);

        if (productRepository.existsByname(request.getName())) {
            throw new AppException(ErrorCode.PRODUCT_EXISTED);
        }

        Product product = productMapper.toProduct(request);
        product.setImageUrl(imageUrl);

        log.info("Product befor save: {}" , product);

        ProductResponse productResponse = productMapper.toProductResponse(productRepository.save(product));

        String msg = String.format("Saved %s product to DB",productResponse.getName());
        productResponse.setMsg(msg);

        log.info("Product response : {}" , productResponse);

        return productResponse;
    }

    public List<ProductResponse> getProducts() {
        return productMapper.toListProductResponse(productRepository.findAll());
    }

    public ProductResponse getProduct(String productId) {
        return productMapper.toProductResponse(
                productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse updateProduct(String productId, ProductUpdateRequest request) {
        Product product =
                productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        productMapper.updateProduct(product, request);
        return productMapper.toProductResponse(productRepository.save(product));
    }

    public void deleteProduct(String productId) {
        if (!(productRepository.existsById(productId))) {
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        productRepository.deleteById(productId);
    }
}
