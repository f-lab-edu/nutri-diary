package flab.nutridiary.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@ToString
@NoArgsConstructor
@Getter
@Document(indexName = "product")
public class ProductDocument {

    @Id
    private Long id;

    @Field(type = FieldType.Long)
    private Long productId;

    @Field(type = FieldType.Text)
    private String productName;

    @Field(type = FieldType.Text)
    private String productCorp;

    public ProductDocument(Long productId, String productName, String productCorp) {
        this.productId = productId;
        this.productName = productName;
        this.productCorp = productCorp;
    }
}

