package ma.youcode.api.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ma.youcode.api.model.Appointment;
import ma.youcode.api.model.Dates;
import ma.youcode.api.model.User;
import ma.youcode.api.service.AdminService;

@Controller
public class AdminController {
	private String isAppointmentCreated = null;
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping(value = "/admin")
	public String admin(Model model, HttpSession session) {
		List<User> usersList = adminService.loadUsers();
		List<Appointment> appointmentsList = adminService.loadAppointments();

		model.addAttribute("users", usersList);
		model.addAttribute("appointmentsList", appointmentsList);
		model.addAttribute("isAppointmentCreated", isAppointmentCreated);
		
		User user = (User) session.getAttribute("currentUser");
		System.out.println("@@SessionAttribute User: " + user);
		
		if (user != null) {

			return "admin";	
		} else {
			return "redirect:/";	
		}
	}
	
	@PostMapping(value = "/accept")
	public String accept(User user) {
		System.out.println("Accept User: " + user);
		
		adminService.acceptUser(user.getId().toString(), user.getEmail());
		
		return "redirect:/admin";
	}
	
	@PostMapping(value = "/reject")
	public String reject(User user) {
		System.out.println("Reject User: " + user);
		
		adminService.rejectUser(user.getId().toString(), user.getEmail());

		return "redirect:/admin";
	}
	
	@PostMapping(value = "/acceptAppointment")
	public String acceptAppointment(Appointment appointment) {
		System.out.println("Accept Appointment with ID : " + appointment.getId());
		System.out.println("Accept Appointment with email : " + appointment.getUser().getEmail());
//		adminService.rejectUser(user.getId().toString(), user.getEmail());
		
		adminService.acceptAppointment(appointment.getId(), appointment.getUser().getEmail());
		
		return "redirect:/admin";
	}
	
	@PostMapping(value = "/rejectAppointment")
	public String rejectAppointment(Appointment appointment) {
		System.out.println("Reject Appointment with ID : " + appointment.getId());
		System.out.println("Reject Appointment with email : " + appointment.getUser().getEmail());
//		adminService.rejectUser(user.getId().toString(), user.getEmail());
		
		adminService.rejectAppointment(appointment.getId(), appointment.getUser().getEmail());
		
		return "redirect:/admin";
	}
	
	@PostMapping(value = "/createDate")
	public String createDate(Dates date) {
		System.out.println("Create date : " + date);
		
		int affectedRow = adminService.createAppointment(date);
		
		if (affectedRow > 0) {
			isAppointmentCreated = "true";
			System.out.println("create Apoointment - affected row : " + affectedRow);
		}else {
			isAppointmentCreated = "false";
		}
		
		return "redirect:/admin";
	}
	
}
