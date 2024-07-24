DROP TABLE IF EXISTS NUTRITION_FACTS;
DROP TABLE IF EXISTS NUTRITION_FACTS_PER_GRAM;
DROP TABLE IF EXISTS PRODUCT;

CREATE TABLE PRODUCT (
    PRODUCT_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    PRODUCT_NAME VARCHAR(255) NOT NULL,
    PRODUCT_CORP VARCHAR(255)
);

CREATE TABLE NUTRITION_FACTS (
    NUTRITION_FACTS_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    PRODUCT_ID BIGINT NOT NULL,
    CALORIES DECIMAL(10, 2) NOT NULL,
    CARBOHYDRATE DECIMAL(10, 2) NOT NULL,
    PROTEIN DECIMAL(10, 2) NOT NULL,
    SERVING_UNIT VARCHAR(255) NOT NULL,
    SERVING_WEIGHT_GRAM INT NOT NULL,
    CONSTRAINT PRODUCT_ID_FK FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (PRODUCT_ID)
);

CREATE TABLE NUTRITION_FACTS_PER_GRAM (
    NUTRITION_FACTS_PER_GRAM_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    PRODUCT_ID BIGINT NOT NULL,
    CALORIES DECIMAL(10, 2) NOT NULL,
    CARBOHYDRATE DECIMAL(10, 2) NOT NULL,
    PROTEIN DECIMAL(10, 2) NOT NULL,
    FAT DECIMAL(10, 2) NOT NULL,
    CONSTRAINT PRODUCT_ID_FK2 FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (PRODUCT_ID)
);
