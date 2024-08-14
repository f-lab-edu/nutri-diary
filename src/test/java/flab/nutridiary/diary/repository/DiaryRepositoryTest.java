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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class DiaryRepositoryTest {

    @Autowired
    private DiaryRepository diaryRepository;

    private Long memberId = 1L;

    @DisplayName("memberId 와 date 로 사용자 Diary 를 조회한다.")
    @Test
    void findByMemberIdAndDate() throws Exception {

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
        diaryRepository.save(diary);

        // when
        Diary findDiary = diaryRepository.findByMemberIdAndDate(memberId, date).get();

        // then
        assertThat(findDiary).extracting("memberId", "diaryDate", "diaryRecords")
                .containsExactly(memberId, date, Set.of(diaryRecord));
    }

    @DisplayName("Diary 의 id 로 다이어리를 조회한다.")
    @Test
    void findById() throws Exception {
        // given
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
        assertThat(findDiary).extracting("memberId", "diaryDate", "diaryRecords")
                .containsExactly(memberId, date, Set.of(diaryRecord));
    }
}