package flab.nutridiary.diary.repository;

import flab.nutridiary.TestContainerSupport;
import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.diary.domain.Diary;
import flab.nutridiary.diary.domain.DiaryRecord;
import flab.nutridiary.diary.domain.MealType;
import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.domain.ServingUnit;
import flab.nutridiary.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

class DiaryRepositoryTest extends TestContainerSupport {
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private ProductRepository productRepository;
    private List<Long> savedProductIds = new ArrayList<>();
    private Long memberId = 1L;

    @BeforeEach
    void init() {
        Product product1 = Product.builder()
                .productName("사과")
                .productCorp("사과회사")
                .nutritionFacts(NutritionFacts.builder()
                        .nutritionPerOneServingUnit(Nutrition.of(valueOf(50), valueOf(5), valueOf(10), valueOf(15)))
                        .allowedProductServingUnits(
                                new ArrayList<>(List.of(
                                        ServingUnit.asOneServingUnit("개"),
                                        ServingUnit.ofGram(BigDecimal.ONE, valueOf(50)))))
                        .build())
                .build();

        Product product2 = Product.builder()
                .productName("바나나")
                .productCorp("바나나회사")
                .nutritionFacts(NutritionFacts.builder()
                        .nutritionPerOneServingUnit(Nutrition.of(valueOf(250), valueOf(20), valueOf(20), valueOf(15)))
                        .allowedProductServingUnits(
                                new ArrayList<>(List.of(
                                        ServingUnit.asOneServingUnit("컵"),
                                        ServingUnit.ofGram(BigDecimal.ONE, valueOf(150)))))
                        .build())
                .build();
        savedProductIds.add(productRepository.save(product1).getId());
        savedProductIds.add(productRepository.save(product2).getId());
    }

    @DisplayName("Diary를 저장한다.")
    @Test
    void save() throws Exception {
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