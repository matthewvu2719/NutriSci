package service;

import java.util.List;

import dao.UserDAO;
import model.User;

public class UserService implements IUserService {

	private final UserDAO userDao = UserDAO.getInstance();
	static final UserService userService = new UserService();
	private UserService() {}
	
	public static UserService getInstance() {
		return userService;
	}
	
	@Override
	public void createUser(User user) {
		userDao.insert(user);
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<User> getAllUsers() {
	    return userDao.getAllUsers();
	}

	@Override
	public void deleteUserById(int id) {
		// TODO Auto-generated method stub
		userDao.deleteUserById(id);
	}

	@Override
	public void updateUserById(User user, int id) {
		// TODO Auto-generated method stub
		userDao.updateUserById(user, id);
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return userDao.getUserById(id);
		
	}

}
