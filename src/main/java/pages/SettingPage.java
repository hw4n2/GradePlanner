package pages;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SettingPage extends JPanel {
    public SettingPage() {
        setLayout(new BorderLayout(0, 10));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel centerPanel = new JPanel(new FlowLayout());

        JPanel westPanel = new JPanel();
        JLabel halfline = new JLabel("line");
        JPanel settingPanel = new JPanel(new FlowLayout());
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        JButton getPhoto = new JButton("Change Photo");
        JPanel profilePanel = new JPanel(new BorderLayout(0, 10));

        westPanel.add(getPhoto, BorderLayout.NORTH);
        westPanel.add(profilePanel, BorderLayout.CENTER);



    }
}
