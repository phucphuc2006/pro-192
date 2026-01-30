package views.panels;

import managers.GradeManager;
import managers.StudentManager;
import managers.CourseManager;
import models.Grade;
import models.Student;
import models.Course;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Panel for Grade Management
 */
public class GradeManagementPanel extends JPanel {
    private GradeManager gradeManager;
    private StudentManager studentManager;
    private CourseManager courseManager;
    private DefaultTableModel tableModel;
    private JTable table;

    private JTextField txtId, txtMidterm, txtFinal;
    private JComboBox<String> cmbStudent, cmbCourse;
    private JLabel lblTotal;
    private JTextField txtSearch;

    private static final Color PRIMARY_DARK = new Color(26, 35, 75);
    private static final Color ACCENT_BLUE = new Color(66, 133, 244);

    public GradeManagementPanel(GradeManager gradeManager, StudentManager studentManager, CourseManager courseManager) {
        this.gradeManager = gradeManager;
        this.studentManager = studentManager;
        this.courseManager = courseManager;

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Title
        JLabel title = new JLabel("📝 Grade Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(PRIMARY_DARK);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        // Main content
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(300);
        splitPane.setDividerSize(5);

        JPanel inputPanel = createInputPanel();
        splitPane.setLeftComponent(inputPanel);

        JPanel tablePanel = createTablePanel();
        splitPane.setRightComponent(tablePanel);

        add(splitPane, BorderLayout.CENTER);

        refreshTable(gradeManager.getAll());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtId.setText((String) tableModel.getValueAt(row, 0));

                String studentId = (String) tableModel.getValueAt(row, 1);
                selectComboItem(cmbStudent, studentId);

                String courseId = (String) tableModel.getValueAt(row, 2);
                selectComboItem(cmbCourse, courseId);

                txtMidterm.setText(String.valueOf(tableModel.getValueAt(row, 3)));
                txtFinal.setText(String.valueOf(tableModel.getValueAt(row, 4)));
                lblTotal.setText("Total: " + tableModel.getValueAt(row, 5));
            }
        });
    }

    private void selectComboItem(JComboBox<String> combo, String id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).startsWith(id + " -")) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(248, 249, 250));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel lblForm = new JLabel("Grade Information");
        lblForm.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblForm.setForeground(PRIMARY_DARK);
        lblForm.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblForm);
        panel.add(Box.createVerticalStrut(15));

        panel.add(createFormField("Grade ID:", txtId = new JTextField()));
        panel.add(Box.createVerticalStrut(10));

        // Student combo
        panel.add(createComboField("Student:", cmbStudent = new JComboBox<>()));
        loadStudents();
        panel.add(Box.createVerticalStrut(10));

        // Course combo
        panel.add(createComboField("Course:", cmbCourse = new JComboBox<>()));
        loadCourses();
        panel.add(Box.createVerticalStrut(10));

        panel.add(createFormField("Midterm (40%):", txtMidterm = new JTextField()));
        panel.add(Box.createVerticalStrut(10));
        panel.add(createFormField("Final Exam (60%):", txtFinal = new JTextField()));
        panel.add(Box.createVerticalStrut(10));

        // Total display
        lblTotal = new JLabel("Total: --");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotal.setForeground(ACCENT_BLUE);
        lblTotal.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblTotal);

        // Calculate button
        JButton btnCalc = createButton("Calculate Total", new Color(255, 193, 7));
        btnCalc.setForeground(Color.BLACK);
        btnCalc.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnCalc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        btnCalc.addActionListener(e -> calculateTotal());
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnCalc);

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

        btnAdd.addActionListener(e -> addGrade());
        btnUpdate.addActionListener(e -> updateGrade());
        btnDelete.addActionListener(e -> deleteGrade());
        btnClear.addActionListener(e -> clearFields());

        return panel;
    }

    private void loadStudents() {
        cmbStudent.removeAllItems();
        cmbStudent.addItem("-- Select Student --");
        for (Student s : studentManager.getAll()) {
            cmbStudent.addItem(s.getId() + " - " + s.getName());
        }
    }

    private void loadCourses() {
        cmbCourse.removeAllItems();
        cmbCourse.addItem("-- Select Course --");
        for (Course c : courseManager.getAll()) {
            cmbCourse.addItem(c.getId() + " - " + c.getName());
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

        String[] columns = { "ID", "Student ID", "Course ID", "Midterm", "Final", "Total" };
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

        btnSearch.addActionListener(e -> searchGrade());
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            refreshTable(gradeManager.getAll());
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

    private JPanel createComboField(String label, JComboBox<String> combo) {
        JPanel panel = new JPanel(new BorderLayout(5, 2));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        combo.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(combo, BorderLayout.CENTER);

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

    private void calculateTotal() {
        try {
            double midterm = Double.parseDouble(txtMidterm.getText().trim());
            double finalExam = Double.parseDouble(txtFinal.getText().trim());
            double total = midterm * 0.4 + finalExam * 0.6;
            lblTotal.setText(String.format("Total: %.2f", total));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for scores!");
        }
    }

    private void refreshTable(ArrayList<Grade> grades) {
        tableModel.setRowCount(0);
        for (Grade g : grades) {
            tableModel.addRow(new Object[] {
                    g.getGradeID(), g.getStudentID(), g.getCourseID(),
                    g.getMidterm(), g.getFinalExam(),
                    String.format("%.2f", g.getTotal())
            });
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtMidterm.setText("");
        txtFinal.setText("");
        cmbStudent.setSelectedIndex(0);
        cmbCourse.setSelectedIndex(0);
        lblTotal.setText("Total: --");
        table.clearSelection();
    }

    private void addGrade() {
        String id = txtId.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Grade ID is required!");
            return;
        }

        if (gradeManager.findGradeById(id) != null) {
            JOptionPane.showMessageDialog(this, "Grade ID already exists!");
            return;
        }

        if (cmbStudent.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select a student!");
            return;
        }

        if (cmbCourse.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select a course!");
            return;
        }

        try {
            double midterm = Double.parseDouble(txtMidterm.getText().trim());
            double finalExam = Double.parseDouble(txtFinal.getText().trim());

            if (midterm < 0 || midterm > 10 || finalExam < 0 || finalExam > 10) {
                JOptionPane.showMessageDialog(this, "Scores must be between 0 and 10!");
                return;
            }

            String studentId = ((String) cmbStudent.getSelectedItem()).split(" - ")[0];
            String courseId = ((String) cmbCourse.getSelectedItem()).split(" - ")[0];

            Grade grade = new Grade(id, studentId, courseId, midterm, finalExam);
            gradeManager.addGrade(grade);
            gradeManager.saveToFile();

            JOptionPane.showMessageDialog(this, "Grade added successfully!");
            refreshTable(gradeManager.getAll());
            clearFields();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for scores!");
        }
    }

    private void updateGrade() {
        String id = txtId.getText().trim();

        if (gradeManager.findGradeById(id) == null) {
            JOptionPane.showMessageDialog(this, "Grade not found!");
            return;
        }

        try {
            double midterm = Double.parseDouble(txtMidterm.getText().trim());
            double finalExam = Double.parseDouble(txtFinal.getText().trim());

            if (midterm < 0 || midterm > 10 || finalExam < 0 || finalExam > 10) {
                JOptionPane.showMessageDialog(this, "Scores must be between 0 and 10!");
                return;
            }

            // Update using the existing method (only updates scores, not student/course)
            gradeManager.updateGrade(id, midterm, finalExam);
            gradeManager.saveToFile();

            JOptionPane.showMessageDialog(this, "Grade updated successfully!");
            refreshTable(gradeManager.getAll());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for scores!");
        }
    }

    private void deleteGrade() {
        String id = txtId.getText().trim();

        if (id.isEmpty()) {
            int row = table.getSelectedRow();
            if (row != -1) {
                id = (String) tableModel.getValueAt(row, 0);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a grade to delete!");
                return;
            }
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete grade: " + id + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            gradeManager.deleteGrade(id);
            gradeManager.saveToFile();
            JOptionPane.showMessageDialog(this, "Grade deleted successfully!");
            refreshTable(gradeManager.getAll());
            clearFields();
        }
    }

    private void searchGrade() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        if (keyword.isEmpty()) {
            refreshTable(gradeManager.getAll());
            return;
        }

        ArrayList<Grade> all = gradeManager.getAll();
        ArrayList<Grade> filtered = new ArrayList<>();

        for (Grade g : all) {
            if (g.getGradeID().toLowerCase().contains(keyword) ||
                    g.getStudentID().toLowerCase().contains(keyword) ||
                    g.getCourseID().toLowerCase().contains(keyword)) {
                filtered.add(g);
            }
        }

        refreshTable(filtered);
    }
}
