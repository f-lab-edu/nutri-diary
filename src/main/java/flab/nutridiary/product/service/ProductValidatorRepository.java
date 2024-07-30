package flab.nutridiary.product.service;

public interface ProductValidatorRepository {
    Boolean isExitsDuplicatedProductByNormalizedName(String normalizedName);
}
