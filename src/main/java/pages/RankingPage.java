package pages;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class RankingPage extends JPanel {
    private int usernum = 15;
    private JPanel[] userLabels = new JPanel[usernum];
    private String[] data = { "ID", "Name", "Year/Semester", "Grade" };

    public RankingPage() {
        setLayout(new BorderLayout(0, 10));
        setBorder(new EmptyBorder(10, 0, 0, 0));
        setBackground(Color.WHITE);
        JLabel pageTitle = new JLabel("Grade Ranking");
        pageTitle.setPreferredSize(new Dimension(0, 50));
        pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        pageTitle.setBackground(Color.BLUE);
        pageTitle.setOpaque(true);
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        labelPanel.setBackground(Color.WHITE);

        for(int i = 0; i < userLabels.length; i++) {
            userLabels[i] = new JPanel(new FlowLayout(FlowLayout.LEFT, 80, 0));
            userLabels[i].setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
            userLabels[i].add(new JLabel("[" + (i + 1) + "]"));
            for(int j = 0; j < data.length; j++) {
                JLabel label = new JLabel(data[j]);
                userLabels[i].add(label);
            }
            labelPanel.add(userLabels[i]);
            labelPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        }

        JScrollPane scrollPane = new JScrollPane(labelPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(pageTitle, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
