package flab.nutridiary.diary.repository;

import flab.nutridiary.diary.dto.response.query.DiaryRecordWithProduct;

import java.time.LocalDate;
import java.util.List;

public interface DiaryRetrievalRepository {
    List<DiaryRecordWithProduct> findDiaryWithProductAllByMemberIdAndDiaryDate(Long memberId, LocalDate diaryDate);
}
