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
//public class InsertProductDietTagDummy {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    private final int batchSize = 5000;
//    private final int threadCount = 10;
//    private final int totalDataCount = 1000000;
//    private final Random random = new Random();
//
//    private final String productDietTagSQL = "INSERT INTO product_diet_tag (product_id, diet_tag_id, tag_count, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
//    private final String[] diet_tag_names = {"저칼로리", "저탄수화물", "무탄수화물", "저단백질", "고단백질", "무설탕", "저지방", "고지방", "저탄고지", "기토제닉"};
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
//            executorService.submit(() -> insertChunk(productDietTagSQL, start, end));
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
//            int productId = i;
//            for (int j = 1 ; j <= 10 ; j++){
//                int dietTagId = j;
//                Long tagCount = random.nextLong(300);
//                String createdAt = "2024-09-01 00:00:00";
//                String updatedAt = "2024-09-01 00:00:00";
//                batchArgs.add(new Object[]{productId, dietTagId, tagCount, createdAt, updatedAt});
//            }
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
