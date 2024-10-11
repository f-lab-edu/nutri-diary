package flab.nutridiary.product.repository.query;

import flab.nutridiary.product.dto.response.ProductSearchResponse;
import flab.nutridiary.product.repository.ProductSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class JdbcTemplateProductSearchRepository implements ProductSearchRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    DataClassRowMapper<ProductSearchResponse> beanPropertyRowMapper = new DataClassRowMapper<>(ProductSearchResponse.class);

    @Override
    public List<ProductSearchResponse> findFullTextSearch(String keyword) {
        String sql = "SELECT " +
                "p.product_id, p.product_name, p.product_corp " +
                "FROM product p " +
                "WHERE MATCH (p.product_name, p.product_corp) AGAINST (:keyword)";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("keyword", keyword);
        return namedParameterJdbcTemplate.query(sql, parameters, beanPropertyRowMapper);
    }
}
