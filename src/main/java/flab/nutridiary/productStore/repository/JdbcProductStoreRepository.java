package flab.nutridiary.productStore.repository;

import flab.nutridiary.productStore.domain.ProductStore;
import org.springframework.data.repository.CrudRepository;

public interface JdbcProductStoreRepository extends CrudRepository<ProductStore, Long> {
}
