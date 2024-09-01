package flab.nutridiary.diary.service;

import flab.nutridiary.diary.domain.*;
import flab.nutridiary.diary.dto.DiaryRegisterRequest;
import flab.nutridiary.diary.dto.DiarySavedResponse;
import flab.nutridiary.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class DiaryRegisterService {

    private final DiaryRepository diaryRepository;
    private final NutritionCalculator nutritionCalculator;
    private final ProductIntakeInfoMapper productIntakeInfoMapper;
    private final DiaryValidator diaryValidator;

    public DiarySavedResponse createDiary(DiaryRegisterRequest diaryRegisterRequest) {
        ProductIntakeInfo productIntakeInfo = productIntakeInfoMapper.from(diaryRegisterRequest);
        DiaryRecord diaryRecord = DiaryRecord.of(productIntakeInfo, nutritionCalculator);
        Diary saved = diaryRepository.save(Diary.of(diaryRegisterRequest.getIntakeDate(), diaryRecord, diaryValidator));
        return DiarySavedResponse.of(saved.getId());
    }
}

