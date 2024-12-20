package pages;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

import data.manager.*;
import data.models.*;

public class SettingPage extends JPanel {
    private JButton saveBtn;
    private JButton getPhoto;
    private JTextField nameInput;
    private JCheckBox nameCheck;
    private JCheckBox gradeCheck;
    private JCheckBox IdCheck;
    private JCheckBox semesterCheck;
    private JLabel photoLabel;
    private JLabel profileID;
    private JLabel profileName;

    private UserModel user;
    private UserManager userManager;

    public SettingPage(UserModel user, UserManager um, JList<String> btnList, JButton userBtn) {
        this.user = user;
        this.userManager = um;
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

        JPanel settingPanel = new JPanel(new GridLayout(7, 1, 10, 0));
        settingPanel.setBackground(Color.WHITE);

        ImageChooser chooser = new ImageChooser();
        getPhoto = new JButton("Change Photo");
        getPhoto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                chooser.choose();
            }
        });
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String status = "";
                status += (IdCheck.isSelected() ? "Y" : "N");
                status += (nameCheck.isSelected() ? "Y" : "N");
                status += (semesterCheck.isSelected() ? "Y" : "N");
                status += (gradeCheck.isSelected() ? "Y" : "N");

                if(user.getStatus().equals(status) && user.getName().equals(nameInput.getText()) && chooser.getFile() == null){
                    JOptionPane.showMessageDialog(null, "Nothing changed", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                userManager.saveSettings(user, new String[]{ status, nameInput.getText() }, chooser.getFile());
                setInfos();
                userBtn.setText("User " + user.getName());
            }
        });

        JPanel profilePanel = new JPanel(new BorderLayout(0, 10));
        profilePanel.setBackground(Color.WHITE);
        photoLabel = new JLabel();
        profileID = new JLabel("ID");
        profileName = new JLabel("Name");
        profileID.setBorder(new EmptyBorder(0, 0, 20, 0));
        JPanel infoWrapper = new JPanel(new GridLayout(2, 1, 0, 0));
        infoWrapper.setBorder(new EmptyBorder(0, 10, 30, 0));
        infoWrapper.setBackground(Color.WHITE);
        infoWrapper.add(profileID);
        infoWrapper.add(profileName);
        infoWrapper.setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK));
        profilePanel.add(photoLabel, BorderLayout.CENTER);
        profilePanel.add(infoWrapper, BorderLayout.SOUTH);

        profileWrapper.add(getPhoto, BorderLayout.NORTH);
        profileWrapper.add(profilePanel, BorderLayout.CENTER);
        profileWrapper.setBorder(new MatteBorder(2, 2, 2, 2, Color.GRAY));

        westPanel.add(profileWrapper, BorderLayout.CENTER);

        JLabel settingLabel = new JLabel("Settings");
        settingLabel.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        Font tmp = settingLabel.getFont().deriveFont(20f);
        settingLabel.setFont(tmp);

        JLabel explain = new JLabel("Check what you want to share");
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.setBackground(Color.WHITE);
        JLabel nameLabel = new JLabel("rename");
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
        settingPanel.add(explain);
        settingPanel.add(nameCheck);
        settingPanel.add(gradeCheck);
        settingPanel.add(IdCheck);
        settingPanel.add(semesterCheck);
        settingPanel.add(namePanel);

        centerPanel.add(westPanel, BorderLayout.WEST);
        centerPanel.add(settingPanel, BorderLayout.CENTER);

        add(btnPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        btnList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e){
                if(btnList.getSelectedValue() != null && btnList.getSelectedValue().equals("Settings")){
                    setInfos();
                }
            }
        });
    }
    public void setInfos(){
        profileID.setText("ID  " + user.getStudentID());
        profileName.setText("Name  " + user.getName());
        nameInput.setText(user.getName());

        ImageIcon photo = new ImageIcon("src/main/userdata/" + user.getStudentID() + "/userimage.png");
        ImageIcon resized = new ImageIcon(photo.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        photoLabel.setIcon(resized);
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setVerticalAlignment(SwingConstants.CENTER);

        String status = user.getStatus();
        nameCheck.setSelected(status.charAt(1) == 'Y');
        gradeCheck.setSelected(status.charAt(3) == 'Y');
        IdCheck.setSelected(status.charAt(0) == 'Y');
        semesterCheck.setSelected(status.charAt(2) == 'Y');
    }

    class ImageChooser extends JFileChooser {
        JFileChooser chooser;
        private File selected;

        ImageChooser(){
            selected = null;
            chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("images", "jpeg", "jpg", "png", "gif");
            chooser.setFileFilter(filter);
        }

        void choose(){
            int ret = chooser.showOpenDialog(null);
            if(ret == JFileChooser.APPROVE_OPTION) {
                selected = chooser.getSelectedFile();
                ImageIcon photo = new ImageIcon(selected.getPath());
                ImageIcon resized = new ImageIcon(photo.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
                photoLabel.setIcon(resized);
                photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                photoLabel.setVerticalAlignment(SwingConstants.CENTER);
            }
            else selected = null;
        }

        File getFile(){
            return selected;
        }
    }
}
