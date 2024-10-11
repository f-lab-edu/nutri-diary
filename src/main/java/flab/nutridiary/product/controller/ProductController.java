package flab.nutridiary.product.controller;

import flab.nutridiary.commom.dto.ApiResponse;
import flab.nutridiary.product.dto.request.NewProductRequest;
import flab.nutridiary.product.dto.response.NewProductResponse;
import flab.nutridiary.product.dto.response.ProductSearchResponse;
import flab.nutridiary.product.service.ProductRegisterService;
import flab.nutridiary.product.service.ProductSearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductRegisterService productRegisterService;
    private final ProductSearchService productSearchService;

    @PostMapping("/product/new")
    public ApiResponse<NewProductResponse> addProduct(@RequestBody @Valid NewProductRequest productRequest) {
        return ApiResponse.success(productRegisterService.process(productRequest));
    }

    //todo : 전문검색 글자 2글자가능하도록, 검색결과 페이징?, 이미지가 필요할까?
    @GetMapping("/product")
    public ApiResponse<List<ProductSearchResponse>> searchProduct(@RequestParam(name = "search") String condition) {
        return ApiResponse.success(productSearchService.search(condition));
    }
}
