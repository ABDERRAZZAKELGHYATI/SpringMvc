package ma.youcode.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ma.youcode.api.model.Appointment;
import ma.youcode.api.model.Dates;
import ma.youcode.api.model.User;
import ma.youcode.api.rowmapper.AppointmentRowMapper;
import ma.youcode.api.rowmapper.UserRowMapper;

@Repository
public class AdminDAOImpl implements AdminDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<User> loadUsers() {

		String sql = "SELECT * FROM users WHERE isAuthenticated = 0";

		List<User> usersList = jdbcTemplate.query(sql, new UserRowMapper());

		return usersList;
	}

	@Override
	public int acceptUser(String id) {
		String updateQuery = "UPDATE users SET isAuthenticated = 1 WHERE id = ?";
		
		int afftedRows = jdbcTemplate.update(updateQuery, id);
		

		System.out.println("user accepted with ID: " + id);
		System.out.println("Affected Rows: " + afftedRows);
		
		return afftedRows;
		
	}

	@Override
	public int rejectUser(String id) {
		String deleteQuery = "DELETE FROM users WHERE id = ?";
		int afftedRows = jdbcTemplate.update(deleteQuery, id);
		System.out.println("rejectUser - Affected Rows: " + afftedRows);
		
		return afftedRows;
	}

	@Override
	public List<Appointment> loadAppointments() {
		String sql = "SELECT *\r\n"
				+ "FROM appointments\r\n"
				+ "INNER JOIN users ON appointments.user_id = users.id AND \r\n"
				+ "appointments.isTaken = 1 AND appointments.isAccepted != 1;";

		List<Appointment> appointmentsList = jdbcTemplate.query(sql, new AppointmentRowMapper());
		System.out.println(appointmentsList);
		return appointmentsList;
	}

	@Override
	public int acceptAppointment(Integer id) {
		String updateQuery = "UPDATE appointments SET isAccepted = 1 WHERE id = ?";
		
		int afftedRows = jdbcTemplate.update(updateQuery, id);

		System.out.println("Appointment accepted with ID: " + id);
		System.out.println("Affected Rows: " + afftedRows);
		
		return afftedRows;
	}

	@Override
	public int rejectAppointment(Integer id) {
		String deleteQuery = "DELETE FROM appointments WHERE id = ?";
		int afftedRows = jdbcTemplate.update(deleteQuery, id);
		System.out.println("rejectAppointment - Affected Rows: " + afftedRows);
		
		return afftedRows;
	}

	@Override
	public int createAppointment(Dates date) {
		Object[] sqlArgs = { date.getAppointmentDate(), date.getAppointmentTime(), date.getSeatsNumber() };

		String sql = "INSERT INTO dates(appointment_date, appointment_time, seats_number) VALUES(?, ?, ?)";

		int affectedRow = jdbcTemplate.update(sql, sqlArgs);

		return affectedRow;
	}

}

