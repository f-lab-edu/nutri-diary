package flab.nutridiary.diary.service;

import flab.nutridiary.commom.exception.BusinessException;
import flab.nutridiary.diary.domain.Diary;
import flab.nutridiary.diary.domain.DiaryRecord;
import flab.nutridiary.diary.domain.NutritionCalculator;
import flab.nutridiary.diary.domain.ProductIntakeInfo;
import flab.nutridiary.diary.dto.request.AddDiaryRecordRequest;
import flab.nutridiary.diary.dto.response.DiarySavedResponse;
import flab.nutridiary.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static flab.nutridiary.commom.exception.StatusConst.DIARY_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AddDiaryRecordService {
    private final DiaryRepository diaryRepository;
    private final NutritionCalculator nutritionCalculator;

    public DiarySavedResponse addDiaryRecord(AddDiaryRecordRequest addDiaryRecordRequest, Long diaryId) {
        ProductIntakeInfo productIntakeInfo = addDiaryRecordRequest.toProductIntakeInfo();
        DiaryRecord diaryRecord = DiaryRecord.of(productIntakeInfo, nutritionCalculator);

        Diary diary = diaryRepository.findById(diaryId)
                .map(existingDiary -> {
                    existingDiary.addDiaryRecord(diaryRecord);
                    return existingDiary;
                })
                .orElseThrow(() -> new BusinessException(DIARY_NOT_FOUND));

        Diary saved = diaryRepository.save(diary);
        return DiarySavedResponse.of(saved.getId());
    }
}
