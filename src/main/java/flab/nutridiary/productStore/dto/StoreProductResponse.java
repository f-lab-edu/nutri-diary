package flab.nutridiary.productStore.dto;

import flab.nutridiary.product.domain.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class StoreProductResponse {
    private final Long storeId;
    private final List<ProductInfo> products;

    public static StoreProductResponse of(Long storeId, List<Product> products) {
        List<ProductInfo> productInfos = products.stream()
                .map(product -> ProductInfo.of(product.getId(), product.getProductName(), product.getProductCorp()))
                .toList();

        return new StoreProductResponse(storeId, productInfos);
    }

    @Getter
    public static class ProductInfo {
        private final Long productId;
        private final String productName;
        private final String productCorp;

        private ProductInfo(Long productId, String productName, String productCorp) {
            this.productId = productId;
            this.productName = productName;
            this.productCorp = productCorp;
        }

        public static ProductInfo of(Long productId, String productName, String productCorp) {
            return new ProductInfo(productId, productName, productCorp);
        }
    }
}
