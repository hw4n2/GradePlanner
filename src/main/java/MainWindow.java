import pages.LobbyPage;
import pages.SettingPage;
import pages.RankingPage;
import pages.DetailsPage;
import pages.PlanPage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.File;
import java.io.IOException;

public class MainWindow extends JFrame {
    private Container frame;
    private CardLayout pagesCard;
    private JPanel pagePanel;
    private JButton userBtn;
    private JList<String> btnList;

    private JPanel lobby;
    private JPanel setting;
    private JPanel ranking;
    private JPanel details;
    private JPanel plan;

    private MainWindow(){
        frame = getContentPane();
        pagesCard = new CardLayout();
        setTitle("GradePlanner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.add(initLogin());
        frame.add(initPages());

        lobby = new LobbyPage(pagesCard, pagePanel, btnList);
        setting = new SettingPage();
        ranking = new RankingPage();
        details = new DetailsPage();
        plan = new PlanPage();
        pagePanel.add(lobby, "Lobby");
        pagePanel.add(setting, "Settings");
        pagePanel.add(ranking, "Users");
        pagePanel.add(details, "Details");
        pagePanel.add(plan, "Planner");
        updateCard("planPage");

        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private Container initLogin() {
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(new GridLayout(3, 1, 0, 20));

        ImageIcon icon = new ImageIcon("src/main/resources/SymbolMark.jpg");
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(125, 150, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(resized);
        JLabel logo = new JLabel(newIcon);
        logo.setBorder(BorderFactory.createEmptyBorder(70, 0, 50, 0));

        JLabel progName = new JLabel("GradePlanner");
        progName.setHorizontalAlignment(SwingConstants.CENTER);
        progName.setBackground(Color.WHITE);
        progName.setFont(progName.getFont().deriveFont(40f));
        progName.setOpaque(true);

        JPanel loginInputPanel = new JPanel(new BorderLayout(20, 0));
        loginInputPanel.setBorder(new EmptyBorder(10, 10, 15, 10));
        loginInputPanel.setBackground(Color.CYAN);
        loginInputPanel.setPreferredSize(new Dimension(300, 100));

        JPanel inputComPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        inputComPanel.setBackground(Color.cyan);
        JTextField loginIdInput = new JTextField("Student ID (12000000)");
        JTextField loginpwInput = new JTextField("Password");
        JButton loginBtn = new JButton("SignIn");
        loginBtn.setPreferredSize(new Dimension(100, 100));
        inputComPanel.add(loginIdInput);
        inputComPanel.add(loginpwInput);

        loginInputPanel.add(inputComPanel, BorderLayout.CENTER);
        loginInputPanel.add(loginBtn, BorderLayout.EAST);

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        wrapper.setPreferredSize(new Dimension(300, 100));
        wrapper.setBackground(Color.WHITE);
        wrapper.add(loginInputPanel);

        loginPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        loginPanel.add(logo);
        loginPanel.add(progName);
        loginPanel.add(wrapper);
        return loginPanel;
    }
    private Container initPages() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setPreferredSize(new Dimension(0, 40));
        userPanel.setBackground(new Color(40, 255, 255));
        userBtn = new JButton("User 12000000");
        JButton logoutBtn = new JButton("logout");
        logoutBtn.setBorderPainted(false);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setFocusPainted(false);
        userBtn.setBorderPainted(false);
        userBtn.setContentAreaFilled(false);
        userBtn.setFocusPainted(false);
        userBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCard("Settings");
                btnList.clearSelection();
            }
        });
        userPanel.add(userBtn);
        userPanel.add(logoutBtn);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 20));

        final String[] pageBtns = { "Lobby", "Users", "Details", "Planner", "Settings" };
        btnList = new JList<>(pageBtns);
        btnList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        btnList.setBorder(new EmptyBorder(50, 5, 5, 5));
        btnList.setFixedCellHeight(50);
        btnList.setSelectionBackground(null);
        btnList.setSelectionForeground(null);
        btnList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String pagename = (String)btnList.getSelectedValue();
                updateCard(pagename);
            }
        });

        pagePanel = new JPanel(pagesCard);
        pagePanel.setBackground(Color.BLACK);
        pagePanel.setOpaque(true);

        centerPanel.add(btnList, BorderLayout.WEST);
        centerPanel.add(pagePanel, BorderLayout.CENTER);

        mainPanel.add(userPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    private void updateCard(String pagename) {
        pagesCard.show(pagePanel, pagename);
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
    }
}
