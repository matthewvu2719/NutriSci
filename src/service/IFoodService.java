package service;

import java.sql.SQLException;
import java.util.List;

import model.Food;

public interface IFoodService {
	public List<Food> getAllFood();
	public int getSwapCandidate(int originalFoodId, String goal) throws SQLException;
	public Food getFoodById(int id);
}
