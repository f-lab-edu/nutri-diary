package flab.nutridiary.productStore.repository;

import flab.nutridiary.productStore.domain.StoreProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreProductRepository {
    Page<StoreProduct> findByStoreId(Long storeId, Pageable pageable);
}
