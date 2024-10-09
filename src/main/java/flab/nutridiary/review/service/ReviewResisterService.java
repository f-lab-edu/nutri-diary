package flab.nutridiary.review.service;

import flab.nutridiary.commom.exception.BusinessException;
import flab.nutridiary.commom.file.FileStoreService;
import flab.nutridiary.productDietTag.ProductDietTag;
import flab.nutridiary.productDietTag.ProductDietTagRepository;
import flab.nutridiary.productStore.ProductStore;
import flab.nutridiary.productStore.ProductStoreRepository;
import flab.nutridiary.review.domain.Review;
import flab.nutridiary.review.dto.request.CreateReviewRequest;
import flab.nutridiary.review.dto.response.CreateReviewResponse;
import flab.nutridiary.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static flab.nutridiary.commom.exception.StatusConst.DUPLICATED_PRODUCT_REVIEW;

@Transactional
@RequiredArgsConstructor
@Service
public class ReviewResisterService {
    private final ProductStoreRepository productStoreRepository;
    private final ProductDietTagRepository productDietTagRepository;
    private final ReviewRepository reviewRepository;
    private final FileStoreService fileStoreService;
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

    private ProductStore saveProductStore(CreateReviewRequest request) {
        return productStoreRepository.save(ProductStore.builder()
                .productId(request.getProductId())
                .storeId(request.getStoreId())
                .build());
    }

    private ProductDietTag saveProductDietTag(CreateReviewRequest request) {
        return productDietTagRepository.save(ProductDietTag.builder()
                .productId(request.getProductId())
                .dietTagId(request.getDietTagId())
                .build());
    }

    private Review createReview(CreateReviewRequest request) {
        String imageUrl = Optional.ofNullable(request.getImage())
                .map(fileStoreService::uploadReviewImage)
                .orElse(null);

        return reviewRepository.save(Review.builder()
                .productId(request.getProductId())
                .content(request.getContent())
                .rating(request.getRating())
                .imageUrl(imageUrl)
                .build());
    }
}

