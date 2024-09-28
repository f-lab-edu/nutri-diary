package flab.nutridiary.diary.repository.query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flab.nutridiary.commom.exception.SystemException;
import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.diary.domain.MealType;
import flab.nutridiary.diary.dto.response.query.DiaryRecordWithProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
@Component
public class DiaryRecordWithProductRowMapper implements RowMapper<DiaryRecordWithProduct> {
    private final ObjectMapper objectMapper;

    @Override
    public DiaryRecordWithProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
        return DiaryRecordWithProduct.builder()
                .diaryId(rs.getLong("diary_id"))
                .memberId(rs.getLong("member_id"))
                .diaryDate(rs.getDate("diary_date").toLocalDate())
                .diaryRecordId(rs.getLong("diary_record_id"))
                .mealType(MealType.valueOf(rs.getString("meal_type")))
                .productName(rs.getString("product_name"))
                .productCorp(rs.getString("product_corp"))
                .quantity(rs.getBigDecimal("quantity"))
                .clientChoiceServingUnitDescription(rs.getString("client_choice_serving_unit_description"))
                .calculatedNutrition(getNutritionFromJsonString(rs.getString("calculated_nutrition")))
                .build();
    }

    private Nutrition getNutritionFromJsonString(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, Nutrition.class);
        } catch (JsonProcessingException e) {
            throw new SystemException("Nutrition 객체 매핑 중 오류가 발생했습니다.");
        }
    }
}
