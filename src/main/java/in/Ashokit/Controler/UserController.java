package in.Ashokit.Controler;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.Ashokit.Entity.User;
import in.Ashokit.Service.UserServiceImpl;

@Controller
public class UserController {

	@Autowired
	private UserServiceImpl userService;

	// login-page-display
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "index";
	}

	// login -button-handle
	@PostMapping("/login")
	public String handleLogin(User user, Model model) {
		User userObj = userService.getUser(user.getEmail(), user.getPwd());
		if (userObj == null) {
			model.addAttribute("emsg", "Invalid Credentials");
			return "index";
		} else {
			model.addAttribute("msg", userObj.getName() + " " + "welcome to Ashok It...");

			return "dashboard";
		}

	}

	@GetMapping("/register")
	public String resistration(Model model) {
		model.addAttribute("user", new User());
		return "registerview";

	}

	@PostMapping("/register")
	public String handleRegister(User user, Model model) {
		Optional<User> byEmail = userService.findByEmail(user.getEmail());
		boolean saveUser = false;
		if (byEmail.isPresent()) {
			model.addAttribute("vmsg", "Email exists,Use an another Email to Register");
		} else {
			saveUser = userService.saveUser(user);
		}

		if (saveUser) {
			model.addAttribute("msg", "User registered");
		} else {
			model.addAttribute("emsg", "Registration Failed");
		}
		return "registerview";

	}

	@GetMapping("/logout")
	public String logout(Model model) {
		model.addAttribute("user", new User());

		return "index";

	}

	@GetMapping("/loadForgotPassword")
	public String loadForgotPassword(Model model) {
		model.addAttribute("user", new User());
		return "forgot_password";

	}

	@GetMapping("/loadResetPassword/{id}")
	public String loadResetPassword(@PathVariable Integer id, Model model) {
		System.out.println(id);
		model.addAttribute("id", id);
		model.addAttribute("user", new User());

		return "reset_password";
	}

	@PostMapping("/forgotPassword")
	public String forgotPassword(@RequestParam String email, Model model) {
		Optional<User> user = userService.findByEmail(email);
		model.addAttribute("user", new User());

		if (user.isPresent()) {
			User getUser = user.get();
			return "redirect:/loadResetPassword/" + getUser.getUid();
		} else {
			model.addAttribute("msg", "invalide email & password");
			return "forgot_password";
		}

	}

	@PostMapping("/resetPassword")
	public String resetPassword(@RequestParam String pwd, @RequestParam Integer id, Model model) {
		Optional<User> byId = userService.findById(id);
		User user = byId.get();
		user.setPwd(pwd);
		boolean saveUser = userService.saveUser(user);
		model.addAttribute("user", new User());
		if (saveUser) {
			model.addAttribute("msg", "password successfully changed");
			return"reset_password";
		}else {
		return "redirect:/loadForgotPassword";}
	}

}
