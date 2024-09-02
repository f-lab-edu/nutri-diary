package flab.nutridiary.product.repository;

import flab.nutridiary.product.domain.Product;

import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
}
