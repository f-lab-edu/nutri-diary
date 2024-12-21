package flab.nutridiary.product.service;

import flab.nutridiary.search.ProductDocument;
import flab.nutridiary.search.ProductDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductSearchService {
    private final ProductDocumentRepository productDocumentRepository;

    public Page<ProductDocument> search(String condition, Pageable pageable) {
        return productDocumentRepository.findByProductName(condition, pageable);
    }
}
