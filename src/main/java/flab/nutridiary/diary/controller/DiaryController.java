package flab.nutridiary.diary.controller;

import flab.nutridiary.diary.service.DiaryRegisterService;
import flab.nutridiary.diary.dto.DiaryRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DiaryController {

    private final DiaryRegisterService diaryRegisterService;

    @PostMapping("/diary/new")
    public void createDiary(@RequestBody DiaryRegisterRequest diaryRegisterRequest) {
        diaryRegisterService.writeDiaryRecord(diaryRegisterRequest);
    }
}
