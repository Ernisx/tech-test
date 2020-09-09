package tech.test;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DetailDaoImpl implements DetailDao {
    public DetailDaoImpl(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    NamedParameterJdbcTemplate template;

    @Override
    public List<Detail> getDetails() {
        return template.query("SELECT * FROM DB_TEST", new InfoRowMapper());
    }

    @Override
    public List<Detail> getDetails(String searchBy, String value) {
        String query = String.format("SELECT * FROM DB_TEST WHERE %s=%s", searchBy, value);
        return template.query(query, new InfoRowMapper());
    }

    @Override
    public void insert(Detail detail) {
        final String sql = "INSERT INTO " +
                "DB_TEST(id, account, destination, startDate, endDate, status, costPerMinute) " +
                "values(:id,:account, :destination, :startDate, :endDate, :status, :costPerMinute)";
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", detail.getId())
                .addValue("account", detail.getAccount())
                .addValue("destination", detail.getDestination())
                .addValue("startDate", detail.getStartDate())
                .addValue("endDate", detail.getEndDate())
                .addValue("status", detail.getStatus())
                .addValue("costPerMinute", detail.getCostPerMinute());
        template.update(sql, param, holder);
    }

    class InfoRowMapper implements RowMapper<Detail> {
        @Override
        public Detail mapRow(ResultSet rs, int arg1) throws SQLException {
            Detail detail = new Detail();
            detail.setId(rs.getString("id"));
            detail.setAccount(rs.getString("account"));
            detail.setDestination(rs.getString("destination"));
            detail.setStartDate(rs.getString("startDate"));
            detail.setEndDate(rs.getString("endDate"));
            detail.setStatus(rs.getString("status"));
            detail.setCostPerMinute(rs.getString("costPerMinute"));
            return detail;
        }
    }

}
