package food.donation.app;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class AboutUsPanel extends JPanel {
    private Image backgroundImage;

    public AboutUsPanel() {
        // Load the background image (replace with your actual image path)
        try {
            backgroundImage = new ImageIcon("src/food/donation/app/icons/p3.jpeg").getImage();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not load background image", "Error", JOptionPane.ERROR_MESSAGE);
            backgroundImage = null;
        }

        setLayout(new BorderLayout(0, 20));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Make the panel transparent so background shows through
        setOpaque(false);

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false); // Transparent
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        JLabel titleLabel = new JLabel("About Our Organization", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 29));
        titleLabel.setForeground(new Color(255,255,255));
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        JLabel impactStatement = new JLabel(
            "<html><div style='text-align: center; color: #FFFFFF;'><b>Your support transforms lives and creates lasting change in communities around the world</b></div></html>", 
            JLabel.CENTER
        );
        impactStatement.setFont(new Font("Arial", Font.PLAIN, 16));
        impactStatement.setBorder(new EmptyBorder(10, 50, 20, 50));
        headerPanel.add(impactStatement, BorderLayout.SOUTH);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content panel - now with semi-transparent background
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Semi-transparent white background
                g.setColor(new Color(245, 249, 249, 200)); // Last parameter is alpha (transparency)
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false); // Transparent
        
        // Donation impact section
        JLabel impactTitle = new JLabel("Your Donation Makes a Difference");
        impactTitle.setFont(new Font("Arial", Font.BOLD, 20));
        impactTitle.setForeground(new Color(0, 107, 107));
        impactTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        impactTitle.setBorder(new EmptyBorder(20, 0, 10, 0));
        
        JTextArea impactText = new JTextArea(
            "When you donate to Food Donation App, you're not just giving food - " +
            "you're providing hope, opportunity, and the means for communities to thrive. " +
            "Your generosity enables us to:"
        );
        styleTextArea(impactText);
        impactText.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        contentPanel.add(impactTitle);
        contentPanel.add(impactText);
        contentPanel.add(Box.createVerticalStrut(10));
        
        // Impact items
        String[] impacts = {
            "Provide nutritious meals to families in need",
            "Reduce food waste in our community",
            "Support local food banks and shelters",
            "Create sustainable food distribution networks",
            "Educate communities about food security",
            "Respond quickly to emergency food needs"
        };
        
        JPanel impactItemsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Semi-transparent white background
                g.setColor(new Color(245, 249, 249, 200));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        impactItemsPanel.setLayout(new GridLayout(0, 2, 20, 20));
        impactItemsPanel.setOpaque(false);
        impactItemsPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        
        for (String impact : impacts) {
            JLabel item = new JLabel("<html><p style='color: #333333;'>• " + impact + "</p></html>");
            item.setFont(new Font("Arial", Font.PLAIN, 14));
            impactItemsPanel.add(item);
        }
        
        contentPanel.add(impactItemsPanel);
        
        // CTA Button
        JButton donateButton = new JButton("Donate Now");
        styleButton(donateButton);
        donateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        donateButton.setBorder(new EmptyBorder(10, 30, 10, 30));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Transparent
        buttonPanel.add(donateButton);
        
        add(contentPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Fallback if no image - solid color
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
    
    private void styleTextArea(JTextArea textArea) {
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setBorder(new EmptyBorder(0, 50, 0, 50));
    }
    
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 107, 107));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
    
    // For testing the panel standalone
    public static void main(String[] args) {
        JFrame frame = new JFrame("About Us");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        
        AboutUsPanel aboutUsPanel = new AboutUsPanel();
        frame.add(aboutUsPanel);
        
        frame.setVisible(true);
    }
}