package flab.nutridiary.diary.service;

import flab.nutridiary.diary.domain.ProductIntakeInfo;
import flab.nutridiary.diary.dto.NewDiaryRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductIntakeInfoExtractor {
    public ProductIntakeInfo extract(NewDiaryRequest newDiaryRequest) {
        return ProductIntakeInfo.builder()
                .productId(newDiaryRequest.getProductId())
                .mealType(newDiaryRequest.getMealType())
                .servingUnit(newDiaryRequest.getServingUnit())
                .quantity(newDiaryRequest.getQuantity())
                .ServingUnit(newDiaryRequest.getServingUnit())
                .build();
    }
}
