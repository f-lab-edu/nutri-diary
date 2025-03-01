package flab.nutridiary.productStore.domain;

import flab.nutridiary.product.domain.Product;
import flab.nutridiary.store.domain.Store;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
public class StoreProduct {
    @Id @Column("store_product_id")
    private Long id;

    private AggregateReference<Store, Long> storeId;

    private AggregateReference<Product, Long> productId;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public StoreProduct(Long storeId, Long productId) {
        this.storeId = AggregateReference.to(storeId);
        this.productId = AggregateReference.to(productId);
    }

    public Long getStoreId() {
        return storeId.getId();
    }

    public Long getProductId() {
        return productId.getId();
    }
}
