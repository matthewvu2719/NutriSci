package controller;
import java.util.List;

import model.User;
import service.IUserService;
import service.UserService;
import view.IUserView;


public class UserController {
	private final IUserService userService = UserService.getInstance();
	static final UserController userController = new UserController();
	
	private IUserView userFrame;
	
	private UserController() {}
	
	public static UserController getInstance() {
		return userController;
	}
	
	
	
    public void setUserFrame(IUserView frame) {
        this.userFrame = frame;
        initEventListeners();
        loadUsersToDropdown();
    }
    
    
    private void initEventListeners() {
        userFrame.addCreateUserListener(e -> addUser());
    }
    
    
    public void loadUsersToDropdown() {
    	 List<User> users = userService.getAllUsers();
    	    String[] userList = users.stream()
    	        .map(user -> String.format("%s (%s, %d years old, %.1f cm, %.1f kg)",
    	                                   user.getName(),
    	                                   user.getSex(),
    	                                   user.getAge(),
    	                                   user.getHeight(),
    	                                   user.getWeight()))
    	        .toArray(String[]::new);
    	    userFrame.setUserDropdownOptions(userList);
    }
    
    private void addUser() {
        String name = userFrame.getName();
        String sexStr = userFrame.getSelectedSex();
        int age = userFrame.getAge();
        double height = userFrame.getUserHeight();
        double weight = userFrame.getUserWeight();

//        if (name.isEmpty()) {
//            userFrame.showMessage("Name cannot be empty");
//            return;
//        }

        User user = new User();
        user.setName(name);
        user.setSex(User.Sex.valueOf(sexStr));
        user.setAge(age);
        user.setHeight(height);
        user.setWeight(weight);

        userService.createUser(user);
        loadUsersToDropdown();
//        userFrame.showMessage("User created successfully!");
//        userFrame.clearInputs();
    }
    
	
	
	public void deleteUserById(int id) {
		userService.deleteUserById(id);
	}
	
	public void getUserById(int id) {
		userService.getUserById(id);
	}
	
	public void updateUserById(User user, int id) {
		userService.updateUserById(user, id);
	}
	
	
}
