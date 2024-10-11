package flab.nutridiary.product.repository;

import flab.nutridiary.product.dto.response.ProductSearchResponse;

import java.util.List;

public interface ProductSearchRepository {
    List<ProductSearchResponse> findFullTextSearch(String keyword);
}
