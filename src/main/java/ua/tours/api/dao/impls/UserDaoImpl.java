package ua.tours.api.dao.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ua.tours.api.dao.interfaces.UserDao;
import ua.tours.api.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

@Component
public class UserDaoImpl implements UserDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public User getUserByID(int id) {
        String sql = "SELECT * FROM users WHERE id=:id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        try {
            return jdbcTemplate.queryForObject(sql, params, new UserMapper());
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void insertUser(User user) {
        String sql = "INSERT INTO users " +
                "(username, email, password, full_name, mobile_number)" +
                "VALUES" +
                "(:username, :email, :password, :fullName, :mobileNumber)";
        MapSqlParameterSource params =  getUserSQLParams(user);
        jdbcTemplate.update(sql, params);
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE users SET " +
                "username=:username, " +
                "email=:email, " +
                "password=:password, " +
                "full_name=:fullName, " +
                "mobile_number=:mobileNumber " +
                "WHERE id=:id";
        MapSqlParameterSource params = getUserSQLParams(user);
        jdbcTemplate.update(sql, params);
    }

    @Override
    public void removeUser(int userId) {

    }



    private MapSqlParameterSource getUserSQLParams(User user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", user.getId());
        params.addValue("username", user.getUsername());
        params.addValue("email", user.getEmail());
        params.addValue("password", user.getPassword());
        params.addValue("fullName", user.getFullName());
        params.addValue("mobileNumber", user.getMobileNum());
        return params;
    }

    private static final class UserMapper implements RowMapper<User> {

        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setFullName(rs.getString("full_name"));
            user.setMobileNum(rs.getString("mobile_number"));
            return  user;
        }
    }
}
