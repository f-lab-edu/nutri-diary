package flab.nutridiary.product.repository;

import flab.nutridiary.product.domain.Product;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductCrudRepository extends CrudRepository<Product, Long> {

    @Query("select count(*) from product where product_normalized_name = :normalizedName")
    Integer isExistDuplicatedProductByNormalizedName(String normalizedName);
}
