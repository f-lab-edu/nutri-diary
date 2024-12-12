package flab.nutridiary.store.dto.response;

import flab.nutridiary.store.dto.StoreName;
import lombok.Getter;

import java.util.List;

@Getter
public class AllStore {
    private final List<StoreName> storeNames;

    private AllStore(List<StoreName> storeNames) {
        this.storeNames = storeNames;
    }

    public static AllStore of(List<StoreName> storeNames) {
        return new AllStore(storeNames);
    }
}
