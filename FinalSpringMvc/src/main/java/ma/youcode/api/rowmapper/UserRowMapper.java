package ma.youcode.api.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

import ma.youcode.api.model.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {

		User user = new User(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"),
				rs.getString("email"), rs.getString("password"), rs.getString("role"),
				rs.getBoolean("isAuthenticated"));

		return user;
	}

}
