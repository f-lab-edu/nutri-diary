package flab.nutridiary.diary.controller;

import flab.nutridiary.commom.dto.ApiResponse;
import flab.nutridiary.diary.dto.DiaryRegisterResponse;
import flab.nutridiary.diary.service.DiaryRegisterService;
import flab.nutridiary.diary.dto.DiaryRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final DiaryRegisterService diaryRegisterService;

    @PostMapping("/diary/new")
    public ApiResponse<DiaryRegisterResponse> createDiary(@Valid @RequestBody DiaryRegisterRequest diaryRegisterRequest) {
        return ApiResponse.success(diaryRegisterService.writeDiaryRecord(diaryRegisterRequest));
    }
}
