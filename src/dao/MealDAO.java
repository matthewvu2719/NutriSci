package dao;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbconnect.DBConnection;
import model.Food;
import model.Meal;




public class MealDAO {
	static final MealDAO mealDAO = new MealDAO();
	public static MealDAO getInstance() {
		return mealDAO;
	}
    private FoodDAO foodDAO = FoodDAO.getInstance();

    public int insertMeal(Meal meal) throws SQLException {
        int mealId = -1;

        String sql = "INSERT INTO meal (Protein, Fat, Carbs, Fibre, Sodium, Energy,Name) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String mealFoodSql = "INSERT INTO meal_food (MealID, FoodID) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, meal.getProtein());
            stmt.setDouble(2, meal.getFat());
            stmt.setDouble(3, meal.getCarbs());
            stmt.setDouble(4, meal.getFibre());
            stmt.setDouble(5, meal.getSodium());
            stmt.setDouble(6, meal.getEnergy());
            stmt.setString(7, meal.getName());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                mealId = rs.getInt(1);
            }

            try (PreparedStatement foodStmt = conn.prepareStatement(mealFoodSql)) {
                for (Food food : meal.getFood()) {
                    foodStmt.setInt(1, mealId);
                    foodStmt.setInt(2, food.getFoodId());
                    foodStmt.addBatch();
                }
                foodStmt.executeBatch();
            }
        }

        return mealId;
    }

    public int insertMealWithoutFood(Meal meal) throws SQLException {
        int mealId = -1;

        String sql = "INSERT INTO meal (Protein, Fat, Carbs, Fibre, Sodium, Energy,Name) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDouble(1, meal.getProtein());
            stmt.setDouble(2, meal.getFat());
            stmt.setDouble(3, meal.getCarbs());
            stmt.setDouble(4, meal.getFibre());
            stmt.setDouble(5, meal.getSodium());
            stmt.setDouble(6, meal.getEnergy());
            stmt.setString(7, meal.getName());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                mealId = rs.getInt(1);
            }
        }

        return mealId;
    }

    
    public Meal getMealById(int id) throws SQLException {
        Meal meal = new Meal();
        meal.setId(id);

        String sql = "SELECT * FROM meal WHERE Id = ?";
        String foodLinkSql = "SELECT FoodID FROM meal_food WHERE MealID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	meal.setId(id);
            	meal.setName(rs.getString("Name"));
                meal.setProtein(rs.getDouble("Protein"));
                meal.setFat(rs.getDouble("Fat"));
                meal.setCarbs(rs.getDouble("Carbs"));
                meal.setFibre(rs.getDouble("Fibre"));
                meal.setSodium(rs.getDouble("Sodium"));
                meal.setEnergy(rs.getDouble("Energy"));
            }

            List<Food> foods = new ArrayList<>();
            try (PreparedStatement foodStmt = conn.prepareStatement(foodLinkSql)) {
                foodStmt.setInt(1, id);
                ResultSet foodRs = foodStmt.executeQuery();
                while (foodRs.next()) {
                    int foodId = foodRs.getInt("FoodID");
                    Food food = foodDAO.getFoodById(foodId);
                    foods.add(food);
                }
            }

            meal.setFood(foods);
        }

        return meal;
    }

    public List<Meal> getAllMealsByLogId(int logId) throws SQLException {
        List<Meal> meals = new ArrayList<>();
        String sql = "SELECT MealID FROM log_meal WHERE LogID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, logId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int mealId = rs.getInt("MealID");
                Meal meal = getMealById(mealId);  // Reuse existing method
                meals.add(meal);
            }
        }

        return meals;
    }

    
    public boolean removeFoodFromMeal(int mealId, int foodId) throws SQLException {
        String sql = "DELETE FROM meal_food WHERE MealID = ? AND FoodID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mealId);
            stmt.setInt(2, foodId);

            return stmt.executeUpdate() > 0;
        }
    }

    

    public boolean updateMealById(int id, Meal updatedMeal) throws SQLException {
        String sql = "UPDATE meal SET Protein = ?, Fat = ?, Carbs = ?, Fibre = ?, Sodium = ?, Energy = ? WHERE Id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, updatedMeal.getProtein());
            stmt.setDouble(2, updatedMeal.getFat());
            stmt.setDouble(3, updatedMeal.getCarbs());
            stmt.setDouble(4, updatedMeal.getFibre());
            stmt.setDouble(5, updatedMeal.getSodium());
            stmt.setDouble(6, updatedMeal.getEnergy());
            stmt.setInt(7, id);

            return stmt.executeUpdate() > 0;
        }
    }
    
    public boolean deleteMealById(int mealId) throws SQLException {
        String deleteMealFoodSql = "DELETE FROM meal_food WHERE MealID = ?";
        String deleteLogMealSql = "DELETE FROM log_meal WHERE MealID = ?";
        String deleteMealSql = "DELETE FROM meal WHERE Id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (
                PreparedStatement deleteMealFoodStmt = conn.prepareStatement(deleteMealFoodSql);
                PreparedStatement deleteLogMealStmt = conn.prepareStatement(deleteLogMealSql);
                PreparedStatement deleteMealStmt = conn.prepareStatement(deleteMealSql)
            ) {
                // Delete from meal_food
                deleteMealFoodStmt.setInt(1, mealId);
                deleteMealFoodStmt.executeUpdate();

                // Delete from log_meal
                deleteLogMealStmt.setInt(1, mealId);
                deleteLogMealStmt.executeUpdate();

                // Delete from meal
                deleteMealStmt.setInt(1, mealId);
                int rowsAffected = deleteMealStmt.executeUpdate();

                conn.commit();
                return rowsAffected > 0;
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

}
