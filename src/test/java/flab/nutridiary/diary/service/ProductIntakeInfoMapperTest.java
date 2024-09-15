package flab.nutridiary.diary.service;

import flab.nutridiary.diary.domain.MealType;
import flab.nutridiary.diary.domain.ProductIntakeInfo;
import flab.nutridiary.diary.dto.request.DiaryRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ProductIntakeInfoMapperTest {

    private ProductIntakeInfoMapper productIntakeInfoMapper = new ProductIntakeInfoMapper();

    @DisplayName("DiaryRegisterRequest 로부터 ProductIntakeInfo 를 추출한다.")
    @Test
    void from() throws Exception {
        // given
        DiaryRegisterRequest diaryRegisterRequest = new DiaryRegisterRequest(2L, "BREAKFAST", BigDecimal.valueOf(25), "gram", LocalDate.of(2021, 8, 1));

        // when
        ProductIntakeInfo productIntakeInfo = productIntakeInfoMapper.from(diaryRegisterRequest);

        // then
        assertThat(productIntakeInfo).extracting("productId", "mealType", "quantity", "clientChoiceServingUnitDescription")
                .containsExactly(2L, MealType.BREAKFAST, BigDecimal.valueOf(25), "gram");
    }

}