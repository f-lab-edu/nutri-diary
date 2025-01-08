package flab.nutridiary.search;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductDocumentAsyncRegisterService {
    private final ProductDocumentRepository productDocumentRepository;

    @Async
    public void saveDocumentAsync(ProductDocument document) {
        productDocumentRepository.save(document);
    }
}
