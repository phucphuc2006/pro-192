package views;

import managers.UserAccountManager;
import models.UserAccount;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {
    private UserAccountManager userManager;
    private managers.StudentManager studentManager; // Reference to pass to next screen
    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public LoginUI(UserAccountManager userManager, managers.StudentManager studentManager) {
        this.userManager = userManager;
        this.studentManager = studentManager;

        setTitle("Login - Student Management System");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        JButton btnLogin = new JButton("Login");
        JButton btnCancel = new JButton("Cancel");

        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(btnLogin);
        btnPanel.add(btnCancel);

        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // Action Listeners
        btnLogin.addActionListener(e -> performLogin());

        // Enter key shortcut
        getRootPane().setDefaultButton(btnLogin);

        btnCancel.addActionListener(e -> System.exit(0));
    }

    private void performLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password!");
            return;
        }

        UserAccount user = userManager.login(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            this.dispose(); // Close Login Window

            // Open Main UI
            SwingUtilities.invokeLater(() -> {
                new StudentManagementUI(studentManager).setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password!", "Login Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
