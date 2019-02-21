package ua.tours.api.dao.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.tours.api.dao.interfaces.TourReservationDao;
import ua.tours.api.models.TourReservation;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class TourReservationDaoImpl implements TourReservationDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public TourReservation getReservationById(int id) {
        String sql = "SELECT * FROM tour_reservations WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        try {
            return jdbcTemplate.queryForObject(sql, params, new TourReservationMapper());
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<TourReservation> listReservationsByUserId(int userId) {
        String sql = "SELECT * FROM tour_reservations WHERE user_id=:userId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        try {
            return jdbcTemplate.query(sql, params, new TourReservationMapper());
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int createReservation(TourReservation reservation) {
        String sql = "INSERT INTO tour_reservations " +
                "(user_id, tour_id, reservation_comment) " +
                "VALUES " +
                "(:userId, :tourId, :reservationComment)";
        MapSqlParameterSource params = getTourReservationSQLParams(reservation);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        return (int) keyHolder.getKey();
    }

    @Override
    public void updateReservation(TourReservation reservation) {
        String sql = "UPDATE tour_reservations " +
                "SET " +
                "user_id=:userId, tour_id=:tourId, reservation_comment=:reservationComment " +
                "WHERE id=:id";
        MapSqlParameterSource params = getTourReservationSQLParams(reservation);
        jdbcTemplate.update(sql, params);
    }


    private MapSqlParameterSource getTourReservationSQLParams(TourReservation reservation) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", reservation.getId());
        params.addValue("userId", reservation.getUserId());
        params.addValue("tourId", reservation.getTour());
        params.addValue("reservationComment", reservation.getComment());
        return params;
    }

    private static final class TourReservationMapper implements RowMapper<TourReservation> {

        public TourReservation mapRow(ResultSet rs, int rowNum) throws SQLException {
            TourReservation reservation = new TourReservation();
            reservation.setId(rs.getInt("id"));
            reservation.setUserId(rs.getInt("user_id"));
            reservation.setTourId(rs.getInt("tour_id"));
            reservation.setComment(rs.getString("reservation_comment"));
            return  reservation;
        }
    }
}
