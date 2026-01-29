package views;

import managers.StudentManager;
import models.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StudentManagementUI extends JFrame {
    private StudentManager studentManager;
    private DefaultTableModel tableModel;
    private JTable table;

    // Input fields
    private JTextField txtId, txtName, txtDob, txtEmail, txtPhone, txtClassId;
    private JComboBox<String> cbGender;
    private JTextField txtSearch;

    public StudentManagementUI(StudentManager manager) {
        // Initialize Manager (Passed from Main)
        this.studentManager = manager;
        // studentManager.loadFromFile(); // Data already loaded by Main

        setTitle("Student Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- Left Panel: Input Form ---
        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Student Information"));
        inputPanel.setPreferredSize(new Dimension(300, 0));

        inputPanel.add(new JLabel("Student ID:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Full Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Date of Birth (dd/mm/yyyy):"));
        txtDob = new JTextField();
        inputPanel.add(txtDob);

        inputPanel.add(new JLabel("Gender:"));
        cbGender = new JComboBox<>(new String[] { "Nam", "Nu", "Khac" });
        inputPanel.add(cbGender);

        inputPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        inputPanel.add(txtEmail);

        inputPanel.add(new JLabel("Phone:"));
        txtPhone = new JTextField();
        inputPanel.add(txtPhone);

        inputPanel.add(new JLabel("Class ID:"));
        txtClassId = new JTextField();
        inputPanel.add(txtClassId);

        // Buttons Panel inside Input Panel
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        // --- Center Panel: Table and Search ---
        JPanel centerPanel = new JPanel(new BorderLayout());

        // Search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtSearch = new JTextField(20);
        JButton btnSearch = new JButton("Search by Name");
        JButton btnRefresh = new JButton("Refresh / Show All");
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);

        // Table
        String[] columnNames = { "ID", "Name", "DOB", "Gender", "Email", "Phone", "Class ID" };
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Add to Frame
        add(inputPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // --- Load Data ---
        refreshTable(studentManager.getAll());

        // --- Event Listeners (Calling Existing Functions) ---

        // 1. Add Student
        btnAdd.addActionListener(e -> {
            String id = txtId.getText().trim();
            String name = txtName.getText().trim();
            String dob = txtDob.getText().trim();
            String gender = (String) cbGender.getSelectedItem();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String classId = txtClassId.getText().trim();

            // Basic Validation before calling Manager
            if (id.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID and Name are required!");
                return;
            }

            // Check existence using existing method
            if (studentManager.findById(id) != null) {
                JOptionPane.showMessageDialog(this, "Student ID already exists!");
                return;
            }

            // Create Model and Call Manager
            Student newStudent = new Student(id, name, dob, gender, email, phone, classId);
            studentManager.addStudent(newStudent); // CALL EXISTING FUNCTION
            studentManager.saveToFile(); // CALL EXISTING FUNCTION

            JOptionPane.showMessageDialog(this, "Added successfully!");
            refreshTable(studentManager.getAll());
            clearFields();
        });

        // 2. Update Student
        btnUpdate.addActionListener(e -> {
            String id = txtId.getText().trim();

            // Check existence using existing method
            if (studentManager.findById(id) == null) {
                JOptionPane.showMessageDialog(this, "Student not found to update!");
                return;
            }

            String name = txtName.getText().trim();
            String dob = txtDob.getText().trim();
            String gender = (String) cbGender.getSelectedItem();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String classId = txtClassId.getText().trim();

            // CALL EXISTING FUNCTION
            studentManager.updateStudent(id, name, dob, gender, email, phone, classId);
            studentManager.saveToFile(); // CALL EXISTING FUNCTION

            JOptionPane.showMessageDialog(this, "Updated successfully!");
            refreshTable(studentManager.getAll());
        });

        // 3. Delete Student
        btnDelete.addActionListener(e -> {
            String id = txtId.getText().trim();
            if (id.isEmpty()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    id = (String) tableModel.getValueAt(selectedRow, 0);
                    txtId.setText(id); // Sync to field
                } else {
                    JOptionPane.showMessageDialog(this, "Please enter ID or select a row!");
                    return;
                }
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete ID: " + id + "?");
            if (confirm == JOptionPane.YES_OPTION) {
                // CALL EXISTING FUNCTION
                studentManager.deleteStudent(id);
                studentManager.saveToFile(); // CALL EXISTING FUNCTION

                JOptionPane.showMessageDialog(this, "Deleted successfully!");
                refreshTable(studentManager.getAll());
                clearFields();
            }
        });

        // 4. Search
        btnSearch.addActionListener(e -> {
            String keyword = txtSearch.getText().trim().toLowerCase();
            if (keyword.isEmpty()) {
                refreshTable(studentManager.getAll());
                return;
            }
            // Logic: Filter client-side or use manager (Manager only prints, so we filter
            // list here using getAll)
            ArrayList<Student> all = studentManager.getAll(); // CALL EXISTING FUNCTION
            ArrayList<Student> filtered = new ArrayList<>();
            for (Student s : all) {
                if (s.getName().toLowerCase().contains(keyword) || s.getId().toLowerCase().contains(keyword)) {
                    filtered.add(s);
                }
            }
            refreshTable(filtered);
        });

        // 5. Refresh
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            refreshTable(studentManager.getAll());
        });

        // 6. Clear Fields
        btnClear.addActionListener(e -> clearFields());

        // Table Selection Listener
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtId.setText((String) tableModel.getValueAt(row, 0));
                txtName.setText((String) tableModel.getValueAt(row, 1));
                txtDob.setText((String) tableModel.getValueAt(row, 2));
                cbGender.setSelectedItem(tableModel.getValueAt(row, 3));
                txtEmail.setText((String) tableModel.getValueAt(row, 4));
                txtPhone.setText((String) tableModel.getValueAt(row, 5));
                txtClassId.setText((String) tableModel.getValueAt(row, 6));
            }
        });
    }

    // Helper: Refresh Table
    private void refreshTable(List<Student> students) {
        tableModel.setRowCount(0); // Clear table
        for (Student s : students) {
            tableModel.addRow(new Object[] {
                    s.getId(), s.getName(), s.getDob(), s.getGender(),
                    s.getEmail(), s.getPhone(), s.getClassID()
            });
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtDob.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtClassId.setText("");
        table.clearSelection();
    }

    public static void main(String[] args) {
        // Run UI
        SwingUtilities.invokeLater(() -> {
            StudentManager sm = new StudentManager();
            sm.loadFromFile();
            new StudentManagementUI(sm).setVisible(true);
        });
    }
}
