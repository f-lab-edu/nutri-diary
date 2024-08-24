package flab.nutridiary.diary.domain;

import flab.nutridiary.commom.exception.BusinessException;
import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static flab.nutridiary.commom.exception.StatusConst.INVALID_PRODUCT_ID;

@RequiredArgsConstructor
@Component
public class NutritionCalculator {
    private final ProductRepository productRepository;

    public Nutrition calculate(ProductIntakeInfo productIntakeInfo) {
        Product product = productRepository.findById(productIntakeInfo.getProductId())
                .orElseThrow(() -> new BusinessException(INVALID_PRODUCT_ID));
        NutritionFacts nutritionFacts = product.getNutritionFacts();
        return nutritionFacts.calculate(productIntakeInfo.getServingUnit(), productIntakeInfo.getQuantity());
    }
}

