package views.panels;

import managers.CourseManager;
import managers.TeacherManager;
import models.Course;
import models.Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel for Course Management
 */
public class CourseManagementPanel extends JPanel {
    private CourseManager courseManager;
    private TeacherManager teacherManager;
    private DefaultTableModel tableModel;
    private JTable table;

    private JTextField txtId, txtName, txtCredits, txtSemester;
    private JComboBox<String> cmbTeacher;
    private JTextField txtSearch;

    private static final Color PRIMARY_DARK = new Color(26, 35, 75);
    private static final Color ACCENT_BLUE = new Color(66, 133, 244);

    public CourseManagementPanel(CourseManager courseManager, TeacherManager teacherManager) {
        this.courseManager = courseManager;
        this.teacherManager = teacherManager;

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Title
        JLabel title = new JLabel("📖 Course Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(PRIMARY_DARK);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        // Main content
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(280);
        splitPane.setDividerSize(5);

        JPanel inputPanel = createInputPanel();
        splitPane.setLeftComponent(inputPanel);

        JPanel tablePanel = createTablePanel();
        splitPane.setRightComponent(tablePanel);

        add(splitPane, BorderLayout.CENTER);

        refreshTable(courseManager.getAll());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtId.setText((String) tableModel.getValueAt(row, 0));
                txtName.setText((String) tableModel.getValueAt(row, 1));
                txtCredits.setText(String.valueOf(tableModel.getValueAt(row, 2)));
                txtSemester.setText((String) tableModel.getValueAt(row, 3));

                // Select teacher in combo
                String teacherId = (String) tableModel.getValueAt(row, 4);
                for (int i = 0; i < cmbTeacher.getItemCount(); i++) {
                    if (cmbTeacher.getItemAt(i).startsWith(teacherId + " -")) {
                        cmbTeacher.setSelectedIndex(i);
                        break;
                    }
                }
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

        JLabel lblForm = new JLabel("Course Information");
        lblForm.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblForm.setForeground(PRIMARY_DARK);
        lblForm.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblForm);
        panel.add(Box.createVerticalStrut(15));

        panel.add(createFormField("Course ID:", txtId = new JTextField()));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createFormField("Course Name:", txtName = new JTextField()));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createFormField("Credits:", txtCredits = new JTextField()));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createFormField("Semester:", txtSemester = new JTextField()));
        panel.add(Box.createVerticalStrut(10));

        // Teacher combo
        JPanel teacherPanel = new JPanel(new BorderLayout(5, 2));
        teacherPanel.setOpaque(false);
        teacherPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        teacherPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel lblTeacher = new JLabel("Teacher:");
        lblTeacher.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        cmbTeacher = new JComboBox<>();
        loadTeachers();
        cmbTeacher.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        teacherPanel.add(lblTeacher, BorderLayout.NORTH);
        teacherPanel.add(cmbTeacher, BorderLayout.CENTER);
        panel.add(teacherPanel);

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

        btnAdd.addActionListener(e -> addCourse());
        btnUpdate.addActionListener(e -> updateCourse());
        btnDelete.addActionListener(e -> deleteCourse());
        btnClear.addActionListener(e -> clearFields());

        return panel;
    }

    private void loadTeachers() {
        cmbTeacher.removeAllItems();
        cmbTeacher.addItem("-- Select Teacher --");
        for (Teacher t : teacherManager.getAll()) {
            cmbTeacher.addItem(t.getId() + " - " + t.getName());
        }
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);

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

        String[] columns = { "ID", "Name", "Credits", "Semester", "Teacher ID" };
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

        btnSearch.addActionListener(e -> searchCourse());
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            refreshTable(courseManager.getAll());
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

    private void refreshTable(ArrayList<Course> courses) {
        tableModel.setRowCount(0);
        for (Course c : courses) {
            tableModel.addRow(new Object[] {
                    c.getId(), c.getName(), c.getCredits(),
                    c.getSemester(), c.getTeacherID()
            });
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtCredits.setText("");
        txtSemester.setText("");
        cmbTeacher.setSelectedIndex(0);
        table.clearSelection();
    }

    private void addCourse() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String creditsStr = txtCredits.getText().trim();
        String semester = txtSemester.getText().trim();

        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID and Name are required!");
            return;
        }

        if (courseManager.findById(id) != null) {
            JOptionPane.showMessageDialog(this, "Course ID already exists!");
            return;
        }

        int credits;
        try {
            credits = Integer.parseInt(creditsStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Credits must be a number!");
            return;
        }

        String teacherId = "";
        if (cmbTeacher.getSelectedIndex() > 0) {
            String selected = (String) cmbTeacher.getSelectedItem();
            teacherId = selected.split(" - ")[0];
        }

        Course course = new Course(id, name, credits, semester, teacherId);
        courseManager.addCourse(course);
        courseManager.saveToFile();

        JOptionPane.showMessageDialog(this, "Course added successfully!");
        refreshTable(courseManager.getAll());
        clearFields();
    }

    private void updateCourse() {
        String id = txtId.getText().trim();

        if (courseManager.findById(id) == null) {
            JOptionPane.showMessageDialog(this, "Course not found!");
            return;
        }

        String name = txtName.getText().trim();
        String creditsStr = txtCredits.getText().trim();
        String semester = txtSemester.getText().trim();

        int credits;
        try {
            credits = Integer.parseInt(creditsStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Credits must be a number!");
            return;
        }

        String teacherId = "";
        if (cmbTeacher.getSelectedIndex() > 0) {
            String selected = (String) cmbTeacher.getSelectedItem();
            teacherId = selected.split(" - ")[0];
        }

        courseManager.update(id, name, credits, semester, teacherId);
        courseManager.saveToFile();

        JOptionPane.showMessageDialog(this, "Course updated successfully!");
        refreshTable(courseManager.getAll());
    }

    private void deleteCourse() {
        String id = txtId.getText().trim();

        if (id.isEmpty()) {
            int row = table.getSelectedRow();
            if (row != -1) {
                id = (String) tableModel.getValueAt(row, 0);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course to delete!");
                return;
            }
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete course: " + id + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            courseManager.deleteCourse(id);
            courseManager.saveToFile();
            JOptionPane.showMessageDialog(this, "Course deleted successfully!");
            refreshTable(courseManager.getAll());
            clearFields();
        }
    }

    private void searchCourse() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            refreshTable(courseManager.getAll());
            return;
        }

        ArrayList<Course> all = courseManager.getAll();
        ArrayList<Course> filtered = new ArrayList<>();

        for (Course c : all) {
            if (c.getId().toLowerCase().contains(keyword) ||
                    c.getName().toLowerCase().contains(keyword)) {
                filtered.add(c);
            }
        }

        refreshTable(filtered);
    }
}
