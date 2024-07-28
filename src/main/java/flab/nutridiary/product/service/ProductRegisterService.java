package flab.nutridiary.product.service;

import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.domain.ProductMapper;
import flab.nutridiary.product.dto.NewProductRequest;
import flab.nutridiary.product.dto.NewProductResponse;
import flab.nutridiary.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProductRegisterService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public NewProductResponse process(NewProductRequest productRequest) {
        Product product = productMapper.from(productRequest);
        return NewProductResponse.of(productRepository.save(product).getId());
    }
}
