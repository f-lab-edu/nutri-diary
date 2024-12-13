package flab.nutridiary.productStore.repository;

import flab.nutridiary.productStore.domain.StoreProduct;

import java.util.List;

public interface StoreProductRepository {
    List<StoreProduct> findByStoreId(Long storeId);
}
