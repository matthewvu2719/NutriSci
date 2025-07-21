package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dbconnect.DBConnection;
import model.User;

public class UserDAO {
	static final UserDAO userDAO = new UserDAO();
	public static UserDAO getInstance() {
		return userDAO;
	}
	
	public void insert(User user) {
		String sql = "INSERT INTO user (name, sex, age, height, weight) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSex().name());
            stmt.setInt(3, user.getAge());
            stmt.setDouble(4, user.getHeight());
            stmt.setDouble(5, user.getWeight());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // ideally, use a logger
        }
    
	}
	
	public User getUserById(int id) {
        String sql = "SELECT * FROM user WHERE userId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("userId"));
                    user.setName(rs.getString("name"));
                    user.setSex(User.Sex.valueOf(rs.getString("sex")));
                    user.setAge(rs.getInt("age"));
                    user.setHeight(rs.getDouble("height"));
                    user.setWeight(rs.getDouble("weight"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public List<User> getAllUsers() {
	    List<User> users = new ArrayList<>();
	    String sql = "SELECT * FROM user";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            User user = new User();
	            user.setId(rs.getInt("userId"));
	            user.setName(rs.getString("name"));
	            user.setSex(User.Sex.valueOf(rs.getString("sex")));
	            user.setAge(rs.getInt("age"));
	            user.setHeight(rs.getDouble("height"));
	            user.setWeight(rs.getDouble("weight"));
	            users.add(user);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Use logger ideally
	    }

	    return users;
	}

    public void deleteUserById(int id) {
        String sql = "DELETE FROM user WHERE userId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserById(User user, int id) {
        String sql = "UPDATE user SET name = ?, sex = ?, age = ?, height = ?, weight = ? WHERE userId = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getSex().name());
            stmt.setInt(3, user.getAge());
            stmt.setDouble(4, user.getHeight());
            stmt.setDouble(5, user.getWeight());
            stmt.setInt(6, id);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
}
