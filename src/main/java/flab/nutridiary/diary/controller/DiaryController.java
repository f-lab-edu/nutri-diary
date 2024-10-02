package flab.nutridiary.diary.controller;

import flab.nutridiary.commom.dto.ApiResponse;
import flab.nutridiary.diary.dto.request.AddDiaryRecordRequest;
import flab.nutridiary.diary.dto.request.DiaryRegisterRequest;
import flab.nutridiary.diary.dto.response.DiarySavedResponse;
import flab.nutridiary.diary.dto.response.query.DiaryRecordWithProduct;
import flab.nutridiary.diary.service.AddDiaryRecordService;
import flab.nutridiary.diary.service.DiaryRegisterService;
import flab.nutridiary.diary.service.DiarySearchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class DiaryController {
    private final DiaryRegisterService diaryRegisterService;
    private final AddDiaryRecordService addDiaryRecordService;
    private final DiarySearchService diarySearchService;

    @PostMapping("/diary/new")
    public ApiResponse<DiarySavedResponse> createDiary(@Valid @RequestBody DiaryRegisterRequest diaryRegisterRequest) {
        return ApiResponse.success(diaryRegisterService.createDiary(diaryRegisterRequest));
    }

    @PostMapping("/diary/{diaryId}")
    public ApiResponse<DiarySavedResponse> addDiaryRecord(@Valid @RequestBody AddDiaryRecordRequest addDiaryRecordRequest,
                                                          @PathVariable Long diaryId) {
        return ApiResponse.success(addDiaryRecordService.addDiaryRecord(addDiaryRecordRequest, diaryId));
    }

    @GetMapping("/diary")
    public ApiResponse<List<DiaryRecordWithProduct>> getDiary(@RequestParam(name = "diaryDate") LocalDate diaryDate) {
        Long memberId = 1L;
        return ApiResponse.success(diarySearchService.getDiary(memberId, diaryDate));
    }
}
