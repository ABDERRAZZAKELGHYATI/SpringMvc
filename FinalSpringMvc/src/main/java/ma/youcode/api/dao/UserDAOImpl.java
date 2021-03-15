package ma.youcode.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ma.youcode.api.model.Dates;
import ma.youcode.api.model.User;
import ma.youcode.api.rowmapper.UserRowMapper;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int saveUser(User user) {
		Object[] sqlArgs = { user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword() };

		String sql = "INSERT INTO users(first_name, last_name, email, password) VALUES(?, ?, ?, ?)";

		int isSignedUp = jdbcTemplate.update(sql, sqlArgs);

		return isSignedUp;

	}

	@Override
	public User login(String email, String password) {
		String loginQuery = "SELECT * FROM users WHERE email = ? AND password = ?";
		List<User> user = jdbcTemplate.query(loginQuery, new UserRowMapper(), new Object[] { email, password });

		if (user.size() > 0) {
			return user.get(0);
		} else {
			return null;
		}
	}

	@Override
	public int makeAppointment(Dates date, int id) {

		Object[] sqlArgs = { date.getAppointmentDate(), date.getAppointmentTime(), date.getSeatsNumber(), id , 1};

		String sql = "INSERT INTO appointments(appointment_date, appointment_time, seats_number, user_id, isTaken) VALUES(?, ?, ?, ?, ?)";
		
		int isSignedUp = jdbcTemplate.update(sql, sqlArgs);

		return isSignedUp;
	}

}
