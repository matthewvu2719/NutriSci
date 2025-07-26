package service;

import java.sql.SQLException;
import java.util.List;

import dao.MealDAO;
import model.Meal;

public class MealService implements IMealService{
	
	private final MealDAO mealDao = MealDAO.getInstance();
	static final MealService mealService = new MealService();
	
	public static MealService getInstance() {
		return mealService;
	}
	
	@Override
	public List<Meal> getAllMealsByLogId(int id) {
		try {
			return mealDao.getAllMealsByLogId(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Meal getMealById(int id) {
		try {
			return mealDao.getMealById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int addMeal(Meal meal) {
		// TODO Auto-generated method stub
		try {
			return mealDao.insertMeal(meal);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public int addMealWithoutFood(Meal meal) {
		// TODO Auto-generated method stub
		try {
			return mealDao.insertMealWithoutFood(meal);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	public void updateMealById(int id, Meal meal) {
		// TODO Auto-generated method stub
		try {
			mealDao.updateMealById(id, meal);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void deleteMealById(int id) {
		// TODO Auto-generated method stub
		try {
			if(id>0) {
				mealDao.deleteMealById(id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public int addOrUpdateMeal(Meal meal) {
	    try {
	        if (meal.getId() > 0) {
	            mealDao.updateMealById(meal.getId(), meal);
	            return meal.getId();
	        } else {
	            return mealDao.insertMeal(meal);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return 0;
	    }
	}
	
	
	

	@Override
	public void removeFoodFromMeal(int mealId, int foodId) {
		try {
			mealDao.removeFoodFromMeal(mealId, foodId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
