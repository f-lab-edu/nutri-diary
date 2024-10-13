package flab.nutridiary.product.repository;

import flab.nutridiary.TestContainerSupport;
import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.domain.ServingUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

class ProductRepositoryTest extends TestContainerSupport {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("상품 저장 리포지토리 테스트.")
    @Test
    void saveTest() throws Exception {
        // given
        NutritionFacts nutritionFacts = NutritionFacts.builder()
                .nutritionPerOneServingUnit(Nutrition.of(valueOf(50), valueOf(5), valueOf(10), valueOf(15)))
                .allowedProductServingUnits(
                        new ArrayList<>(List.of(
                                ServingUnit.asOneServingUnit("개"),
                                ServingUnit.ofGram(BigDecimal.ONE, valueOf(50)))))
                .build();

        Product product = Product.builder()
                .productName("사과")
                .productCorp("사과회사")
                .nutritionFacts(nutritionFacts)
                .build();

        // when
        Product savedProduct = productRepository.save(product);

        // then
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getProductName()).isEqualTo("사과");
        assertThat(savedProduct.getProductCorp()).isEqualTo("사과회사");
        assertThat(savedProduct.getNutritionFacts().getNutritionPerOneServingUnit()).isEqualTo(Nutrition.of(valueOf(50), valueOf(5), valueOf(10), valueOf(15)));
        assertThat(savedProduct.getNutritionFacts().getAllowedProductServingUnits()).containsExactly(ServingUnit.asOneServingUnit("개"), ServingUnit.ofGram(BigDecimal.ONE, valueOf(50)));

    }

    @DisplayName("상품 조회 리포지토리 테스트.")
    @Test
    void findById() throws Exception {
        // given
        NutritionFacts nutritionFacts = NutritionFacts.builder()
                .nutritionPerOneServingUnit(Nutrition.of(valueOf(50), valueOf(5), valueOf(10), valueOf(15)))
                .allowedProductServingUnits(
                        new ArrayList<>(List.of(
                                ServingUnit.asOneServingUnit("개"),
                                ServingUnit.ofGram(BigDecimal.ONE, valueOf(50)))))
                .build();

        Product product = Product.builder()
                .productName("사과")
                .productCorp("사과회사")
                .nutritionFacts(nutritionFacts)
                .build();

        Product savedProduct = productRepository.save(product);
        Long savedProductId = savedProduct.getId();

        // when
        Product foundProduct = productRepository.findById(savedProductId).get();

        // then
        assertThat(foundProduct.getId()).isEqualTo(savedProductId);
        assertThat(foundProduct.getProductName()).isEqualTo("사과");
        assertThat(foundProduct.getProductCorp()).isEqualTo("사과회사");
        assertThat(foundProduct.getNutritionFacts().getNutritionPerOneServingUnit()).isEqualTo(Nutrition.of(valueOf(50), valueOf(5), valueOf(10), valueOf(15)));
        assertThat(foundProduct.getNutritionFacts().getAllowedProductServingUnits()).containsExactly(ServingUnit.asOneServingUnit("개"), ServingUnit.ofGram(BigDecimal.ONE, valueOf(50)));
    }
}