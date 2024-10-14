package flab.nutridiary.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.nutridiary.TestContainerControllerSupport;
import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.dto.NewProductRequest;
import flab.nutridiary.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends TestContainerControllerSupport {

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
                .productDefaultServingSize(BigDecimal.TWO)
                .productDefaultServingUnit("컵")
                .productTotalWeightGram(BigDecimal.valueOf(90))
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
                .andExpect(jsonPath("$.statusCode").value(2001))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.productId").exists());
    }

    @DisplayName("중복된 상품 등록 실패 컨트롤러 테스트.")
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
                .productDefaultServingSize(BigDecimal.ONE)
                .productDefaultServingUnit("개")
                .productTotalWeightGram(BigDecimal.valueOf(90))
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
                .andExpect(jsonPath("$.statusCode").value(4002))
                .andExpect(jsonPath("$.message").value("이미 등록된 식품입니다."));
    }

    @DisplayName("유효성 체크 실패 테스트")
    @Test
    void validation() throws Exception {
        // given
        NewProductRequest newProductRequest = NewProductRequest.builder()
                .productName("상품명")
                .corpName("업체명")
                .productDefaultServingSize(BigDecimal.TWO)
                .productTotalWeightGram(BigDecimal.valueOf(-1))
                .calories(BigDecimal.valueOf(120))
                .carbohydrate(BigDecimal.valueOf(15.5))
                .protein(BigDecimal.valueOf(-100))
                .fat(BigDecimal.valueOf(5.5))
                .build();

        // when then
        mockMvc.perform(
                        post("/product/new")
                                .content(objectMapper.writeValueAsString(newProductRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(6001))
                .andExpect(jsonPath("$.message").value("유효성 검사에 실패했습니다."))
                .andExpect(jsonPath("$.data.productTotalWeightGram").value("총 중량은 0 이상이어야 합니다."))
                .andExpect(jsonPath("$.data.protein").value("단백질은 0 이상이어야 합니다."))
                .andExpect(jsonPath("$.data.productDefaultServingUnit").value("서빙 단위를 입력해주세요."));
    }
}