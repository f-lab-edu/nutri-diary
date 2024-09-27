package flab.nutridiary.diary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.diary.domain.*;
import flab.nutridiary.product.domain.ServingUnit;
import flab.nutridiary.diary.dto.request.AddDiaryRecordRequest;
import flab.nutridiary.diary.dto.request.DiaryRegisterRequest;
import flab.nutridiary.diary.repository.DiaryRepository;
import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class DiaryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private NutritionCalculator nutritionCalculator;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private List<Long> savedProductIds = new ArrayList<>();

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


    @DisplayName("다이어리 등록 테스트")
    @Test
    void createDiary() throws Exception {
        // given
        DiaryRegisterRequest diaryRegisterRequest = new DiaryRegisterRequest(savedProductIds.getFirst(), "BREAKFAST", valueOf(1), "gram", LocalDate.of(2024, 8, 10));

        // when then
        mockMvc.perform(
                        post("/diary/new")
                                .content(objectMapper.writeValueAsString(diaryRegisterRequest))
                                .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(2001))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.diaryId").exists());
    }

    @DisplayName("기존 다이어리에 기록을 추가한다.")
    @Test
    void addDiaryRecord() throws Exception {
        // given
        Diary diary = new Diary(LocalDate.of(2024, 8, 10),
                DiaryRecord.builder()
                        .productId(savedProductIds.getFirst())
                        .mealType(MealType.BREAKFAST)
                        .quantity(valueOf(1))
                        .clientChoiceServingUnitDescription("컵")
                        .calculatedNutrition(Nutrition.of(valueOf(100), valueOf(10), valueOf(20), valueOf(30)))
                        .build());
        Long diaryId = diaryRepository.save(diary).getId();

        AddDiaryRecordRequest addDiaryRecordRequest = new AddDiaryRecordRequest(savedProductIds.getFirst(), "BREAKFAST", BigDecimal.valueOf(10), "gram");

        // when then
        mockMvc.perform(
                        post("/diary/{diaryId}", diaryId)
                                .content(objectMapper.writeValueAsString(addDiaryRecordRequest))
                                .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(2001))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.diaryId").value(diaryId));
    }

    @DisplayName("허용되지 않은 서빙 단위는 예외가 발생한다.")
    @Test
    void addDiaryRecordWithInvalidServingUnit() throws Exception {
        // given
        DiaryRegisterRequest diaryRegisterRequest = new DiaryRegisterRequest(savedProductIds.getFirst(), "BREAKFAST", valueOf(1), "InvalidServingUnit", LocalDate.of(2024, 8, 10));

        // when then
        mockMvc.perform(
                        post("/diary/new")
                                .content(objectMapper.writeValueAsString(diaryRegisterRequest))
                                .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(6002))
                .andExpect(jsonPath("$.message").value("허용되지 않은 서빙 단위입니다."))
                .andExpect(jsonPath("$.data.diaryId").doesNotExist());
    }

    @DisplayName("올바르지 않은 식사타입 실패 테스트")
    @Test
    void mealType() throws Exception {
        // given
        DiaryRegisterRequest diaryRegisterRequest = new DiaryRegisterRequest(savedProductIds.getFirst(), "WRONG", valueOf(1), "gram", LocalDate.of(2024, 8, 10));

        // when then
        mockMvc.perform(
                        post("/diary/new")
                                .content(objectMapper.writeValueAsString(diaryRegisterRequest))
                                .contentType("application/json")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(6001))
                .andExpect(jsonPath("$.message").value("유효성 검사에 실패했습니다."))
                .andExpect(jsonPath("$.data.mealType").value("올바른 식사 타입을 입력해주세요."));
    }

    @DisplayName("다이어리 조회 테스트")
    @Test
    void getDiary() throws Exception {
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

        // when then
        mockMvc.perform(
                        get("/diary")
                                .param("diaryDate", "2024-08-10")
                                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(2001))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.diaryId").value(diaryId))
                .andExpect(jsonPath("$.data.diaryDate").value("2024-08-10"))
                .andExpect(jsonPath("$.data.diarySummary").exists());
    }
}