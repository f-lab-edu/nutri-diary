package flab.nutridiary.product.repository;

import flab.nutridiary.product.domain.Product;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductCrudRepository extends CrudRepository<Product, Long> {
    int countByProductNormalizedName(String normalizedName);

    @Query("SELECT * FROM product WHERE product_id IN (:productIds)")
    List<Product> findByIds(List<Long> productIds);
}
