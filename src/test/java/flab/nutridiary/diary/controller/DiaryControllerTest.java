package flab.nutridiary.diary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.nutridiary.diary.dto.DiaryRegisterRequest;
import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.math.BigDecimal.valueOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class DiaryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private Long savedProductId;

    @BeforeEach
    void init() {
        // given
        NutritionFacts nutritionFacts = NutritionFacts.builder()
                .productTotalCalories(valueOf(100))
                .productTotalCarbohydrate(valueOf(10))
                .productTotalProtein(valueOf(20))
                .productTotalFat(valueOf(30))
                .productServingSize(valueOf(2))
                .productServingUnit("개")
                .productTotalWeightGram(valueOf(100))
                .build();

        Product product = Product.builder()
                .productName("사과")
                .productCorp("사과회사")
                .nutritionFacts(nutritionFacts)
                .build();

        savedProductId = productRepository.save(product).getId();
    }


    @DisplayName("다이어리 등록 테스트")
    @Test
    void createDiary() throws Exception {
        // given
        DiaryRegisterRequest diaryRegisterRequest = new DiaryRegisterRequest(savedProductId, "BREAKFAST", BigDecimal.valueOf(1), "gram", LocalDate.of(2024, 8, 10));

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
}