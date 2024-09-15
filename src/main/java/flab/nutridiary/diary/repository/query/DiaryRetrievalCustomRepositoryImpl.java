package flab.nutridiary.diary.repository.query;

import flab.nutridiary.diary.dto.response.query.DiaryRetrievalQueryDto;
import flab.nutridiary.diary.repository.DiaryRetrievalCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DiaryRetrievalCustomRepositoryImpl implements DiaryRetrievalCustomRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final DiaryRetrievalQueryDtoExtractor extractor;

    @Override
    public Optional<DiaryRetrievalQueryDto> findDiaryWithProductsByMemberIdAndDiaryDate(Long memberId, LocalDate diaryDate) {
        String sql = "SELECT " +
                "d.diary_id, d.member_id, d.diary_date, " +
                "dr.diary_record_id, dr.meal_type, dr.quantity, dr.client_choice_serving_unit_description, dr.calculated_nutrition, " +
                "p.product_id, p.product_name, p.product_corp " +
                "FROM diary d " +
                "JOIN diary_record dr ON d.diary_id = dr.diary_id " +
                "JOIN product p ON dr.product_id = p.product_id " +
                "WHERE d.member_id = :memberId AND d.diary_date = :diaryDate";

        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("diaryDate", diaryDate);

        return Optional.ofNullable(namedParameterJdbcTemplate.query(sql, parameters, extractor));
    }
}
