package flab.nutridiary.productStore;

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
public class ProductStore {
    @Id @Column("product_store_id")
    private Long id;

    private AggregateReference<Store, Long> storeId;

    private AggregateReference<Product, Long> productId;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public ProductStore(Long storeId, Long productId) {
        this.storeId = AggregateReference.to(storeId);
        this.productId = AggregateReference.to(productId);
    }
}
