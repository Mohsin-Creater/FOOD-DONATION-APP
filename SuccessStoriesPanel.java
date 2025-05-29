package food.donation.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.InputStream;

public class SuccessStoriesPanel extends JPanel {
    private final Story[] stories = {
        new Story("Community Kitchen Serves 10,000 Meals", 
                 "A local community kitchen in Chicago partnered with food banks to serve over 10,000 meals to homeless individuals during winter months. The initiative brought together volunteers from all walks of life to prepare and distribute nutritious meals to those in need.",
                 "/food/donation/app/icons/p7.jpeg"),
        new Story("Student Initiative Reduces Campus Waste", 
                 "University students created a program that redirects unused cafeteria food to shelters, serving 500 meals weekly. What started as a class project has now become a permanent program, reducing food waste while helping the local community.",
                 "/food/donation/app/icons/p2.jpg"),
        new Story("Restaurant Chain's Zero-Waste Program", 
                 "A national restaurant chain now donates all unused food, serving 1 million meals annually to food-insecure families. Their innovative packaging system keeps food fresh during transportation to distribution centers.",
                 "/food/donation/app/icons/p3.jpeg"),
        new Story("Farm-to-Shelter Initiative", 
                 "Local farmers donate surplus produce, providing fresh vegetables to 50 shelters in the region. This program has not only reduced food waste but also improved nutrition for shelter residents.",
                 "/food/donation/app/icons/p4.jpg"),
        new Story("Corporate Partnership Feeds Thousands", 
                 "Tech company employees volunteer to pack and distribute meals, serving 25,000 children in school programs. The company matches employee volunteer hours with monetary donations to local food banks.",
                 "/food/donation/app/icons/p5.jpg"),
        new Story("Mobile Food Pantry Expands Reach", 
                 "A converted bus now delivers food to rural areas, reaching 15 new communities each month. The mobile pantry has been particularly impactful for elderly residents who have difficulty traveling to distribution centers.",
                 "/food/donation/app/icons/p6.jpg")
    };

    public SuccessStoriesPanel() {
        setLayout(new GridLayout(2, 3, 20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(240, 240, 240));

        for (int i = 0; i < stories.length; i++) {
            StoryCard card = new StoryCard(stories[i]);
            int index = i;
            card.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    showFullStory(index);
                }
            });
            add(card);
        }
    }

    private void showFullStory(int index) {
        Story story = stories[index];
        
        // Create a new frame for the full story
        JFrame storyFrame = new JFrame(story.title);
        storyFrame.setSize(800, 600);
        storyFrame.setLocationRelativeTo(null);
        storyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel title = new JLabel(story.title, SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Image
        JPanel imagePanel = new JPanel(new BorderLayout());
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        try (InputStream is = getClass().getResourceAsStream(story.imageUrl)) {
            if (is != null) {
                Image image = ImageIO.read(is);
                image = image.getScaledInstance(700, 350, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(image));
            } else {
                throw new Exception("Image not found");
            }
        } catch (Exception e) {
            imageLabel.setText("Image not available");
            imageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        }
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        
        // Description
        JTextArea description = new JTextArea(story.description);
        description.setWrapStyleWord(true);
        description.setLineWrap(true);
        description.setEditable(false);
        description.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        description.setBackground(new Color(240, 240, 240));
        description.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(imagePanel, BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(description), BorderLayout.SOUTH);
        
        storyFrame.add(mainPanel);
        storyFrame.setVisible(true);
    }

    private class StoryCard extends JPanel {
        public StoryCard(Story story) {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            setBackground(Color.WHITE);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(250, 200));
            
            JLabel title = new JLabel("<html><center>" + story.title + "</center></html>");
            title.setFont(new Font("Segoe UI", Font.BOLD, 14));
            title.setHorizontalAlignment(SwingConstants.CENTER);
            
            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            try (InputStream is = getClass().getResourceAsStream(story.imageUrl)) {
                if (is != null) {
                    Image image = ImageIO.read(is);
                    image = image.getScaledInstance(200, 120, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(image));
                } else {
                    throw new Exception("Image not found");
                }
            } catch (Exception e) {
                imageLabel.setText("Click to view story");
                imageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            }
            
            add(title, BorderLayout.NORTH);
            add(imageLabel, BorderLayout.CENTER);
        }
    }

    private static class Story {
        String title;
        String description;
        String imageUrl;
        
        public Story(String title, String description, String imageUrl) {
            this.title = title;
            this.description = description;
            this.imageUrl = imageUrl;
        }
    }
}