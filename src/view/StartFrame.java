package view;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import model.User;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;


import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;



public class StartFrame extends JFrame implements IUserView{
	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    JTextField nameField;
    JComboBox<User.Sex> sexCombo;
    JSpinner ageSpinner;
    JSpinner heightSpinner;
    JSpinner weightSpinner;
    JButton createButton;
    JButton loadButton;
    private JComboBox<User> userDropdown;

    
    //Frame was created with the help of AI
    public StartFrame() {
        setTitle("NutriSci User");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        // Left Panel (Create User)
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Create New User"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.anchor = GridBagConstraints.WEST;

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Name:");
        leftPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        nameField = new JTextField(15);
        leftPanel.add(nameField, gbc);

        // Sex
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel sexLabel = new JLabel("Sex:");
        leftPanel.add(sexLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        sexCombo = new JComboBox<>(User.Sex.values());
        leftPanel.add(sexCombo, gbc);

        // Age
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel ageLabel = new JLabel("Age:");
        leftPanel.add(ageLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        ageSpinner = new JSpinner(new SpinnerNumberModel(18, 0, 120, 1));
        leftPanel.add(ageSpinner, gbc);

        // Height
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel heightLabel = new JLabel("Height (cm):");
        leftPanel.add(heightLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        heightSpinner = new JSpinner(new SpinnerNumberModel(160.0, 50.0, 250.0, 0.1));
        leftPanel.add(heightSpinner, gbc);

        // Weight
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel weightLabel = new JLabel("Weight (kg):");
        leftPanel.add(weightLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        weightSpinner = new JSpinner(new SpinnerNumberModel(60.0, 20.0, 200.0, 0.1));
        leftPanel.add(weightSpinner, gbc);

        // Button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        createButton = new JButton("Create User");
        leftPanel.add(createButton, gbc);

        // Right Panel (Existing User)
        JPanel rightPanel = new JPanel(new GridBagLayout());
        //rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Existing User"));
        rightPanel.setPreferredSize(new Dimension(250, getHeight()));

        JLabel existingLabel = new JLabel("Already an user?");
        existingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        setUserDropdown(new JComboBox<>());
        // Increase user dropdown width
        getUserDropdown().setMaximumSize(new Dimension(600, 30));
        getUserDropdown().setPreferredSize(new Dimension(600, 30));
        getUserDropdown().setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton = new JButton("Load User");
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);

//        rightPanel.add(existingLabel);
//        rightPanel.add(Box.createVerticalStrut(10));
//        rightPanel.add(getUserDropdown());
//        rightPanel.add(Box.createVerticalStrut(20));
//        rightPanel.add(loadButton);

        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.insets = new Insets(5, 5, 5, 5);
        gbcRight.anchor = GridBagConstraints.WEST;

        // Row 0: Label
        gbcRight.gridx = 0;
        gbcRight.gridy = 0;
        gbcRight.anchor = GridBagConstraints.CENTER;
        rightPanel.add(existingLabel, gbcRight);

        // Row 1: Dropdown
        gbcRight.gridy = 1;
        gbcRight.anchor = GridBagConstraints.CENTER;
        rightPanel.add(getUserDropdown(), gbcRight);

        // Row 2: Button
        gbcRight.gridy = 2;
        gbcRight.anchor = GridBagConstraints.CENTER;
        rightPanel.add(loadButton, gbcRight);

     // Create a wrapper panel with horizontal BoxLayout
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.X_AXIS));
     // Limit left panel width
        leftPanel.setPreferredSize(new Dimension(400, getHeight()));
        rightPanel.setPreferredSize(new Dimension(330, getHeight()));

        // Optionally, add some horizontal gap between panels
//        int gap = 10;
//        wrapperPanel.add(leftPanel);
//        wrapperPanel.add(Box.createRigidArea(new Dimension(gap, 0))); // horizontal gap
//        wrapperPanel.add(rightPanel);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.5); // optional: distributes resizing
        contentPane.add(splitPane, BorderLayout.CENTER);

        // Add the wrapper panel to contentPane center
        //contentPane.add(wrapperPanel, BorderLayout.CENTER);

    }

    @Override
    public String getName() { 
    	return nameField.getText(); 
    }
    @Override
    public String getSelectedSex() { 
    	return (String) sexCombo.getSelectedItem().toString(); 
    }
    @Override
    public int getAge() { 
    	return (Integer) ageSpinner.getValue(); 
    }
    @Override
    public double getUserHeight() { 
    	return (Double) heightSpinner.getValue();
    }
    @Override
    public double getUserWeight() {
    	return (Double) weightSpinner.getValue();
    }
    
    @Override
    public void setUserDropdownOptions(User[] users) {
        getUserDropdown().removeAllItems();
        for (User user : users) {
            getUserDropdown().addItem(user);
        }
    }
    
   

    
    @Override
    public void addLoadUserListener(ActionListener listener) {
        loadButton.addActionListener(listener);
    }

    @Override
    public void addCreateUserListener(ActionListener listener) {
        createButton.addActionListener(listener);
    }

    @Override
    public JComboBox<User> getUserDropdown() {
        return userDropdown;
    }

    @Override
    public void setUserDropdown(JComboBox<User> userDropdown) {
        this.userDropdown = userDropdown;
    }

    @Override
    public void exit() {
    	this.dispose();
    }
    
}
