package food.donation.app;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {
    private UserDatabase userDatabase;
    private String currentUser;

    public Dashboard(UserDatabase userDatabase, String username) {
        this.userDatabase = userDatabase;
        this.currentUser = username;

        setTitle("Food Donation Dashboard");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header with gradient background
        JPanel header = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(60, 179, 113), 0, getHeight(), new Color(32, 178, 170));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        header.setPreferredSize(new Dimension(0, 80));
        JLabel title = new JLabel("Welcome, " + currentUser);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setLayout(new GridBagLayout());
        header.add(title);
        add(header, BorderLayout.NORTH);

        // Side navigation panel with 8 buttons
        JPanel navPanel = new JPanel();
        navPanel.setBackground(new Color(245, 245, 245));
        navPanel.setLayout(new GridLayout(8, 1, 0, 10));
        navPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JButton homeBtn = createNavButton("Home", new Color(105, 105, 105));
        JButton donateBtn = createNavButton("Donate Food", new Color(60, 179, 113));
        JButton viewBtn = createNavButton("View My Donations", new Color(32, 178, 170));
        JButton mapBtn = createNavButton("Food Map", new Color(70, 130, 180));
        JButton aboutBtn = createNavButton("About Us", new Color(100, 149, 237));
        JButton contactBtn = createNavButton("Contact Us", new Color(255, 165, 0));
        JButton storiesBtn = createNavButton("Success Stories", new Color(138, 43, 226));
        JButton logoutBtn = createNavButton("Logout", new Color(220, 20, 60));

        navPanel.add(homeBtn);
        navPanel.add(donateBtn);
        navPanel.add(viewBtn);
        navPanel.add(mapBtn);
        navPanel.add(aboutBtn);
        navPanel.add(contactBtn);
        navPanel.add(storiesBtn);
        navPanel.add(logoutBtn);

        add(navPanel, BorderLayout.WEST);

        // Content panel with CardLayout
        JPanel contentPanel = new JPanel(new CardLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Wallpaper panel with Background.jpg wallpaper image
        JPanel wallpaperPanel = new JPanel() {
            private Image scaledBg;

            {
                // Load and scale the background image only once
                ImageIcon bgIcon = new ImageIcon(getClass().getResource("/food/donation/app/icons/p.jpg"));
                Image original = bgIcon.getImage();
                scaledBg = original.getScaledInstance(900, 650, Image.SCALE_FAST);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(scaledBg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        DonatePanel donatePanel = new DonatePanel(currentUser);
        ViewDonationsPanel viewDonationsPanel = new ViewDonationsPanel(currentUser);
        MapPanel mapPanel = new MapPanel();
        AboutUsPanel aboutUsPanel = new AboutUsPanel();
        ContactUsPanel contactUsPanel = new ContactUsPanel();
        SuccessStoriesPanel successStoriesPanel = new SuccessStoriesPanel();

        contentPanel.add(wallpaperPanel, "blank");
        contentPanel.add(donatePanel, "donate");
        contentPanel.add(viewDonationsPanel, "view");
        contentPanel.add(mapPanel, "map");
        contentPanel.add(aboutUsPanel, "AboutUs");
        contentPanel.add(contactUsPanel, "ContactUs");
        contentPanel.add(successStoriesPanel, "SuccessStories");

        add(contentPanel, BorderLayout.CENTER);

        // Button listeners to switch panels
        homeBtn.addActionListener(e -> switchPanel(contentPanel, "blank"));
        donateBtn.addActionListener(e -> switchPanel(contentPanel, "donate"));
        viewBtn.addActionListener(e -> {
            viewDonationsPanel.refreshTable();
            switchPanel(contentPanel, "view");
        });
        mapBtn.addActionListener(e -> switchPanel(contentPanel, "map"));
        aboutBtn.addActionListener(e -> switchPanel(contentPanel, "AboutUs"));
        contactBtn.addActionListener(e -> switchPanel(contentPanel, "ContactUs"));
        storiesBtn.addActionListener(e -> switchPanel(contentPanel, "SuccessStories"));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginPage();  // Replace with your login screen class
        });

        switchPanel(contentPanel, "blank");
        setVisible(true);
    }

    private JButton createNavButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void switchPanel(JPanel contentPanel, String name) {
        CardLayout cl = (CardLayout) contentPanel.getLayout();
        cl.show(contentPanel, name);
    }
}