package food.donation.app;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.*;
import java.util.ArrayList;

import food.donation.app.Donation;  // Import your central Donation class

public class DonatePanel extends JPanel {
    private JTextField donorNameField, phoneField, quantityField, locationField;
    private JComboBox<String> foodTypeCombo, cookedCombo, tinnedCombo, freshCombo;
    private JTextArea additionalNotesArea;
    private String currentUser;

    private static final String DONATION_FILE = "donations.dat";
    private ArrayList<Donation> donations;
    private BufferedImage blurredBackground;

    public DonatePanel(String currentUser) {
        this.currentUser = currentUser;

        backupAndDeleteOldDonationFile();  // Backup and remove old file to avoid ClassNotFoundException

        this.donations = loadDonations();
        setLayout(new GridBagLayout());

        try {
            BufferedImage original = ImageIO.read(new File("src/food/donation/app/icons/p3.jpeg"));
            blurredBackground = blurImage(original);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setOpaque(false);

        JPanel glassPanel = new JPanel(new BorderLayout());
        glassPanel.setBackground(new Color(255, 255, 255, 180));
        glassPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        glassPanel.setOpaque(true);

        JLabel title = new JLabel("Donate Food");
        title.setFont(new Font("Segoe UI", Font.BOLD, 30));
        title.setForeground(new Color(40, 120, 90));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        glassPanel.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        donorNameField = createTextField();
        phoneField = createTextField();
        quantityField = createTextField();
        locationField = createTextField();

        foodTypeCombo = createComboBox(new String[]{"Select", "Cooked", "Tinned", "Fresh"});
        cookedCombo = createComboBox(new String[]{"Select", "Rice & Curry", "Pasta", "Grilled Chicken", "Vegetable Stir Fry", "Soups & Stews"});
        tinnedCombo = createComboBox(new String[]{"Select", "Beans", "Corn", "Tuna", "Soup Cans", "Fruit Cans", "Tomato Sauce"});
        freshCombo = createComboBox(new String[]{"Select", "Vegetables", "Fruits", "Dairy Products", "Bread", "Eggs", "Fresh Juice"});

        cookedCombo.setEnabled(false);
        tinnedCombo.setEnabled(false);
        freshCombo.setEnabled(false);

        foodTypeCombo.addActionListener(e -> {
            cookedCombo.setEnabled(false);
            tinnedCombo.setEnabled(false);
            freshCombo.setEnabled(false);
            switch ((String) foodTypeCombo.getSelectedItem()) {
                case "Cooked" -> cookedCombo.setEnabled(true);
                case "Tinned" -> tinnedCombo.setEnabled(true);
                case "Fresh" -> freshCombo.setEnabled(true);
            }
        });

        additionalNotesArea = createTextArea();

        int row = 0;
        gbc.gridx = 0;
        gbc.gridy = row;
        formPanel.add(createLabel("Donor Name:"), gbc);
        gbc.gridx = 2;
        formPanel.add(donorNameField, gbc);

        row++;
        gbc.gridy = row; gbc.gridx = 0;
        formPanel.add(createLabel("Phone Number:"), gbc);
        gbc.gridx = 2;
        formPanel.add(phoneField, gbc);

        row++;
        gbc.gridy = row; gbc.gridx = 0;
        formPanel.add(createLabel("Food Type:"), gbc);
        gbc.gridx = 2;
        formPanel.add(foodTypeCombo, gbc);

        row++;
        gbc.gridy = row; gbc.gridx = 0;
        formPanel.add(createLabel("Cooked Options:"), gbc);
        gbc.gridx = 2;
        formPanel.add(cookedCombo, gbc);

        row++;
        gbc.gridy = row; gbc.gridx = 0;
        formPanel.add(createLabel("Tinned Options:"), gbc);
        gbc.gridx = 2;
        formPanel.add(tinnedCombo, gbc);

        row++;
        gbc.gridy = row; gbc.gridx = 0;
        formPanel.add(createLabel("Fresh Options:"), gbc);
        gbc.gridx = 2;
        formPanel.add(freshCombo, gbc);

        row++;
        gbc.gridy = row; gbc.gridx = 0;
        formPanel.add(createLabel("Quantity (e.g., 5 kg):"), gbc);
        gbc.gridx = 2;
        formPanel.add(quantityField, gbc);

        row++;
        gbc.gridy = row; gbc.gridx = 0;
        formPanel.add(createLabel("Pickup Location:"), gbc);
        gbc.gridx = 2;
        formPanel.add(locationField, gbc);

        row++;
        gbc.gridy = row; gbc.gridx = 0;
        formPanel.add(createLabel("Additional Notes:"), gbc);
        gbc.gridx = 2;
        formPanel.add(new JScrollPane(additionalNotesArea), gbc);

        row++;
        gbc.gridx = 1; gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton submitBtn = new JButton("Submit Donation");
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        submitBtn.setBackground(new Color(60, 179, 113));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitBtn.addActionListener(e -> submitDonation());
        formPanel.add(submitBtn, gbc);

        glassPanel.add(formPanel, BorderLayout.CENTER);
        add(glassPanel);
    }

    private void backupAndDeleteOldDonationFile() {
        File file = new File(DONATION_FILE);
        if (file.exists()) {
            File backupFile = new File("donations_backup_" + System.currentTimeMillis() + ".dat");
            boolean renamed = file.renameTo(backupFile);
            if (renamed) {
                System.out.println("Old donations.dat backed up as " + backupFile.getName());
            } else {
                System.err.println("Failed to back up old donations.dat, deleting instead.");
                if (!file.delete()) {
                    System.err.println("Failed to delete old donations.dat");
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (blurredBackground != null) {
            g.drawImage(blurredBackground.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, null);
        }
    }

    private BufferedImage blurImage(BufferedImage image) {
        float[] matrix = new float[49];
        for (int i = 0; i < 49; i++) matrix[i] = 1.0f / 49.0f;
        BufferedImage blurred = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        new ConvolveOp(new Kernel(7, 7, matrix), ConvolveOp.EDGE_NO_OP, null).filter(image, blurred);
        return blurred;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(new Color(20, 60, 40));
        return label;
    }

    private JTextField createTextField() {
        JTextField tf = new JTextField(25);
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tf.setBackground(new Color(255, 255, 255, 200));
        tf.setBorder(createFocusBorder(false));
        tf.setCaretColor(new Color(60, 179, 113));
        tf.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { tf.setBorder(createFocusBorder(true)); }
            public void focusLost(FocusEvent e) { tf.setBorder(createFocusBorder(false)); }
        });
        return tf;
    }

    private JTextArea createTextArea() {
        JTextArea ta = new JTextArea(4, 30);
        ta.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBackground(new Color(255, 255, 255, 200));
        ta.setBorder(createFocusBorder(false));
        ta.setCaretColor(new Color(60, 179, 113));
        ta.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { ta.setBorder(createFocusBorder(true)); }
            public void focusLost(FocusEvent e) { ta.setBorder(createFocusBorder(false)); }
        });
        return ta;
    }

    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        cb.setBackground(new Color(255, 255, 255, 200));
        cb.setBorder(createFocusBorder(false));
        return cb;
    }

    private Border createFocusBorder(boolean focused) {
        Color color = focused ? new Color(32, 178, 170) : new Color(60, 179, 113);
        return BorderFactory.createLineBorder(color, focused ? 3 : 2);
    }

    private void submitDonation() {
        String donorName = donorNameField.getText().trim();
        String phone = phoneField.getText().trim();
        String foodType = (String) foodTypeCombo.getSelectedItem();
        String cooked = (String) cookedCombo.getSelectedItem();
        String tinned = (String) tinnedCombo.getSelectedItem();
        String fresh = (String) freshCombo.getSelectedItem();
        String quantity = quantityField.getText().trim();
        String location = locationField.getText().trim();
        String notes = additionalNotesArea.getText().trim();

        if (donorName.isEmpty() || phone.isEmpty() || "Select".equals(foodType) || quantity.isEmpty() || location.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String specificFood = switch (foodType) {
            case "Cooked" -> !cooked.equals("Select") ? cooked : "";
            case "Tinned" -> !tinned.equals("Select") ? tinned : "";
            case "Fresh" -> !fresh.equals("Select") ? fresh : "";
            default -> "";
        };

        Donation donation = new Donation(currentUser, donorName, phone, foodType, specificFood, quantity, location, notes);
        donations.add(donation);

        if (saveDonations()) {
            JOptionPane.showMessageDialog(this, "Donation submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to save donation.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        donorNameField.setText("");
        phoneField.setText("");
        foodTypeCombo.setSelectedIndex(0);
        cookedCombo.setSelectedIndex(0);
        cookedCombo.setEnabled(false);
        tinnedCombo.setSelectedIndex(0);
        tinnedCombo.setEnabled(false);
        freshCombo.setSelectedIndex(0);
        freshCombo.setEnabled(false);
        quantityField.setText("");
        locationField.setText("");
        additionalNotesArea.setText("");
    }

    private ArrayList<Donation> loadDonations() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DONATION_FILE))) {
            return (ArrayList<Donation>) ois.readObject();
        } catch (Exception e) {
            // File not found or other error, start fresh
            return new ArrayList<>();
        }
    }

    private boolean saveDonations() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DONATION_FILE))) {
            oos.writeObject(donations);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
