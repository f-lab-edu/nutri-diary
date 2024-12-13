package flab.nutridiary.store.service;

import flab.nutridiary.commom.exception.BusinessException;
import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.repository.ProductRepository;
import flab.nutridiary.productStore.dto.StoreProductResponse;
import flab.nutridiary.productStore.repository.StoreProductRepository;
import flab.nutridiary.store.dto.StoreName;
import flab.nutridiary.store.dto.response.AllStore;
import flab.nutridiary.store.repository.StoreRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static flab.nutridiary.commom.exception.StatusConst.STORE_PRODUCT_NOT_FOUND;

@Getter
@RequiredArgsConstructor
@Transactional
@Service
public class StoreReadService {
    private final StoreRepository storeRepository;
    private final StoreProductRepository storeProductRepository;
    private final ProductRepository productRepository;

    public AllStore getAllStore() {
        return AllStore.of(storeRepository.findAll()
                .stream()
                .map(store -> StoreName.of(store.getId(), store.getStoreName()))
                .toList());
    }

    public StoreProductResponse getStoreProduct(Long storeId) {
        List<Long> productIds = storeProductRepository.findByStoreId(storeId)
                .stream()
                .map(storeProduct -> storeProduct.getProductId())
                .toList();
        if (productIds.isEmpty()) {
            throw new BusinessException(STORE_PRODUCT_NOT_FOUND);
        }
        List<Product> products = productRepository.findByIds(productIds);
        return StoreProductResponse.of(storeId, products);
    }
}
