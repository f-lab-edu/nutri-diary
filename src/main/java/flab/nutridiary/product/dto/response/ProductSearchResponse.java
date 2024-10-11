package flab.nutridiary.product.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ProductSearchResponse {
    private final Long productId;
    private final String productName;
    private final String productCorp;
}