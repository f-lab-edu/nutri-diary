package flab.nutridiary.dummy;

//@SpringBootTest
//public class InsertProductDietTagDummy {
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    private final int batchSize = 1000;
//    private final int threadCount = 5;
//    private final int totalDataCount = 20000000;
//
//    private final String productDietTagSQL = "INSERT INTO product_diet_tag (product_id, diet_tag_id, created_at, updated_at) VALUES (?, ?, ?, ?)";
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
//            int productId = (i % 8) * 100000 + i % 100000;
//            int dietTagId = i % 10 + 1;
//            String createdAt = "2024-09-01 00:00:00";
//            String updatedAt = "2024-09-01 00:00:00";
//            batchArgs.add(new Object[]{productId, dietTagId, createdAt, updatedAt});
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
