package flab.nutridiary.product.service;

import flab.nutridiary.commom.exception.BusinessException;
import flab.nutridiary.product.dto.response.ProductSearchResponse;
import flab.nutridiary.productDietTag.ProductDietTagDto;
import flab.nutridiary.productDietTag.repository.ProductDietTagRepository;
import flab.nutridiary.review.repository.ProductReviewCount;
import flab.nutridiary.review.repository.ReviewRepository;
import flab.nutridiary.search.ProductDocument;
import flab.nutridiary.search.ProductDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static flab.nutridiary.commom.exception.StatusConst.PRODUCT_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductSearchService {
    private final ProductDocumentRepository productDocumentRepository;
    private final ReviewRepository reviewRepository;
    private final ProductDietTagRepository productDietTagRepository;
    private final ProductSearchResponseMapper productSearchResponseMapper;

    public Page<ProductSearchResponse> search(String condition, Pageable pageable) {
        Page<ProductDocument> productDocuments = productDocumentRepository.findByProductName(condition, pageable);

        if (productDocuments.isEmpty()) {
            throw new BusinessException(PRODUCT_NOT_FOUND);
        }

        List<Long> productIds = productDocuments.stream()
                .map(ProductDocument::getId)
                .toList();

        List<ProductReviewCount> productReviewCounts = reviewRepository.countReviewsByProductIds(productIds);
        List<ProductDietTagDto> tags = productDietTagRepository.findByProductIds(productIds);
        return productSearchResponseMapper.toResponseList(productDocuments, productReviewCounts, tags);
    }
}
