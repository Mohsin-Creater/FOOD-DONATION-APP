package food.donation.app;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class ContactUsPanel extends JPanel {
    private JLabel header;
    private Image backgroundImage;

    public ContactUsPanel() {
        // Load background image
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/food/donation/app/icons/p6.jpg")).getImage();
        } catch (Exception e) {
            System.out.println("Background image not found");
            backgroundImage = null;
        }

        setLayout(new BorderLayout());
        
        // Background panel with image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        
        // Header with color animation (no background)
       header = new JLabel("Get in Touch With Us", SwingConstants.CENTER);
header.setFont(new Font("Segoe UI", Font.BOLD, 34));
header.setForeground(Color.WHITE); // White text color
header.setBorder(new EmptyBorder(30, 0, 20, 0)); // Padding only
header.setOpaque(false); // Transparent background
backgroundPanel.add(header, BorderLayout.NORTH);

        // Main content
        JPanel content = new JPanel(new GridLayout(1, 2, 40, 0));
        content.setOpaque(false);
        content.setBorder(new EmptyBorder(10, 50, 50, 50));

        // Form Panel
        JPanel formPanel = createFormPanel();
        content.add(formPanel);

        // Info Panel
        JPanel infoPanel = createInfoPanel();
        content.add(infoPanel);

        backgroundPanel.add(content, BorderLayout.CENTER);
        add(backgroundPanel);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(new Color(248, 249, 250, 220));
        formPanel.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            new EmptyBorder(30, 30, 30, 30)
        ));

        JLabel formTitle = new JLabel("Send Us a Message");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        formTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Bold text fields
        JTextField nameField = createBoldTextField("Your Name");
        JTextField emailField = createBoldTextField("Email");
        JTextArea messageArea = createBoldTextArea("Message");

        JButton sendBtn = new JButton("Send Message");
        styleButton(sendBtn);
        sendBtn.addActionListener(e -> handleForm(nameField, emailField, messageArea));

        formPanel.add(formTitle);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        formPanel.add(nameField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(emailField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        formPanel.add(new JScrollPane(messageArea));
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        formPanel.add(sendBtn);

        return formPanel;
    }

    private JPanel createInfoPanel() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(245, 245, 245, 220));
        infoPanel.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            new EmptyBorder(40, 30, 40, 30)
        ));

        ImageIcon icon = new ImageIcon(getClass().getResource("/food/donation/app/icons/p4.jpg"));
        Image scaled = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(scaled));
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel contactTitle = new JLabel("Contact Details");
        contactTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        contactTitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        contactTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel phone = createInfoLabel("Phone : +92 34326 76142");
        JLabel email = createInfoLabel("Email : support@helpinghands.org");
        JLabel address = createInfoLabel("Location : PECHS Karachi, Pakistan");

        infoPanel.add(imgLabel);
        infoPanel.add(contactTitle);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(phone);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(email);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(address);

        return infoPanel;
    }

    private JTextField createBoldTextField(String title) {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.BOLD, 18)); // Bold text
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        tf.setBorder(createTitledBorder(title));
        return tf;
    }

    private JTextArea createBoldTextArea(String title) {
        JTextArea ta = new JTextArea(5, 20);
        ta.setFont(new Font("Segoe UI", Font.BOLD, 14)); // Bold text
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBorder(createTitledBorder(title));
        return ta;
    }

    private Border createTitledBorder(String title) {
        return BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(150, 150, 150)),
            title,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), // Bold title
            new Color(70, 70, 70)
        );
    }

    private void animateHeaderColor() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    header.setForeground(colors[colorIndex]);
                    colorIndex = (colorIndex + 1) % colors.length;
                });
            }
        }, 0, 1500);
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(new Color(0, 107, 107));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(160, 40));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private JLabel createInfoLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private void handleForm(JTextField name, JTextField email, JTextArea message) {
        if (name.getText().isEmpty() || email.getText().isEmpty() || message.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!email.getText().matches("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
            "<html><div style='text-align:center;'><b>Thank you!</b><br>Your message has been sent.</div></html>",
            "Success", JOptionPane.INFORMATION_MESSAGE);

        name.setText("");
        email.setText("");
        message.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Contact Us - Food Donation App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 600);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new ContactUsPanel());
            frame.setVisible(true);
        });
    }
}