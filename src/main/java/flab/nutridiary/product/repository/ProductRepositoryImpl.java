package flab.nutridiary.product.repository;

import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.service.ProductValidatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Repository
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
    public List<Product> findByIds(List<Long> ids) {
        return productCrudRepository.findByIds(ids);
    }

    @Override
    public Boolean isExistDuplicatedProductByNormalizedName(String normalizedName) {
        return productCrudRepository.countByProductNormalizedName(normalizedName) > 0;
    }
}
