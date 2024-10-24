package flab.nutridiary.product.controller;

import flab.nutridiary.commom.dto.ApiResponse;
import flab.nutridiary.product.dto.request.NewProductRequest;
import flab.nutridiary.product.dto.response.NewProductResponse;
import flab.nutridiary.product.dto.response.ProductSearchResponse;
import flab.nutridiary.product.service.ProductRegisterService;
import flab.nutridiary.product.service.ProductSearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductRegisterService productRegisterService;
    private final ProductSearchService productSearchService;

    @PostMapping("/product/new")
    public ApiResponse<NewProductResponse> addProduct(@RequestBody @Valid NewProductRequest productRequest) {
        return ApiResponse.success(productRegisterService.process(productRequest));
    }

    @GetMapping("/product")
    public ApiResponse<Page<ProductSearchResponse>> searchProduct(@RequestParam(name = "search") String condition, Pageable pageable) {
        return ApiResponse.success(productSearchService.search(condition, pageable));
    }
}
