package flab.nutridiary.diary.dto.response.query;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class DiaryRetrievalQueryDto {
    private final Long diaryId;
    private final Long memberId;
    private final LocalDate diaryDate;
    private final DiarySummary diarySummary;

    @Builder
    public DiaryRetrievalQueryDto(Long diaryId, Long memberId, LocalDate diaryDate, DiarySummary diarySummary) {
        this.diaryId = diaryId;
        this.memberId = memberId;
        this.diaryDate = diaryDate;
        this.diarySummary = diarySummary;
    }
}
