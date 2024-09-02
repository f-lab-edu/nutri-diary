package flab.nutridiary.diary.service;

import flab.nutridiary.diary.domain.ProductIntakeInfo;
import flab.nutridiary.diary.dto.DiaryRegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductIntakeInfoMapper {
    public ProductIntakeInfo from(DiaryRegisterRequest diaryRegisterRequest) {
        return ProductIntakeInfo.builder()
                .productId(diaryRegisterRequest.getProductId())
                .mealType(diaryRegisterRequest.getMealType())
                .clientChoiceServingUnitDescription(diaryRegisterRequest.getClientChoiceServingUnit())
                .quantity(diaryRegisterRequest.getQuantity())
                .build();
    }
}
