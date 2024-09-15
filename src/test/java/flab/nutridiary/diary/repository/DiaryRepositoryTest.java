package flab.nutridiary.diary.repository;

import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.diary.domain.*;
import flab.nutridiary.diary.dto.response.query.DiaryRetrievalQueryDto;
import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.domain.ServingUnit;
import flab.nutridiary.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class DiaryRepositoryTest {
    @Autowired
    private NutritionCalculator nutritionCalculator;
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

    @DisplayName("memberId와 date로 DiaryRetrievalQueryDto를 조회한다.")
    @Test
    void findDiaryWithProductsByMemberIdAndDiaryDate() throws Exception {
        // given
        Diary diary = Diary.builder()
                .diaryDate(LocalDate.of(2024, 8, 10))
                .diaryRecord(DiaryRecord.of(
                        ProductIntakeInfo.builder()
                                .productId(savedProductIds.get(0))
                                .mealType("BREAKFAST")
                                .clientChoiceServingUnitDescription("개")
                                .quantity(valueOf(2))
                                .build(),
                        nutritionCalculator))
                .build();
        diary.addDiaryRecord(DiaryRecord.of(
                ProductIntakeInfo.builder()
                        .productId(savedProductIds.get(1))
                        .mealType("BREAKFAST")
                        .clientChoiceServingUnitDescription("컵")
                        .quantity(valueOf(2))
                        .build(),
                nutritionCalculator));
        diary.addDiaryRecord(DiaryRecord.of(
                ProductIntakeInfo.builder()
                        .productId(savedProductIds.get(0))
                        .mealType("LUNCH")
                        .clientChoiceServingUnitDescription("gram")
                        .quantity(valueOf(200))
                        .build(),
                nutritionCalculator));
        Long diaryId = diaryRepository.save(diary).getId();

        // when
        DiaryRetrievalQueryDto result = diaryRepository.findDiaryWithProductsByMemberIdAndDiaryDate(memberId, LocalDate.of(2024, 8, 10)).get();
        System.out.println(result.getDiarySummary().getBreakfast().getDiaryRecords());
        System.out.println(result.getDiarySummary().getLunch().getDiaryRecords());
        System.out.println(result.getDiarySummary().getDinner().getDiaryRecords());
        System.out.println(result.getDiarySummary().getSnack().getDiaryRecords());

        // then
        Nutrition expectedTotalNutrition = Nutrition.of(valueOf(800), valueOf(70), valueOf(100), valueOf(120));
        Nutrition expectedBreakfastNutrition = Nutrition.of(valueOf(600), valueOf(50), valueOf(60), valueOf(60));
        Nutrition expectedLunchNutrition = Nutrition.of(valueOf(200), valueOf(20), valueOf(40), valueOf(60));
        Nutrition expectedDinnerNutrition = Nutrition.empty();
        Nutrition expectedSnackNutrition = Nutrition.empty();

        assertThat(result.getDiaryId()).isEqualTo(diaryId);
        assertThat(result.getMemberId()).isEqualTo(memberId);
        assertThat(result.getDiaryDate()).isEqualTo(LocalDate.of(2024, 8, 10));
        assertThat(result.getDiarySummary().getDiaryTotalCalculatedNutrition()).isEqualTo(expectedTotalNutrition);
        //breakfast
        assertThat(result.getDiarySummary().getBreakfast().getMealType()).isEqualTo(MealType.BREAKFAST);
        assertThat(result.getDiarySummary().getBreakfast().getMealTotalCalculatedNutrition()).isEqualTo(expectedBreakfastNutrition);
        assertThat(result.getDiarySummary().getBreakfast().getDiaryRecords()).hasSize(2);
        //lunch
        assertThat(result.getDiarySummary().getLunch().getMealType()).isEqualTo(MealType.LUNCH);
        assertThat(result.getDiarySummary().getLunch().getMealTotalCalculatedNutrition()).isEqualTo(expectedLunchNutrition);
        assertThat(result.getDiarySummary().getLunch().getDiaryRecords()).hasSize(1);
        //dinner
        assertThat(result.getDiarySummary().getDinner().getMealType()).isEqualTo(MealType.DINNER);
        assertThat(result.getDiarySummary().getDinner().getMealTotalCalculatedNutrition()).isEqualTo(expectedDinnerNutrition);
        assertThat(result.getDiarySummary().getDinner().getDiaryRecords()).isEmpty();
        //snack
        assertThat(result.getDiarySummary().getSnack().getMealType()).isEqualTo(MealType.SNACK);
        assertThat(result.getDiarySummary().getSnack().getMealTotalCalculatedNutrition()).isEqualTo(expectedSnackNutrition);
        assertThat(result.getDiarySummary().getSnack().getDiaryRecords()).isEmpty();
    }
}