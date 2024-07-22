package flab.nutridiary.diary.repository;

import flab.nutridiary.diary.domain.Diary;

import java.time.LocalDate;
import java.util.Optional;

public interface DiaryRepository {
    Optional<Diary> findByMemberIdAndDate(Long memberId, LocalDate date);
    Diary save(Diary diary);
    Optional<Diary> findById(Long id);
}
