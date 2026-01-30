package views.panels;

import managers.TeacherManager;
import models.Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel for Teacher Management
 */
public class TeacherManagementPanel extends JPanel {
    private TeacherManager teacherManager;
    private DefaultTableModel tableModel;
    private JTable table;

    private JTextField txtId, txtName, txtDepartment, txtEmail, txtPhone;
    private JTextField txtSearch;

    private static final Color PRIMARY_DARK = new Color(26, 35, 75);
    private static final Color ACCENT_BLUE = new Color(66, 133, 244);

    public TeacherManagementPanel(TeacherManager manager) {
        this.teacherManager = manager;

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Title
        JLabel title = new JLabel("👨‍🏫 Teacher Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(PRIMARY_DARK);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        // Main content with split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(280);
        splitPane.setDividerSize(5);

        // Left: Input form
        JPanel inputPanel = createInputPanel();
        splitPane.setLeftComponent(inputPanel);

        // Right: Table and search
        JPanel tablePanel = createTablePanel();
        splitPane.setRightComponent(tablePanel);

        add(splitPane, BorderLayout.CENTER);

        // Load data
        refreshTable(teacherManager.getAll());

        // Table selection listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtId.setText((String) tableModel.getValueAt(row, 0));
                txtName.setText((String) tableModel.getValueAt(row, 1));
                txtDepartment.setText((String) tableModel.getValueAt(row, 2));
                txtEmail.setText((String) tableModel.getValueAt(row, 3));
                txtPhone.setText((String) tableModel.getValueAt(row, 4));
            }
        });
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel lblForm = new JLabel("Teacher Information");
        lblForm.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblForm.setForeground(PRIMARY_DARK);
        lblForm.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblForm);
        panel.add(Box.createVerticalStrut(15));

        // Fields
        panel.add(createFormField("Teacher ID:", txtId = new JTextField()));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createFormField("Full Name:", txtName = new JTextField()));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createFormField("Department:", txtDepartment = new JTextField()));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createFormField("Email:", txtEmail = new JTextField()));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createFormField("Phone:", txtPhone = new JTextField()));
        panel.add(Box.createVerticalStrut(20));

        // Buttons
        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        btnPanel.setOpaque(false);
        btnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JButton btnAdd = createButton("Add", ACCENT_BLUE);
        JButton btnUpdate = createButton("Update", new Color(40, 167, 69));
        JButton btnDelete = createButton("Delete", new Color(220, 53, 69));
        JButton btnClear = createButton("Clear", new Color(108, 117, 125));

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        panel.add(btnPanel);
        panel.add(Box.createVerticalGlue());

        // Button actions
        btnAdd.addActionListener(e -> addTeacher());
        btnUpdate.addActionListener(e -> updateTeacher());
        btnDelete.addActionListener(e -> deleteTeacher());
        btnClear.addActionListener(e -> clearFields());

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);

        // Search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(Color.WHITE);

        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JButton btnSearch = createButton("Search", ACCENT_BLUE);
        JButton btnRefresh = createButton("Refresh", new Color(108, 117, 125));

        searchPanel.add(new JLabel("🔍"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Table
        String[] columns = { "ID", "Name", "Department", "Email", "Phone" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setSelectionBackground(new Color(232, 240, 254));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Search action
        btnSearch.addActionListener(e -> searchTeacher());
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            refreshTable(teacherManager.getAll());
        });

        return panel;
    }

    private JPanel createFormField(String label, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(5, 2));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(new Color(80, 80, 80));

        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

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
        btn.setPreferredSize(new Dimension(80, 32));

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

    private void refreshTable(ArrayList<Teacher> teachers) {
        tableModel.setRowCount(0);
        for (Teacher t : teachers) {
            tableModel.addRow(new Object[] {
                    t.getId(), t.getName(), t.getDepartment(),
                    t.getEmail(), t.getPhone()
            });
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtDepartment.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        table.clearSelection();
    }

    private void addTeacher() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String department = txtDepartment.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID and Name are required!");
            return;
        }

        if (teacherManager.findById(id) != null) {
            JOptionPane.showMessageDialog(this, "Teacher ID already exists!");
            return;
        }

        Teacher teacher = new Teacher(id, name, department, email, phone);
        teacherManager.addTeacher(teacher);
        teacherManager.saveToFile();

        JOptionPane.showMessageDialog(this, "Teacher added successfully!");
        refreshTable(teacherManager.getAll());
        clearFields();
    }

    private void updateTeacher() {
        String id = txtId.getText().trim();

        if (teacherManager.findById(id) == null) {
            JOptionPane.showMessageDialog(this, "Teacher not found!");
            return;
        }

        String name = txtName.getText().trim();
        String department = txtDepartment.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();

        teacherManager.updateTeacher(id, name, department, email, phone);
        teacherManager.saveToFile();

        JOptionPane.showMessageDialog(this, "Teacher updated successfully!");
        refreshTable(teacherManager.getAll());
    }

    private void deleteTeacher() {
        String id = txtId.getText().trim();

        if (id.isEmpty()) {
            int row = table.getSelectedRow();
            if (row != -1) {
                id = (String) tableModel.getValueAt(row, 0);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a teacher to delete!");
                return;
            }
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete teacher: " + id + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            teacherManager.deleteTeacher(id);
            teacherManager.saveToFile();
            JOptionPane.showMessageDialog(this, "Teacher deleted successfully!");
            refreshTable(teacherManager.getAll());
            clearFields();
        }
    }

    private void searchTeacher() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            refreshTable(teacherManager.getAll());
            return;
        }

        ArrayList<Teacher> all = teacherManager.getAll();
        ArrayList<Teacher> filtered = new ArrayList<>();

        for (Teacher t : all) {
            if (t.getId().toLowerCase().contains(keyword) ||
                    t.getName().toLowerCase().contains(keyword) ||
                    t.getDepartment().toLowerCase().contains(keyword)) {
                filtered.add(t);
            }
        }

        refreshTable(filtered);
    }
}
