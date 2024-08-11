package flab.nutridiary.diary.service;

import flab.nutridiary.diary.domain.Diary;
import flab.nutridiary.diary.domain.DiaryRecord;
import flab.nutridiary.diary.domain.NutritionCalculator;
import flab.nutridiary.diary.domain.ProductIntakeInfo;
import flab.nutridiary.diary.dto.DiaryRegisterRequest;
import flab.nutridiary.diary.dto.DiaryRegisterResponse;
import flab.nutridiary.diary.repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class DiaryRegisterService {

    private final DiaryRepository diaryRepository;
    private final NutritionCalculator nutritionCalculator;
    private final ProductIntakeInfoExtractor productIntakeInfoExtractor;

    public DiaryRegisterResponse writeDiaryRecord(DiaryRegisterRequest diaryRegisterRequest) {
        ProductIntakeInfo productIntakeInfo = productIntakeInfoExtractor.extract(diaryRegisterRequest);
        DiaryRecord diaryRecord = DiaryRecord.of(productIntakeInfo, nutritionCalculator);
        Optional<Diary> diaryOptional = diaryRepository.findByMemberIdAndDate(diaryRegisterRequest.getMemberId(), diaryRegisterRequest.getIntakeDate());
        if (diaryOptional.isPresent()) {
            Diary diary = diaryOptional.get();
            diary.addDiaryRecord(diaryRecord);
            Diary saved = diaryRepository.save(diary);
            return DiaryRegisterResponse.of(saved.getId());
        } else {
            Diary diary = new Diary(diaryRegisterRequest.getIntakeDate(), diaryRecord);
            Diary saved = diaryRepository.save(diary);
            return DiaryRegisterResponse.of(saved.getId());
        }
    }
}
