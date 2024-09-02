package flab.nutridiary.diary.repository;

import flab.nutridiary.diary.domain.Diary;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DiaryCrudRepository extends CrudRepository<Diary, Long> {
    Optional<Diary> findByMemberIdAndDiaryDate(Long memberId, LocalDate date);
}
