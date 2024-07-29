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

    /**
     * 여기에 특정 전략이 들어오도록 할 수 있을까?
     * @param normalizedName
     * @return
     */
    @Override
    public Boolean DuplicatedProductCheck(String normalizedName) {
        return productCrudRepository.countByNormalizedName(normalizedName) > 0;
    }
}
