package flab.nutridiary.diary.service;

import flab.nutridiary.TestContainerSupport;
import flab.nutridiary.commom.exception.BusinessException;
import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.diary.domain.Diary;
import flab.nutridiary.diary.domain.DiaryRecord;
import flab.nutridiary.diary.domain.MealType;
import flab.nutridiary.diary.dto.request.AddDiaryRecordRequest;
import flab.nutridiary.diary.dto.request.DiaryRegisterRequest;
import flab.nutridiary.diary.dto.response.DiarySavedResponse;
import flab.nutridiary.diary.repository.DiaryRepository;
import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.domain.ServingUnit;
import flab.nutridiary.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

class DiaryRegisterServiceTest extends TestContainerSupport {

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
                .nutritionPerOneServingUnit(Nutrition.of(valueOf(50), valueOf(5), valueOf(10), valueOf(15)))
                .allowedProductServingUnits(
                        new ArrayList<>(List.of(
                                ServingUnit.asOneServingUnit("개"),
                                ServingUnit.ofGram(BigDecimal.ONE, valueOf(50)))
                        ))
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
                .clientChoiceServingUnitDescription("gram")
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
                .clientChoiceServingUnitDescription("gram")
                .calculatedNutrition(Nutrition.of(valueOf(1), valueOf(0.1), valueOf(0.2), valueOf(0.3))
                ).build();
        Diary savedDiary = diaryRepository.save(new Diary(LocalDate.of(2024, 8, 1), diaryRecord));
        Long diaryId = savedDiary.getId();

        AddDiaryRecordRequest addDiaryRecordRequest = new AddDiaryRecordRequest(productId, "LUNCH", BigDecimal.TEN, "개");

        // when
        DiarySavedResponse diarySavedResponse = addDiaryRecordService.addDiaryRecord(addDiaryRecordRequest, diaryId);
        Long savedId = diarySavedResponse.getDiaryId();

        // then
        Diary findDiary = diaryRepository.findById(savedId).get();

        DiaryRecord expectedDiaryRecord1 = DiaryRecord.builder()
                .productId(productId)
                .mealType(MealType.BREAKFAST)
                .quantity(BigDecimal.ONE)
                .clientChoiceServingUnitDescription("gram")
                .calculatedNutrition(Nutrition.of(valueOf(1), valueOf(0.1), valueOf(0.2), valueOf(0.3)))
                .build();

        DiaryRecord expectedDiaryRecord2 = DiaryRecord.builder()
                .productId(productId)
                .mealType(MealType.LUNCH)
                .quantity(BigDecimal.TEN)
                .clientChoiceServingUnitDescription("개")
                .calculatedNutrition(Nutrition.of(valueOf(500), valueOf(50), valueOf(100), valueOf(150)))
                .build();

        assertThat(findDiary)
                .extracting("diaryDate", "diaryRecords")
                .contains(LocalDate.of(2024, 8, 1), Set.of(expectedDiaryRecord2, expectedDiaryRecord1));
    }

    @DisplayName("허용되지 않은 서빙 단위를 입력하면 예외가 발생한다.")
    @Test
    void exception() throws Exception {
        // given
        Long productId = savedProduct.getId();
        DiaryRegisterRequest diaryRegisterRequest = new DiaryRegisterRequest(productId, "BREAKFAST", BigDecimal.ONE, "인분", LocalDate.of(2024, 8, 1));

        // when then
        BusinessException businessException = Assertions.assertThrows(BusinessException.class, () -> diaryRegisterService.createDiary(diaryRegisterRequest));
        assertThat(businessException.getMessage()).isEqualTo("허용되지 않은 서빙 단위입니다.");
        assertThat(businessException.getStatusCode()).isEqualTo(6002);
    }
}