package flab.nutridiary.diary.controller;

import flab.nutridiary.commom.dto.ApiResponse;
import flab.nutridiary.diary.dto.AddDiaryRecordRequest;
import flab.nutridiary.diary.dto.DiaryRegisterRequest;
import flab.nutridiary.diary.dto.DiarySavedResponse;
import flab.nutridiary.diary.service.AddDiaryRecordService;
import flab.nutridiary.diary.service.DiaryRegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DiaryController {
    private final DiaryRegisterService diaryRegisterService;
    private final AddDiaryRecordService addDiaryRecordService;

    @PostMapping("/diary/new")
    public ApiResponse<DiarySavedResponse> createDiary(@Valid @RequestBody DiaryRegisterRequest diaryRegisterRequest) {
        return ApiResponse.success(diaryRegisterService.createDiary(diaryRegisterRequest));
    }

    @PostMapping("/diary/{diaryId}")
    public ApiResponse<DiarySavedResponse> addDiaryRecord(@Valid @RequestBody AddDiaryRecordRequest addDiaryRecordRequest,
                                                          @PathVariable Long diaryId) {
        return ApiResponse.success(addDiaryRecordService.addDiaryRecord(addDiaryRecordRequest, diaryId));
    }
}
