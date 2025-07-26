package controller;
import java.util.List;

import javax.swing.JOptionPane;

import model.User;
import service.IUserService;
import service.UserService;
import view.IUserView;
import view.MainFrame;


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
        
        userFrame.addLoadUserListener(e -> {
            User selectedUser = (User) userFrame.getUserDropdown().getSelectedItem();
            if (selectedUser != null) {	                	
            	
            	MainFrame mainFrame = new MainFrame(selectedUser);
            	MainFrameController mainController = MainFrameController.getInstance();
            	mainController.setMainFrame(mainFrame);
            	mainFrame.setVisible(true);
            	userFrame.exit();
            	
            } else {
                JOptionPane.showMessageDialog(null, "Please select a user.");
            }
        });
        
    }
    
    
    public void loadUsersToDropdown() {
        List<User> users = userService.getAllUsers();
        User[] userArray = users.toArray(new User[0]);
        userFrame.setUserDropdownOptions(userArray);
    }

    
    private void addUser() {
        String name = userFrame.getName();
        String sexStr = userFrame.getSelectedSex();
        int age = userFrame.getAge();
        double height = userFrame.getUserHeight();
        double weight = userFrame.getUserWeight();
        User user = new User();
        user.setName(name);
        user.setSex(User.Sex.valueOf(sexStr));
        user.setAge(age);
        user.setHeight(height);
        user.setWeight(weight);

        userService.createUser(user);
        loadUsersToDropdown();
        JOptionPane.showMessageDialog(null, "User created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
//        userFrame.clearInputs();
    }
    
	
	
	public void deleteUserById(int id) {
		userService.deleteUserById(id);
	}
	
	public User getUserById(int id) {
		return userService.getUserById(id);
	}
	
	public void updateUserById(User user, int id) {
		userService.updateUserById(user, id);
	}
	
	
}
