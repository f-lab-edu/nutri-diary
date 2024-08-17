package flab.nutridiary.diary.service;

import flab.nutridiary.diary.domain.*;
import flab.nutridiary.diary.dto.DiaryRegisterRequest;
import flab.nutridiary.diary.dto.DiaryRegisterResponse;
import flab.nutridiary.diary.repository.DiaryRepository;
import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class DiaryRegisterServiceTest {

    @Autowired
    private DiaryRegisterService diaryRegisterService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DiaryRepository diaryRepository;
    private Product savedProduct;

    @BeforeEach
    void init() {
        // given
        NutritionFacts nutritionFacts = NutritionFacts.builder()
                .productTotalCalories(valueOf(100))
                .productTotalCarbohydrate(valueOf(10))
                .productTotalProtein(valueOf(20))
                .productTotalFat(valueOf(30))
                .productServingSize(valueOf(1))
                .productServingUnit("컵")
                .productTotalWeightGram(valueOf(100))
                .build();

        Product product = Product.builder()
                .productName("사과")
                .productCorp("사과회사")
                .nutritionFacts(nutritionFacts)
                .build();

        // when
        savedProduct = productRepository.save(product);
    }

    @DisplayName("다이어리에 섭취한 식품을 기록한다.")
    @Test
    void writeDiaryRecord() throws Exception {
        // given
        Long productId = savedProduct.getId();
        DiaryRegisterRequest diaryRegisterRequest = new DiaryRegisterRequest(productId, "BREAKFAST", BigDecimal.ONE, "gram", LocalDate.of(2024, 8, 1));

        // when
        DiaryRegisterResponse diaryRegisterResponse = diaryRegisterService.writeDiaryRecord(diaryRegisterRequest);
        Long savedDiaryId = diaryRegisterResponse.getDiaryId();

        // then
        Diary findDiary = diaryRepository.findById(savedDiaryId).get();

        DiaryRecord expectedDiaryRecord = DiaryRecord.builder()
                .productId(productId)
                .mealType(MealType.BREAKFAST)
                .quantity(BigDecimal.ONE)
                .servingUnit("gram")
                .calculatedNutrition(CalculatedNutrition.builder()
                        .calories(BigDecimal.valueOf(1))
                        .carbohydrate(BigDecimal.valueOf(0.1))
                        .protein(BigDecimal.valueOf(0.2))
                        .fat(BigDecimal.valueOf(0.3))
                        .build())
                .build();

        assertThat(findDiary)
                .extracting("diaryDate", "diaryRecords")
                .contains(LocalDate.of(2024, 8, 1), Set.of(expectedDiaryRecord));
    }

    @DisplayName("다이어리에 섭취한 식품을 기록한다. - 기존 다이어리에 추가.")
    @Test
    void writeDiaryRecord2() throws Exception {
        // given
        Long productId = savedProduct.getId();
        DiaryRegisterRequest diaryRegisterRequest1 = new DiaryRegisterRequest(productId, "BREAKFAST", BigDecimal.ONE, "gram", LocalDate.of(2024, 8, 1));
        diaryRegisterService.writeDiaryRecord(diaryRegisterRequest1);

        DiaryRegisterRequest diaryRegisterRequest2 = new DiaryRegisterRequest(productId, "LUNCH", BigDecimal.TEN, "컵", LocalDate.of(2024, 8, 1));

        // when
        DiaryRegisterResponse diaryRegisterResponse = diaryRegisterService.writeDiaryRecord(diaryRegisterRequest2);
        Long savedId = diaryRegisterResponse.getDiaryId();

        // then
        Diary findDiary = diaryRepository.findById(savedId).get();

        DiaryRecord expectedDiaryRecord1 = DiaryRecord.builder()
                .productId(productId)
                .mealType(MealType.BREAKFAST)
                .quantity(BigDecimal.ONE)
                .servingUnit("gram")
                .calculatedNutrition(CalculatedNutrition.builder()
                        .calories(BigDecimal.valueOf(1))
                        .carbohydrate(BigDecimal.valueOf(0.1))
                        .protein(BigDecimal.valueOf(0.2))
                        .fat(BigDecimal.valueOf(0.3))
                        .build())
                .build();

        DiaryRecord expectedDiaryRecord2 = DiaryRecord.builder()
                .productId(productId)
                .mealType(MealType.LUNCH)
                .quantity(BigDecimal.TEN)
                .servingUnit("컵")
                .calculatedNutrition(CalculatedNutrition.builder()
                        .calories(BigDecimal.valueOf(1000))
                        .carbohydrate(BigDecimal.valueOf(100))
                        .protein(BigDecimal.valueOf(200))
                        .fat(BigDecimal.valueOf(300))
                        .build())
                .build();

        assertThat(findDiary)
                .extracting("diaryDate", "diaryRecords")
                .contains(LocalDate.of(2024, 8, 1), Set.of(expectedDiaryRecord2, expectedDiaryRecord1));


    }

}