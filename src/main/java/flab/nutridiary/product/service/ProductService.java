package flab.nutridiary.product.service;

import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Long addProduct(Product product) {
        log.info("ProductService.addProduct - product: {}", product);
        Product savedProduct = productRepository.save(product);
        return savedProduct.getId();
    }
}
