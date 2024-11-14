//package flab.nutridiary.dummy;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//@SpringBootTest
//public class InsertReviewDummy {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    private final int batchSize = 5000;
//    private final int threadCount = 10;
//    private final int totalDataCount = 10000000;
//    private final Random random = new Random();
//
//
//    private final String reviewSQL = "INSERT INTO review (product_id, member_id, content, rating, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";
//
//    @Test
//    public void insertProductDataInParallel() throws InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
//        int chunkSize = totalDataCount / threadCount;
//
//        for (int i = 0; i < threadCount; i++) {
//            int start = i * chunkSize + 1;
//            int end = (i == threadCount - 1) ? totalDataCount : (i + 1) * chunkSize;
//
//            executorService.submit(() -> insertChunk(reviewSQL, start, end));
//        }
//
//        executorService.shutdown();
//        executorService.awaitTermination(1, TimeUnit.HOURS); // 스레드가 모두 종료될 때까지 대기
//    }
//
//    public void insertChunk(String sql, int start, int end) {
//        List<Object[]> batchArgs = new ArrayList<>();
//
//        for (int i = start; i <= end; i++) {
//            int productId = random.nextInt(1000000);
//            int memberId = i;
//            String content = i + "content " + i;
//            int rating = i % 5 + 1;
//            String createdAt = "2024-09-01 00:00:00";
//            String updatedAt = "2024-09-01 00:00:00";
//            batchArgs.add(new Object[]{productId, memberId, content, rating, createdAt, updatedAt});
//
//            if (batchArgs.size() % batchSize == 0) {
//                jdbcTemplate.batchUpdate(sql, batchArgs);
//                batchArgs.clear();
//            }
//        }
//
//        if (!batchArgs.isEmpty()) {
//            jdbcTemplate.batchUpdate(sql, batchArgs);
//        }
//
//        System.out.println("스레드가 " + start + "부터 " + end + "까지 데이터를 삽입했습니다.");
//    }
//}
