package flab.nutridiary.store.repository;

import flab.nutridiary.store.domain.Store;

import java.util.List;

public interface StoreRepository {
    List<Store> findAll();
}
