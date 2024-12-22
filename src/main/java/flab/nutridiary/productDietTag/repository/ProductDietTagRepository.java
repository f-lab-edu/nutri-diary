package flab.nutridiary.productDietTag.repository;

import flab.nutridiary.productDietTag.ProductDietTag;
import flab.nutridiary.productDietTag.ProductDietTagDto;

import java.util.List;

public interface ProductDietTagRepository {
    List<ProductDietTag> findByProductId(Long productId);
    List<ProductDietTagDto> findByProductIds(List<Long> productIds);
    void saveAll(List<ProductDietTag> productDietTags);
}
