package views;

import managers.*;
import models.UserAccount;
import models.UserAccount.UserRole;
import views.panels.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

/**
 * Main Dashboard UI with role-based menu
 */
public class MainDashboardUI extends JFrame {
    private UserAccount currentUser;
    private StudentManager studentManager;
    private UserAccountManager userManager;

    // All managers
    private TeacherManager teacherManager;
    private CourseManager courseManager;
    private ClassRoomManager classRoomManager;
    private GradeManager gradeManager;
    private EnrollmentManager enrollmentManager;

    // UI Components
    private JPanel contentPanel;
    private JPanel sidebarPanel;

    // Colors
    private static final Color PRIMARY_DARK = new Color(26, 35, 75);
    private static final Color PRIMARY_BLUE = new Color(30, 60, 114);
    private static final Color SIDEBAR_BG = new Color(36, 41, 62);
    private static final Color SIDEBAR_HOVER = new Color(50, 56, 82);
    private static final Color SIDEBAR_ACTIVE = new Color(66, 133, 244);
    private static final Color TEXT_WHITE = new Color(255, 255, 255);
    private static final Color TEXT_GRAY = new Color(180, 180, 180);
    private static final Color CONTENT_BG = new Color(245, 247, 250);

    public MainDashboardUI(UserAccount user, StudentManager studentManager, UserAccountManager userManager) {
        this.currentUser = user;
        this.studentManager = studentManager;
        this.userManager = userManager;

        // Initialize other managers
        initializeManagers();

        setTitle("Student Management System - Dashboard");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(900, 600));

        // Main layout
        setLayout(new BorderLayout());

        // Create sidebar
        sidebarPanel = createSidebar();
        add(sidebarPanel, BorderLayout.WEST);

        // Create content area
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(CONTENT_BG);
        add(contentPanel, BorderLayout.CENTER);

        // Show welcome screen initially
        showWelcomePanel();
    }

    private void initializeManagers() {
        teacherManager = new TeacherManager();
        teacherManager.loadFromFile();

        courseManager = new CourseManager();
        courseManager.loadFromFile();

        classRoomManager = new ClassRoomManager();
        classRoomManager.loadFromFile();

        gradeManager = new GradeManager();
        gradeManager.loadFromFile();

        enrollmentManager = new EnrollmentManager();
        enrollmentManager.loadFromFile();
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Header with user info
        JPanel headerPanel = createSidebarHeader();
        sidebar.add(headerPanel);

        sidebar.add(Box.createVerticalStrut(20));

        // Menu items based on role
        addMenuItems(sidebar);

        sidebar.add(Box.createVerticalGlue());

        // Logout button at bottom
        JPanel logoutPanel = createMenuItem("🚪  Logout", e -> performLogout());
        logoutPanel.setBackground(new Color(180, 60, 60));
        sidebar.add(logoutPanel);
        sidebar.add(Box.createVerticalStrut(10));

        return sidebar;
    }

    private JPanel createSidebarHeader() {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(PRIMARY_DARK);
        header.setBorder(BorderFactory.createEmptyBorder(25, 20, 25, 20));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        // App name
        JLabel lblApp = new JLabel("📚 Student MS");
        lblApp.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblApp.setForeground(TEXT_WHITE);
        lblApp.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(lblApp);

        header.add(Box.createVerticalStrut(10));

        // User info
        JLabel lblUser = new JLabel("👤 " + currentUser.getUsername());
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUser.setForeground(TEXT_WHITE);
        lblUser.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(lblUser);

        // Role badge
        JLabel lblRole = createRoleBadge();
        lblRole.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(Box.createVerticalStrut(5));
        header.add(lblRole);

        return header;
    }

    private JLabel createRoleBadge() {
        String roleText = currentUser.getRoleDisplayName();
        Color badgeColor;

        switch (currentUser.getRole()) {
            case ADMIN:
                badgeColor = new Color(220, 53, 69);
                break;
            case TEACHER:
                badgeColor = new Color(40, 167, 69);
                break;
            default:
                badgeColor = new Color(0, 123, 255);
        }

        JLabel badge = new JLabel(roleText) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(badgeColor);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(Color.WHITE);
        badge.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 10));
        badge.setOpaque(false);

        return badge;
    }

    private void addMenuItems(JPanel sidebar) {
        UserRole role = currentUser.getRole();

        // Common items for all roles
        sidebar.add(createMenuItem("🏠  Dashboard", e -> showWelcomePanel()));

        // Role-specific menu items
        switch (role) {
            case ADMIN:
                addAdminMenuItems(sidebar);
                break;
            case TEACHER:
                addTeacherMenuItems(sidebar);
                break;
            case STUDENT:
                addStudentMenuItems(sidebar);
                break;
        }
    }

    private void addAdminMenuItems(JPanel sidebar) {
        sidebar.add(Box.createVerticalStrut(5));

        // Section: User Management
        sidebar.add(createSectionLabel("User Management"));
        sidebar.add(createMenuItem("👥  Manage Users", e -> showUserManagement()));

        sidebar.add(Box.createVerticalStrut(10));

        // Section: Academic Management
        sidebar.add(createSectionLabel("Academic"));
        sidebar.add(createMenuItem("🎓  Students", e -> showStudentManagement()));
        sidebar.add(createMenuItem("👨‍🏫  Teachers", e -> showTeacherManagement()));
        sidebar.add(createMenuItem("📖  Courses", e -> showCourseManagement()));
        sidebar.add(createMenuItem("🏫  Classes", e -> showClassManagement()));

        sidebar.add(Box.createVerticalStrut(10));

        // Section: Records
        sidebar.add(createSectionLabel("Records"));
        sidebar.add(createMenuItem("📝  Grades", e -> showGradeManagement()));
        sidebar.add(createMenuItem("📊  Reports", e -> showReports()));
    }

    private void addTeacherMenuItems(JPanel sidebar) {
        sidebar.add(Box.createVerticalStrut(5));

        // Section: My Classes
        sidebar.add(createSectionLabel("My Classes"));
        sidebar.add(createMenuItem("🎓  View Students", e -> showStudentManagement()));
        sidebar.add(createMenuItem("📝  Manage Grades", e -> showGradeManagement()));

        sidebar.add(Box.createVerticalStrut(10));

        // Section: View Only
        sidebar.add(createSectionLabel("View"));
        sidebar.add(createMenuItem("📖  Courses", e -> showCourseManagement()));
        sidebar.add(createMenuItem("📊  Reports", e -> showReports()));
    }

    private void addStudentMenuItems(JPanel sidebar) {
        sidebar.add(Box.createVerticalStrut(5));

        // Section: My Academic
        sidebar.add(createSectionLabel("My Academic"));
        sidebar.add(createMenuItem("📋  My Profile", e -> showStudentProfile()));
        sidebar.add(createMenuItem("📝  My Grades", e -> showMyGrades()));
        sidebar.add(createMenuItem("📖  My Courses", e -> showMyCourses()));
    }

    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text.toUpperCase());
        label.setFont(new Font("Segoe UI", Font.BOLD, 10));
        label.setForeground(TEXT_GRAY);
        label.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        return label;
    }

    private JPanel createMenuItem(String text, ActionListener action) {
        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(SIDEBAR_BG);
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));
        item.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(TEXT_WHITE);
        item.add(label, BorderLayout.CENTER);

        // Hover effect
        item.addMouseListener(new MouseAdapter() {
            Color originalBg = item.getBackground();

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!item.getBackground().equals(SIDEBAR_ACTIVE)) {
                    item.setBackground(SIDEBAR_HOVER);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!item.getBackground().equals(SIDEBAR_ACTIVE)) {
                    item.setBackground(originalBg);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                action.actionPerformed(null);
            }
        });

        return item;
    }

    // ============ Content Panels ============

    private void showWelcomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CONTENT_BG);

        JPanel card = createCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel lblWelcome = new JLabel("Welcome, " + currentUser.getUsername() + "!");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblWelcome.setForeground(PRIMARY_DARK);
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblRole = new JLabel("You are logged in as: " + currentUser.getRoleDisplayName());
        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblRole.setForeground(TEXT_GRAY);
        lblRole.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblInfo = new JLabel(
                "<html><center>Use the sidebar menu to navigate<br>to different sections of the system.</center></html>");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblInfo.setForeground(new Color(120, 120, 120));
        lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalStrut(30));
        card.add(lblWelcome);
        card.add(Box.createVerticalStrut(15));
        card.add(lblRole);
        card.add(Box.createVerticalStrut(20));
        card.add(lblInfo);
        card.add(Box.createVerticalStrut(30));

        // Quick stats for Admin
        if (currentUser.getRole() == UserRole.ADMIN) {
            card.add(createQuickStats());
        }

        panel.add(card);
        setContentPanel(panel);
    }

    private JPanel createQuickStats() {
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        statsPanel.setOpaque(false);

        statsPanel.add(
                createStatCard("Students", String.valueOf(studentManager.getAll().size()), new Color(0, 123, 255)));
        statsPanel.add(
                createStatCard("Teachers", String.valueOf(teacherManager.getAll().size()), new Color(40, 167, 69)));
        statsPanel
                .add(createStatCard("Courses", String.valueOf(courseManager.getAll().size()), new Color(255, 193, 7)));

        return statsPanel;
    }

    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        card.setPreferredSize(new Dimension(120, 80));

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblValue.setForeground(Color.WHITE);
        lblValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTitle.setForeground(new Color(255, 255, 255, 200));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lblValue);
        card.add(Box.createVerticalStrut(5));
        card.add(lblTitle);

        return card;
    }

    private JPanel createCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));

                // Shadow effect
                g2.setColor(new Color(0, 0, 0, 10));
                g2.fill(new RoundRectangle2D.Float(2, 2, getWidth(), getHeight(), 15, 15));

                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        return card;
    }

    private void showStudentManagement() {
        // Embed StudentManagementUI content
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CONTENT_BG);
        wrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create a modified StudentManagementUI panel
        JPanel studentPanel = createStudentManagementPanel();
        wrapper.add(studentPanel, BorderLayout.CENTER);

        setContentPanel(wrapper);
    }

    private JPanel createStudentManagementPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        // Title
        JLabel title = new JLabel("Student Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(PRIMARY_DARK);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        mainPanel.add(title, BorderLayout.NORTH);

        // Embed the actual StudentManagementUI but as a panel
        StudentManagementUI smUI = new StudentManagementUI(studentManager);
        smUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Get the content pane and add it
        Container content = smUI.getContentPane();
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBackground(Color.WHITE);

        for (Component c : content.getComponents()) {
            content.remove(c);
            contentWrapper.add(c);
        }

        mainPanel.add(contentWrapper, BorderLayout.CENTER);

        return mainPanel;
    }

    private void showUserManagement() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CONTENT_BG);
        wrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        UserManagementPanel panel = new UserManagementPanel(userManager);
        wrapper.add(panel, BorderLayout.CENTER);

        setContentPanel(wrapper);
    }

    private void showTeacherManagement() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CONTENT_BG);
        wrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        TeacherManagementPanel panel = new TeacherManagementPanel(teacherManager);
        wrapper.add(panel, BorderLayout.CENTER);

        setContentPanel(wrapper);
    }

    private void showCourseManagement() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CONTENT_BG);
        wrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        CourseManagementPanel panel = new CourseManagementPanel(courseManager, teacherManager);
        wrapper.add(panel, BorderLayout.CENTER);

        setContentPanel(wrapper);
    }

    private void showClassManagement() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CONTENT_BG);
        wrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        ClassManagementPanel panel = new ClassManagementPanel(classRoomManager, courseManager, teacherManager);
        wrapper.add(panel, BorderLayout.CENTER);

        setContentPanel(wrapper);
    }

    private void showGradeManagement() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CONTENT_BG);
        wrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GradeManagementPanel panel = new GradeManagementPanel(gradeManager, studentManager, courseManager);
        wrapper.add(panel, BorderLayout.CENTER);

        setContentPanel(wrapper);
    }

    private void showReports() {
        showPlaceholder("Reports & Statistics", "View academic reports and statistics.");
    }

    private void showStudentProfile() {
        showPlaceholder("My Profile", "View and edit your student profile.");
    }

    private void showMyGrades() {
        showPlaceholder("My Grades", "View your grades and academic performance.");
    }

    private void showMyCourses() {
        showPlaceholder("My Courses", "View your enrolled courses.");
    }

    private void showPlaceholder(String title, String description) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CONTENT_BG);

        JPanel card = createCard();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(400, 200));

        JLabel lblIcon = new JLabel("🚧");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(PRIMARY_DARK);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblDesc = new JLabel(description);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDesc.setForeground(TEXT_GRAY);
        lblDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblStatus = new JLabel("Coming Soon...");
        lblStatus.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblStatus.setForeground(new Color(150, 150, 150));
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lblIcon);
        card.add(Box.createVerticalStrut(15));
        card.add(lblTitle);
        card.add(Box.createVerticalStrut(10));
        card.add(lblDesc);
        card.add(Box.createVerticalStrut(15));
        card.add(lblStatus);

        panel.add(card);
        setContentPanel(panel);
    }

    private void setContentPanel(JPanel newContent) {
        contentPanel.removeAll();
        contentPanel.add(newContent, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void performLogout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();

            // Show login screen again
            SwingUtilities.invokeLater(() -> {
                new LoginUI(userManager, studentManager).setVisible(true);
            });
        }
    }
}
