import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class MainWindow extends JFrame {
    private Container frame = null;
    private MainWindow(){
        frame = getContentPane();
        //frame.setLayout()
        setTitle("GradePlanner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initLogin();

        setSize(800, 500);
        setLocation(400, 150);
        setVisible(true);
    }
    private void initLogin(){
        //https://velog.io/@yu-jin-song/JAVA-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%83%9D%EC%84%B1-%EB%B0%8F-%ED%81%AC%EA%B8%B0-%EC%A1%B0%EC%A0%88
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("src/main/resources/SymbolMark.png");
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(191, 230, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(resized);
        JLabel logo = new JLabel(newIcon);
        logo.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0));

        JPanel loginInputPanel = new JPanel();
        loginInputPanel.setSize(400, 100);

        JLabel loginInputLabel = new JLabel();
        JTextField loginIdInput = new JTextField("Student ID (12000000)");
        JTextField loginNameInput = new JTextField("Name");
        JButton loginBtn = new JButton("SignIn");
        loginInputLabel.add(loginIdInput);
        loginInputLabel.add(loginNameInput);
        loginInputLabel.add(loginBtn);

        loginInputPanel.add(loginInputLabel);
        loginPanel.add(logo, BorderLayout.NORTH);
        loginPanel.add(loginInputPanel, BorderLayout.SOUTH);
        frame.add(loginPanel);
    }
    public static void main(String[] args) {
        MainWindow window = new MainWindow();
    }
}
