package controller;


import javax.swing.JOptionPane;

import model.User;
import service.IUserService;
import service.UserService;

import view.IProfileView;
import view.MainFrame;

public class ProfileController {
	private final IUserService userService = UserService.getInstance();
	static final ProfileController profileController = new ProfileController();
	private IProfileView profileFrame;
    
	public static ProfileController getInstance() {
        return profileController;
    }
    
    public void setProfileFrame(IProfileView frame) {
        this.profileFrame = frame;
        initEventListeners();
    }

    private void initEventListeners() {
        profileFrame.addSaveButtonListener(event->{
        	User user = profileFrame.getUser();
        	this.updateUserById(user,user.getId());
        	profileFrame.setUser(user);
        	JOptionPane.showMessageDialog(null, "User updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        });
        profileFrame.addBackButtonListener(event->{
			MainFrame mainFrame = new MainFrame(profileFrame.getUser());
			MainFrameController.getInstance().setMainFrame(mainFrame);
        	mainFrame.setVisible(true);
			profileFrame.exit();
		});
        
    }

	public void updateUserById(User user, int id) {
		userService.updateUserById(user, id);
	}
}
