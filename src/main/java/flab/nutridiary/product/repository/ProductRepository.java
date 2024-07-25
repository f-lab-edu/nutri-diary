package flab.nutridiary.product.repository;

import flab.nutridiary.product.domain.Product;

public interface ProductRepository {
    Product save(Product product);
}
