package flab.nutridiary.productStore.repository;

import flab.nutridiary.productStore.domain.StoreProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StoreProductCrudRepository extends CrudRepository<StoreProduct, Long> {
    List<StoreProduct> findByStoreId(Long storeId);
}
