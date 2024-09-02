package flab.nutridiary.product.service;

public interface ProductValidatorRepository {
    Boolean isExistDuplicatedProductByNormalizedName(String normalizedName);
}
