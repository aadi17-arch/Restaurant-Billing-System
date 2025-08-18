package com.restaurant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.restaurant.pojo.Food;
import com.restaurant.utility.DBUtility;

public class FoodDaoImp implements FoodDao {
    private PreparedStatement stmt;
    private ResultSet rs;
    private int row = 0;
    // private Food f;

    static Connection con = DBUtility.connection();

    @Override
    public boolean addFood(Food f) {
        String str = "INSERT INTO food(FoodName, FoodCategory, FoodDescription, Price) VALUES (?, ?, ?, ?)";
        try {
            stmt = con.prepareStatement(str);
            stmt.setString(1, f.getFoodName());
            stmt.setString(2, f.getFoodCategory());
            stmt.setString(3, f.getFoodDescription());
            stmt.setDouble(4, f.getPrice());

            row = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return row > 0;
    }

    @Override
    public boolean updateFood(Food f) {
        String str = "UPDATE food SET FoodName=?, FoodCategory=?, FoodDescription=?, Price=? WHERE FoodId=?";
        try {
            stmt = con.prepareStatement(str);
            stmt.setString(1, f.getFoodName());
            stmt.setString(2, f.getFoodCategory());
            stmt.setString(3, f.getFoodDescription());
            stmt.setDouble(4, f.getPrice());
            stmt.setInt(5, f.getFoodId());

            row = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return row > 0;
    }

    @Override
    public boolean deleteFoodById(int foodId) {
        String str = "DELETE FROM food WHERE FoodId=?";
        try {
            stmt = con.prepareStatement(str);
            stmt.setInt(1, foodId);
            row = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return row > 0;
    }

    @Override
    public Food getFoodById(int foodId) {
        String str = "SELECT * FROM food WHERE FoodId=?";
        try {
            stmt = con.prepareStatement(str);
            stmt.setInt(1, foodId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new Food(
                    rs.getString("FoodName"),
                    rs.getString("FoodCategory"),
                    rs.getString("FoodDescription"),
                    rs.getDouble("Price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Food> getAllFood() {
        List<Food> foodList = new ArrayList<>();
        String str = "SELECT * FROM food";
        try {
            stmt = con.prepareStatement(str);
            rs = stmt.executeQuery();
            while (rs.next()) {
                foodList.add(new Food(
                    rs.getString("FoodName"),
                    rs.getString("FoodCategory"),
                    rs.getString("FoodDescription"),
                    rs.getDouble("Price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodList;
    }

    @Override
    public List<Food> getFoodByCategory(String foodCategory) {
        List<Food> foodList = new ArrayList<>();
        String str = "SELECT * FROM food WHERE FoodCategory=?";
        try {
            stmt = con.prepareStatement(str);
            stmt.setString(1, foodCategory);
            rs = stmt.executeQuery();
            while (rs.next()) {
                foodList.add(new Food(
                    rs.getString("FoodName"),
                    rs.getString("FoodCategory"),
                    rs.getString("FoodDescription"),
                    rs.getDouble("Price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodList;
    }

    @Override
    public String getFoodNameById(int foodId) {
        String str = "SELECT FoodName FROM food WHERE FoodId=?";
        try {
            stmt = con.prepareStatement(str);
            stmt.setInt(1, foodId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("FoodName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // New Methods

    @Override
    public Food getMostExpensiveFood() {
        String str = "SELECT * FROM food ORDER BY Price DESC LIMIT 1";
        try {
            stmt = con.prepareStatement(str);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new Food(
                    rs.getString("FoodName"),
                    rs.getString("FoodCategory"),
                    rs.getString("FoodDescription"),
                    rs.getDouble("Price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Food getCheapestFood() {
        String str = "SELECT * FROM food ORDER BY Price ASC LIMIT 1";
        try {
            stmt = con.prepareStatement(str);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return new Food(
                    rs.getString("FoodName"),
                    rs.getString("FoodCategory"),
                    rs.getString("FoodDescription"),
                    rs.getDouble("Price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getTotalFoodItems() {
        String str = "SELECT COUNT(*) AS total FROM food";
        try {
            stmt = con.prepareStatement(str);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Food> getFoodsInPriceRange(double minPrice, double maxPrice) {
        List<Food> foodList = new ArrayList<>();
        String str = "SELECT * FROM food WHERE Price BETWEEN ? AND ?";
        try {
            stmt = con.prepareStatement(str);
            stmt.setDouble(1, minPrice);
            stmt.setDouble(2, maxPrice);
            rs = stmt.executeQuery();
            while (rs.next()) {
                foodList.add(new Food(
                    rs.getString("FoodName"),
                    rs.getString("FoodCategory"),
                    rs.getString("FoodDescription"),
                    rs.getDouble("Price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodList;
    }

    @Override
    public List<Food> getFoodByName(String foodName) {
        List<Food> foodList = new ArrayList<>();
        String str = "SELECT * FROM food WHERE FoodName LIKE ?";
        try {
            stmt = con.prepareStatement(str);
            stmt.setString(1, "%" + foodName + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                foodList.add(new Food(
                    rs.getString("FoodName"),
                    rs.getString("FoodCategory"),
                    rs.getString("FoodDescription"),
                    rs.getDouble("Price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foodList;
    }
}
