package flab.nutridiary.diary.repository;

import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.diary.domain.*;
import flab.nutridiary.diary.dto.response.query.DiaryRecordWithProduct;
import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.domain.ServingUnit;
import flab.nutridiary.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
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

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class DiaryRetrievalRepositoryTest{
    @Autowired
    private NutritionCalculator nutritionCalculator;
    @Autowired
    private DiaryRetrievalRepository diaryRetrievalRepository;
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

    @DisplayName("memberId와 date로 DiaryRecordWithProduct 리스트를 조회한다.")
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
        List<DiaryRecordWithProduct> results = diaryRetrievalRepository.findDiaryWithProductAllByMemberIdAndDiaryDate(memberId, LocalDate.of(2024, 8, 10));

        // then
        List<DiaryRecordWithProduct> expectedResults = List.of(
                new DiaryRecordWithProduct(1L, 1L, LocalDate.of(2024, 8, 10), 1L,  MealType.BREAKFAST, "사과", "사과회사", valueOf(2), "개", Nutrition.of(valueOf(100), valueOf(10), valueOf(20), valueOf(30))),
                new DiaryRecordWithProduct(1L, 1L, LocalDate.of(2024, 8, 10), 2L, MealType.LUNCH, "사과", "사과회사", valueOf(200), "gram", Nutrition.of(valueOf(200), valueOf(20), valueOf(40), valueOf(60))),
                new DiaryRecordWithProduct(1L, 1L, LocalDate.of(2024, 8, 10), 3L, MealType.BREAKFAST, "바나나", "바나나회사", valueOf(2), "컵", Nutrition.of(valueOf(500), valueOf(40), valueOf(40), valueOf(30)))
        );

        Assertions.assertThat(results).containsExactlyInAnyOrderElementsOf(expectedResults);
    }


}