package views;

import managers.UserAccountManager;
import models.UserAccount;
import models.UserAccount.UserRole;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class RegisterUI extends JDialog {
    private UserAccountManager userManager;
    private JTextField txtUsername;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JPasswordField txtConfirmPassword;
    private JComboBox<String> cmbRole;
    private boolean registered = false;

    // Color scheme
    private static final Color PRIMARY_DARK = new Color(26, 35, 75);
    private static final Color PRIMARY_BLUE = new Color(30, 60, 114);
    private static final Color ACCENT_CREAM = new Color(245, 235, 210);
    private static final Color BUTTON_BLUE = new Color(30, 55, 100);
    private static final Color TEXT_GRAY = new Color(100, 100, 100);
    private static final Color BORDER_COLOR = new Color(200, 200, 200);
    private static final Color SUCCESS_GREEN = new Color(46, 125, 50);
    private static final Color ERROR_RED = new Color(198, 40, 40);

    public RegisterUI(Frame parent, UserAccountManager userManager) {
        super(parent, "Create Account", true);
        this.userManager = userManager;

        setSize(450, 700);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout());

        // Main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(0, 0, PRIMARY_DARK, getWidth(), getHeight(), PRIMARY_BLUE);
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // White form card
        JPanel formCard = createFormCard();
        mainPanel.add(formCard, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createFormCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                g2.dispose();
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(30, 35, 30, 35));

        // Title
        JLabel lblTitle = new JLabel("Create New Account");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(PRIMARY_DARK);
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblTitle);

        JLabel lblSubtitle = new JLabel("Fill in the information below");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitle.setForeground(TEXT_GRAY);
        lblSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblSubtitle);

        card.add(Box.createVerticalStrut(25));

        // Username field
        card.add(createLabel("Username"));
        card.add(Box.createVerticalStrut(5));
        txtUsername = createTextField("Enter username");
        card.add(wrapTextField(txtUsername));
        card.add(Box.createVerticalStrut(15));

        // Email field
        card.add(createLabel("Email"));
        card.add(Box.createVerticalStrut(5));
        txtEmail = createTextField("Enter email address");
        card.add(wrapTextField(txtEmail));
        card.add(Box.createVerticalStrut(15));

        // Role selection
        card.add(createLabel("Role"));
        card.add(Box.createVerticalStrut(5));
        card.add(createRoleSelector());
        card.add(Box.createVerticalStrut(15));

        // Password field
        card.add(createLabel("Password"));
        card.add(Box.createVerticalStrut(5));
        txtPassword = createPasswordField("Enter password");
        card.add(wrapPasswordField(txtPassword));
        card.add(Box.createVerticalStrut(15));

        // Confirm password field
        card.add(createLabel("Confirm Password"));
        card.add(Box.createVerticalStrut(5));
        txtConfirmPassword = createPasswordField("Confirm password");
        card.add(wrapPasswordField(txtConfirmPassword));
        card.add(Box.createVerticalStrut(25));

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setOpaque(false);
        btnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        JButton btnRegister = createButton("REGISTER", BUTTON_BLUE, Color.WHITE);
        JButton btnCancel = createButton("CANCEL", new Color(158, 158, 158), Color.WHITE);

        btnRegister.addActionListener(e -> performRegister());
        btnCancel.addActionListener(e -> dispose());

        btnPanel.add(btnRegister);
        btnPanel.add(btnCancel);
        card.add(btnPanel);

        return card;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(PRIMARY_DARK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setText(placeholder);
        field.setForeground(TEXT_GRAY);
        field.setBorder(null);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.DARK_GRAY);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(TEXT_GRAY);
                }
            }
        });

        return field;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setEchoChar((char) 0);
        field.setText(placeholder);
        field.setForeground(TEXT_GRAY);
        field.setBorder(null);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setEchoChar('●');
                    field.setForeground(Color.DARK_GRAY);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(field.getPassword()).isEmpty()) {
                    field.setEchoChar((char) 0);
                    field.setText(placeholder);
                    field.setForeground(TEXT_GRAY);
                }
            }
        });

        return field;
    }

    private JPanel wrapTextField(JTextField field) {
        return createRoundedFieldPanel(field);
    }

    private JPanel wrapPasswordField(JPasswordField field) {
        return createRoundedFieldPanel(field);
    }

    private JPanel createRoundedFieldPanel(JComponent field) {
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setAlignmentX(Component.LEFT_ALIGNMENT);
        container.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        JPanel fieldPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BORDER_COLOR);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, 15, 15));
                g2.dispose();
            }
        };
        fieldPanel.setOpaque(false);
        fieldPanel.setBackground(Color.WHITE);
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        fieldPanel.add(field, BorderLayout.CENTER);

        container.add(fieldPanel, BorderLayout.CENTER);
        return container;
    }

    private JPanel createRoleSelector() {
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setAlignmentX(Component.LEFT_ALIGNMENT);
        container.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        String[] roles = {
                "Sinh viên (Student)",
                "Giáo viên (Teacher)"
        };

        cmbRole = new JComboBox<>(roles);
        cmbRole.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbRole.setBackground(Color.WHITE);
        cmbRole.setForeground(Color.DARK_GRAY);
        cmbRole.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        container.add(cmbRole, BorderLayout.CENTER);
        return container;
    }

    private JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bgColor.brighter());
                } else {
                    g2.setColor(bgColor);
                }

                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
                g2.dispose();

                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(fgColor);
        btn.setPreferredSize(new Dimension(120, 38));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return btn;
    }

    private void performRegister() {
        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());

        // Check placeholders
        if (username.equals("Enter username") || username.isEmpty()) {
            showError("Please enter a username!");
            txtUsername.requestFocus();
            return;
        }

        if (email.equals("Enter email address") || email.isEmpty()) {
            showError("Please enter an email!");
            txtEmail.requestFocus();
            return;
        }

        if (password.equals("Enter password") || password.isEmpty()) {
            showError("Please enter a password!");
            txtPassword.requestFocus();
            return;
        }

        if (confirmPassword.equals("Confirm password") || confirmPassword.isEmpty()) {
            showError("Please confirm your password!");
            txtConfirmPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match!");
            txtConfirmPassword.requestFocus();
            return;
        }

        // Get selected role
        UserRole role = UserRole.STUDENT;
        int selectedIndex = cmbRole.getSelectedIndex();
        if (selectedIndex == 1) {
            role = UserRole.TEACHER;
        }

        // Validate with manager
        String error = userManager.getRegisterError(username, email, password);
        if (error != null) {
            showError(error);
            return;
        }

        // Register
        UserAccount newUser = userManager.register(username, email, password, role);
        if (newUser != null) {
            userManager.saveToFile();

            String message = "Account created successfully!\n\nUsername: " + newUser.getUsername() +
                    "\nRole: " + newUser.getRoleDisplayName();

            if (newUser.getRole() == UserRole.TEACHER) {
                message += "\n\nNOTE: Teacher accounts require Admin approval before you can log in.\nPlease contact an administrator.";
            } else {
                message += "\n\nYou can now login with your credentials.";
            }

            showSuccess(message);
            registered = true;
            dispose();
        } else {
            showError("Registration failed. Please try again.");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Registration Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean isRegistered() {
        return registered;
    }
}
