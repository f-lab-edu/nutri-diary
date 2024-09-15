package flab.nutridiary.diary.service;

import flab.nutridiary.commom.exception.BusinessException;
import flab.nutridiary.diary.dto.response.query.DiaryRetrievalQueryDto;
import flab.nutridiary.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static flab.nutridiary.commom.exception.StatusConst.DIARY_NOT_FOUND;

@Transactional
@Service
@RequiredArgsConstructor
public class DiaryRetrievalService {
    private final DiaryRepository diaryRepository;

    @Transactional(readOnly = true)
    public DiaryRetrievalQueryDto getDiary(Long memberId, LocalDate diaryDate) {
        return diaryRepository.findDiaryWithProductsByMemberIdAndDiaryDate(memberId, diaryDate)
                .orElseThrow(() ->new BusinessException(DIARY_NOT_FOUND));
    }
}
