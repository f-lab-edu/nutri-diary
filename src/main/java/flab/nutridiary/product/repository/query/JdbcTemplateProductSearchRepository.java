package flab.nutridiary.product.repository.query;

import flab.nutridiary.product.dto.response.ProductSearchResponse;
import flab.nutridiary.product.repository.ProductSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<ProductSearchResponse> findFullTextSearch(String keyword, Pageable pageable) {
        Integer total = getTotalCount(keyword);

        String sql = "SELECT " +
                "p.product_id, p.product_name, p.product_corp, IFNULL(review_count, 0) AS review_count " +
                "FROM product p " +
                "LEFT JOIN (SELECT product_id, COUNT(*) AS review_count FROM review GROUP BY product_id) r ON p.product_id = r.product_id " +
                "WHERE MATCH (p.product_name, p.product_corp) AGAINST (:keyword)" +
                "LIMIT :offset, :limit";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("keyword", keyword)
                .addValue("offset", pageable.getOffset())
                .addValue("limit", pageable.getPageSize());
        List<ProductSearchResponse> queried = namedParameterJdbcTemplate.query(sql, parameters, beanPropertyRowMapper);
        return new PageImpl<>(queried, pageable, total);
    }

    private Integer getTotalCount(String keyword) {
        String sql = "SELECT COUNT(*) " +
                "FROM product p " +
                "LEFT JOIN (SELECT product_id, COUNT(*) AS review_count FROM review GROUP BY product_id) r ON p.product_id = r.product_id " +
                "WHERE MATCH (p.product_name, p.product_corp) AGAINST (:keyword)";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("keyword", keyword);
        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
    }
}
