package flab.nutridiary.product.service;

import flab.nutridiary.commom.exception.BusinessException;
import flab.nutridiary.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static flab.nutridiary.commom.exception.StatusConst.DUPLICATED_PRODUCT_NAME;

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
        if (productValidatorRepository.isExistDuplicatedProductByNormalizedName(normalizedName)) {
            throw new BusinessException(DUPLICATED_PRODUCT_NAME);
        }
    }

    private String getNormalizedName(String productName, String productCorp) {
        return productCorp.replaceAll(WHITESPACE_REGEX, EMPTY) + productName.replaceAll(WHITESPACE_REGEX, EMPTY);
    }
}
