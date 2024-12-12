package flab.nutridiary.store.service;

import flab.nutridiary.store.dto.StoreName;
import flab.nutridiary.store.dto.response.AllStore;
import flab.nutridiary.store.repository.StoreRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Getter
@RequiredArgsConstructor
@Transactional
@Service
public class StoreReadService {
    private final StoreRepository storeRepository;

    public AllStore getAllStore() {
        return AllStore.of(storeRepository.findAll()
                .stream()
                .map(store -> StoreName.of(store.getId(), store.getStoreName()))
                .toList());
    }

}
