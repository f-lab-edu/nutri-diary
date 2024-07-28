package flab.nutridiary.product.service;

public class ProductDuplicatedException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "이미 등록된 상품입니다.";
    private static final int STATUS_CODE = 400;

    public ProductDuplicatedException() {
        super(DEFAULT_MESSAGE);
    }

    public int getStatusCode() {
        return STATUS_CODE;
    }
}
