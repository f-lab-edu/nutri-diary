package flab.nutridiary.diary.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class DiaryRegisterResponse {
    private final Long diaryId;

    public static DiaryRegisterResponse of(Long diaryId) {
        return new DiaryRegisterResponse(diaryId);
    }
}
