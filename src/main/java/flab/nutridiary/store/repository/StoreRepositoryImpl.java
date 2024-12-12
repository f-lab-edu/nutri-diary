package flab.nutridiary.store.repository;

import flab.nutridiary.store.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepository {

    private final JdbcStoreRepository jdbcStoreRepository;

    @Override
    public List<Store> findAll() {
        return jdbcStoreRepository.findAll();
    }
}
