package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.Food;
import model.Meal;
import model.User;
import service.FoodService;
import service.IFoodService;
import service.IMealService;
import service.LogService;
import service.ILogService;
import service.MealService;
import view.IMealView;
import view.MainFrame;






public class MealController {
    private final IFoodService foodService = FoodService.getInstance();
    private static final MealController mealController = new MealController();
    private final IMealService mealService = MealService.getInstance();
    private final ILogService logService = LogService.getInstance();
    private IMealView mealFrame;

    // Meal categories
    private final String[] meals = {"Breakfast", "Lunch", "Dinner", "Snack"};
    // List of meal items per meal category
    private final List<String>[] mealItems = new ArrayList[meals.length];

    private MealController() {
        for (int i = 0; i < meals.length; i++) {
            mealItems[i] = new ArrayList<>();
        }
    }

    public static MealController getInstance() {
        return mealController;
    }

    public void setMealFrame(IMealView frame) {
        this.mealFrame = frame;

        
        
        loadFoodToDropdowns();
        initEventListeners();
    }

    private void initEventListeners() {
        List<Meal> meals = logService.getLogById(mealFrame.getLog().getId()).getMeals();
        if(meals.size()==0) {
        	List<Meal> temp = new ArrayList<Meal>();
        	Meal breakfast = new Meal();
            breakfast.setName("Breakfast");
            mealFrame.setBreakfast(breakfast);
            Meal lunch = new Meal();
            lunch.setName("Lunch");
            mealFrame.setLunch(lunch);
            Meal dinner = new Meal();
            dinner.setName("Dinner");
            mealFrame.setDinner(dinner);
            Meal snack = new Meal();
            snack.setName("Snack");
            mealFrame.setSnack(snack);
            temp.add(breakfast);
            temp.add(lunch);
            temp.add(dinner);
            temp.add(snack);
            mealFrame.setMeals(temp);
            
            
        }
        else{
        	mealFrame.setMeals(meals);
            int index = 0;
            for (Meal meal : meals) {
                if ("Breakfast".equals(meal.getName())) {
                    mealFrame.setBreakfast(meal);
                    index = 0;
                } else if ("Lunch".equals(meal.getName())) {
                    mealFrame.setLunch(meal);
                    index = 1;
                } else if ("Dinner".equals(meal.getName())) {
                    mealFrame.setDinner(meal);
                    index = 2;
                } else if ("Snack".equals(meal.getName())) {
                    mealFrame.setSnack(meal);
                    index = 3;
                }
                for (Food food : meal.getFood()) {
            	    addFoodToTableAndChart(index, food);
            	}
            }
        }
        

        

    	
        JComboBox<Food>[] dropdowns = mealFrame.getMealDropdowns();
        JButton[] addButtons = mealFrame.getAddButtons();
        JButton[] swapButtons = mealFrame.getSwapButtons();
        JButton[] removeButtons = mealFrame.getRemoveButtons();
        List<Food> foodList = foodService.getAllFood();
        
        mealService.getAllMealsByLogId(0);
        for (int i = 0; i < dropdowns.length; i++) {
            final int mealIndex = i;
            JTextField editor = (JTextField) dropdowns[mealIndex].getEditor().getEditorComponent();
            editor.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    String input = editor.getText();

                    // Filter your list
                    List<Food> filtered = foodList.stream()
                        .filter(food -> food.getDescription().toLowerCase().contains(input.toLowerCase()))
                        .collect(Collectors.toList());

                    // Update dropdown model
                    DefaultComboBoxModel<Food> model = new DefaultComboBoxModel<>();
                    for (Food food : filtered) {
                        model.addElement(food);
                    }
                    dropdowns[mealIndex].setModel(model);
                    dropdowns[mealIndex].setSelectedItem(input);
                    editor.setText(input); // re-set text to avoid overwrite
                    dropdowns[mealIndex].showPopup();
                }
            });
            
            dropdowns[mealIndex].addActionListener(e -> {
                Object selected = dropdowns[mealIndex].getSelectedItem();
                if (selected instanceof Food) {
                    addButtons[mealIndex].setEnabled(true);
                } else {
                    addButtons[mealIndex].setEnabled(false);
                }
            });

            addButtons[mealIndex].addActionListener(e -> {
                Object selected = dropdowns[mealIndex].getSelectedItem();
                
                if (selected instanceof Food) {
                    Food selectedFood = (Food) selected;
                    addFoodToMeal(mealIndex, selectedFood);
                }
            });
            
            removeButtons[mealIndex].addActionListener(e -> {
                int selectedRow = mealFrame.getMealTables()[mealIndex].getSelectedRow();
                if (selectedRow >= 0) {
                    removeFoodFromMeal(mealIndex, selectedRow);
                }
            });
            
            
            
           


            
        }
        
        mealFrame.addBackButtonListener(e->{
        	User user = UserController.getInstance().getUserById(mealFrame.getLog().getUserId());
        	MainFrame mainFrame = new MainFrame(user);
        	MainFrameController.getInstance().setMainFrame(mainFrame);
        	mainFrame.setVisible(true);
        	mealFrame.exit();
        });
        
        mealFrame.addsaveButtonListener(e -> {
        	Meal breakfast = mealFrame.getBreakfast();
        	Meal lunch = mealFrame.getLunch();
        	Meal dinner = mealFrame.getDinner();
        	Meal snack = mealFrame.getSnack();
        	breakfast.reset();
        	lunch.reset();
        	dinner.reset();
        	snack.reset();
        	breakfast.calculateMeal();
        	lunch.calculateMeal();
        	dinner.calculateMeal();
        	snack.calculateMeal();

        	System.out.println(breakfast.getFibre());
        	System.out.println(lunch.getFibre());
        	System.out.println(dinner.getFibre());
        	System.out.println(snack.getFibre());
        	mealService.deleteMealById(breakfast.getId());
        	mealService.deleteMealById(lunch.getId());
        	mealService.deleteMealById(dinner.getId());
        	mealService.deleteMealById(snack.getId());
        	
        	int breakfastId = 0;
	        int lunchId = 0;
	        int dinnerId = 0;
	        int snackId =0;
        	if(breakfast.getFood()==null || breakfast.getFood().size()==0) {
        		breakfastId =mealService.addMealWithoutFood(breakfast);
        	}else {
        		breakfastId =mealService.addMeal(breakfast);
        	}
        	if(lunch.getFood()==null|| lunch.getFood().size()==0) {
        		lunchId =mealService.addMealWithoutFood(lunch);
        	}else {
        		lunchId = mealService.addMeal(lunch);
        	}
        	if(dinner.getFood()==null|| dinner.getFood().size()==0) {
            	dinnerId = mealService.addMealWithoutFood(dinner);
        	}else {
            	dinnerId = mealService.addMeal(dinner);
        	}
        	if(snack.getFood()==null|| snack.getFood().size()==0) {
            	snackId = mealService.addMealWithoutFood(snack);
        	}else {
            	snackId = mealService.addMeal(snack);
        	}

            breakfast.setId(breakfastId);
            lunch.setId(lunchId);
            dinner.setId(dinnerId);
            snack.setId(snackId);
        	
        	
        	mealFrame.setMeals(new ArrayList<>(Arrays.asList(breakfast, lunch, dinner, snack)));


            mealFrame.getLog().setMeals(mealFrame.getMeals());
            logService.updateLogById(mealFrame.getLog().getId(), mealFrame.getLog());
            JOptionPane.showMessageDialog(null, "Log saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void loadFoodToDropdowns() {
        List<Food> allFood = foodService.getAllFood();
        Food[] foodArray = allFood.toArray(new Food[0]);
        mealFrame.setFoodDropdownOptions(foodArray);
    }

    

    private void addFoodToMeal(int mealIndex, Food food) {

        addFoodToTableAndChart(mealIndex,food);
        Food newFood = foodService.getFoodById(food.getFoodId());
        switch(mealIndex) {
        case 0:
        	mealFrame.addFoodToBreakfast(newFood);
        	break;
        case 1:
        	mealFrame.addFoodToLunch(newFood);
        	break;
        case 2:
        	mealFrame.addFoodToDinner(newFood);
        	break;
        case 3:
        	mealFrame.addFoodToSnack(newFood);
        	break;
        }
        

        
        
    }
    
    private void addFoodToTableAndChart(int mealIndex, Food food) {
        mealItems[mealIndex].add(food.getDescription());
        //refreshMealTextArea(mealIndex);

        Food newFood = foodService.getFoodById(food.getFoodId());
        mealFrame.addFoodToTable(mealIndex,newFood);
        
        mealFrame.addDataSetToChart(newFood.getProtein(), newFood.getCarbs(),  newFood.getFibre(),newFood.getFat(),newFood.getSodium(),newFood.getCal(),100);
        mealFrame.addDataSetToPie(newFood.getFoodGroupId(),false);
        
        
    }
    
    
    public void removeFoodFromMeal(int mealIndex, int rowIndex) {
    	
        // Remove from list of food descriptions (for logging or internal tracking)
        if (rowIndex >= 0 && rowIndex < mealItems[mealIndex].size()) {
            mealItems[mealIndex].remove(rowIndex);
        }
        
//        mealFrame.getMeals().get(mealIndex).getFood().remove(rowIndex);

        // Remove from table and get the food object
        Food removedFood = mealFrame.removeFoodFromTable(mealIndex, rowIndex);
        if (removedFood == null) return;

        // Subtract values from chart
        mealFrame.addDataSetToChart(
            -removedFood.getProtein(),
            -removedFood.getCarbs(),
            -removedFood.getFibre(),
            -removedFood.getFat(),
            -removedFood.getSodium(),
            -removedFood.getCal(),
            -100
        );

        // Subtract from pie chart
        mealFrame.addDataSetToPie(removedFood.getFoodGroupId(), true); // Use `true` to subtract if you support it
    }




}
