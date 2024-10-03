package flab.nutridiary.product.repository;

import flab.nutridiary.product.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductCrudRepository extends CrudRepository<Product, Long> {
    int countByProductNormalizedName(String normalizedName);
}
