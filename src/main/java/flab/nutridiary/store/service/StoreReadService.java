package flab.nutridiary.store.service;

import flab.nutridiary.commom.exception.BusinessException;
import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.dto.response.ProductSearchResponse;
import flab.nutridiary.product.repository.ProductRepository;
import flab.nutridiary.product.service.ProductSearchResponseMapper;
import flab.nutridiary.productDietTag.ProductDietTagDto;
import flab.nutridiary.productDietTag.repository.ProductDietTagRepository;
import flab.nutridiary.productStore.domain.StoreProduct;
import flab.nutridiary.productStore.dto.StoreProductResponse;
import flab.nutridiary.productStore.repository.StoreProductRepository;
import flab.nutridiary.review.repository.ProductReviewCount;
import flab.nutridiary.review.repository.ReviewRepository;
import flab.nutridiary.store.dto.StoreName;
import flab.nutridiary.store.dto.response.AllStore;
import flab.nutridiary.store.repository.StoreRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ProductDietTagRepository productDietTagRepository;
    private final ProductSearchResponseMapper productSearchResponseMapper;
    private final ReviewRepository reviewRepository;

    public AllStore getAllStore() {
        return AllStore.of(storeRepository.findAll()
                .stream()
                .map(store -> StoreName.of(store.getId(), store.getStoreName()))
                .toList());
    }

    public List<ProductSearchResponse> getStoreProduct(Long storeId, Pageable pageable) {
        Page<StoreProduct> storeProducts = storeProductRepository.findByStoreId(storeId, pageable);

        if (storeProducts.isEmpty()) {
            throw new BusinessException(STORE_PRODUCT_NOT_FOUND);
        }

        List<Long> productIds = storeProducts.stream()
                .map(StoreProduct::getProductId)
                .toList();

        List<Product> products = productRepository.findByIds(productIds);
        List<ProductReviewCount> productReviewCounts = reviewRepository.countReviewsByProductIds(productIds);
        List<ProductDietTagDto> tags = productDietTagRepository.findByProductIds(productIds);
        return productSearchResponseMapper.toResponseList(products, productReviewCounts, tags);
    }
}
