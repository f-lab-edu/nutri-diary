package flab.nutridiary.diary.repository;

import flab.nutridiary.diary.dto.response.query.DiaryRecordWithProduct;

import java.time.LocalDate;
import java.util.List;

public interface DiarySearchRepository {
    List<DiaryRecordWithProduct> findDiaryWithProductAllByMemberIdAndDiaryDate(Long memberId, LocalDate diaryDate);
}
