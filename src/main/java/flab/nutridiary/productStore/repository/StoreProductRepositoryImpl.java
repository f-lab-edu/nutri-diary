package flab.nutridiary.productStore.repository;

import flab.nutridiary.productStore.domain.StoreProduct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Transactional
@Repository
public class StoreProductRepositoryImpl implements StoreProductRepository {
    private final StoreProductCrudRepository storeProductCrudRepository;

    @Override
    public List<StoreProduct> findByStoreId(Long storeId) {
        return storeProductCrudRepository.findByStoreId(storeId);
    }
}
