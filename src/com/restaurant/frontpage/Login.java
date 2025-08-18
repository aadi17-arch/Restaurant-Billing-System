package com.restaurant.frontpage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import com.restaurant.dao.AdminDaoImp;
import com.restaurant.pojo.Admin;

@SuppressWarnings("serial")
public class Login extends JFrame {
    private JPanel contentPane;
    private JTextField uname;
    private JPasswordField pass;
    private Image backgroundImage; // Background image

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Login frame = new Login();
                frame.setVisible(true);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Login() {
        setTitle("Admin Login - Restaurant Billing System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1263, 710);

        // Load the background image
        backgroundImage = new ImageIcon("path/to/your/background/image.jpg").getImage();

        // Custom JPanel to draw the background image
        contentPane = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        setContentPane(contentPane);

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));
        headerPanel.setBackground(new Color(44, 62, 80));
        headerPanel.setPreferredSize(new Dimension(1365, 80));
        contentPane.add(headerPanel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("RESTAURANT BILLING SYSTEM", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        // Center Panel (Login Form)
        JPanel centerPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Make the center panel transparent
                g.setColor(new Color(255, 255, 255, 150)); // Semi-transparent white
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        centerPanel.setOpaque(false); // Make the panel transparent
        contentPane.add(centerPanel, BorderLayout.CENTER);

        JPanel loginPanel = new JPanel();
        loginPanel.setBorder(new LineBorder(Color.BLACK, 2));
        loginPanel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent white
        loginPanel.setPreferredSize(new Dimension(400, 300));
        loginPanel.setLayout(null);
        centerPanel.add(loginPanel);

        JLabel lblLogin = new JLabel("Admin Login");
        lblLogin.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
        lblLogin.setBounds(100, 20, 200, 30);
        loginPanel.add(lblLogin);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblUsername.setBounds(50, 80, 100, 25);
        loginPanel.add(lblUsername);

        uname = new JTextField();
        uname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        uname.setBounds(160, 80, 180, 25);
        loginPanel.add(uname);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblPassword.setBounds(50, 130, 100, 25);
        loginPanel.add(lblPassword);

        pass = new JPasswordField();
        pass.setFont(new Font("Tahoma", Font.PLAIN, 16));
        pass.setBounds(160, 130, 180, 25);
        loginPanel.add(pass);

        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 18));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(new Color(41, 128, 185));
        btnLogin.setBounds(130, 190, 140, 40);
        loginPanel.add(btnLogin);

        JLabel lblRegister = new JLabel("New User? Click here to Register", SwingConstants.CENTER);
        lblRegister.setForeground(Color.BLUE);
        lblRegister.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblRegister.setBounds(80, 250, 250, 20);
        loginPanel.add(lblRegister);
        lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Event Listeners
        btnLogin.addActionListener(e -> handleLogin());
        lblRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Register().setVisible(true);
                dispose();
            }
        });
    }

    private void handleLogin() {
        String username = uname.getText().trim();
        String password = new String(pass.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Admin admin = new AdminDaoImp().getAdmin(username, password);
        if (admin == null) {
            JOptionPane.showMessageDialog(this, "Invalid credentials! Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Welcome " + username + "!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
            Home home = new Home();
            home.setVisible(true);
            dispose();
        }
    }
}