package com.restaurant.dao;

import java.util.List;
import com.restaurant.pojo.Food;

public interface FoodDao {
    boolean addFood(Food f);
    boolean updateFood(Food f);
    boolean deleteFoodById(int foodId);
    Food getFoodById(int foodId);
    List<Food> getAllFood();
    List<Food> getFoodByCategory(String foodCategory);
    String getFoodNameById(int foodId);

    // New methods
    Food getMostExpensiveFood();
    Food getCheapestFood();
    int getTotalFoodItems();
    List<Food> getFoodsInPriceRange(double minPrice, double maxPrice);
    List<Food> getFoodByName(String foodName);
}
