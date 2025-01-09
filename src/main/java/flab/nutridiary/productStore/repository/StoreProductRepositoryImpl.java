package flab.nutridiary.productStore.repository;

import flab.nutridiary.productStore.domain.StoreProduct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Getter
@RequiredArgsConstructor
@Transactional
@Repository
public class StoreProductRepositoryImpl implements StoreProductRepository {
    private final StoreProductCrudRepository storeProductCrudRepository;

    @Override
    public Page<StoreProduct> findByStoreId(Long storeId, Pageable pageable) {
        return storeProductCrudRepository.findByStoreId(storeId, pageable);
    }
}
