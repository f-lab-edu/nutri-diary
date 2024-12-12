package flab.nutridiary.store.controller;

import flab.nutridiary.commom.dto.ApiResponse;
import flab.nutridiary.store.dto.response.AllStore;
import flab.nutridiary.store.service.StoreReadService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@RequiredArgsConstructor
@RestController
public class StoreController {

    private final StoreReadService storeReadService;

    @GetMapping("/store")
    public ApiResponse<AllStore> getAllStore() {
        return ApiResponse.success(storeReadService.getAllStore());
    }
}
