package flab.nutridiary.dietTag.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class DietTag {
    @Id @Column("diet_tag_id")
    private Long id;

    private String dietPlan;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public DietTag(String dietPlan) {
        this.dietPlan = dietPlan;
    }
}
