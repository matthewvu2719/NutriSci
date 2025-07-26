package main;

import javax.swing.JOptionPane;

import javax.swing.SwingUtilities;

import controller.MainFrameController;
import controller.MealController;
import controller.ProfileController;
import controller.UserController;
import view.MainFrame;
import view.MealFrame;
import view.ProfileFrame;
import view.StartFrame;
import model.User;
public class Main {
	
	static StartFrame userView;
	static MainFrame mainFrame;
	static MealFrame mealFrame;
	static ProfileFrame profileFrame;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				//User create use case
				userView = new StartFrame();
	            UserController userController = UserController.getInstance();
	            userController.setUserFrame(userView);
	            userView.setVisible(true);
//	            userView.addLoadUserListener(e -> {
//	                User selectedUser = (User) userView.getUserDropdown().getSelectedItem();
//	                if (selectedUser != null) {	                	
//	                	
//	                	mainFrame = new MainFrame(selectedUser);
//	                	MainFrameController mainController = MainFrameController.getInstance();
//	                	mainController.setMainFrame(mainFrame);
//	                	mainFrame.setVisible(true);
//	                	userView.dispose();
//	                	
//	                } else {
//	                    JOptionPane.showMessageDialog(userView, "Please select a user.");
//	                }
//	            });

				
			}
			
			
		});
	}

}
