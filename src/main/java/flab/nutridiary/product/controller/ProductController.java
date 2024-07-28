package flab.nutridiary.product.controller;

import flab.nutridiary.commom.dto.ApiDataResponse;
import flab.nutridiary.commom.dto.ApiResponse;
import flab.nutridiary.product.dto.NewProductRequest;
import flab.nutridiary.product.service.ProductRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductRegisterService productRegisterService;

    @PostMapping("/product/new")
    public ApiResponse addProduct(@RequestBody @Valid NewProductRequest productRequest) {
        return ApiDataResponse.OK(productRegisterService.process(productRequest));
    }
}
