package flab.nutridiary.product.service;

import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.domain.ProductMapper;
import flab.nutridiary.product.dto.request.NewProductRequest;
import flab.nutridiary.product.dto.response.NewProductResponse;
import flab.nutridiary.product.repository.ProductRepository;
import flab.nutridiary.search.ProductDocumentAsyncRegisterService;
import flab.nutridiary.search.ProductDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProductRegisterService {
    private final ProductRepository productRepository;
    private final ProductDocumentAsyncRegisterService productDocumentAsyncRegisterService;
    private final ProductValidator productValidator;
    private final ProductMapper productMapper;

    public NewProductResponse process(NewProductRequest productRequest) {
        Product product = productMapper.from(productRequest);
        productValidator.validate(product);
        return NewProductResponse.of(saveProduct(product));
    }

    private Long saveProduct(Product product) {
        Long id = productRepository.save(product).getId();
        productDocumentAsyncRegisterService.saveDocumentAsync(productMapper.toDocument(id, product));
        return id;
    }
}
