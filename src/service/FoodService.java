package service;

import java.sql.SQLException;
import java.util.List;

import dao.FoodDAO;
import model.Food;


public class FoodService implements IFoodService{
	private final FoodDAO foodDao = FoodDAO.getInstance();
	static final FoodService foodService = new FoodService();
	private FoodService() {}
	
	public static FoodService getInstance() {
		return foodService;
	}
	
	@Override
	public List<Food> getAllFood() {
	    return foodDao.getAllFood();
	}
	
	@Override
	public int getSwapCandidate(int originalFoodId, String goal) throws SQLException{
		return 0;
	}

	@Override
	public Food getFoodById(int id) {
		// TODO Auto-generated method stub
		return foodDao.getFoodById(id);
	}
	
	
}
