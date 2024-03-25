package in.Ashokit.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.Ashokit.Entity.User;
import in.Ashokit.Repo.UserRepo;
import in.Ashokit.email.EmailUtils;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public boolean saveUser(User user) {
		// TODO Auto-generated method stub

		User savedUser = userRepo.save(user);

		if (savedUser.getUid() != null) {
			String subject = "your Account Created-Ashok IT";
			String body = "congratulations,welcome to dashboard";
			emailUtils.sendEmail(user.getEmail(), subject, body);
		}

		return true;
	}

	@Override
	public User getUser(String email, String pwd) {
		// TODO Auto-generated method stub
		return userRepo.findByEmailAndPwd(email, pwd);

	}

	public Optional<User> findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public Optional<User> findById(Integer id) {
		Optional<User> byId = userRepo.findById(id);
		return byId;
	}

}
