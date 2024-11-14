package flab.nutridiary.diary.repository;

import flab.nutridiary.diary.domain.Diary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Repository
public class DiaryRepositoryImpl implements DiaryRepository{
    private final DiaryCrudRepository diaryCrudRepository;

    @Override
    public Optional<Diary> findByMemberIdAndDiaryDate(Long memberId, LocalDate date) {
        return diaryCrudRepository.findByMemberIdAndDiaryDate(memberId, date);
    }

    @Override
    public Diary save(Diary diary) {
        return diaryCrudRepository.save(diary);
    }

    @Override
    public Optional<Diary> findById(Long id) {
        return diaryCrudRepository.findById(id);
    }
}
