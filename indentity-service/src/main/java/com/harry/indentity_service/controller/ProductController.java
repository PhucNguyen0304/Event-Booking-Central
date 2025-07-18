package com.harry.indentity_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.harry.indentity_service.dto.request.ProductCreationRequest;
import com.harry.indentity_service.dto.request.ProductUpdateRequest;
import com.harry.indentity_service.dto.response.ApiResponse;
import com.harry.indentity_service.dto.response.ProductResponse;
import com.harry.indentity_service.repository.ProductRepository;
import com.harry.indentity_service.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ProductService productService;

//    @PostMapping(
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public ApiResponse<ProductResponse> createProduct(
//            @RequestPart("product") ProductCreationRequest request,
//            @RequestPart("file") MultipartFile file
//    ) {
//        log.info("Create product controller: {}", request);
//        ProductResponse productResponse = productService.createProduct(request, file);
//
//        return ApiResponse.<ProductResponse>builder()
//                .message(productResponse.getMsg())
//                .result(productResponse)
//                .build();
//    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<ProductResponse> createProduct(
            @RequestParam String name,
            @RequestParam String author,
            @RequestParam int remain,
            @RequestParam int discount,
            @RequestParam float price,
            @RequestParam MultipartFile file) {

        ProductCreationRequest request = new ProductCreationRequest();
        request.setName(name);
        request.setPrice(price);
        request.setAuthor(author);
        request.setRemain(remain);
        request.setDiscount(discount);

        log.info("Create product controller: {}", request);
        ProductResponse productResponse = productService.createProduct(request, file);
        return ApiResponse.<ProductResponse>builder()
                .message(productResponse.getMsg())
                .result(productResponse)
                .build();
    }

    @GetMapping
    public ApiResponse<List<ProductResponse>> getProducts() {
        ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productService.getProducts());
        return apiResponse;
    }

    @GetMapping("/{productId}")
    public ApiResponse<ProductResponse> getProduct(@PathVariable("productId") String productId) {
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productService.getProduct(productId));
        return apiResponse;
    }

    @PutMapping("/{productId}")
    public ApiResponse<ProductResponse> updateProduct(
            @PathVariable("productId") String productId, @RequestBody ProductUpdateRequest productUpdate) {
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(productService.updateProduct(productId, productUpdate));
        return apiResponse;
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteProduct(@PathVariable("productId") String productId) {
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        productService.deleteProduct(productId);
        apiResponse.setMessage("Product deleted");
        return apiResponse;
    }
}
