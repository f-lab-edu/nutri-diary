package flab.nutridiary.review.service;

import flab.nutridiary.commom.exception.BusinessException;
import flab.nutridiary.file.FileStore;
import flab.nutridiary.productDietTag.ProductDietTag;
import flab.nutridiary.productDietTag.repository.ProductDietTagRepository;
import flab.nutridiary.productStore.domain.StoreProduct;
import flab.nutridiary.productStore.repository.StoreProductCrudRepository;
import flab.nutridiary.review.domain.Review;
import flab.nutridiary.review.dto.request.CreateReviewRequest;
import flab.nutridiary.review.dto.response.CreateReviewResponse;
import flab.nutridiary.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static flab.nutridiary.commom.exception.StatusConst.DUPLICATED_PRODUCT_REVIEW;

@Transactional
@RequiredArgsConstructor
@Service
public class ReviewResisterService {
    private final StoreProductCrudRepository productStoreRepository;
    private final ProductDietTagRepository productDietTagRepository;
    private final ReviewRepository reviewRepository;
    private final FileStore fileStore;
    private final ReviewResisterValidator reviewResisterValidator;

    public CreateReviewResponse create(Long memberId, CreateReviewRequest createReviewRequest) {
        if (reviewResisterValidator.isExists(memberId, createReviewRequest.getProductId())) {
            throw new BusinessException(DUPLICATED_PRODUCT_REVIEW);
        }
        saveProductStore(createReviewRequest);
        saveProductDietTag(createReviewRequest);

        Review savedReview = createReview(createReviewRequest);

        return CreateReviewResponse.of(savedReview.getId());
    }

    private void saveProductStore(CreateReviewRequest request) {
        productStoreRepository.save(StoreProduct.builder()
                .productId(request.getProductId())
                .storeId(request.getStoreId())
                .build());
    }

    private void saveProductDietTag(CreateReviewRequest request) {
        List<ProductDietTag> existingTags = productDietTagRepository.findByProductId(request.getProductId());
        List<Long> requestTagIds = request.getDietTagId();

        Map<Long, ProductDietTag> existingTagMap = existingTags.stream()
                .collect(Collectors.toMap(ProductDietTag::getDietTagId, Function.identity()));

        List<ProductDietTag> tagsToSave = new ArrayList<>();

        for (Long tagId : requestTagIds) {
            if (existingTagMap.containsKey(tagId)) {
                existingTagMap.get(tagId).increaseTagCount();
            } else {
                // 새로운 태그는 리스트에 추가
                tagsToSave.add(ProductDietTag.builder()
                        .productId(request.getProductId())
                        .dietTagId(tagId)
                        .build());
            }
        }

        // 변경된 태그들을 저장
        tagsToSave.addAll(existingTags); // 수정된 기존 태그도 포함
        productDietTagRepository.saveAll(tagsToSave);
    }


    private Review createReview(CreateReviewRequest request) {
        String imageUrl = Optional.ofNullable(request.getImage())
                .map(fileStore::uploadReviewImage)
                .orElse(null);

        return reviewRepository.save(Review.builder()
                .productId(request.getProductId())
                .content(request.getContent())
                .rating(request.getRating())
                .imageUrl(imageUrl)
                .build());
    }
}

