package view;

import javax.swing.JFrame;
import javax.swing.JPanel;


import javax.swing.*;
import javax.swing.border.TitledBorder;


import java.awt.*;
import java.awt.event.ActionListener;

public class MealFrame extends JFrame implements IMealView {
	private static final long serialVersionUID = 1L;
//    private JPanel contentPane;
    private JComboBox<String> foodDropdown;
    private JButton addButton;
    private JButton swapButton;
    private JTextArea mealTextArea;

    
    //Frame was created with the help of AI
    public MealFrame() {
        setTitle("Meal Logging");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        // Main panel with border title
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Meal Logging", TitledBorder.CENTER, TitledBorder.TOP));

        // Left panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel addFoodLabel = new JLabel("Add Food");
        addFoodLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(addFoodLabel);
        leftPanel.add(Box.createVerticalStrut(10));

        foodDropdown = new JComboBox<>();
        foodDropdown.setMaximumSize(new Dimension(500, 30));
        leftPanel.add(foodDropdown);
        leftPanel.add(Box.createVerticalStrut(10));

        addButton = new JButton("Add");
        addButton.setEnabled(false);
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(addButton);

        // Right panel
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel mealLabel = new JLabel("Meal");
        mealLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(mealLabel);
        rightPanel.add(Box.createVerticalStrut(10));

        mealTextArea = new JTextArea(10, 30);
        mealTextArea.setEditable(false);
        mealTextArea.setLineWrap(true);
        mealTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(mealTextArea);
        rightPanel.add(scrollPane);
        rightPanel.add(Box.createVerticalStrut(10));

        swapButton = new JButton("Swap");
        swapButton.setEnabled(false);
        swapButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(swapButton);
        
        leftPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        rightPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
        
        // Horizontal split panel
//        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
//        splitPane.setResizeWeight(0.5);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(400); // Set left panel width explicitly
        splitPane.setResizeWeight(0);      // Left side stays fixed

        mainPanel.add(splitPane, BorderLayout.CENTER);
        add(mainPanel);

	        // Behavior
	//        foodDropdown.addActionListener(e -> {
	//            addButton.setEnabled(foodDropdown.getSelectedItem() != null);
	//        });
	//
	//        addButton.addActionListener((ActionEvent e) -> {
	//            String selectedFood = (String) foodDropdown.getSelectedItem();
	//            if (selectedFood != null) {
	//                mealTextArea.append(selectedFood + "\n");
	//                swapButton.setEnabled(true);
	//            }
	//        });
        
        
    }

	@Override
	public void setFoodDropdownOptions(String[] foodList) {
		 foodDropdown.removeAllItems();
	        for (String food : foodList) {
	            foodDropdown.addItem(food);
	        }
	}
	
	@Override
	public void addAddFoodListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }
	@Override
	public void addSwapListener(ActionListener listener) {
		swapButton.addActionListener(listener);
	}

	@Override
	public JComboBox<String> getFoodDropdown() {
	    return foodDropdown;
	}

	@Override
	public JButton getAddButton() {
	    return addButton;
	}

	@Override
	public JButton getSwapButton() {
	    return swapButton;
	}

	@Override
	public JTextArea getMealTextArea() {
	    return mealTextArea;
	}
}

