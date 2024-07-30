package flab.nutridiary.product.service;

import flab.nutridiary.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WhiteSpaceProductValidator implements ProductValidator {

    private static final String WHITESPACE_REGEX = "\\s+";
    private static final String EMPTY = "";
    private final ProductValidatorRepository productValidatorRepository;

    @Override
    public void validate(Product product) {
        validate(getNormalizedName(product.getProductName(), product.getProductCorp()));
    }

    private void validate(String normalizedName) {
        if (productValidatorRepository.DuplicatedProductCheck(normalizedName)) {
            throw new ProductDuplicatedException();
        }
    }

    private String getNormalizedName(String productName, String productCorp) {
        return productCorp.replaceAll(WHITESPACE_REGEX, EMPTY) + productName.replaceAll(WHITESPACE_REGEX, EMPTY);
    }
}
