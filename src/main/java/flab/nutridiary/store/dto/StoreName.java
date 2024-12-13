package flab.nutridiary.store.dto;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class StoreName {
    private final Long id;
    private final String name;

    private StoreName(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static StoreName of(Long id, String name) {
        return new StoreName(id, name);
    }
}
