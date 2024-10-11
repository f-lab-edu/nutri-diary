package flab.nutridiary.product.service;

import flab.nutridiary.product.dto.response.ProductSearchResponse;
import flab.nutridiary.product.repository.ProductSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductSearchService {
    private final ProductSearchRepository productSearchRepository;

    public List<ProductSearchResponse> search(String condition) {
        return productSearchRepository.findFullTextSearch(condition);
    }
}
