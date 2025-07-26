package view;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import model.Log;
import model.User;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainFrame extends JFrame implements IMainFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable logTable;
    private DefaultTableModel tableModel;
    private JButton editLogButton;
    private JButton addLogButton;
    private JLabel dateLabel;
    private JLabel avatarLabel;
    private JButton editUserButton;
    private JButton backButton;
    private List<Log> logList;
    private User user;


    private Set<String> existingLogDates = new HashSet<>(); // simulate stored log dates
    
    public MainFrame(User user) {
    	this.user = user;
        setTitle("Welcome " + user.getName() + " !");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

     // Avatar and Date Panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Avatar
        ImageIcon avatarIcon = new ImageIcon(getClass().getResource("/resources/avatar.jpg"));
        Image scaledAvatar = avatarIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        avatarLabel = new JLabel(new ImageIcon(scaledAvatar));
        avatarLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

        // User Info Panel (Name + Edit Button)
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BoxLayout(userInfoPanel, BoxLayout.Y_AXIS));
        userInfoPanel.setAlignmentY(Component.CENTER_ALIGNMENT);


        JLabel nameLabel = new JLabel(user.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        editUserButton = new JButton("Edit");
        editUserButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        userInfoPanel.add(nameLabel);
        userInfoPanel.add(Box.createVerticalStrut(5));
        userInfoPanel.add(editUserButton);

        // Date Panel aligned top-right
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
        datePanel.setAlignmentY(Component.TOP_ALIGNMENT);
        datePanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // spacing from center

        dateLabel = new JLabel("Date: " + LocalDate.now().toString());
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        datePanel.add(dateLabel);
        datePanel.add(Box.createVerticalGlue()); // push date label to top

        // Assemble top panel
        topPanel.add(avatarLabel);
        topPanel.add(Box.createHorizontalStrut(10));
        topPanel.add(userInfoPanel);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(datePanel);

        add(topPanel, BorderLayout.NORTH);


        // Table Setup
        tableModel = new DefaultTableModel(new Object[]{"Date"}, 0) {
        	private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        
        logTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(logTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        backButton = new JButton("Back");
        editLogButton = new JButton("Edit Selected Log");
        addLogButton = new JButton("Add New Daily Log");

        JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton.setEnabled(true);
        editLogButton.setEnabled(false);
        addLogButton.setEnabled(true);

        buttonPanel.add(addLogButton);
        buttonPanel.add(editLogButton);
        buttonPanel2.add(backButton);
        
        JPanel mainButtonPanel = new JPanel(new BorderLayout());
        
        mainButtonPanel.add(buttonPanel2, BorderLayout.WEST);
        mainButtonPanel.add(buttonPanel, BorderLayout.EAST);
        add(mainButtonPanel, BorderLayout.SOUTH);

        
        
       



        setVisible(true);
    }
    
    //editLog button listener
    @Override
    public void addEditLogListener(ActionListener listener) {
        editLogButton.addActionListener(listener);
    }

    @Override
    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    //addLog button listener
	@Override
	public void addAddLogListener(ActionListener listener) {
		addLogButton.addActionListener(listener);
	}

	//EditUser button listener
	@Override
	public void addEditUserListener(ActionListener listener) {
		// TODO Auto-generated method stub	
		editUserButton.addActionListener(listener);
		
	}

	//initialize list of user's logs
	@Override
	public void setLogList(List<Log> logs) {
		this.logList = new ArrayList<>();
	    for (Log log : logs) {
	    	logList.add(log);
	        String date = log.getDate();
	        if (!existingLogDates.contains(date)) {
	            tableModel.addRow(new Object[]{date});
	            existingLogDates.add(date);
	        }
	    }

	    if (existingLogDates.contains(LocalDate.now().toString())) {
	        addLogButton.setEnabled(false);
	    }
	}

	
	//Get selected log
	@Override
	public Log getSelectedLog() {
	    int selectedIndex = logTable.getSelectedRow();
	    System.out.println(selectedIndex);
	    if (selectedIndex >= 0 && selectedIndex < logList.size()) {
	        return logList.get(selectedIndex);
	    }
	    return null;
	}

	@Override
	public void exit() {
		this.dispose();
	}

	@Override
	public List<Log> getLogList() {
		return logList;
	}
	@Override
	public User getSelectedUser() {
		// TODO Auto-generated method stub
		return user;	
	}

	@Override
	public void addLogSelectionListener(ListSelectionListener listener) {
	    logTable.getSelectionModel().addListSelectionListener(listener);
	}

	@Override
	public DefaultTableModel getTable() {
		// TODO Auto-generated method stub
		return this.tableModel;
	}

	@Override
	public Set<String> getLogDates() {
		// TODO Auto-generated method stub
		return this.existingLogDates;
	}

	@Override
	public JButton getAddLogButton() {
		// TODO Auto-generated method stub
		return this.addLogButton;
	}

	@Override
	public JButton getEditLogButton() {
		// TODO Auto-generated method stub
		return this.editLogButton;
	}
    

    
}
