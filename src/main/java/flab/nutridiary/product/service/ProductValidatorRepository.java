package flab.nutridiary.product.service;

public interface ProductValidatorRepository {
    Boolean DuplicatedProductCheck(String normalizedName);
}
