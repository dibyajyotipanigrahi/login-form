package in.Ashokit.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.Ashokit.Entity.User;

public interface UserRepo extends JpaRepository<User,Integer> {
	public User findByEmailAndPwd(String email,String pwd);

	public Optional<User> findByEmail(String email);
	
	
}
