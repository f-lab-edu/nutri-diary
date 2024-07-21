package flab.nutridiary.product.controller;

import flab.nutridiary.product.dto.NewProductRequest;
import flab.nutridiary.product.dto.NewProductResponse;
import flab.nutridiary.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product/new")
    public NewProductResponse addProduct(@RequestBody NewProductRequest productRequest) {
        return NewProductResponse.of(productService.addProduct(productRequest.toProduct()));
    }
}
