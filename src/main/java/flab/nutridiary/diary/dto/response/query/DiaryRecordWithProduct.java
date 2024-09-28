package flab.nutridiary.diary.dto.response.query;

import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.diary.domain.MealType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(exclude = {"diaryId", "diaryRecordId"})
@Builder
@RequiredArgsConstructor
@ToString
@Getter
public class DiaryRecordWithProduct {
    private final Long diaryId;
    private final Long memberId;
    private final LocalDate diaryDate;
    private final Long diaryRecordId;
    private final MealType mealType;
    private final String productName;
    private final String productCorp;
    private final BigDecimal quantity;
    private final String clientChoiceServingUnitDescription;
    private final Nutrition calculatedNutrition;
}
