package com.restaurant.frontpage;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.restaurant.dao.FoodDaoImp;
import com.restaurant.pojo.Food;

@SuppressWarnings("serial")
public class FoodCRUD extends JFrame {
    private JPanel contentPane;
    private JTextField fname, price, fid;
    private JTextArea description;
    private JComboBox<String> category;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                FoodCRUD frame = new FoodCRUD();
                frame.setVisible(true);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FoodCRUD() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1263, 710);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(44, 62, 80));
        headerPanel.setBounds(0, 0, 1365, 75);
        contentPane.add(headerPanel);
        headerPanel.setLayout(null);

        JLabel titleLabel = new JLabel("RESTAURANT BILLING SYSTEM");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(10, 11, 1340, 53);
        headerPanel.add(titleLabel);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(236, 240, 241));
        mainPanel.setBounds(0, 86, 1365, 700);
        contentPane.add(mainPanel);
        mainPanel.setLayout(null);

        JLabel manageLabel = new JLabel("Manage Food Items");
        manageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        manageLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
        manageLabel.setBounds(450, 20, 500, 40);
        mainPanel.add(manageLabel);

        JLabel idLabel = new JLabel("Food ID:");
        idLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        idLabel.setBounds(220, 100, 120, 30);
        mainPanel.add(idLabel);

        fid = new JTextField();
        fid.setBounds(350, 100, 220, 30);
        mainPanel.add(fid);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        nameLabel.setBounds(220, 150, 120, 30);
        mainPanel.add(nameLabel);

        fname = new JTextField();
        fname.setBounds(350, 150, 220, 30);
        mainPanel.add(fname);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        categoryLabel.setBounds(600, 150, 120, 30);
        mainPanel.add(categoryLabel);

        category = new JComboBox<>(new String[]{"", "Snack", "Light Food", "Soft Drinks", "Desserts", "Meal", "Other"});
        category.setBounds(730, 150, 220, 30);
        mainPanel.add(category);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        priceLabel.setBounds(220, 200, 120, 30);
        mainPanel.add(priceLabel);

        price = new JTextField();
        price.setBounds(350, 200, 220, 30);
        mainPanel.add(price);

        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        descLabel.setBounds(600, 200, 120, 30);
        mainPanel.add(descLabel);

        description = new JTextArea();
        description.setBorder(new LineBorder(Color.GRAY));
        description.setBounds(730, 200, 220, 60);
        mainPanel.add(description);

        JButton addButton = createButton("ADD FOOD", 220, 300);
        addButton.addActionListener(e -> addFood());
        mainPanel.add(addButton);

        JButton updateButton = createButton("UPDATE FOOD", 400, 300);
        updateButton.addActionListener(e -> updateFood());
        mainPanel.add(updateButton);

        JButton deleteButton = createButton("DELETE FOOD", 580, 300);
        deleteButton.addActionListener(e -> deleteFood());
        mainPanel.add(deleteButton);

        JButton getButton = createButton("GET FOOD", 760, 300);
        getButton.addActionListener(e -> getFood());
        mainPanel.add(getButton);

        JButton backButton = createButton("BACK", 50, 20);
        backButton.addActionListener(e -> goBack());
        mainPanel.add(backButton);
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(41, 128, 185));
        button.setBounds(x, y, 160, 40);
        button.setBorder(new LineBorder(Color.BLACK, 1));
        return button;
    }

    private void addFood() {
        String name = fname.getText();
        String cat = (String) category.getSelectedItem();
        String prc = price.getText();
        String desc = description.getText();

        if (name.isEmpty() || cat.isEmpty() || prc.isEmpty() || desc.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required.");
            return;
        }

        Food food = new Food(name, cat, desc, Double.parseDouble(prc));
        boolean success = new FoodDaoImp().addFood(food);
        JOptionPane.showMessageDialog(null, success ? "Food Added Successfully" : "Failed to Add Food");
    }

    private void updateFood() {
        String id = fid.getText();
        String name = fname.getText();
        String cat = (String) category.getSelectedItem();
        String prc = price.getText();
        String desc = description.getText();

        if (id.isEmpty() || name.isEmpty() || cat.isEmpty() || prc.isEmpty() || desc.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields are required.");
            return;
        }

        Food food = new Food(name, cat, desc, Double.parseDouble(prc));
        boolean success = new FoodDaoImp().updateFood(food);
        JOptionPane.showMessageDialog(null, success ? "Food Updated Successfully" : "Failed to Update Food");
    }

    private void deleteFood() {
        String id = fid.getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter Food ID to delete.");
            return;
        }

        boolean success = new FoodDaoImp().deleteFoodById(Integer.parseInt(id));
        JOptionPane.showMessageDialog(null, success ? "Food Deleted Successfully" : "Failed to Delete Food");
    }

    private void getFood() {
        String id = fid.getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter Food ID to fetch details.");
            return;
        }

        Food food = new FoodDaoImp().getFoodById(Integer.parseInt(id));

        if (food != null) {
            fname.setText(food.getFoodName());
            category.setSelectedItem(food.getFoodCategory());
            price.setText(String.valueOf(food.getPrice()));
            description.setText(food.getFoodDescription());
        } else {
            JOptionPane.showMessageDialog(null, "Food not found.");
        }
    }

    private void goBack() {
        this.dispose();
        Home home = new Home();
        home.setVisible(true);
    }
}  