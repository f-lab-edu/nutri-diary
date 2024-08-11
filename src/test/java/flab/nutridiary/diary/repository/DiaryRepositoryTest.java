package flab.nutridiary.diary.repository;

import flab.nutridiary.diary.domain.CalculatedNutrition;
import flab.nutridiary.diary.domain.Diary;
import flab.nutridiary.diary.domain.DiaryRecord;
import flab.nutridiary.diary.domain.MealType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class DiaryRepositoryTest {

    @Autowired
    private DiaryRepository diaryRepository;

    @DisplayName("memberId 와 date 로 Diary 조회")
    @Test
    void findByMemberIdAndDate() throws Exception {
        // given
        Long memberId = 1L;
        LocalDate date = LocalDate.of(2024, 8, 1);
        CalculatedNutrition calculatedNutrition = CalculatedNutrition.builder()
                .calories(BigDecimal.valueOf(100))
                .carbohydrate(BigDecimal.valueOf(10))
                .protein(BigDecimal.valueOf(20))
                .fat(BigDecimal.valueOf(30))
                .build();
        DiaryRecord diaryRecord = DiaryRecord.builder()
                .productId(1L)
                .mealType(MealType.BREAKFAST)
                .quantity(BigDecimal.ONE)
                .servingUnit("개")
                .calculatedNutrition(calculatedNutrition)
                .build();
        Diary diary = new Diary(date, diaryRecord);
        diaryRepository.save(diary);

        // when
        Diary findDiary = diaryRepository.findByMemberIdAndDate(memberId, date).get();

        // then
        assertThat(findDiary.getDiaryDate()).isEqualTo(date);
        assertThat(findDiary.getDiaryRecords().get(0).getMealType()).isEqualTo(MealType.BREAKFAST);
        assertThat(findDiary.getDiaryRecords().get(0).getQuantity()).isEqualTo(BigDecimal.ONE);
        assertThat(findDiary.getDiaryRecords().get(0).getServingUnit()).isEqualTo("개");
        assertThat(findDiary.getDiaryRecords().get(0).getCalculatedNutrition().getCalories()).isEqualTo(BigDecimal.valueOf(100));
        assertThat(findDiary.getDiaryRecords().get(0).getCalculatedNutrition().getCarbohydrate()).isEqualTo(BigDecimal.valueOf(10));
        assertThat(findDiary.getDiaryRecords().get(0).getCalculatedNutrition().getProtein()).isEqualTo(BigDecimal.valueOf(20));
        assertThat(findDiary.getDiaryRecords().get(0).getCalculatedNutrition().getFat()).isEqualTo(BigDecimal.valueOf(30));
    }

    @DisplayName("새로운 Diary 저장")
    @Test
    void save() throws Exception {
        // given
        LocalDate date = LocalDate.of(2024, 8, 1);
        CalculatedNutrition calculatedNutrition = CalculatedNutrition.builder()
                .calories(BigDecimal.valueOf(100))
                .carbohydrate(BigDecimal.valueOf(10))
                .protein(BigDecimal.valueOf(20))
                .fat(BigDecimal.valueOf(30))
                .build();
        DiaryRecord diaryRecord = DiaryRecord.builder()
                .productId(1L)
                .mealType(MealType.BREAKFAST)
                .quantity(BigDecimal.ONE)
                .servingUnit("개")
                .calculatedNutrition(calculatedNutrition)
                .build();
        Diary diary = new Diary(date, diaryRecord);

        // when
        Diary saved = diaryRepository.save(diary);

        // then
        Long id = saved.getId();
        Diary findDiary = diaryRepository.findById(id).get();

        assertThat(findDiary.getDiaryDate()).isEqualTo(date);
        assertThat(findDiary.getDiaryRecords().get(0).getMealType()).isEqualTo(MealType.BREAKFAST);
        assertThat(findDiary.getDiaryRecords().get(0).getQuantity()).isEqualTo(BigDecimal.ONE);
        assertThat(findDiary.getDiaryRecords().get(0).getServingUnit()).isEqualTo("개");
        assertThat(findDiary.getDiaryRecords().get(0).getCalculatedNutrition().getCalories()).isEqualTo(BigDecimal.valueOf(100));
        assertThat(findDiary.getDiaryRecords().get(0).getCalculatedNutrition().getCarbohydrate()).isEqualTo(BigDecimal.valueOf(10));
        assertThat(findDiary.getDiaryRecords().get(0).getCalculatedNutrition().getProtein()).isEqualTo(BigDecimal.valueOf(20));
        assertThat(findDiary.getDiaryRecords().get(0).getCalculatedNutrition().getFat()).isEqualTo(BigDecimal.valueOf(30));
    }
}