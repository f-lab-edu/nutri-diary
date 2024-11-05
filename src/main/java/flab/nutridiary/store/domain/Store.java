package flab.nutridiary.store.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
@Getter
public class Store {
    @Id @Column("store_id")
    private Long id;

    private String storeName;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Store(String storeName) {
        this.storeName = storeName;
    }
}
