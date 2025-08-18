package com.restaurant.frontpage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import com.restaurant.utility.DBUtility;
import net.proteanit.sql.DbUtils;

@SuppressWarnings("serial")
public class Home extends JFrame {
    private JPanel contentPane;
    private JTextField searchName, searchPrice;
    private JTable table;
    private JComboBox<String> cate;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Home frame = new Home();
                frame.setVisible(true);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Home() {
        setTitle("Restaurant Billing System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1263, 710);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setLayout(new BorderLayout(10, 10));
        contentPane.setBackground(new Color(236, 240, 241)); // Light Gray Background
        setContentPane(contentPane);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(44, 62, 80)); // Dark Blue Header
        headerPanel.setBorder(new MatteBorder(0, 0, 3, 0, Color.BLACK));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel titleLabel = new JLabel("RESTAURANT BILLING SYSTEM");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        contentPane.add(headerPanel, BorderLayout.NORTH);

        // Sidebar Panel
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new GridLayout(7, 1, 10, 10));
        sidebarPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        sidebarPanel.setBackground(new Color(236, 240, 241)); // Light Gray Sidebar

        String[] buttonNames = { "ADD FOOD", "UPDATE FOOD", "DELETE FOOD", "ORDER FOOD", "TOTAL ORDERS", "LOGOUT", "EXIT" };
        for (String name : buttonNames) {
            JButton button = createStyledButton(name);
            sidebarPanel.add(button);
            button.addActionListener(new ButtonActionListener(name));
        }
        contentPane.add(sidebarPanel, BorderLayout.WEST);

        // Main Content Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(236, 240, 241)); // Light Gray Background
        contentPane.add(mainPanel, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(new Color(236, 240, 241)); // Light Gray

        searchName = new JTextField(15);
        searchPrice = new JTextField(10);
        cate = new JComboBox<>();
        loadCategories();

        JButton searchByNameBtn = createStyledButton("Search By Name");
        JButton searchByCategoryBtn = createStyledButton("Search By Category");
        JButton searchByPriceBtn = createStyledButton("Search By Price");

        searchPanel.add(createStyledLabel("Name:"));
        searchPanel.add(searchName);
        searchPanel.add(searchByNameBtn);
        searchPanel.add(createStyledLabel("Category:"));
        searchPanel.add(cate);
        searchPanel.add(searchByCategoryBtn);
        searchPanel.add(createStyledLabel("Max Price:"));
        searchPanel.add(searchPrice);
        searchPanel.add(searchByPriceBtn);

        searchByNameBtn.addActionListener(e -> searchByName());
        searchByCategoryBtn.addActionListener(e -> searchByCategory());
        searchByPriceBtn.addActionListener(e -> searchByPrice());

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // Table Panel
        table = new JTable();
        loadTableData();
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBorder(new LineBorder(new Color(128, 128, 128))); // Gray Border
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 14));
        button.setBackground(new Color(41, 128, 185)); // Blue Background
        button.setForeground(Color.WHITE); // White Text
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Black Border
        return button;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Tahoma", Font.BOLD, 12));
        label.setForeground(Color.BLACK); // Black Text Labels
        return label;
    }

    private void loadCategories() {
        Connection con = DBUtility.connection();
        String query = "SELECT DISTINCT FoodCategory FROM food";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            cate.addItem("");
            while (rs.next()) {
                cate.addItem(rs.getString("FoodCategory"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTableData() {
        Connection con = DBUtility.connection();
        String query = "SELECT * FROM food";
        try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchByName() {
        executeSearch("SELECT * FROM food WHERE FoodName LIKE ?", searchName.getText());
    }

    private void searchByCategory() {
        executeSearch("SELECT * FROM food WHERE FoodCategory = ?", (String) cate.getSelectedItem());
    }

    private void searchByPrice() {
        try {
            Double price = Double.parseDouble(searchPrice.getText());
            executeSearch("SELECT * FROM food WHERE Price <= ?", price.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Enter a valid price.");
        }
    }

    private void executeSearch(String query, String param) {
        if (param == null || param.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a value.");
            return;
        }
        Connection con = DBUtility.connection();
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, param);
            ResultSet rs = stmt.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class ButtonActionListener implements ActionListener {
        private final String action;
        public ButtonActionListener(String action) {
            this.action = action;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (action) {
                case "ADD FOOD": case "UPDATE FOOD": case "DELETE FOOD":
                    new FoodCRUD().setVisible(true);
                    dispose();
                    break;
                case "ORDER FOOD":
                    new Bill().setVisible(true);
                    dispose();
                    break;
                case "TOTAL ORDERS":
                    new TotalOrders().setVisible(true);
                    dispose();
                    break;
                case "LOGOUT":
                    if (JOptionPane.showConfirmDialog(null, "Logout?", "Confirm", JOptionPane.YES_NO_OPTION) == 0) {
                        new Login().setVisible(true);
                        dispose();
                    }
                    break;
                case "EXIT":
                    System.exit(0);
                    break;
            }
        }
    }
}
