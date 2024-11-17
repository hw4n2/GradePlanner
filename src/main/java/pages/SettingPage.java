package pages;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class SettingPage extends JPanel {
    private JButton saveBtn;
    private JButton getPhoto;
    private JTextField nameInput;
    private JCheckBox nameCheck;
    private JCheckBox gradeCheck;
    private JCheckBox IdCheck;
    private JCheckBox semesterCheck;
    private ImageIcon photo;
    private JLabel profileID;
    private JLabel profileName;

    public SettingPage() {
        setLayout(new BorderLayout(0, 10));
        setBackground(Color.WHITE);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(Color.WHITE);
        JPanel centerPanel = new JPanel(new BorderLayout(10, 0));
        centerPanel.setBorder(new EmptyBorder(0, 0, 0, 50));
        centerPanel.setBackground(Color.WHITE);

        saveBtn = new JButton("Save");
        btnPanel.add(saveBtn);

        JPanel westPanel = new JPanel(new BorderLayout(10, 0));
        JPanel profileWrapper = new JPanel(new BorderLayout(0, 0));
        profileWrapper.setBorder(new EmptyBorder(0, 0, 0, 10));
        profileWrapper.setBackground(Color.WHITE);
        westPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        westPanel.setPreferredSize(new Dimension(200, 0));
        westPanel.setBackground(Color.WHITE);

        JPanel settingPanel = new JPanel(new GridLayout(6, 1, 20, 0));
        settingPanel.setBackground(Color.WHITE);

        getPhoto = new JButton("Change Photo");
        JPanel profilePanel = new JPanel(new BorderLayout(0, 10));
        profilePanel.setBackground(Color.WHITE);
        photo = new ImageIcon("src/main/resources/SymbolMark.jpg");
        ImageIcon resized = new ImageIcon(photo.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        JLabel photoLabel = new JLabel(resized);
        photoLabel.setBorder(new MatteBorder(2, 2, 2, 2, Color.GRAY));
        profileID = new JLabel("ID");
        profileName = new JLabel("Name");
        profileID.setBorder(new EmptyBorder(0, 0, 20, 0));
        JPanel infoWrapper = new JPanel(new GridLayout(2, 1, 0, 0));
        infoWrapper.setBorder(new EmptyBorder(0, 10, 30, 0));
        infoWrapper.setBackground(Color.WHITE);
        infoWrapper.add(profileID);
        infoWrapper.add(profileName);
        profilePanel.add(photoLabel, BorderLayout.CENTER);
        profilePanel.add(infoWrapper, BorderLayout.SOUTH);

        profileWrapper.add(getPhoto, BorderLayout.NORTH);
        profileWrapper.add(profilePanel, BorderLayout.CENTER);

        westPanel.add(profileWrapper, BorderLayout.CENTER);

        JLabel settingLabel = new JLabel("Check what you want to share");
        settingLabel.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));


        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.setBackground(Color.WHITE);
        JLabel nameLabel = new JLabel("Name");
        namePanel.add(nameLabel);
        nameInput = new JTextField(10);
        nameCheck = new JCheckBox("Name");
        gradeCheck = new JCheckBox("Grade");
        IdCheck = new JCheckBox("Student ID");
        semesterCheck = new JCheckBox("Year/Semester");
        nameCheck.setBackground(Color.WHITE);
        gradeCheck.setBackground(Color.WHITE);
        IdCheck.setBackground(Color.WHITE);
        semesterCheck.setBackground(Color.WHITE);

        namePanel.add(nameInput);
        settingPanel.add(settingLabel);
        settingPanel.add(namePanel);
        settingPanel.add(nameCheck);
        settingPanel.add(gradeCheck);
        settingPanel.add(IdCheck);
        settingPanel.add(semesterCheck);

        centerPanel.add(westPanel, BorderLayout.WEST);
        centerPanel.add(settingPanel, BorderLayout.CENTER);

        add(btnPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }
}
