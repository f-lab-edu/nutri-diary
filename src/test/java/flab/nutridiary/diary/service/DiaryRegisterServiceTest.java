package flab.nutridiary.diary.service;

import flab.nutridiary.diary.domain.Diary;
import flab.nutridiary.diary.domain.DiaryRecord;
import flab.nutridiary.diary.domain.MealType;
import flab.nutridiary.diary.dto.DiaryRegisterRequest;
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
import java.util.List;

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
        DiaryRegisterRequest diaryRegisterRequest = new DiaryRegisterRequest(productId, MealType.BREAKFAST, BigDecimal.ONE, "gram", LocalDate.of(2024, 8, 1));

        // when
        Long savedId = diaryRegisterService.writeDiaryRecord(diaryRegisterRequest);

        // then
        Diary findDiary = diaryRepository.findById(savedId).get();
        List<DiaryRecord> diaryRecords = findDiary.getDiaryRecords();
        DiaryRecord diaryRecord = diaryRecords.get(0);

        assertThat(findDiary.getMemberId()).isEqualTo(1L);
        assertThat(findDiary.getDiaryDate()).isEqualTo(LocalDate.of(2024, 8, 1));
        assertThat(findDiary.getDiaryRecords().size()).isEqualTo(1);
        assertThat(diaryRecord.getMealType()).isEqualTo(MealType.BREAKFAST);
        assertThat(diaryRecord.getQuantity()).isEqualTo(BigDecimal.ONE);
        assertThat(diaryRecord.getServingUnit()).isEqualTo("gram");
        assertThat(diaryRecord.getCalculatedNutrition().getCalories()).isEqualTo(BigDecimal.valueOf(1.0));
        assertThat(diaryRecord.getCalculatedNutrition().getCarbohydrate()).isEqualTo(BigDecimal.valueOf(0.1));
        assertThat(diaryRecord.getCalculatedNutrition().getProtein()).isEqualTo(BigDecimal.valueOf(0.2));
    }

    @DisplayName("다이어리에 섭취한 식품을 기록한다. - 기존 다이어리에 추가.")
    @Test
    void writeDiaryRecord2() throws Exception {
        // given
        Long productId = savedProduct.getId();
        DiaryRegisterRequest diaryRegisterRequest1 = new DiaryRegisterRequest(productId, MealType.BREAKFAST, BigDecimal.ONE, "gram", LocalDate.of(2024, 8, 1));
        diaryRegisterService.writeDiaryRecord(diaryRegisterRequest1);

        DiaryRegisterRequest diaryRegisterRequest2 = new DiaryRegisterRequest(productId, MealType.LUNCH, BigDecimal.TEN, "개", LocalDate.of(2024, 8, 1));

        // when
        Long savedId = diaryRegisterService.writeDiaryRecord(diaryRegisterRequest2);

        // then
        Diary findDiary = diaryRepository.findById(savedId).get();
        List<DiaryRecord> diaryRecords = findDiary.getDiaryRecords();
        assertThat(diaryRecords.size()).isEqualTo(2);
    }

}