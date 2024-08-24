package flab.nutridiary.diary.service;

import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.diary.domain.Diary;
import flab.nutridiary.diary.domain.DiaryRecord;
import flab.nutridiary.diary.domain.MealType;
import flab.nutridiary.diary.dto.AddDiaryRecordRequest;
import flab.nutridiary.diary.dto.DiaryRegisterRequest;
import flab.nutridiary.diary.dto.DiarySavedResponse;
import flab.nutridiary.diary.repository.DiaryRepository;
import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.Product;
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
import java.util.Set;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class DiaryRegisterServiceTest {

    @Autowired
    private DiaryRegisterService diaryRegisterService;
    @Autowired
    private AddDiaryRecordService addDiaryRecordService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DiaryRepository diaryRepository;

    private Product savedProduct;

    @BeforeEach
    void init() {
        // given
        NutritionFacts nutritionFacts = NutritionFacts.builder()
                .totalNutrition(Nutrition.of(valueOf(100), valueOf(10), valueOf(20), valueOf(30)))
                .productServingSize(valueOf(2))
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
    void registDiary() throws Exception {
        // given
        Long productId = savedProduct.getId();
        DiaryRegisterRequest diaryRegisterRequest = new DiaryRegisterRequest(productId, "BREAKFAST", BigDecimal.ONE, "gram", LocalDate.of(2024, 8, 1));

        // when
        DiarySavedResponse diarySavedResponse = diaryRegisterService.createDiary(diaryRegisterRequest);
        Long savedDiaryId = diarySavedResponse.getDiaryId();

        // then
        Diary findDiary = diaryRepository.findById(savedDiaryId).get();

        DiaryRecord expectedDiaryRecord = DiaryRecord.builder()
                .productId(productId)
                .mealType(MealType.BREAKFAST)
                .quantity(BigDecimal.ONE)
                .servingUnit("gram")
                .calculatedNutrition(Nutrition.of(valueOf(1), valueOf(0.1), valueOf(0.2), valueOf(0.3)))
                .build();

        assertThat(findDiary)
                .extracting("diaryDate", "diaryRecords")
                .contains(LocalDate.of(2024, 8, 1), Set.of(expectedDiaryRecord));
    }

    @DisplayName("기존의 다이어리에 섭취한 식품을 기록한다.")
    @Test
    void registDiary2() throws Exception {
        // given
        Long productId = savedProduct.getId();
        DiaryRecord diaryRecord = DiaryRecord.builder()
                .productId(productId)
                .mealType(MealType.BREAKFAST)
                .quantity(BigDecimal.ONE)
                .servingUnit("gram")
                .calculatedNutrition(Nutrition.of(valueOf(1), valueOf(0.1), valueOf(0.2), valueOf(0.3))
                ).build();
        Diary savedDiary = diaryRepository.save(new Diary(LocalDate.of(2024, 8, 1), diaryRecord));
        Long diaryId = savedDiary.getId();

        AddDiaryRecordRequest addDiaryRecordRequest = new AddDiaryRecordRequest(productId, "LUNCH", BigDecimal.TEN, "컵");

        // when
        DiarySavedResponse diarySavedResponse = addDiaryRecordService.addDiaryRecord(addDiaryRecordRequest, diaryId);
        Long savedId = diarySavedResponse.getDiaryId();

        // then
        Diary findDiary = diaryRepository.findById(savedId).get();

        DiaryRecord expectedDiaryRecord1 = DiaryRecord.builder()
                .productId(productId)
                .mealType(MealType.BREAKFAST)
                .quantity(BigDecimal.ONE)
                .servingUnit("gram")
                .calculatedNutrition(Nutrition.of(valueOf(1), valueOf(0.1), valueOf(0.2), valueOf(0.3)))
                .build();

        DiaryRecord expectedDiaryRecord2 = DiaryRecord.builder()
                .productId(productId)
                .mealType(MealType.LUNCH)
                .quantity(BigDecimal.TEN)
                .servingUnit("컵")
                .calculatedNutrition(Nutrition.of(valueOf(500), valueOf(50), valueOf(100), valueOf(150)))
                .build();

        assertThat(findDiary)
                .extracting("diaryDate", "diaryRecords")
                .contains(LocalDate.of(2024, 8, 1), Set.of(expectedDiaryRecord2, expectedDiaryRecord1));
    }
}