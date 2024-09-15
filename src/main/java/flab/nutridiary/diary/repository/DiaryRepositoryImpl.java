package flab.nutridiary.diary.repository;

import flab.nutridiary.diary.domain.Diary;
import flab.nutridiary.diary.dto.response.query.DiaryRetrievalQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DiaryRepositoryImpl implements DiaryRepository{
    private final DiaryCrudRepository diaryCrudRepository;
    private final DiaryRetrievalCustomRepository diaryRetrievalCustomRepository;

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

    @Override
    public Optional<DiaryRetrievalQueryDto> findDiaryWithProductsByMemberIdAndDiaryDate(Long memberId, LocalDate diaryDate) {
        return diaryRetrievalCustomRepository.findDiaryWithProductsByMemberIdAndDiaryDate(memberId, diaryDate);
    }
}
