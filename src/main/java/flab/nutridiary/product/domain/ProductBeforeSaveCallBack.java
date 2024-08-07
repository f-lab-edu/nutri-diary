package flab.nutridiary.product.domain;

import org.springframework.data.relational.core.conversion.MutableAggregateChange;
import org.springframework.data.relational.core.mapping.event.BeforeSaveCallback;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductBeforeSaveCallBack implements BeforeSaveCallback<Product> {

    @Override
    public Product onBeforeSave(Product product, MutableAggregateChange<Product> aggregateChange) {
        if (product.getCreatedAt() == null) {
            product.setCreatedAt(LocalDateTime.now());
        }
        product.setUpdatedAt(LocalDateTime.now());
        return product;
    }
}