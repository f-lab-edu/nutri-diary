package flab.nutridiary.diary.dto.response.query;

import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.diary.domain.MealType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
@RequiredArgsConstructor
public class MealSummary {
    private final MealType mealType;
    private final Nutrition mealTotalCalculatedNutrition;
    private final List<DiaryRecordWithProduct> diaryRecords;

    public static MealSummary empty(MealType mealType) {
        return new MealSummary(mealType, Nutrition.empty(), List.of());
    }

    public static MealSummary of(MealType mealType, List<DiaryRecordWithProduct> diaryRecords) {
        return MealSummary.builder()
                .mealType(mealType)
                .mealTotalCalculatedNutrition(
                        diaryRecords.stream()
                                .map(DiaryRecordWithProduct::getCalculatedNutrition)
                                .reduce(Nutrition::add)
                                .orElse(Nutrition.empty())
                )
                .diaryRecords(diaryRecords)
                .build();
    }
}
