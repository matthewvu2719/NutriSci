package service;


import java.util.List;

import model.Meal;

public interface IMealService {
	public List<Meal> getAllMealsByLogId(int id);
	public Meal getMealById(int id);
	public int addOrUpdateMeal(Meal meal);
	public int addMeal(Meal meal);
	public void updateMealById(int id,Meal meal);
	public void removeFoodFromMeal(int mealId, int foodId);
	public int addMealWithoutFood(Meal meal);
	public void deleteMealById(int id);
}
