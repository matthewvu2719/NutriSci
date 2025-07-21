package service;

import java.util.List;

import model.User;

public interface IUserService {
	public void createUser(User user);
	public void deleteUserById(int id);
	public void updateUserById(User user, int id);
	public User getUserById(int id);
	public List<User> getAllUsers();

}
