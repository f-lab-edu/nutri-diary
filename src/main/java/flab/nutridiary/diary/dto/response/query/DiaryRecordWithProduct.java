package flab.nutridiary.diary.dto.response.query;

import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.diary.domain.MealType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@RequiredArgsConstructor
@Getter
public class DiaryRecordWithProduct {
    private final Long diaryRecordId;
    private final MealType mealType;
    private final String productName;
    private final String productCorp;
    private final BigDecimal quantity;
    private final String clientChoiceServingUnitDescription;
    private final Nutrition calculatedNutrition;
}
