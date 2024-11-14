import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
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
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void initLogin(){
        //https://velog.io/@yu-jin-song/JAVA-%EC%9D%B4%EB%AF%B8%EC%A7%80-%EC%83%9D%EC%84%B1-%EB%B0%8F-%ED%81%AC%EA%B8%B0-%EC%A1%B0%EC%A0%88
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.WHITE);
        GridBagLayout gbl = new GridBagLayout();
        loginPanel.setLayout(gbl);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        ImageIcon icon = new ImageIcon("src/main/resources/SymbolMark.png");
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(191, 230, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(resized);
        JLabel logo = new JLabel(newIcon);
        logo.setBorder(BorderFactory.createEmptyBorder(50, 0, 20, 0));

        JPanel loginInputPanel = new JPanel(new BorderLayout(20, 0));
        loginInputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        loginInputPanel.setBackground(Color.CYAN);
        loginInputPanel.setSize(600, 400);

        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        inputPanel.setBackground(Color.cyan);
        JTextField loginIdInput = new JTextField("Student ID (12000000)");
        JTextField loginNameInput = new JTextField("Name");
        JButton loginBtn = new JButton("SignIn");
        inputPanel.add(loginIdInput);
        inputPanel.add(loginNameInput);

        loginInputPanel.add(inputPanel, BorderLayout.CENTER);
        loginInputPanel.add(loginBtn, BorderLayout.EAST);


        MySetLayout.setgbc(gbl, gbc, logo, 3, 1, 2, 3);
        //gbc.fill = GridBagConstraints.VERTICAL;
        MySetLayout.setgbc(gbl, gbc, loginInputPanel, 2, 4, 4, 2);

        loginPanel.add(logo);
        loginPanel.add(loginInputPanel);
        frame.add(loginPanel);
    }
    public static void main(String[] args) {
        MainWindow window = new MainWindow();
    }
}
