package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Food;
import service.FoodService;
import service.IFoodService;
import view.IMealView;

public class MealController {
	private final List<String> mealItems = new ArrayList<>();
	private final IFoodService foodService = FoodService.getInstance();
	static final MealController mealController = new MealController();
	
	private IMealView mealFrame;
	
	private MealController() {}
	
	public static MealController getInstance() {
		return mealController;
	}
	
	
	//initialize the view MealFrame
    public void setMealFrame(IMealView frame) {
        this.mealFrame = frame;
        loadFoodToDropdown();
        initEventListeners();
    }
    
    private void initEventListeners() {
        // Enable/disable Add button when dropdown changes
        mealFrame.getFoodDropdown().addActionListener(e -> {
            Object selected = mealFrame.getFoodDropdown().getSelectedItem();
            mealFrame.getAddButton().setEnabled(selected != null);
        });

        // Add food when Add button is clicked
        mealFrame.addAddFoodListener(e -> {
            String selectedFood = (String) mealFrame.getFoodDropdown().getSelectedItem();
            if (selectedFood != null) {
                addFood(selectedFood);
            }
        });

        // Swap food when Swap button is clicked
        mealFrame.addSwapListener(e -> swapFood());
    }

    //Add food to the meal
    private void addFood(String food) {
        mealItems.add(food);
        refreshMealTextArea();
        mealFrame.getSwapButton().setEnabled(true);
    }

    // Swap a random food from the meal with a random food from food_name table in database
    public void swapFood() {
        if (mealItems.isEmpty()) return;

        int index = new Random().nextInt(mealItems.size());
        List<Food> allFood = foodService.getAllFood();
        int index2 = new Random().nextInt(allFood.size());
        mealItems.set(index, allFood.get(index2).getDescription());
        
        
        refreshMealTextArea();
    }
    
    public Food swapAndReturnNewFood(int id, String goal) throws SQLException {
    	int newId = foodService.getSwapCandidate(id, goal);
    	return foodService.getFoodById(newId);
    }

    
    private void refreshMealTextArea() {
        StringBuilder builder = new StringBuilder();
        for (String item : mealItems) {
            builder.append(item).append("\n");
        }
        mealFrame.getMealTextArea().setText(builder.toString());
    }

    
    public void loadFoodToDropdown() {
    	List<Food> allFood = foodService.getAllFood();
    	String[] foodList = allFood.stream()
    	        .map(food -> String.format("%s",
    	                                   food.getDescription()))
    	        .toArray(String[]::new);
    	    mealFrame.setFoodDropdownOptions(foodList);
    }
}
