package in.Ashokit.Service;

import java.util.Optional;

import in.Ashokit.Entity.User;

public interface UserService {

	public boolean saveUser(User user);

	public User getUser(String email, String pwd);

	public Optional<User> findByEmail(String email);

	public Optional<User> findById(Integer id);
}
