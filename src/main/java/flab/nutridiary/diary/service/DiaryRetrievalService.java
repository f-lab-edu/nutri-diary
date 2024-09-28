package flab.nutridiary.diary.service;

import flab.nutridiary.diary.dto.response.query.DiaryRecordWithProduct;
import flab.nutridiary.diary.repository.DiaryRetrievalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class DiaryRetrievalService {
    private final DiaryRetrievalRepository diaryRetrievalRepository;

    @Transactional(readOnly = true)
    public List<DiaryRecordWithProduct> getDiary(Long memberId, LocalDate diaryDate) {
        return diaryRetrievalRepository.findDiaryWithProductAllByMemberIdAndDiaryDate(memberId, diaryDate);
    }
}
