DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS diary_record;
DROP TABLE IF EXISTS diary;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS diet_tag;
DROP TABLE IF EXISTS product_diet_tag;
DROP TABLE IF EXISTS store;
DROP TABLE IF EXISTS product_store;

CREATE TABLE product (
    product_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(255) NOT NULL,
    product_corp VARCHAR(255),
    product_normalized_name VARCHAR(255) UNIQUE,
    nutrition_facts VARCHAR(255),
    member_id BIGINT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE diary (
    diary_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    diary_date DATE NOT NULL,
    CONSTRAINT uc_diary_date UNIQUE (member_id, diary_date)
);

CREATE TABLE diary_record (
    diary_record_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    diary_id BIGINT,
    product_id BIGINT NOT NULL,
    meal_type VARCHAR(255) NOT NULL,
    quantity DECIMAL NOT NULL,
    client_choice_serving_unit_description VARCHAR(255),
    calculated_nutrition VARCHAR(255)
);

CREATE TABLE review (
    review_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL,
    member_id BIGINT NOT NULL,
    content VARCHAR(255),
    rating TINYINT NOT NULL,
    image_url VARCHAR(255),
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE diet_tag (
    diet_tag_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    diet_plan VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE product_diet_tag (
    product_diet_tag_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT,
    diet_tag_id BIGINT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE store (
    store_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    store_name VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE TABLE product_store (
    product_store_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT,
    store_id BIGINT,
    status VARCHAR(255),
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);
