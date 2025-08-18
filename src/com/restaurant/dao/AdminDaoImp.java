package com.restaurant.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.restaurant.pojo.Admin;
import com.restaurant.utility.DBUtility;

public class AdminDaoImp implements AdminDao {
    
    private static final Connection con = DBUtility.connection();

    @Override
    public boolean addAdmin(Admin a) {
        String query = "INSERT INTO admin(adminName, username, phone, address, password) VALUES(?,?,?,?,?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, a.getAdminName());
            stmt.setString(2, a.getUsername());
            stmt.setString(3, a.getPhone());
            stmt.setString(4, a.getAddress());
            stmt.setString(5, a.getPassword());
            
            return stmt.executeUpdate() > 0; // Returns true if at least one row is affected
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Admin getAdmin(String username, String password) {
        String query = "SELECT * FROM admin WHERE username=? AND password=?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {  // If admin exists, create object
                    Admin a = new Admin(
                        rs.getString("adminName"),
                        rs.getString("phone"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("address")
                    );
                    a.setAdminId(rs.getInt("adminId"));
                    return a;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no admin is found
    }
}
