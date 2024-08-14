package flab.nutridiary.diary.service;

import flab.nutridiary.diary.domain.MealType;
import flab.nutridiary.diary.domain.ProductIntakeInfo;
import flab.nutridiary.diary.dto.DiaryRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ProductIntakeInfoExtractorTest {

   private ProductIntakeInfoExtractor productIntakeInfoExtractor = new ProductIntakeInfoExtractor();

    @DisplayName("DiaryRegisterRequest로부터 ProductIntakeInfo를 추출한다.")
    @Test
    void extract() throws Exception {
        // given
        DiaryRegisterRequest diaryRegisterRequest = new DiaryRegisterRequest(2L, MealType.BREAKFAST, BigDecimal.valueOf(25), "gram", LocalDate.of(2021, 8, 1));

        // when
        ProductIntakeInfo productIntakeInfo = productIntakeInfoExtractor.extract(diaryRegisterRequest);

        // then
        assertThat(productIntakeInfo).extracting("productId", "mealType", "quantity", "servingUnit")
                .containsExactly(2L, MealType.BREAKFAST, BigDecimal.valueOf(25), "gram");
    }

}