package flab.nutridiary.productStore.repository;

import flab.nutridiary.productStore.domain.StoreProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface StoreProductCrudRepository extends CrudRepository<StoreProduct, Long> {
    Page<StoreProduct> findByStoreId(Long storeId, Pageable pageable);
}
