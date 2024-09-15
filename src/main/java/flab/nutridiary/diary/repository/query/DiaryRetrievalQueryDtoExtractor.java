package flab.nutridiary.diary.repository.query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flab.nutridiary.commom.exception.SystemException;
import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.diary.domain.MealType;
import flab.nutridiary.diary.dto.response.query.DiaryRecordWithProduct;
import flab.nutridiary.diary.dto.response.query.DiaryRetrievalQueryDto;
import flab.nutridiary.diary.dto.response.query.DiarySummary;
import flab.nutridiary.diary.dto.response.query.MealSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DiaryRetrievalQueryDtoExtractor implements ResultSetExtractor<DiaryRetrievalQueryDto> {
    private final ObjectMapper objectMapper;

    @Override
    public DiaryRetrievalQueryDto extractData(ResultSet rs) throws SQLException, DataAccessException {
        Long diaryId = null;
        Long memberId = null;
        LocalDate diaryDate = null;
        List<DiaryRecordWithProduct> breakfastRecords = new ArrayList<>();
        List<DiaryRecordWithProduct> lunchRecords = new ArrayList<>();
        List<DiaryRecordWithProduct> dinnerRecords = new ArrayList<>();
        List<DiaryRecordWithProduct> snackRecords = new ArrayList<>();
        if (!rs.first()) {
            return null;
        }
        do {
            diaryId = diaryId == null ? rs.getLong("diary_id") : diaryId;
            memberId = memberId == null ? rs.getLong("member_id") : memberId;
            diaryDate = diaryDate == null ? rs.getDate("diary_date").toLocalDate() : diaryDate;
            MealType mealType = MealType.valueOf(rs.getString("meal_type"));
            DiaryRecordWithProduct record = null;
            try {
                record = mapRowToDiaryRecordWithProduct(rs);
            } catch (JsonProcessingException e) {
                throw new SystemException("DiaryRecordWithProduct 객체 매핑 중 오류가 발생했습니다.");
            }

            if (mealType == MealType.BREAKFAST) {
                breakfastRecords.add(record);
            } else if (mealType == MealType.LUNCH) {
                lunchRecords.add(record);
            } else if (mealType == MealType.DINNER) {
                dinnerRecords.add(record);
            } else if (mealType == MealType.SNACK) {
                snackRecords.add(record);
            }
        } while (rs.next());

        MealSummary breakfast = breakfastRecords.isEmpty() ? MealSummary.empty(MealType.BREAKFAST) : MealSummary.of(MealType.BREAKFAST, breakfastRecords);
        MealSummary lunch = lunchRecords.isEmpty() ? MealSummary.empty(MealType.LUNCH) : MealSummary.of(MealType.LUNCH, lunchRecords);
        MealSummary dinner = dinnerRecords.isEmpty() ? MealSummary.empty(MealType.DINNER) : MealSummary.of(MealType.DINNER, dinnerRecords);
        MealSummary snack = snackRecords.isEmpty() ? MealSummary.empty(MealType.SNACK) : MealSummary.of(MealType.SNACK, snackRecords);
        return DiaryRetrievalQueryDto.builder()
                .diaryId(diaryId)
                .memberId(memberId)
                .diaryDate(diaryDate)
                .diarySummary(DiarySummary.builder()
                        .breakfast(breakfast)
                        .lunch(lunch)
                        .dinner(dinner)
                        .snack(snack)
                        .build())
                .build();
    }

    private DiaryRecordWithProduct mapRowToDiaryRecordWithProduct(ResultSet rs) throws SQLException, JsonProcessingException {
        return new DiaryRecordWithProduct(
                rs.getLong("diary_record_id"),
                MealType.valueOf(rs.getString("meal_type")),
                rs.getString("product_name"),
                rs.getString("product_corp"),
                rs.getBigDecimal("quantity"),
                rs.getString("client_choice_serving_unit_description"),
                objectMapper.readValue(rs.getString("calculated_nutrition"), Nutrition.class)
        );
    }
}
