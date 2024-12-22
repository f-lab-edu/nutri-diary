package flab.nutridiary.product.service;

import flab.nutridiary.product.dto.response.ProductSearchResponse;
import flab.nutridiary.productDietTag.ProductDietTagDto;
import flab.nutridiary.review.repository.ProductReviewCount;
import flab.nutridiary.search.ProductDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProductSearchResponseMapper {
    private static final int TOP_DIET_TAG_COUNT = 3;

    public Page<ProductSearchResponse> toResponseList(
            Page<ProductDocument> productDocumentsPage,
            List<ProductReviewCount> productReviewCounts,
            List<ProductDietTagDto> productDietTagDtos
    ) {
        List<ProductDocument> productDocuments = productDocumentsPage.getContent();
        Pageable pageable = productDocumentsPage.getPageable();
        long total = productDocumentsPage.getTotalElements();

        Map<Long, Long> reviewCountMap = productReviewCounts.stream()
                .collect(Collectors.toMap(
                        ProductReviewCount::getProductId,
                        ProductReviewCount::getReviewCount
                ));


        Map<Long, List<ProductDietTagDto>> tagsByProductId = productDietTagDtos.stream()
                .collect(Collectors.groupingBy(ProductDietTagDto::getProductId));

        List<ProductSearchResponse> result = productDocuments.stream()
                .map(document -> {
                    Long productId = document.getProductId();

                    Long reviewCount = reviewCountMap.get(productId);

                    List<String> top3DietTags = Optional.ofNullable(tagsByProductId.get(productId))
                            .orElse(Collections.emptyList())
                            .stream()
                            .sorted(Comparator.comparing(ProductDietTagDto::getTag_count).reversed())
                            .limit(TOP_DIET_TAG_COUNT)
                            .map(ProductDietTagDto::getDietPlan)
                            .collect(Collectors.toList());

                    return new ProductSearchResponse(
                            productId,
                            document.getProductName(),
                            document.getProductCorp(),
                            reviewCount,
                            top3DietTags
                    );
                })
                .collect(Collectors.toList());
        return new PageImpl<>(result, pageable, total);
    }
}
