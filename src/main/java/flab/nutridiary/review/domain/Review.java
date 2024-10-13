package flab.nutridiary.review.domain;

import flab.nutridiary.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
@Getter
public class Review {
    @Id @Column("review_id")
    private Long id;

    private Long memberId = 1L;

    private AggregateReference<Product, Long> productId;

    private String content;

    private Short rating;

    private String imageUrl;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Review(Long productId, String content, Short rating, String imageUrl) {
        this.productId = AggregateReference.to(productId);
        this.content = content;
        this.rating = rating;
        this.imageUrl = imageUrl;
    }
}
