package flab.nutridiary.diary.repository;

import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.diary.domain.Diary;
import flab.nutridiary.diary.domain.DiaryRecord;
import flab.nutridiary.diary.domain.MealType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class DiaryRepositoryTest {

    @Autowired
    private DiaryRepository diaryRepository;

    private Long memberId = 1L;

    @DisplayName("Diary를 저장한다.")
    @Test
    void savd() throws Exception {
        // given
        LocalDate date = LocalDate.of(2024, 8, 1);
        Nutrition calculatedNutrition = Nutrition.of(valueOf(100), valueOf(10), valueOf(20), valueOf(30));
        DiaryRecord diaryRecord = DiaryRecord.builder()
                .productId(1L)
                .mealType(MealType.BREAKFAST)
                .quantity(ONE)
                .clientChoiceServingUnitDescription("개")
                .calculatedNutrition(calculatedNutrition)
                .build();
        Diary diary = new Diary(date, diaryRecord);

        // when
        Diary savedDiary = diaryRepository.save(diary);

        // then
        assertThat(savedDiary).extracting("memberId", "diaryDate", "diaryRecords")
                .containsExactly(memberId, date, Set.of(diaryRecord));
    }


    @DisplayName("memberId 와 date 로 사용자 Diary 를 조회한다.")
    @Test
    void findByMemberIdAndDiaryDate() throws Exception {

        // given
        LocalDate date = LocalDate.of(2024, 8, 1);
        Nutrition calculatedNutrition = Nutrition.of(valueOf(100), valueOf(10), valueOf(20), valueOf(30));
        DiaryRecord diaryRecord = DiaryRecord.builder()
                .productId(1L)
                .mealType(MealType.BREAKFAST)
                .quantity(ONE)
                .clientChoiceServingUnitDescription("개")
                .calculatedNutrition(calculatedNutrition)
                .build();
        Diary diary = new Diary(date, diaryRecord);
        diaryRepository.save(diary);

        // when
        Diary findDiary = diaryRepository.findByMemberIdAndDiaryDate(memberId, date).get();

        // then
        assertThat(findDiary).extracting("memberId", "diaryDate", "diaryRecords")
                .containsExactly(memberId, date, Set.of(diaryRecord));
    }

    @DisplayName("Diary의 id 로 다이어리를 조회한다.")
    @Test
    void findById() throws Exception {
        // given
        LocalDate date = LocalDate.of(2024, 8, 1);
        Nutrition calculatedNutrition = Nutrition.of(valueOf(100), valueOf(10), valueOf(20), valueOf(30));
        DiaryRecord diaryRecord = DiaryRecord.builder()
                .productId(1L)
                .mealType(MealType.BREAKFAST)
                .quantity(ONE)
                .clientChoiceServingUnitDescription("개")
                .calculatedNutrition(calculatedNutrition)
                .build();
        Diary diary = new Diary(date, diaryRecord);

        // when
        Diary saved = diaryRepository.save(diary);
        Long id = saved.getId();

        // then
        Diary findDiary = diaryRepository.findById(id).get();
        assertThat(findDiary).extracting("memberId", "diaryDate", "diaryRecords")
                .containsExactly(memberId, date, Set.of(diaryRecord));
    }
}