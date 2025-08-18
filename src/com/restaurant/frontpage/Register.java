package com.restaurant.frontpage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import com.restaurant.dao.AdminDaoImp;
import com.restaurant.pojo.Admin;

@SuppressWarnings("serial")
public class Register extends JFrame {
    private JPanel contentPane;
    private JTextField aName, aUName, aPhone;
    private JPasswordField aPass, aCpass;
    private JTextArea aAddress;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Register frame = new Register();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Register() {
        setTitle("Admin Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 150, 500, 550);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(236, 240, 241)); // Light Gray Background
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(44, 62, 80)); // Dark Blue Header
        headerPanel.setBounds(0, 0, 500, 50);
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        contentPane.add(headerPanel);

        JLabel title = new JLabel("Register as Admin");
        title.setFont(new Font("Tahoma", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        headerPanel.add(title);

        // Form Fields
        createStyledLabel("Full Name:", 50, 70);
        aName = createStyledTextField(170, 70);

        createStyledLabel("Username:", 50, 110);
        aUName = createStyledTextField(170, 110);

        createStyledLabel("Phone No:", 50, 150);
        aPhone = createStyledTextField(170, 150);

        createStyledLabel("Address:", 50, 190);
        aAddress = new JTextArea();
        aAddress.setBorder(new LineBorder(new Color(128, 128, 128))); // Gray Border
        aAddress.setBounds(170, 190, 250, 60);
        contentPane.add(aAddress);

        createStyledLabel("Password:", 50, 270);
        aPass = new JPasswordField();
        aPass.setBounds(170, 270, 250, 25);
        aPass.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Black Border
        contentPane.add(aPass);

        createStyledLabel("Confirm Password:", 50, 310);
        aCpass = new JPasswordField();
        aCpass.setBounds(170, 310, 250, 25);
        aCpass.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Black Border
        contentPane.add(aCpass);

        // Register Button
        JButton btnRegister = new JButton("Register");
        btnRegister.setBounds(170, 360, 250, 35);
        btnRegister.setBackground(new Color(41, 128, 185)); // Blue Background
        btnRegister.setForeground(Color.WHITE); // White Text
        btnRegister.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnRegister.setFocusPainted(false);
        btnRegister.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Black Border
        btnRegister.addActionListener(e -> registerAdmin());
        contentPane.add(btnRegister);

        // Login Redirect Label
        JLabel lblLogin = new JLabel("Already a user? Click here to Login");
        lblLogin.setBounds(150, 410, 300, 25);
        lblLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblLogin.setForeground(Color.BLUE);
        lblLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Login().setVisible(true);
                dispose();
                
            }
        });
        contentPane.add(lblLogin);
    }

    private void createStyledLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 120, 25);
        label.setFont(new Font("Tahoma", Font.BOLD, 12));
        label.setForeground(Color.BLACK); // Black Text Labels
        contentPane.add(label);
    }

    private JTextField createStyledTextField(int x, int y) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, 250, 25);
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Black Border
        contentPane.add(textField);
        return textField;
    }

    private void registerAdmin() {
        String name = aName.getText().trim();
        String uname = aUName.getText().trim();
        String phone = aPhone.getText().trim();
        String address = aAddress.getText().trim();
        String password = new String(aPass.getPassword()).trim();
        String cpassword = new String(aCpass.getPassword()).trim();

        if (name.isEmpty() || uname.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty() || cpassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(cpassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Admin admin = new Admin(name, phone, uname, password, address);
        boolean flag = new AdminDaoImp().addAdmin(admin);

        if (flag) {
            JOptionPane.showMessageDialog(this, "Registered Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            new Login().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
