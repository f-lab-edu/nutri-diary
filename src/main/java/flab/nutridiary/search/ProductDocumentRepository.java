package flab.nutridiary.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ProductDocumentRepository extends CrudRepository<ProductDocument, Long> {
    Page<ProductDocument> findByProductName(String productName, Pageable pageable);
}