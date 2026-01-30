package views;

import managers.UserAccountManager;
import models.UserAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import models.UserAccount.UserRole;
import models.UserAccount.UserStatus;

public class LoginUI extends JFrame {
    private UserAccountManager userManager;
    private managers.StudentManager studentManager;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chkRemember;
    private JComboBox<String> cmbRole;

    // Color scheme
    private static final Color PRIMARY_DARK = new Color(26, 35, 75);
    private static final Color PRIMARY_BLUE = new Color(30, 60, 114);
    private static final Color ACCENT_CREAM = new Color(245, 235, 210);
    private static final Color ACCENT_LIGHT = new Color(200, 220, 240);
    private static final Color BUTTON_BLUE = new Color(30, 55, 100);
    private static final Color TEXT_GRAY = new Color(100, 100, 100);
    private static final Color BORDER_COLOR = new Color(200, 200, 200);

    public LoginUI(UserAccountManager userManager, managers.StudentManager studentManager) {
        this.userManager = userManager;
        this.studentManager = studentManager;

        setTitle("Login - Student Management System");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(700, 450));
        setLayout(new BorderLayout());

        // Main container
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(PRIMARY_DARK);

        // Left Panel - Login Form (white background)
        JPanel leftPanel = createLoginPanel();
        leftPanel.setPreferredSize(new Dimension(320, 550));

        // Right Panel - Welcome with gradient wave
        JPanel rightPanel = createWelcomePanel();

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Enter key shortcut
        getRootPane().setDefaultButton(null);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 35, 30, 35));

        // Logo/Brand
        JLabel lblBrand = new JLabel("Student");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblBrand.setForeground(PRIMARY_BLUE);
        lblBrand.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblBrand);

        JLabel lblBrand2 = new JLabel("Management");
        lblBrand2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblBrand2.setForeground(PRIMARY_BLUE);
        lblBrand2.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblBrand2);

        panel.add(Box.createVerticalStrut(25));

        // User Avatar Circle
        JPanel avatarWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        avatarWrapper.setBackground(Color.WHITE);
        avatarWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        avatarWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        JPanel avatarPanel = new AvatarPanel();
        avatarPanel.setPreferredSize(new Dimension(80, 80));
        avatarWrapper.add(avatarPanel);
        panel.add(avatarWrapper);

        panel.add(Box.createVerticalStrut(25));

        // Username Field
        JPanel usernamePanel = createInputField("USERNAME", false);
        panel.add(usernamePanel);

        panel.add(Box.createVerticalStrut(15));

        // Password Field
        JPanel passwordPanel = createInputField("PASSWORD", true);
        panel.add(passwordPanel);

        panel.add(Box.createVerticalStrut(15));

        // Role Selection - Label
        JLabel lblRole = new JLabel("Vai trò:");
        lblRole.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblRole.setForeground(PRIMARY_DARK);
        lblRole.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblRole);

        panel.add(Box.createVerticalStrut(5));

        // Role Selection - ComboBox
        JPanel rolePanel = new JPanel(new BorderLayout());
        rolePanel.setBackground(Color.WHITE);
        rolePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rolePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        rolePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        String[] roles = {
                "Sinh viên (Student)",
                "Giáo viên (Teacher)",
                "Quản trị viên (Admin)"
        };

        cmbRole = new JComboBox<>(roles);
        cmbRole.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbRole.setBackground(Color.WHITE);
        cmbRole.setForeground(Color.DARK_GRAY);
        cmbRole.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

        rolePanel.add(cmbRole, BorderLayout.CENTER);
        panel.add(rolePanel);

        panel.add(Box.createVerticalStrut(15));

        // Login Button
        JButton btnLogin = createLoginButton();
        JPanel btnWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        btnWrapper.setBackground(Color.WHITE);
        btnWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnWrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnWrapper.add(btnLogin);
        panel.add(btnWrapper);

        panel.add(Box.createVerticalStrut(15));

        // Remember me & Forgot password
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        chkRemember = new JCheckBox("Remember me");
        chkRemember.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        chkRemember.setForeground(TEXT_GRAY);
        chkRemember.setBackground(Color.WHITE);
        chkRemember.setFocusPainted(false);

        JLabel lblForgot = new JLabel("Forgot your password?");
        lblForgot.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblForgot.setForeground(TEXT_GRAY);
        lblForgot.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblForgot.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblForgot.setForeground(PRIMARY_BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblForgot.setForeground(TEXT_GRAY);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(LoginUI.this,
                        "Please contact administrator to reset your password.",
                        "Forgot Password", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        bottomPanel.add(chkRemember, BorderLayout.WEST);
        bottomPanel.add(lblForgot, BorderLayout.EAST);
        panel.add(bottomPanel);

        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createInputField(String placeholder, boolean isPassword) {
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.WHITE);
        container.setAlignmentX(Component.LEFT_ALIGNMENT);
        container.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        // Rounded border panel
        JPanel fieldPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 25, 25));
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BORDER_COLOR);
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, 25, 25));
                g2.dispose();
            }
        };
        fieldPanel.setOpaque(false);
        fieldPanel.setBackground(Color.WHITE);
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        // Icon
        JLabel iconLabel = new JLabel(isPassword ? "🔒" : "👤");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        iconLabel.setForeground(TEXT_GRAY);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        // Text field
        if (isPassword) {
            txtPassword = new JPasswordField();
            txtPassword.setBorder(null);
            txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            txtPassword.setForeground(Color.DARK_GRAY);

            // Placeholder
            txtPassword.setEchoChar((char) 0);
            txtPassword.setText(placeholder);
            txtPassword.setForeground(TEXT_GRAY);
            txtPassword.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (String.valueOf(txtPassword.getPassword()).equals(placeholder)) {
                        txtPassword.setText("");
                        txtPassword.setEchoChar('●');
                        txtPassword.setForeground(Color.DARK_GRAY);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (String.valueOf(txtPassword.getPassword()).isEmpty()) {
                        txtPassword.setEchoChar((char) 0);
                        txtPassword.setText(placeholder);
                        txtPassword.setForeground(TEXT_GRAY);
                    }
                }
            });

            fieldPanel.add(iconLabel, BorderLayout.WEST);
            fieldPanel.add(txtPassword, BorderLayout.CENTER);
        } else {
            txtUsername = new JTextField();
            txtUsername.setBorder(null);
            txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));

            // Placeholder
            txtUsername.setText(placeholder);
            txtUsername.setForeground(TEXT_GRAY);
            txtUsername.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    if (txtUsername.getText().equals(placeholder)) {
                        txtUsername.setText("");
                        txtUsername.setForeground(Color.DARK_GRAY);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if (txtUsername.getText().isEmpty()) {
                        txtUsername.setText(placeholder);
                        txtUsername.setForeground(TEXT_GRAY);
                    }
                }
            });

            fieldPanel.add(iconLabel, BorderLayout.WEST);
            fieldPanel.add(txtUsername, BorderLayout.CENTER);
        }

        container.add(fieldPanel, BorderLayout.CENTER);
        return container;
    }

    private JButton createLoginButton() {
        JButton btn = new JButton("LOGIN") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(PRIMARY_DARK);
                } else if (getModel().isRollover()) {
                    g2.setColor(PRIMARY_BLUE);
                } else {
                    g2.setColor(BUTTON_BLUE);
                }

                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 25, 25));
                g2.dispose();

                super.paintComponent(g);
            }
        };

        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setPreferredSize(new Dimension(120, 40));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> performLogin());

        // Add Enter key support
        btn.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        });

        return btn;
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                int w = getWidth();
                int h = getHeight();

                // Background gradient
                GradientPaint bgGradient = new GradientPaint(0, 0, PRIMARY_DARK, w, h, PRIMARY_BLUE);
                g2.setPaint(bgGradient);
                g2.fillRect(0, 0, w, h);

                // Draw abstract wave/blob shapes
                drawWavePattern(g2, w, h);

                g2.dispose();
            }

            private void drawWavePattern(Graphics2D g2, int w, int h) {
                // Create smooth wave/blob effect similar to the reference image

                // Main wave path
                Path2D wave = new Path2D.Double();
                wave.moveTo(w * 0.1, h * 0.2);
                wave.curveTo(w * 0.3, h * 0.1, w * 0.5, h * 0.3, w * 0.7, h * 0.15);
                wave.curveTo(w * 0.85, h * 0.05, w * 0.95, h * 0.2, w, h * 0.35);
                wave.lineTo(w, h * 0.8);
                wave.curveTo(w * 0.8, h * 0.7, w * 0.6, h * 0.85, w * 0.4, h * 0.75);
                wave.curveTo(w * 0.2, h * 0.65, w * 0.1, h * 0.5, w * 0.1, h * 0.2);
                wave.closePath();

                // Gradient for the wave
                GradientPaint waveGradient = new GradientPaint(
                        w * 0.2f, h * 0.1f, ACCENT_CREAM,
                        w * 0.8f, h * 0.9f, ACCENT_LIGHT);
                g2.setPaint(waveGradient);
                g2.fill(wave);

                // Secondary smaller wave
                Path2D wave2 = new Path2D.Double();
                wave2.moveTo(w * 0.3, h * 0.35);
                wave2.curveTo(w * 0.45, h * 0.25, w * 0.6, h * 0.45, w * 0.75, h * 0.35);
                wave2.curveTo(w * 0.85, h * 0.28, w * 0.95, h * 0.4, w, h * 0.5);
                wave2.lineTo(w, h * 0.65);
                wave2.curveTo(w * 0.75, h * 0.6, w * 0.55, h * 0.7, w * 0.4, h * 0.55);
                wave2.curveTo(w * 0.35, h * 0.5, w * 0.3, h * 0.45, w * 0.3, h * 0.35);
                wave2.closePath();

                GradientPaint wave2Gradient = new GradientPaint(
                        w * 0.3f, h * 0.3f, new Color(220, 230, 240, 200),
                        w * 0.9f, h * 0.7f, new Color(180, 200, 220, 150));
                g2.setPaint(wave2Gradient);
                g2.fill(wave2);

                // Add subtle glow effect
                RadialGradientPaint glow = new RadialGradientPaint(
                        new Point2D.Float(w * 0.5f, h * 0.4f),
                        w * 0.4f,
                        new float[] { 0f, 1f },
                        new Color[] { new Color(255, 255, 255, 40), new Color(255, 255, 255, 0) });
                g2.setPaint(glow);
                g2.fillOval((int) (w * 0.2), (int) (h * 0.15), (int) (w * 0.6), (int) (h * 0.5));
            }
        };

        // Welcome text panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 100, 50));

        // Add spacer
        textPanel.add(Box.createVerticalGlue());

        // Welcome text
        JLabel lblWelcome = new JLabel("Welcome.");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(lblWelcome);

        textPanel.add(Box.createVerticalStrut(15));

        // Subtitle
        JLabel lblSubtitle = new JLabel(
                "<html><div style='width:200px'>Student Management System - Login to access your dashboard</div></html>");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitle.setForeground(new Color(200, 200, 200));
        lblSubtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        textPanel.add(lblSubtitle);

        textPanel.add(Box.createVerticalStrut(30));

        // Sign up link
        JPanel signupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        signupPanel.setOpaque(false);
        signupPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblNew = new JLabel("New user? ");
        lblNew.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblNew.setForeground(new Color(180, 180, 180));

        JLabel lblSignUp = new JLabel("Sign up now");
        lblSignUp.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblSignUp.setForeground(ACCENT_CREAM);
        lblSignUp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblSignUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblSignUp.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblSignUp.setForeground(ACCENT_CREAM);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                LoginUI.this.openRegisterUI();
            }
        });

        signupPanel.add(lblNew);
        signupPanel.add(lblSignUp);
        textPanel.add(signupPanel);

        textPanel.add(Box.createVerticalStrut(50));

        panel.add(textPanel, BorderLayout.SOUTH);

        return panel;
    }

    // Custom Avatar Panel
    private class AvatarPanel extends JPanel {
        public AvatarPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int size = Math.min(w, h);

            // Draw circle background
            g2.setColor(PRIMARY_BLUE);
            g2.fillOval((w - size) / 2, (h - size) / 2, size, size);

            // Draw user icon (simplified)
            g2.setColor(Color.WHITE);

            // Head
            int headSize = size / 3;
            int headX = (w - headSize) / 2;
            int headY = h / 4;
            g2.fillOval(headX, headY, headSize, headSize);

            // Body (arc)
            int bodyWidth = size / 2;
            int bodyHeight = size / 3;
            int bodyX = (w - bodyWidth) / 2;
            int bodyY = h / 2 + 5;
            g2.fillArc(bodyX, bodyY, bodyWidth, bodyHeight * 2, 0, 180);

            g2.dispose();
        }
    }

    private void performLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        // Check if placeholder text
        if (username.equals("USERNAME") || username.isEmpty()) {
            showError("Please enter your username!");
            txtUsername.requestFocus();
            return;
        }

        if (password.equals("PASSWORD") || password.isEmpty()) {
            showError("Please enter your password!");
            txtPassword.requestFocus();
            return;
        }

        // Get selected role
        UserRole selectedRole = UserRole.STUDENT;
        int selectedIndex = cmbRole.getSelectedIndex();
        switch (selectedIndex) {
            case 0:
                selectedRole = UserRole.STUDENT;
                break;
            case 1:
                selectedRole = UserRole.TEACHER;
                break;
            case 2:
                selectedRole = UserRole.ADMIN;
                break;
        }

        UserAccount user = userManager.login(username, password);

        if (user != null) {
            // Check if role matches
            if (user.getRole() != selectedRole) {
                showError("Vai trò không khớp!\nTài khoản này có vai trò là " + user.getRoleDisplayName() +
                        ".\nVui lòng chọn đúng vai trò để đăng nhập.");
                return;
            }

            showSuccess("Login successful! Welcome, " + user.getUsername() + "\nRole: " + user.getRoleDisplayName());
            this.dispose();

            SwingUtilities.invokeLater(() -> {
                new MainDashboardUI(user, studentManager, userManager).setVisible(true);
            });
        } else {
            // Check specific status for better error message
            UserAccount u = userManager.findUserByUsername(username);

            if (u != null) {
                if (u.getStatus() == UserStatus.PENDING) {
                    showError("Account is pending approval!\nPlease contact an administrator.");
                    return;
                }
                if (u.getStatus() == UserStatus.BANNED) {
                    showError("Account has been banned!");
                    return;
                }
                if (u.isLocked()) {
                    showError("Account is locked due to too many failed attempts!");
                    return;
                }
            }

            showError("Invalid username or password!");
            txtPassword.setText("");
            txtPassword.requestFocus();
        }
    }

    private void showError(String message) {
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
        JDialog dialog = optionPane.createDialog(this, "Login Error");
        dialog.setVisible(true);
    }

    private void showSuccess(String message) {
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(this, "Success");
        dialog.setVisible(true);
    }

    private void openRegisterUI() {
        RegisterUI registerUI = new RegisterUI(this, userManager);
        registerUI.setVisible(true);
    }
}
