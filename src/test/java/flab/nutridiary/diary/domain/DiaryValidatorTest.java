package flab.nutridiary.diary.domain;

import flab.nutridiary.commom.exception.BusinessException;
import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.product.domain.ServingUnit;
import flab.nutridiary.diary.repository.DiaryRepository;
import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Profile("test")
@Transactional
@SpringBootTest
class DiaryValidatorTest {
    @Autowired
    private DiaryValidator diaryValidator;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DiaryRepository diaryRepository;

    private Product savedProduct;

    @BeforeEach
    void init() {
        // given
        NutritionFacts nutritionFacts = NutritionFacts.builder()
                .nutritionPerOneServingUnit(Nutrition.of(valueOf(500), valueOf(5), valueOf(10), valueOf(15)))
                .allowedProductServingUnits(
                        new ArrayList<>(List.of(
                                ServingUnit.asOneServingUnit("컵"),
                                ServingUnit.ofGram(BigDecimal.ONE, valueOf(50)))
                        )
                )
                .build();

        Product product = Product.builder()
                .productName("사과")
                .productCorp("사과회사")
                .nutritionFacts(nutritionFacts)
                .build();

        // when
        savedProduct = productRepository.save(product);
    }

    @DisplayName("동일한 다이어리를 생성한다면 저장에 실패한다.")
    @Test
    void validate() throws Exception {
        // given
        Long productId = savedProduct.getId();
        DiaryRecord diaryRecord = DiaryRecord.builder()
                .productId(productId)
                .mealType(MealType.BREAKFAST)
                .quantity(BigDecimal.ONE)
                .clientChoiceServingUnitDescription("gram")
                .calculatedNutrition(Nutrition.of(valueOf(1), valueOf(0.1), valueOf(0.2), valueOf(0.3))
                ).build();
        diaryRepository.save(new Diary(LocalDate.of(2024, 8, 1), diaryRecord));

        // when then
        Diary diary2 = new Diary(LocalDate.of(2024, 8, 1), diaryRecord);
        BusinessException bizException = assertThrows(BusinessException.class, () -> diaryValidator.validate(diary2));
        assertThat(bizException.getStatusCode()).isEqualTo(4005);
        assertThat(bizException.getMessage()).isEqualTo("이미 등록된 다이어리입니다.");
    }
}