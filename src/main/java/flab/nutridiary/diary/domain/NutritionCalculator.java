package flab.nutridiary.diary.domain;

import flab.nutridiary.commom.exception.BusinessException;
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
    private final NutritionCalculationStrategyFactory strategyFactory;

    public CalculatedNutrition calculate(ProductIntakeInfo productIntakeInfo) {
        Product product = productRepository.findById(productIntakeInfo.getProductId())
                .orElseThrow(() -> new BusinessException(INVALID_PRODUCT_ID));
        NutritionFacts nutritionFacts = product.getNutritionFacts();

        NutritionCalculationStrategy strategy = strategyFactory.getStrategy(productIntakeInfo.getServingUnit());
        return strategy.calculate(nutritionFacts, productIntakeInfo);
    }
}

