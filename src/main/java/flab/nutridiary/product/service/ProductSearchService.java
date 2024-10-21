package flab.nutridiary.product.service;

import flab.nutridiary.product.dto.response.ProductSearchResponse;
import flab.nutridiary.product.repository.ProductSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductSearchService {
    private final ProductSearchRepository productSearchRepository;

    public Page<ProductSearchResponse> search(String condition, Pageable pageable) {
        return productSearchRepository.findFullTextSearch(condition, pageable);
    }
}
