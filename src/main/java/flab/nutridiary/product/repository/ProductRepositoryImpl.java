package flab.nutridiary.product.repository;

import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.service.ProductValidatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository, ProductValidatorRepository {

    private final ProductCrudRepository productCrudRepository;

    @Override
    public Product save(Product product) {
        return productCrudRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productCrudRepository.findById(id);
    }

    @Override
    public Boolean isExistDuplicatedProductByNormalizedName(String normalizedName) {
        return productCrudRepository.countByProductNormalizedName(normalizedName) > 0;
    }
}
