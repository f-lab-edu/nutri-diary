package flab.nutridiary.productDietTag.repository;

import flab.nutridiary.productDietTag.ProductDietTag;
import flab.nutridiary.productDietTag.ProductDietTagDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDietTagCrudRepository extends CrudRepository<ProductDietTag, Long> {
    List<ProductDietTag> findByProductId(Long productId);

    @Query("SELECT pdt.product_id, dt.diet_plan, pdt.tag_count " +
            "FROM product_diet_tag pdt " +
            "LEFT JOIN diet_tag dt on pdt.diet_tag_id = dt.diet_tag_id " +
            "WHERE product_id IN (:productIds)"
    )
    List<ProductDietTagDto> findByProductIds(@Param("productIds") List<Long> productIds);
}
