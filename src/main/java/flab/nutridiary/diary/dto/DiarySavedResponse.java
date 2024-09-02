package flab.nutridiary.diary.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class DiarySavedResponse {
    private final Long diaryId;

    public static DiarySavedResponse of(Long diaryId) {
        return new DiarySavedResponse(diaryId);
    }
}
