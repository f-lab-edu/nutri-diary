package flab.nutridiary.product.service;

import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.dto.response.ProductSearchResponse;
import flab.nutridiary.productDietTag.ProductDietTagDto;
import flab.nutridiary.review.repository.ProductReviewCount;
import flab.nutridiary.search.ProductDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
        Map<Long, Long> reviewCountMap = buildReviewCountMap(productReviewCounts);
        Map<Long, List<ProductDietTagDto>> tagsByProductId = buildTagsByProductId(productDietTagDtos);

        List<ProductSearchResponse> responses = productDocumentsPage
                .getContent()
                .stream()
                .map(document -> toProductSearchResponse(
                        document.getProductId(),
                        document.getProductName(),
                        document.getProductCorp(),
                        reviewCountMap,
                        tagsByProductId
                ))
                .collect(Collectors.toList());

        return new PageImpl<>(responses, productDocumentsPage.getPageable(), productDocumentsPage.getTotalElements());
    }

    public List<ProductSearchResponse> toResponseList(
            List<Product> products,
            List<ProductReviewCount> productReviewCounts,
            List<ProductDietTagDto> productDietTagDtos
    ) {
        Map<Long, Long> reviewCountMap = buildReviewCountMap(productReviewCounts);
        Map<Long, List<ProductDietTagDto>> tagsByProductId = buildTagsByProductId(productDietTagDtos);

        return products.stream()
                .map(product -> toProductSearchResponse(
                        product.getId(),
                        product.getProductName(),
                        product.getProductCorp(),
                        reviewCountMap,
                        tagsByProductId
                ))
                .collect(Collectors.toList());
    }

    private Map<Long, Long> buildReviewCountMap(List<ProductReviewCount> productReviewCounts) {
        return productReviewCounts.stream()
                .collect(Collectors.toMap(
                        ProductReviewCount::getProductId,
                        ProductReviewCount::getReviewCount
                ));
    }

    private Map<Long, List<ProductDietTagDto>> buildTagsByProductId(List<ProductDietTagDto> productDietTagDtos) {
        return productDietTagDtos.stream()
                .collect(Collectors.groupingBy(ProductDietTagDto::getProductId));
    }

    private ProductSearchResponse toProductSearchResponse(
            Long productId,
            String productName,
            String productCorp,
            Map<Long, Long> reviewCountMap,
            Map<Long, List<ProductDietTagDto>> tagsByProductId
    ) {
        Long reviewCount = reviewCountMap.getOrDefault(productId, 0L);
        List<String> top3DietTags = Optional.ofNullable(tagsByProductId.get(productId))
                .orElse(Collections.emptyList())
                .stream()
                .sorted(Comparator.comparing(ProductDietTagDto::getTag_count).reversed())
                .limit(TOP_DIET_TAG_COUNT)
                .map(ProductDietTagDto::getDietPlan)
                .collect(Collectors.toList());

        return new ProductSearchResponse(
                productId,
                productName,
                productCorp,
                reviewCount,
                top3DietTags
        );
    }
}
