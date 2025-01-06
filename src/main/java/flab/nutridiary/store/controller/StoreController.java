package flab.nutridiary.store.controller;

import flab.nutridiary.commom.dto.ApiResponse;
import flab.nutridiary.product.dto.response.ProductSearchResponse;
import flab.nutridiary.productStore.dto.StoreProductResponse;
import flab.nutridiary.store.dto.response.AllStore;
import flab.nutridiary.store.service.StoreReadService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Getter
@RequiredArgsConstructor
@RestController
public class StoreController {

    private final StoreReadService storeReadService;

    @GetMapping("/store")
    public ApiResponse<AllStore> getAllStore() {
        return ApiResponse.success(storeReadService.getAllStore());
    }

    @GetMapping("/store/{store_id}")
    public ApiResponse<List<ProductSearchResponse>> getStoreProduct(@PathVariable(name = "store_id") Long storeId, Pageable pageable) {
        return ApiResponse.success(storeReadService.getStoreProduct(storeId, pageable));
    }
}
