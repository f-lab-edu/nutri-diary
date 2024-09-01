package flab.nutridiary.diary.domain;

import flab.nutridiary.commom.exception.BusinessException;
import flab.nutridiary.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static flab.nutridiary.commom.exception.StatusConst.DUPLICATED_DIARY;

@Component
@RequiredArgsConstructor
public class DiaryValidator {
    private final DiaryRepository diaryRepository;

    public void validate(Diary diary) {
        if (diaryRepository.findByMemberIdAndDiaryDate(diary.getMemberId(), diary.getDiaryDate()).isPresent()) {
            throw new BusinessException(DUPLICATED_DIARY);
        }
    }
}
