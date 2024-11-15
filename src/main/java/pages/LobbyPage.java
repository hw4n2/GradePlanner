package pages;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LobbyPage extends JPanel {
    public LobbyPage() {
        setLayout(new BorderLayout(0, 10));
        setBorder(new EmptyBorder(10, 0, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout(0, 5));
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel infoPanel = new JPanel(new GridLayout(2, 3));
        JLabel halfline = new JLabel("---------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        JPanel graphPanel = new JPanel();
        btnPanel.setPreferredSize(new Dimension(0, 40));
        infoPanel.setPreferredSize(new Dimension(0, 60));
        halfline.setPreferredSize(new Dimension(0, 3));
        graphPanel.setPreferredSize(new Dimension(0, 100));

        btnPanel.setBackground(Color.RED);
        infoPanel.setBackground(Color.BLUE);
        halfline.setBackground(Color.YELLOW);
        graphPanel.setBackground(Color.GREEN);


        JButton usersBtn = new JButton("Users");
        JButton detailsBtn = new JButton("Details");
        btnPanel.add(usersBtn);
        btnPanel.add(detailsBtn);

        JLabel[] infoLabels = {new JLabel("Current Semester"), new JLabel("Major Grade"), new JLabel("Earned Credits"),
                new JLabel("2-2"), new JLabel("3.5/4.5"), new JLabel("18/65") };
        for(JLabel la : infoLabels) {
            infoPanel.add(la);
        }

        JLabel tmpGraph = new JLabel("Graph");
        graphPanel.add(tmpGraph);

        topPanel.add(btnPanel, BorderLayout.NORTH);
        topPanel.add(infoPanel, BorderLayout.CENTER);
        topPanel.add(halfline, BorderLayout.SOUTH);

       add(topPanel, BorderLayout.NORTH);
       add(graphPanel, BorderLayout.CENTER);
    }
}
