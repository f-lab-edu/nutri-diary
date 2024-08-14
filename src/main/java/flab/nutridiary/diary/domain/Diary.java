package flab.nutridiary.diary.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@ToString
@Getter
@NoArgsConstructor
public class Diary {
    @Id
    @Column("DIARY_ID")
    private Long id;

    @Column("MEMBER_ID")
    private Long memberId = 1L;

    private LocalDate diaryDate;

    @MappedCollection(idColumn = "DIARY_ID")
    private Set<DiaryRecord> diaryRecords = new HashSet<>();

    public Diary(LocalDate diaryDate, DiaryRecord diaryRecord) {
        this.diaryDate = diaryDate;
        this.diaryRecords.add(diaryRecord);
    }

    public void addDiaryRecord(DiaryRecord diaryRecord) {
        diaryRecords.add(diaryRecord);
    }
}
