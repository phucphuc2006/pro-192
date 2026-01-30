package views.panels;

import managers.UserAccountManager;
import models.UserAccount;
import models.UserAccount.UserRole;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel for User Management (Admin only)
 */
public class UserManagementPanel extends JPanel {
    private UserAccountManager userManager;
    private DefaultTableModel tableModel;
    private JTable table;

    private JTextField txtSearch;

    private static final Color PRIMARY_DARK = new Color(26, 35, 75);
    private static final Color ACCENT_BLUE = new Color(66, 133, 244);

    public UserManagementPanel(UserAccountManager manager) {
        this.userManager = manager;

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Title
        JLabel title = new JLabel("👥 User Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(PRIMARY_DARK);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        // Table panel
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        // Action panel
        JPanel actionPanel = createActionPanel();
        add(actionPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);

        // Search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(Color.WHITE);

        txtSearch = new JTextField(25);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JButton btnSearch = createButton("Search", ACCENT_BLUE);
        JButton btnRefresh = createButton("Refresh", new Color(108, 117, 125));

        searchPanel.add(new JLabel("🔍 Search:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Table
        String[] columns = { "User ID", "Username", "Email", "Role", "Status" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setSelectionBackground(new Color(232, 240, 254));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        btnSearch.addActionListener(e -> searchUser());
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            refreshTable();
        });

        return panel;
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        JButton btnApprove = createButton("✅ Approve", new Color(0, 150, 0)); // Green
        JButton btnUnlock = createButton("🔓 Unlock Account", new Color(40, 167, 69));
        JButton btnLock = createButton("🔒 Lock Account", new Color(255, 193, 7));
        btnLock.setForeground(Color.BLACK);
        JButton btnChangeRole = createButton("👤 Change Role", ACCENT_BLUE);
        JButton btnDelete = createButton("🗑️ Delete User", new Color(220, 53, 69));

        panel.add(btnApprove);
        panel.add(btnUnlock);
        panel.add(btnLock);
        panel.add(btnChangeRole);
        panel.add(btnDelete);

        btnApprove.addActionListener(e -> approveUser());
        btnUnlock.addActionListener(e -> unlockUser());
        btnLock.addActionListener(e -> lockUser());
        btnChangeRole.addActionListener(e -> changeUserRole());
        btnDelete.addActionListener(e -> deleteUser());

        return panel;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(150, 35));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });

        return btn;
    }

    private void refreshTable() {
        tableModel.setRowCount(0);

        // Use reflection or direct access - for now we'll iterate through searches
        // Since UserAccountManager doesn't have getAll(), we'll need to add it or work
        // around
        // For now, showing a message about this limitation

        // This is a workaround - ideally UserAccountManager should have getAll()
        try {
            java.lang.reflect.Field field = UserAccountManager.class.getDeclaredField("users");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            ArrayList<UserAccount> users = (ArrayList<UserAccount>) field.get(userManager);

            for (UserAccount u : users) {
                String status;
                if (u.isLocked()) {
                    status = "🔒 Locked";
                } else if (u.getStatus() == UserAccount.UserStatus.PENDING) {
                    status = "⏳ Pending";
                } else if (u.getStatus() == UserAccount.UserStatus.BANNED) {
                    status = "🚫 Banned";
                } else {
                    status = "✅ Active";
                }

                String role = u.getRole() != null ? u.getRoleDisplayName() : "Student";

                tableModel.addRow(new Object[] {
                        u.getUserID(),
                        u.getUsername(),
                        u.getEmail(),
                        role,
                        status
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading users: " + e.getMessage());
        }
    }

    private UserAccount getSelectedUser() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user first!");
            return null;
        }

        String userId = (String) tableModel.getValueAt(row, 0);
        return userManager.findUserById(userId);
    }

    private void approveUser() {
        UserAccount user = getSelectedUser();
        if (user == null)
            return;

        if (user.getStatus() != UserAccount.UserStatus.PENDING) {
            JOptionPane.showMessageDialog(this, "This account is not pending approval!");
            return;
        }

        userManager.approveUser(user.getUserID());
        userManager.saveToFile();

        JOptionPane.showMessageDialog(this, "Account approved successfully!");
        refreshTable();
    }

    private void unlockUser() {
        UserAccount user = getSelectedUser();
        if (user == null)
            return;

        if (!user.isLocked()) {
            JOptionPane.showMessageDialog(this, "This account is not locked!");
            return;
        }

        userManager.unlockAccount(user.getUserID());
        userManager.saveToFile();

        JOptionPane.showMessageDialog(this, "Account unlocked successfully!");
        refreshTable();
    }

    private void lockUser() {
        UserAccount user = getSelectedUser();
        if (user == null)
            return;

        if (user.isLocked()) {
            JOptionPane.showMessageDialog(this, "This account is already locked!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to lock user: " + user.getUsername() + "?",
                "Confirm Lock", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            user.setLocked(true);
            userManager.saveToFile();
            JOptionPane.showMessageDialog(this, "Account locked successfully!");
            refreshTable();
        }
    }

    private void changeUserRole() {
        UserAccount user = getSelectedUser();
        if (user == null)
            return;

        String[] roles = { "Student", "Teacher", "Admin" };
        String currentRole = user.getRole() != null ? user.getRole().name() : "STUDENT";

        int currentIndex = 0;
        switch (currentRole) {
            case "TEACHER":
                currentIndex = 1;
                break;
            case "ADMIN":
                currentIndex = 2;
                break;
        }

        String selected = (String) JOptionPane.showInputDialog(this,
                "Select new role for " + user.getUsername() + ":",
                "Change Role",
                JOptionPane.QUESTION_MESSAGE,
                null,
                roles,
                roles[currentIndex]);

        if (selected != null) {
            UserRole newRole = UserRole.STUDENT;
            switch (selected) {
                case "Teacher":
                    newRole = UserRole.TEACHER;
                    break;
                case "Admin":
                    newRole = UserRole.ADMIN;
                    break;
            }

            user.setRole(newRole);
            userManager.saveToFile();

            JOptionPane.showMessageDialog(this, "Role changed to " + selected + " successfully!");
            refreshTable();
        }
    }

    private void deleteUser() {
        UserAccount user = getSelectedUser();
        if (user == null)
            return;

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to DELETE user: " + user.getUsername() + "?\nThis action cannot be undone!",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            userManager.deleteUser(user.getUserID());
            userManager.saveToFile();
            JOptionPane.showMessageDialog(this, "User deleted successfully!");
            refreshTable();
        }
    }

    private void searchUser() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            refreshTable();
            return;
        }

        tableModel.setRowCount(0);

        try {
            java.lang.reflect.Field field = UserAccountManager.class.getDeclaredField("users");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            ArrayList<UserAccount> users = (ArrayList<UserAccount>) field.get(userManager);

            for (UserAccount u : users) {
                if (u.getUserID().toLowerCase().contains(keyword) ||
                        u.getUsername().toLowerCase().contains(keyword) ||
                        u.getEmail().toLowerCase().contains(keyword)) {

                    String status = u.isLocked() ? "🔒 Locked" : "✅ Active";
                    String role = u.getRole() != null ? u.getRoleDisplayName() : "Student";

                    tableModel.addRow(new Object[] {
                            u.getUserID(),
                            u.getUsername(),
                            u.getEmail(),
                            role,
                            status
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error searching users: " + e.getMessage());
        }
    }
}
