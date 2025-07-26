package view;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextArea;

import model.Food;
import model.Log;
import model.Meal;

public interface IMealView {
	public void setFoodDropdownOptions(Food[] food);
	public void addAddFoodListener(ActionListener listener);
	
	public void addBackButtonListener(ActionListener listener);
	public void addsaveButtonListener(ActionListener listener);
	JComboBox<Food> getFoodDropdown();
    JButton getAddButton();
    public void exit();
    public void addFoodToBreakfast(Food food);
    public void addFoodToLunch(Food food);
    public void addFoodToDinner(Food food);
    public void addFoodToSnack(Food food);
    public Meal getBreakfast();
    public Meal getLunch();
    public Meal getDinner();
    public Meal getSnack();
    public void setBreakfast(Meal meal);
    public void setLunch(Meal meal);
    public void setDinner(Meal meal);
    public void setSnack(Meal meal);
    public List<Meal> getMeals();
    public Log getLog();

    public JComboBox<Food>[] getMealDropdowns();
    public JButton[] getAddButtons();
    public JButton[] getRemoveButtons();
    public JButton[] getSwapButtons();
    //public void enableAutoComplete(JComboBox<String> comboBox, List<String> items, JButton addButton);

    public void addDataSetToChart(double protein, double carbs, double fibre, double fat, double sodium, double cal,double total);
    
    
    public void addSwapButtonListener(ActionListener listener);
	public void addRemoveFoodButtonListener(ActionListener listener);
	
	public void addFoodToTable(int mealIndex, Food food);
	
	// Removes the row from the table and returns the Food that was removed.
	Food removeFoodFromTable(int mealIndex, int rowIndex);

	// Optionally modify `addDataSetToPie` to accept a remove flag
	public void addDataSetToPie(int foodGroupId, boolean isRemove);
	
	public JTable[] getMealTables();
	public void setMeals(List<Meal> meals);
	
	

}

