package view;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;


import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;



public class UserFrame extends JFrame implements IUserView{
	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    
    JTextField nameField;
    JComboBox<String> sexCombo;
    JSpinner ageSpinner;
    JSpinner heightSpinner;
    JSpinner weightSpinner;
    JButton createButton;
    JButton loadButton;
    JComboBox<String> userDropdown;
    
    //Frame was created with the help of AI
    public UserFrame() {
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
        sexCombo = new JComboBox<>(new String[] { "Male", "Female", "Other" });
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
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Existing User"));
        rightPanel.setPreferredSize(new Dimension(250, getHeight()));

        JLabel existingLabel = new JLabel("Already an user?");
        existingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userDropdown = new JComboBox<>();
        // Increase user dropdown width
        userDropdown.setMaximumSize(new Dimension(600, 30));
        userDropdown.setPreferredSize(new Dimension(600, 30));
        userDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton = new JButton("Load User");
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        rightPanel.add(existingLabel);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(userDropdown);
        rightPanel.add(Box.createVerticalStrut(20));
        rightPanel.add(loadButton);

     // Create a wrapper panel with horizontal BoxLayout
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.X_AXIS));
     // Limit left panel width
        leftPanel.setPreferredSize(new Dimension(400, getHeight()));
        rightPanel.setPreferredSize(new Dimension(330, getHeight()));

        // Optionally, add some horizontal gap between panels
        int gap = 10;
        wrapperPanel.add(leftPanel);
        wrapperPanel.add(Box.createRigidArea(new Dimension(gap, 0))); // horizontal gap
        wrapperPanel.add(rightPanel);

        // Add the wrapper panel to contentPane center
        contentPane.add(wrapperPanel, BorderLayout.CENTER);

    }

    @Override
    public String getName() { 
    	return nameField.getText(); 
    }
    @Override
    public String getSelectedSex() { 
    	return (String) sexCombo.getSelectedItem(); 
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
    public void setUserDropdownOptions(String[] users) {
        userDropdown.removeAllItems();
        for (String user : users) {
            userDropdown.addItem(user);
        }
    }
    
    @Override
    public void addCreateUserListener(ActionListener listener) {
        createButton.addActionListener(listener);
    }
    
}
