package views.panels;

import managers.ClassRoomManager;
import managers.CourseManager;
import managers.TeacherManager;
import models.ClassRoom;
import models.Course;
import models.Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel for ClassRoom Management
 */
public class ClassManagementPanel extends JPanel {
    private ClassRoomManager classRoomManager;
    private CourseManager courseManager;
    private TeacherManager teacherManager;
    private DefaultTableModel tableModel;
    private JTable table;

    // Form fields
    private JTextField txtClassID;
    private JTextField txtClassName;
    private JComboBox<String> comboTeacher;
    private JComboBox<String> comboCourse;
    private JTextField txtSearch;

    // Colors
    private static final Color PRIMARY_BLUE = new Color(30, 60, 114);
    private static final Color SUCCESS_GREEN = new Color(40, 167, 69);
    private static final Color DANGER_RED = new Color(220, 53, 69);
    private static final Color WARNING_YELLOW = new Color(255, 193, 7);
    private static final Color LIGHT_GRAY = new Color(245, 247, 250);

    public ClassManagementPanel(ClassRoomManager classRoomManager, CourseManager courseManager,
            TeacherManager teacherManager) {
        this.classRoomManager = classRoomManager;
        this.courseManager = courseManager;
        this.teacherManager = teacherManager;

        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel title = new JLabel("Class Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(PRIMARY_BLUE);
        add(title, BorderLayout.NORTH);

        // Main content
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(200);
        splitPane.setResizeWeight(0.3);

        // Input panel (top)
        txtClassID = new JTextField(15);
        txtClassName = new JTextField(15);
        comboTeacher = new JComboBox<>();
        comboCourse = new JComboBox<>();

        loadTeachers();
        loadCourses();

        JPanel inputPanel = createInputPanel();
        splitPane.setTopComponent(inputPanel);

        // Table panel (bottom)
        JPanel tablePanel = createTablePanel();
        splitPane.setBottomComponent(tablePanel);

        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(LIGHT_GRAY);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Row 0
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createFormField("Class ID:", txtClassID), gbc);

        gbc.gridx = 1;
        formPanel.add(createFormField("Class Name:", txtClassName), gbc);

        // Row 1
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(createComboField("Teacher:", comboTeacher), gbc);

        gbc.gridx = 1;
        formPanel.add(createComboField("Course:", comboCourse), gbc);

        panel.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        btnPanel.setBackground(LIGHT_GRAY);

        JButton btnAdd = createButton("Add", SUCCESS_GREEN);
        JButton btnUpdate = createButton("Update", PRIMARY_BLUE);
        JButton btnDelete = createButton("Delete", DANGER_RED);
        JButton btnClear = createButton("Clear", WARNING_YELLOW);

        btnAdd.addActionListener(e -> addClass());
        btnUpdate.addActionListener(e -> updateClass());
        btnDelete.addActionListener(e -> deleteClass());
        btnClear.addActionListener(e -> clearFields());

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        panel.add(btnPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadTeachers() {
        comboTeacher.removeAllItems();
        comboTeacher.addItem("-- Select Teacher --");
        for (Teacher teacher : teacherManager.getAll()) {
            comboTeacher.addItem(teacher.getId() + " - " + teacher.getName());
        }
    }

    private void loadCourses() {
        comboCourse.removeAllItems();
        comboCourse.addItem("-- Select Course --");
        for (Course course : courseManager.getAll()) {
            comboCourse.addItem(course.getId() + " - " + course.getName());
        }
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        searchPanel.setBackground(Color.WHITE);

        JLabel lblSearch = new JLabel("🔍 Search:");
        lblSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        txtSearch = new JTextField(20);
        JButton btnSearch = createButton("Search", PRIMARY_BLUE);
        btnSearch.addActionListener(e -> searchClass());

        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Table
        String[] columns = { "Class ID", "Class Name", "Teacher ID", "Teacher Name", "Course ID", "Course Name" };
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
        table.getTableHeader().setBackground(PRIMARY_BLUE);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Click to select
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtClassID.setText(tableModel.getValueAt(row, 0).toString());
                    txtClassName.setText(tableModel.getValueAt(row, 1).toString());
                    selectComboItem(comboTeacher, tableModel.getValueAt(row, 2).toString());
                    selectComboItem(comboCourse, tableModel.getValueAt(row, 4).toString());
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Load data
        refreshTable(classRoomManager.getAll());

        return panel;
    }

    private void selectComboItem(JComboBox<String> combo, String id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            String item = combo.getItemAt(i);
            if (item.startsWith(id + " -")) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }

    private JPanel createFormField(String label, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(LIGHT_GRAY);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setPreferredSize(new Dimension(100, 25));

        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(200, 30));

        panel.add(lbl, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createComboField(String label, JComboBox<String> combo) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(LIGHT_GRAY);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setPreferredSize(new Dimension(100, 25));

        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setPreferredSize(new Dimension(200, 30));

        panel.add(lbl, BorderLayout.WEST);
        panel.add(combo, BorderLayout.CENTER);

        return panel;
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(100, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void refreshTable(ArrayList<ClassRoom> classes) {
        tableModel.setRowCount(0);
        for (ClassRoom cls : classes) {
            Teacher teacher = teacherManager.findTeacherById(cls.getTeacherID());
            Course course = courseManager.findCourseById(cls.getCourseID());
            tableModel.addRow(new Object[] {
                    cls.getId(),
                    cls.getName(),
                    cls.getTeacherID(),
                    teacher != null ? teacher.getName() : "N/A",
                    cls.getCourseID(),
                    course != null ? course.getName() : "N/A"
            });
        }
    }

    private void clearFields() {
        txtClassID.setText("");
        txtClassName.setText("");
        comboTeacher.setSelectedIndex(0);
        comboCourse.setSelectedIndex(0);
        txtSearch.setText("");
        table.clearSelection();
    }

    private void addClass() {
        String classID = txtClassID.getText().trim();
        String className = txtClassName.getText().trim();
        String teacherSelection = (String) comboTeacher.getSelectedItem();
        String courseSelection = (String) comboCourse.getSelectedItem();

        if (classID.isEmpty() || className.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields!", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (teacherSelection == null || teacherSelection.startsWith("--")) {
            JOptionPane.showMessageDialog(this, "Please select a teacher!", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (courseSelection == null || courseSelection.startsWith("--")) {
            JOptionPane.showMessageDialog(this, "Please select a course!", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String teacherID = teacherSelection.split(" - ")[0];
        String courseID = courseSelection.split(" - ")[0];

        ClassRoom newClass = new ClassRoom(classID, className, teacherID, courseID);

        classRoomManager.addClassRoom(newClass);
        classRoomManager.saveToFile();
        refreshTable(classRoomManager.getAll());
        clearFields();
        JOptionPane.showMessageDialog(this, "Class added successfully!", "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateClass() {
        String classID = txtClassID.getText().trim();
        String className = txtClassName.getText().trim();
        String teacherSelection = (String) comboTeacher.getSelectedItem();
        String courseSelection = (String) comboCourse.getSelectedItem();

        if (classID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a class to update!", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String teacherID = teacherSelection.split(" - ")[0];
        String courseID = courseSelection.split(" - ")[0];

        classRoomManager.update(classID, className, teacherID, courseID);
        classRoomManager.saveToFile();
        refreshTable(classRoomManager.getAll());
        clearFields();
        JOptionPane.showMessageDialog(this, "Class updated successfully!", "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void deleteClass() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a class to delete!", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String classID = tableModel.getValueAt(selectedRow, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete class: " + classID + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            classRoomManager.deleteClassRoom(classID);
            classRoomManager.saveToFile();
            refreshTable(classRoomManager.getAll());
            clearFields();
            JOptionPane.showMessageDialog(this, "Class deleted successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void searchClass() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            refreshTable(classRoomManager.getAll());
            return;
        }

        ArrayList<ClassRoom> results = new ArrayList<>();
        for (ClassRoom cls : classRoomManager.getAll()) {
            if (cls.getId().toLowerCase().contains(keyword) ||
                    cls.getName().toLowerCase().contains(keyword)) {
                results.add(cls);
            }
        }

        refreshTable(results);

        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No classes found!", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
