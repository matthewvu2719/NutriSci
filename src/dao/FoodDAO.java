package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbconnect.DBConnection;
import model.Food;


public class FoodDAO {
	private static final FoodDAO foodDao = new FoodDAO();
	public static FoodDAO getInstance() {
		return foodDao;
	}
	
	public List<Food> getAllFood() {
	    List<Food> foodList = new ArrayList<>();
	    String sql = "SELECT * FROM food_name";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            Food food = new Food();
	            food.setDescription(rs.getString("FoodDescription"));
	            foodList.add(food);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Use logger ideally
	    }

	    return foodList;
	}
	
	public Food getFoodById(int id) {
		String sql = "SELECT * FROM food_name WHERE FoodId = ?";
		Food food = new Food();
		try (Connection conn = DBConnection.getConnection();
		         PreparedStatement ps = conn.prepareStatement(sql)) {
		        ps.setInt(1, id);
		        ResultSet rs = ps.executeQuery();
		        if (rs.next()) {
		            food.setDescription(rs.getString("FoodDescription"));
		        	return food;
		        }
		 }catch (SQLException e) {
		        e.printStackTrace(); // Use logger ideally
		 }
		return food;
	}
	 	
	
	public int getSwapCandidate(int originalFoodId, String goal) throws SQLException {
	  
		// Get food group of original food
    
		int groupId = getFoodGroupId(originalFoodId);

    // Decide nutrient & sorting direction
    String nutrient = goal.equalsIgnoreCase("lower calories")
            ? "ENERGY (KILOCALORIES)"
            : "FIBRE, TOTAL DIETARY";
    String order = goal.equalsIgnoreCase("lower calories") ? "ASC" : "DESC";

    // Query for best alternative i
    String sql = """
        SELECT f.foodid
        FROM food_name f
        JOIN nutrient_amount na ON f.foodid = na.foodid
        JOIN nutrient_name nn ON na.nutrientnameid = nn.nutrientnameid
        WHERE f.foodgroupid = ?
          AND nn.nutrientname = ?
          AND na.nutrientvalue > 0
          AND f.foodid <> ?
        ORDER BY na.nutrientvalue """ + order + " LIMIT 1";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, groupId);
        ps.setString(2, nutrient);
        ps.setInt(3, originalFoodId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("foodid");
        }
    }
    return -1; // No suitable candidate
}

	public int getFoodGroupId(int originalFoodId) {
		
		String sql = "SELECT FoodGroupId FROM food_name WHERE FoodId = ?";
		try (Connection conn = DBConnection.getConnection();
		         PreparedStatement ps = conn.prepareStatement(sql)) {
		        ps.setInt(1, originalFoodId);
		        ResultSet rs = ps.executeQuery();
		        	if (rs.next()) {
		        		return rs.getInt("foodid");
		        	}
		        	
		
		
		}catch (SQLException e) {
	        e.printStackTrace(); // Use logger ideally
	    }
		return -1;
	}

}
