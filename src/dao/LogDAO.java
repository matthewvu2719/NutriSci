package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dbconnect.DBConnection;
import model.Log;
import model.Meal;


public class LogDAO {
	static final LogDAO logDAO = new LogDAO();
	public static LogDAO getInstance() {
		return logDAO;
	}
    private MealDAO mealDAO = MealDAO.getInstance();

    public int insertLog(Log log) throws SQLException {
        int logId = -1;

        String sql = "INSERT INTO log (UserId, Date) VALUES (?, ?)";
        String logMealSql = "INSERT INTO log_meal (LogID, MealID) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, log.getUserId());
            stmt.setString(2, log.getDate());

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                logId = rs.getInt(1);
            }

            if(log.getMeals()!=null) {
	            try (PreparedStatement logMealStmt = conn.prepareStatement(logMealSql)) {
	                for (Meal meal : log.getMeals()) {
	                    logMealStmt.setInt(1, logId);
	                    logMealStmt.setInt(2, meal.getId());
	                    logMealStmt.addBatch();
	                }
	                logMealStmt.executeBatch();
	            }
            }
        }

        return logId;
    }

    public Log getLogById(int id) throws SQLException {
        Log log = new Log();
        log.setId(id);

        String sql = "SELECT * FROM log WHERE Id = ?";
        String mealLinkSql = "SELECT MealID FROM log_meal WHERE LogID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                log.setUserId(rs.getInt("UserId"));
                log.setDate(rs.getString("Date"));
            }

            List<Meal> meals = new ArrayList<>();
            try (PreparedStatement mealStmt = conn.prepareStatement(mealLinkSql)) {
                mealStmt.setInt(1, id);
                ResultSet mealRs = mealStmt.executeQuery();
                while (mealRs.next()) {
                    int mealId = mealRs.getInt("MealID");
                    Meal meal = mealDAO.getMealById(mealId);
                    meals.add(meal);
                }
            }

            log.setMeals(meals);
        }

        return log;
    }

//    public List<Log> getAllLogs() throws SQLException {
//        List<Log> logs = new ArrayList<>();
//        String sql = "SELECT Id FROM log";
//
//        try (Connection conn = DBConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql);
//             ResultSet rs = stmt.executeQuery()) {
//
//            while (rs.next()) {
//                int logId = rs.getInt("Id");
//                Log log = getLogById(logId);
//                logs.add(log);
//            }
//        }
//
//        return logs;
//    }
    
    
    public List<Log> getLogByUserId(int userId) throws SQLException {
        List<Log> logs = new ArrayList<>();

        String logSql = "SELECT * FROM log WHERE UserId = ?";
        String mealLinkSql = "SELECT MealID FROM log_meal WHERE LogID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement logStmt = conn.prepareStatement(logSql)) {

            logStmt.setInt(1, userId);
            ResultSet rs = logStmt.executeQuery();

            while (rs.next()) {
                Log log = new Log();
                int logId = rs.getInt("id");

                log.setId(logId);
                log.setUserId(rs.getInt("UserId"));
                log.setDate(rs.getString("Date"));

                // Fetch associated meals for this log
                List<Meal> meals = new ArrayList<>();
                try (PreparedStatement mealStmt = conn.prepareStatement(mealLinkSql)) {
                    mealStmt.setInt(1, logId);
                    ResultSet mealRs = mealStmt.executeQuery();

                    while (mealRs.next()) {
                        int mealId = mealRs.getInt("MealID");
                        Meal meal = mealDAO.getMealById(mealId); // Assume mealDAO is available
                        if (meal != null) {
                            meals.add(meal);
                        }
                    }
                }

                log.setMeals(meals);
                logs.add(log);
            }
        }

        return logs;
    }
    
    public void updateLogById(int id, Log log) throws SQLException {
        String updateLogSql = "UPDATE log SET UserId = ?, Date = ? WHERE Id = ?";
        String deleteLogMealsSql = "DELETE FROM log_meal WHERE LogID = ?";
        String insertLogMealSql = "INSERT INTO log_meal (LogID, MealID) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Transaction begin

            // 1. Update log table
            try (PreparedStatement updateLogStmt = conn.prepareStatement(updateLogSql)) {
                updateLogStmt.setInt(1, log.getUserId());
                updateLogStmt.setString(2, log.getDate());
                updateLogStmt.setInt(3, id);
                updateLogStmt.executeUpdate();
            }

            // 2. Delete old meal links
            try (PreparedStatement deleteLogMealStmt = conn.prepareStatement(deleteLogMealsSql)) {
                deleteLogMealStmt.setInt(1, id);
                deleteLogMealStmt.executeUpdate();
            }

            // 3. Insert new meal links
            if (log.getMeals() != null) {
                try (PreparedStatement insertLogMealStmt = conn.prepareStatement(insertLogMealSql)) {
                    for (Meal meal : log.getMeals()) {
                        insertLogMealStmt.setInt(1, id);
                        insertLogMealStmt.setInt(2, meal.getId());
                        insertLogMealStmt.addBatch();
                    }
                    insertLogMealStmt.executeBatch();
                }
            }

            conn.commit(); // Transaction end
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    


}
