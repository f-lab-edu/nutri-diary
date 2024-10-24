package flab.nutridiary.product.dto.response;

import lombok.Getter;

@Getter
public class NewProductResponse {
    private Long productId;

    public static NewProductResponse of(Long productId) {
        NewProductResponse response = new NewProductResponse();
        response.productId = productId;
        return response;
    }
}
