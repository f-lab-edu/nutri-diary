package flab.nutridiary.diary.service;

import flab.nutridiary.commom.exception.BusinessException;
import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.diary.domain.*;
import flab.nutridiary.diary.dto.response.query.DiaryRetrievalQueryDto;
import flab.nutridiary.diary.repository.DiaryRepository;
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

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class DiaryRetrievalServiceTest {
    @Autowired
    private NutritionCalculator nutritionCalculator;
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private DiaryRetrievalService diaryRetrievalService;
    @Autowired
    private ProductRepository productRepository;
    private List<Long> savedProductIds = new ArrayList<>();
    private Long memberId = 1L;
    LocalDate date = LocalDate.of(2024, 8, 10);
    private Long diaryId;

    @BeforeEach
    void init() {
        // product
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

        // diary
        Diary diary = Diary.builder()
                .diaryDate(date)
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
        diaryId = diaryRepository.save(diary).getId();
    }

    @DisplayName("memberId와 date로 DiaryRetrievalQueryDto를 조회할 수 있다.")
    @Test
    void getDiary() throws Exception {
        Nutrition expectedTotalNutrition = Nutrition.of(valueOf(800), valueOf(70), valueOf(100), valueOf(120));
        Nutrition expectedBreakfastNutrition = Nutrition.of(valueOf(600), valueOf(50), valueOf(60), valueOf(60));
        Nutrition expectedLunchNutrition = Nutrition.of(valueOf(200), valueOf(20), valueOf(40), valueOf(60));
        Nutrition expectedDinnerNutrition = Nutrition.empty();
        Nutrition expectedSnackNutrition = Nutrition.empty();

        DiaryRetrievalQueryDto diary = diaryRetrievalService.getDiary(memberId, date);

        assertThat(diary.getDiaryId()).isEqualTo(diaryId);
        assertThat(diary.getMemberId()).isEqualTo(memberId);
        assertThat(diary.getDiaryDate()).isEqualTo(LocalDate.of(2024, 8, 10));
        assertThat(diary.getDiarySummary().getDiaryTotalCalculatedNutrition()).isEqualTo(expectedTotalNutrition);
        //breakfast
        assertThat(diary.getDiarySummary().getBreakfast().getMealType()).isEqualTo(MealType.BREAKFAST);
        assertThat(diary.getDiarySummary().getBreakfast().getMealTotalCalculatedNutrition()).isEqualTo(expectedBreakfastNutrition);
        assertThat(diary.getDiarySummary().getBreakfast().getDiaryRecords()).hasSize(2);
        //lunch
        assertThat(diary.getDiarySummary().getLunch().getMealType()).isEqualTo(MealType.LUNCH);
        assertThat(diary.getDiarySummary().getLunch().getMealTotalCalculatedNutrition()).isEqualTo(expectedLunchNutrition);
        assertThat(diary.getDiarySummary().getLunch().getDiaryRecords()).hasSize(1);
        //dinner
        assertThat(diary.getDiarySummary().getDinner().getMealType()).isEqualTo(MealType.DINNER);
        assertThat(diary.getDiarySummary().getDinner().getMealTotalCalculatedNutrition()).isEqualTo(expectedDinnerNutrition);
        assertThat(diary.getDiarySummary().getDinner().getDiaryRecords()).isEmpty();
        //snack
        assertThat(diary.getDiarySummary().getSnack().getMealType()).isEqualTo(MealType.SNACK);
        assertThat(diary.getDiarySummary().getSnack().getMealTotalCalculatedNutrition()).isEqualTo(expectedSnackNutrition);
        assertThat(diary.getDiarySummary().getSnack().getDiaryRecords()).isEmpty();
    }

    @DisplayName("memberId와 date로 DiaryRetrievalQueryDto를 조회할 수 없으면 예외가 발생한다.")
    @Test
    void getDiaryEx() throws Exception {
        // given
        Long UnknownMemberId = 20L;

        // when then
        BusinessException exception = assertThrows(BusinessException.class, () ->
                diaryRetrievalService.getDiary(UnknownMemberId, date));
        assertThat(exception.getStatusCode()).isEqualTo(4004);
        assertThat(exception.getMessage()).isEqualTo("해당 다이어리를 찾을 수 없습니다.");
    }
}