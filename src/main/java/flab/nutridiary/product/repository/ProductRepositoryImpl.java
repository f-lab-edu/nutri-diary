package flab.nutridiary.product.repository;

import flab.nutridiary.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository{

    private final ProductCrudRepository productCrudRepository;

    @Override
    public Product save(Product product) {
        return productCrudRepository.save(product);
    }

    @Override
    public Boolean productDuplicatedCheckByNormalizedName(String normalizedName) {
        return productCrudRepository.countByNormalizedName(normalizedName) > 0;
    }
}
