//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.*;
//import java.awt.image.BufferedImage;
//import javax.imageio.ImageIO;
//import javax.swing.border.Border;
//import javax.swing.border.EmptyBorder;
//import java.io.File;
//import java.io.IOException;
//
//public class MainWindow extends JFrame {
//    private Container frame;
//    private CardLayout basePanel;
//    private MainWindow(){
//        frame = getContentPane();
//        basePanel = new CardLayout();
//        setTitle("GradePlanner");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        frame.add(initLogin());
//
//        setSize(800, 500);
//        setLocationRelativeTo(null);
//        setVisible(true);
//    }
//    private JPanel initLogin(){
//        JPanel loginPanel = new JPanel();
//        loginPanel.setBackground(Color.WHITE);
//        loginPanel.setLayout(new BorderLayout(0, 20));
//
//        ImageIcon icon = new ImageIcon("src/main/resources/SymbolMark.png");
//        Image img = icon.getImage();
//        Image resized = img.getScaledInstance(191, 230, Image.SCALE_SMOOTH);
//        ImageIcon newIcon = new ImageIcon(resized);
//        JLabel logo = new JLabel(newIcon);
//        logo.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0));
//
//        JPanel loginInputPanel = new JPanel(new BorderLayout(20, 0));
//        loginInputPanel.setBorder(new EmptyBorder(10, 10, 15, 10));
//        loginInputPanel.setBackground(Color.CYAN);
//        loginInputPanel.setPreferredSize(new Dimension(500, 120));
//
//        JPanel inputComPanel = new JPanel(new GridLayout(2, 1, 0, 10));
//        inputComPanel.setBackground(Color.cyan);
//        JTextField loginIdInput = new JTextField("Student ID (12000000)");
//        JTextField loginNameInput = new JTextField("Name");
//        JButton loginBtn = new JButton("SignIn");
//        loginBtn.setPreferredSize(new Dimension(120, 120));
//        inputComPanel.add(loginIdInput);
//        inputComPanel.add(loginNameInput);
//
//        loginInputPanel.add(inputComPanel, BorderLayout.CENTER);
//        loginInputPanel.add(loginBtn, BorderLayout.EAST);
//
//        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        wrapper.setPreferredSize(new Dimension(500, 120));
//        wrapper.setBackground(Color.WHITE);
//        wrapper.add(loginInputPanel);
//
//        loginPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
//        loginPanel.add(logo, BorderLayout.CENTER);
//        loginPanel.add(wrapper, BorderLayout.SOUTH);
//        return loginPanel;
//    }
//    public static void main(String[] args) {
//        MainWindow window = new MainWindow();
//    }
//}
