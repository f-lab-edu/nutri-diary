package flab.nutridiary.diary.dto.response.query;

import flab.nutridiary.commom.generic.Nutrition;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class DiarySummary {
    private final Nutrition diaryTotalCalculatedNutrition;
    private final MealSummary breakfast;
    private final MealSummary lunch;
    private final MealSummary dinner;
    private final MealSummary snack;

    @Builder
    public DiarySummary(MealSummary breakfast, MealSummary lunch, MealSummary dinner, MealSummary snack) {
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.snack = snack;
        this.diaryTotalCalculatedNutrition = calculateTotalCalculatedNutrition(breakfast, lunch, dinner, snack);
    }

    private Nutrition calculateTotalCalculatedNutrition(MealSummary breakfast, MealSummary lunch, MealSummary dinner, MealSummary snack) {
        return Nutrition.empty()
                .add(breakfast.getMealTotalCalculatedNutrition())
                .add(lunch.getMealTotalCalculatedNutrition())
                .add(dinner.getMealTotalCalculatedNutrition())
                .add(snack.getMealTotalCalculatedNutrition());
    }
}
