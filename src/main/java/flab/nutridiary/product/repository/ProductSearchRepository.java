package flab.nutridiary.product.repository;

import flab.nutridiary.product.dto.response.ProductSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSearchRepository {
    Page<ProductSearchResponse> findFullTextSearch(String keyword, Pageable pageable);
}
