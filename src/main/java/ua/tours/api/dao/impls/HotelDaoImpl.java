package ua.tours.api.dao.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.tours.api.dao.interfaces.HotelDao;
import ua.tours.api.models.Hotel;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class HotelDaoImpl implements HotelDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Hotel getHotelById(int id) {
        String sql = "SELECT * FROM hotels WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        try {
            return jdbcTemplate.queryForObject(sql, params, new HotelMapper());
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Hotel> listHotels() {
        String sql = "SELECT * FROM hotels";
        try {
            return jdbcTemplate.query(sql, new HotelMapper());
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int insertHotel(Hotel hotel) {
        String sql = "INSERT INTO hotels " +
                "(name, description, country, location) " +
                "VALUES " +
                "(:name, :description, :country, :location)";
        MapSqlParameterSource params = getHotelSQLParams(hotel);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        return (int) keyHolder.getKey();
    }

    @Override
    public void updateHotel(Hotel hotel) {
        String sql = "UPDATE hotels SET " +
                "name=:name, " +
                "description=:description, " +
                "country=:country, " +
                "location=:location " +
                "WHERE id=:id";
        MapSqlParameterSource params = getHotelSQLParams(hotel);
        jdbcTemplate.update(sql, params);
    }

    @Override
    public void removeHotel(int id) {
        String sql = "DELETE FROM hotels WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        jdbcTemplate.update(sql, params);
    }

    private MapSqlParameterSource getHotelSQLParams(Hotel hotel) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", hotel.getName());
        params.addValue("description", hotel.getDescription());
        params.addValue("country", hotel.getCountry());
        params.addValue("location", hotel.getLocation());
        params.addValue("id", hotel.getId());
        return params;
    }

    private static final class HotelMapper implements RowMapper<Hotel> {

        public Hotel mapRow(ResultSet rs, int rowNum) throws SQLException {
            Hotel hotel = new Hotel();
            hotel.setId(rs.getInt("id"));
            hotel.setName(rs.getString("name"));
            hotel.setDescription(rs.getString("description"));
            hotel.setLocation(rs.getString("location"));
            return  hotel;
        }
    }
}
