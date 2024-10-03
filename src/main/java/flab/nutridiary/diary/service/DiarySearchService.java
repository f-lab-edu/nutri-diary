package flab.nutridiary.diary.service;

import flab.nutridiary.diary.dto.response.query.DiaryRecordWithProduct;
import flab.nutridiary.diary.repository.DiarySearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class DiarySearchService {
    private final DiarySearchRepository diarySearchRepository;

    @Transactional(readOnly = true)
    public List<DiaryRecordWithProduct> getDiary(Long memberId, LocalDate diaryDate) {
        return diarySearchRepository.findDiaryWithProductAllByMemberIdAndDiaryDate(memberId, diaryDate);
    }
}
