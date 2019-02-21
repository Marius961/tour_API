package ua.tours.api.dao.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ua.tours.api.dao.interfaces.TourDao;
import ua.tours.api.models.Tour;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TourDaoImpl implements TourDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Tour getTour(int id) {
        String sql = "SELECT * FROM tours WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        try {
            return jdbcTemplate.queryForObject(sql, params, new TourMapper());
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void createTour(Tour tour) {
        String sql = "INSERT INTO tours " +
                "(title, description, start_date, start_time, end_date, number_of_seats, hotel_id) " +
                "VALUES " +
                "(:title, :description, :startDate, :startTime, :endDate, :numberOfSeats, :hotelId)";
        MapSqlParameterSource params = getTourSQLParams(tour);
        jdbcTemplate.update(sql, params);
    }

    @Override
    public void updateTour(Tour tour) {
        String sql = "UPDATE tours " +
                "SET " +
                "title=:title, " +
                "description=:description, " +
                "start_date=:startDate, " +
                "start_time=:startTime, " +
                "end_date=:endDate, " +
                "number_of_seats=:numberOfSeats, " +
                "hotel_id=:hotelId " +
                "WHERE id=:id";
        MapSqlParameterSource params = getTourSQLParams(tour);
        jdbcTemplate.update(sql, params);
    }

    @Override
    public void removeTour(int tourId) {
        String sql = "DELETE from tours WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", tourId);
        jdbcTemplate.update(sql, params);
    }

    private MapSqlParameterSource getTourSQLParams(Tour tour) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", tour.getTitle());
        params.addValue("description", tour.getDescription());
        params.addValue("startDate", tour.getStartDate());
        params.addValue("startTime", tour.getStartTime());
        params.addValue("endDate", tour.getEndDate());
        params.addValue("numberOfSeats", tour.getNumberOfSeats());
        params.addValue("hotelId", tour.getHotelId());
        params.addValue("id", tour.getId());
        return params;
    }

    private static final class TourMapper implements RowMapper<Tour> {

        public Tour mapRow(ResultSet rs, int rowNum) throws SQLException {
            Tour tour = new Tour();
            tour.setId(rs.getInt("id"));
            tour.setTitle(rs.getString("title"));
            tour.setDescription(rs.getString("description"));
            tour.setStartDate(rs.getDate("start_date"));
            tour.setStartTime(rs.getString("start_time"));
            tour.setEndDate(rs.getDate("end_date"));
            tour.setNumberOfSeats(rs.getInt("number_of_seats"));
            tour.setHotelId(rs.getInt("hotel_id"));
            return  tour;
        }
    }
}
