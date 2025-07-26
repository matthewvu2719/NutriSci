package controller;

import java.time.LocalDate;
import java.util.List;

import javax.swing.JOptionPane;

import model.Log;
import model.User;
import service.LogService;
import service.ILogService;
import view.IMainFrame;
import view.MealFrame;
import view.ProfileFrame;
import view.StartFrame;

public class MainFrameController {
	
	private ILogService logService = LogService.getInstance();
	private IMainFrame mainFrame;
	
	private static final MainFrameController mainController = new MainFrameController();
	
	
    public static MainFrameController getInstance() {
        return mainController;
    }
    
    public void setMainFrame(IMainFrame frame) {
        mainFrame = frame;
        int userId = frame.getSelectedUser().getId();
        loadLogToTable(userId);
        initEventListeners();
    }
    
    public void loadLogToTable(int id) {
    	List<Log> logs = logService.getAllLog(id);
        mainFrame.setLogList(logs);
    }

    private void initEventListeners() {
    	
        mainFrame.addEditLogListener(e -> {
        	
        	Log selectedLog = mainFrame.getSelectedLog();
            if (selectedLog != null) {
                MealFrame mealFrame = new MealFrame(selectedLog);
                MealController.getInstance().setMealFrame(mealFrame);
                mealFrame.setVisible(true);
                mainFrame.exit();
            }
        });
        
        
        mainFrame.addEditUserListener(event->{
    		User selectedUser = mainFrame.getSelectedUser();
    		if(selectedUser != null) {
            	ProfileFrame profileFrame = new ProfileFrame(selectedUser);
    			ProfileController profileController = ProfileController.getInstance();
    			profileController.setProfileFrame(profileFrame);
    			profileFrame.setVisible(true);
    			mainFrame.exit();
    		}
    		
        });
        
        mainFrame.addLogSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Log selectedLog = mainFrame.getSelectedLog();
                mainFrame.getEditLogButton().setEnabled(selectedLog != null);
            }
        });

        
		mainFrame.addAddLogListener(e -> {
	            String today = LocalDate.now().toString();
	            if (!mainFrame.getLogDates().contains(today)) {
	                mainFrame.getAddLogButton().setEnabled(true);
	            	mainFrame.getTable().addRow(new Object[]{today});
	            	Log log = new Log();
	            	log.setDate(today);
	            	log.setUserId(mainFrame.getSelectedUser().getId());
	            	int id = logService.createLog(log);
	            	log.setId(id);
	            	mainFrame.getLogList().add(log);
	                mainFrame.getLogDates().add(today);
	                mainFrame.getAddLogButton().setEnabled(false);
	                loadLogToTable(mainFrame.getSelectedUser().getId());
	                JOptionPane.showMessageDialog(null, "Log added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	            
	            }
	     });
		
		mainFrame.addBackListener(e->{
			StartFrame startFrame = new StartFrame();
			UserController.getInstance().setUserFrame(startFrame);
			startFrame.setVisible(true);
			mainFrame.exit();
		});
		


    }

}
