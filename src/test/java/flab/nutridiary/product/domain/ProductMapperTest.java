package flab.nutridiary.product.domain;

import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.product.dto.NewProductRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

class ProductMapperTest {
    private ProductMapper productMapper = new ProductMapper();

    @DisplayName("ProductRequest를 Product로 변환한다.")
    @Test
    void mapping() throws Exception {
        NewProductRequest newProductRequest = NewProductRequest.builder()
                .productName("상품명")
                .corpName("업체명")
                .productDefaultServingSize(BigDecimal.valueOf(3))
                .productDefaultServingUnit("컵")
                .productTotalWeightGram(valueOf(90))
                .calories(valueOf(120))
                .carbohydrate(valueOf(15.5))
                .protein(valueOf(32.54))
                .fat(valueOf(15.52))
                .build();

        // when
        Product product = productMapper.from(newProductRequest);

        // then
        Assertions.assertThat(product.getProductName()).isEqualTo("상품명");
        Assertions.assertThat(product.getProductCorp()).isEqualTo("업체명");
        Assertions.assertThat(product.getNutritionFacts().getNutritionPerOneServingUnit()).isEqualTo(Nutrition.of(valueOf(40), valueOf(5.17), valueOf(10.85), valueOf(5.17)));
    }
}