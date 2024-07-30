package flab.nutridiary.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class NewProductRequest {

    @NotNull(message = "상품명을 입력해주세요.")
    private String productName;

    @NotNull(message = "제조사명을 입력해주세요.")
    private String corpName;

    @Min(value = 0, message = "서빙 사이즈는 0 이상이어야 합니다.")
    @NotNull(message = "서빙 사이즈를 입력해주세요.")
    private BigDecimal servingSize;

    @NotNull(message = "서빙 단위를 입력해주세요.")
    private String servingUnit;

    @Min(value = 0, message = "총 중량은 0 이상이어야 합니다.")
    @NotNull(message = "총 중량을 입력해주세요.")
    private BigDecimal totalWeightGram;

    @Min(value = 0, message = "칼로리는 0 이상이어야 합니다.")
    @NotNull(message = "칼로리를 입력해주세요.")
    private BigDecimal calories;

    @Min(value = 0, message = "탄수화물은 0 이상이어야 합니다.")
    @NotNull(message = "탄수화물을 입력해주세요.")
    private BigDecimal carbohydrate;

    @Min(value = 0, message = "단백질은 0 이상이어야 합니다.")
    @NotNull(message = "단백질을 입력해주세요.")
    private BigDecimal protein;

    @Min(value = 0, message = "지방은 0 이상이어야 합니다.")
    @NotNull(message = "지방을 입력해주세요.")
    private BigDecimal fat;


    @Builder
    private NewProductRequest(String productName, String corpName, BigDecimal servingSize, String servingUnit, BigDecimal totalWeightGram, BigDecimal calories, BigDecimal carbohydrate, BigDecimal protein, BigDecimal fat) {
        this.productName = productName;
        this.corpName = corpName;
        this.servingSize = servingSize;
        this.servingUnit = servingUnit;
        this.totalWeightGram = totalWeightGram;
        this.calories = calories;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
    }
}
