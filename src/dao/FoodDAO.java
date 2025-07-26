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
	            food.setFoodId(rs.getInt("FoodID"));
	            food.setDescription(rs.getString("FoodDescription"));
	            foodList.add(food);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Use logger ideally
	    }

	    return foodList;
	}
	
	public Food getFoodById(int id) {
		String sql = "SELECT c.FoodGroupID, c.FoodDescription, a.NutrientName, b.NutrientValue " +
                "FROM nutrient_amount b " +
                "JOIN nutrient_name a ON a.NutrientID = b.NutrientID " +
                "JOIN food_name c ON c.FoodID = b.FoodID " +
                "WHERE c.FoodID = ? " +
                "AND a.NutrientName IN ('PROTEIN','FAT (TOTAL LIPIDS)','FIBRE, TOTAL DIETARY'," +
                "'CARBOHYDRATE, TOTAL (BY DIFFERENCE)','SODIUM','ENERGY (KILOJOULES)')";

		Food food = new Food();
		try (Connection conn = DBConnection.getConnection();
		         PreparedStatement ps = conn.prepareStatement(sql)) {
		        ps.setInt(1, id);
		        ResultSet rs = ps.executeQuery();
		        while (rs.next()) {
		            // Set description only once
		            if (food.getDescription() == null) {
		                food.setDescription(rs.getString("FoodDescription"));
		            }
		            food.setFoodId(id);
		            food.setFoodGroupId(rs.getInt("FoodGroupID"));
		            // Set each nutrient value based on the nutrient name
		            String nutrientName = rs.getString("NutrientName");
		            double nutrientValue = rs.getDouble("NutrientValue");
		            
		            switch (nutrientName) {
		                case "PROTEIN":
		                    food.setProtein(nutrientValue);
		                    break;
		                case "FAT (TOTAL LIPIDS)":
		                    food.setFat(nutrientValue);
		                    break;
		                case "FIBRE, TOTAL DIETARY":
		                    food.setFibre(nutrientValue);
		                    break;
		                case "CARBOHYDRATE, TOTAL (BY DIFFERENCE)":
		                    food.setCarbs(nutrientValue);
		                    break;
		                case "SODIUM":
		                    food.setSodium(nutrientValue);
		                    break;
		                case "ENERGY (KILOJOULES)":
		                    food.setCal(nutrientValue);
		                    break;
		            }
		        }
		 }catch (SQLException e) {
		        e.printStackTrace(); // Use logger ideally
		 }
		return food;
	}
	 	
	
//	public int getSwapCandidate(int originalFoodId, String goal) throws SQLException {
//	  
//		// Get food group of original food
//    
//		int groupId = getFoodGroupId(originalFoodId);
//
//    // Decide nutrient & sorting direction
//    String nutrient = goal.equalsIgnoreCase("lower calories")
//            ? "ENERGY (KILOCALORIES)"
//            : "FIBRE, TOTAL DIETARY";
//    String order = goal.equalsIgnoreCase("lower calories") ? "ASC" : "DESC";
//
//    // Query for best alternative i
//    String sql = """
//        SELECT f.foodid
//        FROM food_name f
//        JOIN nutrient_amount na ON f.foodid = na.foodid
//        JOIN nutrient_name nn ON na.nutrientnameid = nn.nutrientnameid
//        WHERE f.foodgroupid = ?
//          AND nn.nutrientname = ?
//          AND na.nutrientvalue > 0
//          AND f.foodid <> ?
//        ORDER BY na.nutrientvalue """ + order + " LIMIT 1";
//
//    try (Connection conn = DBConnection.getConnection();
//         PreparedStatement ps = conn.prepareStatement(sql)) {
//        ps.setInt(1, groupId);
//        ps.setString(2, nutrient);
//        ps.setInt(3, originalFoodId);
//        ResultSet rs = ps.executeQuery();
//        if (rs.next()) {
//            return rs.getInt("foodid");
//        }
//    }
//    return -1; // No suitable candidate
//}

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
