package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import model.Food;
import model.Log;
import model.Meal;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;




public class MealFrame extends JFrame implements IMealView {
    private static final long serialVersionUID = 1L;
    private JComboBox<Food>[] mealDropdowns;
    private JButton[] addButtons;
    private JTable[] mealTables;
    private DefaultTableModel[] tableModels;
private ChartPanelWrapper chart;
    private PiePanelWrapper pie1;
    private PiePanelWrapper pie2;
    private Log log;
    private JButton backButton;
    private JButton saveButton;
    private Meal breakfast;
    private Meal lunch;
    private Meal dinner;
    private Meal snack;
    private List<Meal> meals;
    private JButton[] swapButtons;
    private JButton[] removeButtons;


    public MealFrame(Log l) {
//    	breakfast = new Meal(); breakfast.setFood(new ArrayList<>()); breakfast.setName("Breakfast");
//    	lunch = new Meal();     lunch.setFood(new ArrayList<>()); lunch.setName("Lunch");
//    	dinner = new Meal();    dinner.setFood(new ArrayList<>()); dinner.setName("Dinner");
//    	snack = new Meal();     snack.setFood(new ArrayList<>()); snack.setName("Snack");

    	log = l;
        setTitle("Meal Logging");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 800);
        setLocationRelativeTo(null);

        // Main panel with border title
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), 
            "Daily log: "+log.getDate(), 
            TitledBorder.CENTER, 
            TitledBorder.TOP
        ));

        // Left panel - now contains both dropdowns and text areas
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] meals = {"Breakfast", "Lunch", "Dinner", "Snack"};
        mealDropdowns = new JComboBox[meals.length];
        addButtons = new JButton[meals.length];
        mealTables = new JTable[meals.length];
        tableModels = new DefaultTableModel[meals.length];
        swapButtons = new JButton[meals.length];
        removeButtons = new JButton[meals.length];


        for (int i = 0; i < meals.length; i++) {
            // Create a panel for each meal
            JPanel mealPanel = new JPanel(new BorderLayout());
            mealPanel.setBorder(BorderFactory.createTitledBorder(meals[i]));
            
            // Panel for dropdown and add button
            JPanel topPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.anchor = GridBagConstraints.WEST;

            // Label
            JLabel label = new JLabel("Add Food:");
            gbc.gridx = 0;
            gbc.gridy = 0;
            topPanel.add(label, gbc);

            // Editable ComboBox
            JComboBox<Food> mealCombo = new JComboBox<>();
            mealCombo.setEditable(true);
            mealCombo.setPreferredSize(new Dimension(200, 25));
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            topPanel.add(mealCombo, gbc);
            mealDropdowns[i] = mealCombo;

            // Add Button
            JButton addBtn = new JButton("Add");
            addBtn.setEnabled(false);
            gbc.gridx = 2;
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
            topPanel.add(addBtn, gbc);
            addButtons[i] = addBtn;

            String[] columnNames = {"Description"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            JTable table = new JTable(model);
            mealTables[i] = table;
            tableModels[i] = model;

            
            mealPanel.add(topPanel, BorderLayout.NORTH);
            JScrollPane scrollPane = new JScrollPane(table);
            mealPanel.add(scrollPane, BorderLayout.CENTER);

            // Enable/disable Remove button on selection
            int index = i; // capture loop variable
            table.getSelectionModel().addListSelectionListener(e -> {
                boolean rowSelected = table.getSelectedRow() != -1;
                removeButtons[index].setEnabled(rowSelected);
            });
            
            

         // Panel for Swap and Remove buttons
            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton swapBtn = new JButton("Swap");
            JButton removeBtn = new JButton("Remove Food");
            swapButtons[i] = swapBtn;
            removeButtons[i] = removeBtn;

            swapBtn.setEnabled(false);
            removeBtn.setEnabled(false);
            
            bottomPanel.add(swapBtn);
            bottomPanel.add(removeBtn);

            
            // Add bottom panel below scrollPane
            mealPanel.add(bottomPanel, BorderLayout.SOUTH);

            
            leftPanel.add(mealPanel);
            leftPanel.add(Box.createVerticalStrut(10));
        }

        chart = new ChartPanelWrapper(
        	    0,0,0,0,0,0,0,0
        );
        
        pie1 = new PiePanelWrapper(25,25,50,100,"Canada's Food Guide for Food Groups Portions");
        pie2 = new PiePanelWrapper(0,0,0,0,"Current Food Groups Portion");

        JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, chart);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pie2, pie1);
        splitPane1.setDividerLocation(400);
        splitPane1.setResizeWeight(0.5); // optional: distributes resizing
        splitPane2.setDividerLocation(330);
        splitPane2.setResizeWeight(0.5); // optional: distributes resizing
        JSplitPane mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, splitPane1, splitPane2);        
        mainPanel.add(mainPane, BorderLayout.CENTER);
        
         backButton = new JButton("Back");
         saveButton = new JButton("Save");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(saveButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent e) {
                requestFocus();
            }
        });
    }


    public void setFoodDropdownOptions(Food[] foodArray) {
        for (JComboBox<Food> dropdown : mealDropdowns) {
            DefaultComboBoxModel<Food> model = new DefaultComboBoxModel<>(foodArray);
            dropdown.setModel(model);
            
            dropdown.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, 
                        int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Food) {
                        setText(((Food)value).getDescription());
                    }
                    return this;
                }
            });
        }
    }
    
    @Override
    public void addAddFoodListener(ActionListener listener) {
        for (JButton addButton : addButtons) {
            addButton.addActionListener(listener);
        }
    }


    @Override
    public JComboBox<Food> getFoodDropdown() {
        return mealDropdowns[0]; // Return first dropdown for backward compatibility
    }

    @Override
    public JButton getAddButton() {
        return addButtons[0]; // Return first button for backward compatibility
    }


 
    
    @Override
    public JComboBox<Food>[] getMealDropdowns() {
        return mealDropdowns;
    }

    @Override
    public JButton[] getAddButtons() {
        return addButtons;
    }
    

    @Override
    public void addFoodToTable(int mealIndex, Food food) {
        tableModels[mealIndex].addRow(new Object[]{
            food.getDescription()
        });

        // Enable swap button if table is not empty
        if (tableModels[mealIndex].getRowCount() > 0) {
            swapButtons[mealIndex].setEnabled(true);
        }
    }


	@Override
	public void addDataSetToChart(double protein, double carbs, double fibre, double fat, double sodium, double cal,double total) {
		sodium/=1000;
		chart.incrementValues(protein, carbs, fat, fibre, sodium, cal,total);
		
	}

	
	@Override
	public void addDataSetToPie(int foodGroupId, boolean isRemove) {
	    int direction = isRemove ? -1 : 1;

	    switch (foodGroupId) {
	        case 9, 11: // Fruit & Veg
	            pie2.incrementFruitVeggies(direction);
	            break;
	        case 8, 20, 18: // Whole Grains
	            pie2.incrementWholeGrains(direction);
	            break;
	        case 1, 5, 10, 13, 15, 16, 12, 17, 7: // Protein
	            pie2.incrementProteinFoods(direction);
	            break;
	        case 6, 21, 22: // All
	            pie2.incrementAll(direction);
	            break;
	    }
	}



	@Override
	public void addBackButtonListener(ActionListener listener) {
		// TODO Auto-generated method stub
		backButton.addActionListener(listener);
		
	}


	@Override
	public void addsaveButtonListener(ActionListener listener) {
		// TODO Auto-generated method stub
		saveButton.addActionListener(listener);
	}

	@Override
	public void addSwapButtonListener(ActionListener listener) {
	    for (JButton btn : swapButtons) {
	        btn.addActionListener(listener);
	    }
	}

	@Override
	public void addRemoveFoodButtonListener(ActionListener listener) {
	    for (JButton btn : removeButtons) {
	        btn.addActionListener(listener);
	    }
	}




	@Override
	public void exit() {
		// TODO Auto-generated method stub
		this.dispose();
		
	}


	@Override
	public void addFoodToBreakfast(Food food) {
		// TODO Auto-generated method stub
		
		breakfast.getFood().add(food);
		swapButtons[0].setEnabled(true);
	}


	@Override
	public void addFoodToLunch(Food food) {
		// TODO Auto-generated method stub
		lunch.getFood().add(food);
		swapButtons[1].setEnabled(true);
	}


	@Override
	public void addFoodToDinner(Food food) {
		// TODO Auto-generated method stub
		dinner.getFood().add(food);
		swapButtons[2].setEnabled(true);
	}


	@Override
	public void addFoodToSnack(Food food) {
		// TODO Auto-generated method stub
		snack.getFood().add(food);
		swapButtons[3].setEnabled(true);
	}


	@Override
	public Meal getBreakfast() {
		// TODO Auto-generated method stub
		return breakfast;
	}


	@Override
	public Meal getLunch() {
		// TODO Auto-generated method stub
		return lunch;
	}


	@Override
	public Meal getDinner() {
		// TODO Auto-generated method stub
		return dinner;
	}


	@Override
	public Meal getSnack() {
		// TODO Auto-generated method stub
		return snack;
	}


	@Override
	public void setBreakfast(Meal meal) {
		// TODO Auto-generated method stub
		breakfast = meal;
	}


	@Override
	public void setLunch(Meal meal) {
		// TODO Auto-generated method stub
		lunch = meal;
	}


	@Override
	public void setDinner(Meal meal) {
		// TODO Auto-generated method stub
		dinner = meal;
	}


	@Override
	public void setSnack(Meal meal) {
		// TODO Auto-generated method stub
		snack = meal;
	}


	@Override
	public Log getLog() {
		// TODO Auto-generated method stub
		return log;
	}


	@Override
	public JButton[] getRemoveButtons() {
		// TODO Auto-generated method stub
		return removeButtons;
	}


	@Override
	public JButton[] getSwapButtons() {
		// TODO Auto-generated method stub
		return swapButtons;
	}


	@Override
	public Food removeFoodFromTable(int mealIndex, int rowIndex) {
	    if (rowIndex >= 0 && rowIndex < tableModels[mealIndex].getRowCount()) {
	        // Get the food name from the table (assuming it's just the description column)
	        String foodName = (String) tableModels[mealIndex].getValueAt(rowIndex, 0);

	        // Remove from the table model
	        tableModels[mealIndex].removeRow(rowIndex);

	        // Remove from the internal Meal object
	        Meal[] mealArray = {breakfast, lunch, dinner, snack};
	        Meal meal = mealArray[mealIndex];

	        Food removedFood = null;
	        for (Food f : meal.getFood()) {
	            if (f.getDescription().equals(foodName)) {
	                removedFood = f;
	                meal.getFood().remove(f);
	                break;
	            }
	        }

	        // Disable swap button if table becomes empty
	        if (tableModels[mealIndex].getRowCount() == 0) {
	            swapButtons[mealIndex].setEnabled(false);
	        }

	        return removedFood;
	    }
	    return null;
	}



	@Override
	public JTable[] getMealTables() {
		// TODO Auto-generated method stub
		return mealTables;
	}


	@Override
	public List<Meal> getMeals() {
		// TODO Auto-generated method stub
		return meals;
	}

	@Override
	public void setMeals(List<Meal> meals) {
		// TODO Auto-generated method stub
		this.meals = meals;
	}
	
	
	
	
}

