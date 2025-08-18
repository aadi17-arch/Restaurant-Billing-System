package com.restaurant.frontpage;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;

import com.restaurant.utility.DBUtility;
import net.proteanit.sql.DbUtils;

@SuppressWarnings("serial")
public class TotalOrders extends JFrame {
    private JPanel contentPane;
    private JTable table;
    private Connection con;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                TotalOrders frame = new TotalOrders();
                frame.setVisible(true);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TotalOrders() {
        setTitle("Total Orders - Restaurant Billing System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1263, 710);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(new Color(236, 240, 241)); // Light Gray Background
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Header Panel (Dark Blue with White Text)
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(44, 62, 80)); // Dark Blue
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("RESTAURANT BILLING SYSTEM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        contentPane.add(headerPanel, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
        mainPanel.setBackground(new Color(236, 240, 241)); // Light Gray
        contentPane.add(mainPanel, BorderLayout.CENTER);

        // Table Section
        JLabel ordersLabel = new JLabel("TOTAL ORDERS", SwingConstants.CENTER);
        ordersLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        ordersLabel.setForeground(Color.BLACK);
        mainPanel.add(ordersLabel, BorderLayout.NORTH);

        table = new JTable();
        table.setFont(new Font("SansSerif", Font.PLAIN, 16));
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);
        table.setGridColor(Color.BLACK); // Black Grid

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(128, 128, 128), 2)); // Gray Border
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Button Section
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(236, 240, 241));
        buttonPanel.setBorder(new EmptyBorder(10, 50, 20, 50));

        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(e -> navigateToHome());

        JButton logoutButton = createStyledButton("Logout");
        logoutButton.addActionListener(e -> handleLogout());

        JButton exitButton = createStyledButton("Exit");
        exitButton.addActionListener(e -> handleExit());

        buttonPanel.add(backButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Load Order Data
        loadOrderData();
    }

    private void loadOrderData() {
        try {
            con = DBUtility.connection();
            String query = "SELECT * FROM orders";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading orders: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void navigateToHome() {
        Home homeFrame = new Home();
        homeFrame.setVisible(true);
        homeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        dispose();
    }

    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(this, "Do you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            Login loginFrame = new Login();
            loginFrame.setVisible(true);
            loginFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            dispose();
            JOptionPane.showMessageDialog(this, "Successfully Logged Out.");
        }
    }

    private void handleExit() {
        int choice = JOptionPane.showConfirmDialog(this, "Do you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(41, 128, 185)); // Blue Button
        button.setBorder(new LineBorder(Color.BLACK, 2)); // Black Border
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(160, 45));
        return button;
    }
}
