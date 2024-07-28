package flab.nutridiary.product.service;

import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductValidator {

    private final ProductRepository productRepository;

    public void validate(Product product) {
        validate(getNormalizedName(product.getProductName(), product.getProductCorp()));
    }

    private void validate(String normalizedName) {
        if (productRepository.productDuplicatedCheckByNormalizedName(normalizedName)) {
            throw new ProductDuplicatedException();
        }
    }

    private String getNormalizedName(String productName, String productCorp) {
        String EMPTY_REGEX = "\\s+";
        return productCorp.replaceAll(EMPTY_REGEX, "") + productName.replaceAll(EMPTY_REGEX, "");
    }
}
