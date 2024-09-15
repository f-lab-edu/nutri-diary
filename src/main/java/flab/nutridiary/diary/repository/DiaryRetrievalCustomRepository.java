package flab.nutridiary.diary.repository;

import flab.nutridiary.diary.dto.response.query.DiaryRetrievalQueryDto;

import java.time.LocalDate;
import java.util.Optional;

public interface DiaryRetrievalCustomRepository {
    Optional<DiaryRetrievalQueryDto> findDiaryWithProductsByMemberIdAndDiaryDate(Long memberId, LocalDate diaryDate);
}
