package flab.nutridiary.dummy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class InsertProductDummy {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final int batchSize = 1000;
    private final int threadCount = 5;
    private final String[] productNames = {"스팀 닭가슴살 칠리맛", "크리스피 닭가슴살 핫도그", "전복 내장 미역국", "닭가슴살 양배추쌈", "무봤나 반마리 삼계탕", "제육 덮밥", "맥앤치즈볼", "스팸마일드", "삼양라면", "당면 고기만두", "동치미 물냉면"};
    private final String[] productCorps = {"코스트코", "롯데리아", "맥도날드", "버거킹", "KFC", "파리바게트", "CU", "GS25", "세븐일레븐", "이마트", "홈플러스"};

    private final String productSQL = "INSERT INTO product (product_name, product_corp, member_id, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";

    @Test
    public void insertProductDataInParallel() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        int chunkSize = 1000000 / threadCount;

        for (int i = 0; i < threadCount; i++) {
            int start = i * chunkSize + 1;
            int end = (i == threadCount - 1) ? 1000000 : (i + 1) * chunkSize;

            executorService.submit(() -> insertChunk(productSQL, start, end));
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS); // 스레드가 모두 종료될 때까지 대기
    }

    public void insertChunk(String sql, int start, int end) {
        List<Object[]> batchArgs = new ArrayList<>();

        for (int i = start; i <= end; i++) {
            String productName = i + "productName " + productNames[i % productNames.length] + i;
            String productCorp = i + "productCorp " + productCorps[i % productCorps.length] + i;
            int memberId = i;
            String createdAt = "2024-09-01 00:00:00";
            String updatedAt = "2024-09-01 00:00:00";
            batchArgs.add(new Object[]{productName, productCorp, memberId, createdAt, updatedAt});

            if (batchArgs.size() % batchSize == 0) {
                jdbcTemplate.batchUpdate(sql, batchArgs);
                batchArgs.clear();
            }
        }

        if (!batchArgs.isEmpty()) {
            jdbcTemplate.batchUpdate(sql, batchArgs);
        }

        System.out.println("스레드가 " + start + "부터 " + end + "까지 데이터를 삽입했습니다.");
    }
}
