package flab.nutridiary.productDietTag.repository;

import flab.nutridiary.productDietTag.ProductDietTag;
import flab.nutridiary.productDietTag.ProductDietTagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Repository
public class ProductDietTagRepositoryImpl implements ProductDietTagRepository{
    private final ProductDietTagCrudRepository productDietTagCrudRepository;


    @Override
    public List<ProductDietTag> findByProductId(Long productId) {
        return productDietTagCrudRepository.findByProductId(productId);
    }

    @Override
    public List<ProductDietTagDto> findByProductIds(List<Long> productIds) {
        return productDietTagCrudRepository.findByProductIds(productIds);
    }

    @Override
    public void saveAll(List<ProductDietTag> productDietTags) {
        productDietTagCrudRepository.saveAll(productDietTags);
    }
}
