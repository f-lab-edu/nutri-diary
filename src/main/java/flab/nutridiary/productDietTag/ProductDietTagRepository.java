package flab.nutridiary.productDietTag;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductDietTagRepository extends CrudRepository<ProductDietTag, Long> {
    List<ProductDietTag> findByProductId(Long productId);
}
