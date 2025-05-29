package food.donation.app;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class CreateAccountPage extends JFrame {
    private JTextField nameField, usernameField, emailField;
    private JPasswordField passwordField, confirmPasswordField;

    public CreateAccountPage() {
        setTitle("Create Account");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());

        // Header bar
        JPanel header = new JPanel();
        header.setBackground(new Color(255, 140, 0)); // Orange
        header.setLayout(new BorderLayout());

        JLabel title = new JLabel("  Register");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);

        JButton closeBtn = new JButton("X");
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFocusPainted(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setFont(new Font("Arial", Font.BOLD, 16));
        closeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> dispose());
        header.add(closeBtn, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(40, 55, 71));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = new JTextField(20);
        usernameField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);

        addLabeledField(formPanel, gbc, "Full Name:", nameField, 0);
        addLabeledField(formPanel, gbc, "Username:", usernameField, 1);
        addLabeledField(formPanel, gbc, "Email:", emailField, 2);
        addLabeledField(formPanel, gbc, "Password:", passwordField, 3);
        addLabeledField(formPanel, gbc, "Confirm Password:", confirmPasswordField, 4);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(40, 55, 71));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        JButton createButton = new JButton("Register");
        createButton.setBackground(new Color(30, 144, 255));
        createButton.setForeground(Color.WHITE);
        createButton.setFocusPainted(false);
        buttonPanel.add(createButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        JLabel loginLabel = new JLabel("click here to login");
        loginLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loginLabel.setForeground(Color.CYAN);
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                new LoginPage();
            }
        });

        gbc.gridy = 6;
        formPanel.add(loginLabel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button action
        createButton.addActionListener(e -> createAccount());
        setVisible(true);
    }

    private void addLabeledField(JPanel panel, GridBagConstraints gbc, String label, JComponent field, int y) {
        JLabel jLabel = new JLabel(label);
        jLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void createAccount() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(confirmPasswordField.getPassword());

        if (name.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Incomplete", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (FileWriter fw = new FileWriter("users.txt", true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(username + "," + password);
            bw.newLine();
            JOptionPane.showMessageDialog(this, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new LoginPage();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving user.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
