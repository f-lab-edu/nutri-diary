package flab.nutridiary.productDietTag;

import flab.nutridiary.dietTag.domain.DietTag;
import flab.nutridiary.product.domain.Product;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class ProductDietTag {
    @Id @Column("product_diet_tag_id")
    private Long id;

    private AggregateReference<DietTag, Long> dietTagId;

    private AggregateReference<Product, Long> productId;

    @Getter
    private Long tagCount;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public ProductDietTag(Long dietTagId, Long productId, Long tagCount) {
        this.dietTagId = AggregateReference.to(dietTagId);
        this.productId = AggregateReference.to(productId);
        this.tagCount = tagCount;
    }

    public void increaseTagCount() {
        this.tagCount++;
    }

    public Long getDietTagId() {
        return dietTagId.getId();
    }
}
