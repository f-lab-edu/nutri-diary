package flab.nutridiary.store.repository;

import flab.nutridiary.store.domain.Store;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StoreCrudRepository extends CrudRepository<Store, Long> {
    List<Store> findAll();
}
