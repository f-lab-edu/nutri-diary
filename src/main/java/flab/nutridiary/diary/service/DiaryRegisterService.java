package flab.nutridiary.diary.service;

import flab.nutridiary.diary.domain.Diary;
import flab.nutridiary.diary.domain.DiaryRecord;
import flab.nutridiary.diary.domain.NutritionCalculator;
import flab.nutridiary.diary.domain.ProductIntakeInfo;
import flab.nutridiary.diary.dto.NewDiaryRequest;
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

    // 영양성분을 여기서 계산해야하나?
    public void writeDiaryRecord(NewDiaryRequest newDiaryRequest) {
        ProductIntakeInfo productIntakeInfo = productIntakeInfoExtractor.extract(newDiaryRequest);
        DiaryRecord diaryRecord = DiaryRecord.of(productIntakeInfo, nutritionCalculator);
        // 근데 기존 날짜의 다이어리가 있으면 그것을 가져와야 한다.
        // 없으면 새로 만들어야 한다.
        Optional<Diary> diaryOptional = diaryRepository.findByMemberIdAndDate(newDiaryRequest.getMemberId(), newDiaryRequest.getIntakeDate());
        if (diaryOptional.isPresent()) {
            Diary diary = diaryOptional.get();
            diary.addDiaryRecord(diaryRecord);
            diaryRepository.save(diary);
        } else {
            Diary diary = new Diary(newDiaryRequest.getIntakeDate(), diaryRecord);
            log.info("diary: {}", diary);
            diaryRepository.save(diary);
        }
    }
}
