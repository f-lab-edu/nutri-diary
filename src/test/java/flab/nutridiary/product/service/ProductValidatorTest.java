package flab.nutridiary.product.service;

import flab.nutridiary.product.domain.Product;
import flab.nutridiary.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class ProductValidatorTest {

    @Autowired
    private ProductValidator productValidator;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("중복된 상품이 등록되면 예외가 발생한다.")
    @Test
    void validate() throws Exception {
        // given
        Product product = Product.builder()
                .productName("촉촉한   초코칩  쿠키")
                .productCorp(" 오리온")
                .build();
        productRepository.save(product);

        // when
        Product product2 = Product.builder()
                .productName("촉촉한 초코칩쿠키")
                .productCorp("오리온")
                .build();

        // then
        Assertions.assertThrows(ProductDuplicatedException.class, () -> {
            productValidator.validate(product2);
        });

    }
    @DisplayName("서로 다른 상품이 등록되면 정상처리된다..")
    @Test
    void validate2() throws Exception {
        // given
        Product product = Product.builder()
                .productName("촉촉한   초코칩  쿠키")
                .productCorp(" 오리온")
                .build();
        productRepository.save(product);

        // when
        Product product2 = Product.builder()
                .productName("쵹촉한초코칩쿠키")
                .productCorp("오리온")
                .build();

        // then
        Assertions.assertDoesNotThrow(() -> {
            productValidator.validate(product2);
        });
    }
}