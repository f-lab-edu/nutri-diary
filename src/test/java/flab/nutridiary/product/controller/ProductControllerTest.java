package flab.nutridiary.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.dto.NewProductRequest;
import flab.nutridiary.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("상품 등록 컨트롤러 테스트.")
    @Test
    void addProduct() throws Exception {
        // given
        NewProductRequest newProductRequest = NewProductRequest.builder()
                .productName("상품명")
                .corpName("업체명")
                .servingSize(BigDecimal.ONE)
                .servingUnit("컵")
                .totalWeightGram(BigDecimal.valueOf(90))
                .calories(BigDecimal.valueOf(120))
                .carbohydrate(BigDecimal.valueOf(15.5))
                .protein(BigDecimal.valueOf(3.5))
                .fat(BigDecimal.valueOf(5.5))
                .build();

        // when
        mockMvc.perform(
                post("/product/new")
                        .content(objectMapper.writeValueAsString(newProductRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.productId").exists());
    }

    @DisplayName("상품 등록 실패 컨트롤러 테스트.")
    @Test
    void addProductEx() throws Exception {
        // given
        Product prevProduct = Product.builder()
                .productName("촉촉한 초코칩 쿠키")
                .productCorp("오리온")
                .build();
        productRepository.save(prevProduct);


        NewProductRequest newProductRequest = NewProductRequest.builder()
                .productName("촉촉한초코칩쿠키")
                .corpName("오리온")
                .servingSize(BigDecimal.ONE)
                .servingUnit("컵")
                .totalWeightGram(BigDecimal.valueOf(90))
                .calories(BigDecimal.valueOf(120))
                .carbohydrate(BigDecimal.valueOf(15.5))
                .protein(BigDecimal.valueOf(3.5))
                .fat(BigDecimal.valueOf(5.5))
                .build();

        // when
        mockMvc.perform(
                        post("/product/new")
                                .content(objectMapper.writeValueAsString(newProductRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value("이미 등록된 상품입니다."));
    }
}