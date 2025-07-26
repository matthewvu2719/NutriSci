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
import model.User.Sex;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;


import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;



public class ProfileFrame extends JFrame implements IProfileView{
	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private User user;
    
    JTextField nameField;
    JComboBox<String> sexCombo;
    JSpinner ageSpinner;
    JSpinner heightSpinner;
    JSpinner weightSpinner;
    JButton saveButton;
    JButton backButton;

    
    //Frame was created with the help of AI
    public ProfileFrame(User user) {
    	this.user = user;
        setTitle(user.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Edit User"));
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

     // Buttons Panel
        JPanel buttonPanel = new JPanel(); // uses FlowLayout by default
        saveButton = new JButton("Save");
        backButton = new JButton("Back");
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        // Add the button panel to the GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        leftPanel.add(buttonPanel, gbc);


        contentPane.add(leftPanel, BorderLayout.CENTER);
        
     // Set default values based on user object
        nameField.setText(user.getName());
        sexCombo.setSelectedItem(user.getSex());
        ageSpinner.setValue(user.getAge());
        heightSpinner.setValue(user.getHeight());
        weightSpinner.setValue(user.getWeight());

    }

    
    @Override
    public User getUser() {
        user.setName(nameField.getText());
        String sexStr = (String)sexCombo.getSelectedItem();
        user.setSex(User.Sex.valueOf(sexStr));
        user.setAge((Integer) ageSpinner.getValue());
        user.setHeight((Double) heightSpinner.getValue());
        user.setWeight((Double) weightSpinner.getValue());
        return user;
    }
    
    public void setUser(User user) {
    	this.user = user;
    }

    
    @Override
    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    @Override
    public void addSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }


	@Override
	public void exit() {
		// TODO Auto-generated method stub
		this.dispose();
		
	}

    
}
