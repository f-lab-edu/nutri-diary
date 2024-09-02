package flab.nutridiary.diary.domain;

import lombok.Getter;

@Getter
public enum MealType {
    BREAKFAST("아침식사"), LUNCH("점심식사"), DINNER("저녁식사"), SNACK("간식");

    private final String description;

    MealType(String description) {
        this.description = description;
    }

}
