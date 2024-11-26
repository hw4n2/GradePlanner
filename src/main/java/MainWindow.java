import pages.*;
import data.manager.*;
import data.models.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

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

    private UserModel loginUser;
    private CourseModel courseModel;
    private UserManager userManager;;
    private CourseManager courseManager;

    private MainWindow(){
        frame = getContentPane();
        pagesCard = new CardLayout();
        setTitle("GradePlanner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userManager = new UserManager();
        courseManager = new CourseManager();

        frame.add(initPages());
        frame.add(initLogin());

        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private JPanel initLogin() {
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

        JPanel inputComPanel = new JPanel(new GridLayout(2, 2, 0, 10));
        inputComPanel.setBackground(Color.cyan);
        JLabel idLabel = new JLabel("Student ID");
        JLabel pwLabel = new JLabel("Password");
        JTextField loginIdInput = new JTextField("12211587");
        JPasswordField loginpwInput = new JPasswordField("1234");
        JButton loginBtn = new JButton("SignIn");
        loginBtn.setPreferredSize(new Dimension(100, 100));
        loginBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String id = loginIdInput.getText();
                String pw = new String(loginpwInput.getPassword());
                if((loginUser = userManager.login(id, pw)) == null){
                    System.out.println("Login Failed");
                }
                else {
                    System.out.println("[sign in] " + loginUser.getStudentID());
                    frame.removeAll();
                    frame.add(initPages());
                    updateCard("Lobby");
                    userBtn.setText("User " + loginUser.getName());
                    courseManager.setCourseList(loginUser.getEnrollment());
                }
            }
        });
        loginIdInput.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    loginBtn.doClick();
                }
            }
        });
        loginpwInput.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    loginBtn.doClick();
                }
            }
        });
        inputComPanel.add(idLabel);
        inputComPanel.add(loginIdInput);
        inputComPanel.add(pwLabel);
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
    private JPanel initPages() {
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
        logoutBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.removeAll();
                frame.add(initLogin());
                revalidate();
                repaint();
                System.out.println("[logout] " + loginUser.getStudentID());
                loginUser = null;
                courseManager.clearCourseList();
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

        lobby = new LobbyPage(btnList);
        setting = new SettingPage();
        ranking = new RankingPage();
        details = new DetailsPage(courseManager);
        plan = new PlanPage();
        pagePanel.add(lobby, "Lobby");
        pagePanel.add(setting, "Settings");
        pagePanel.add(ranking, "Users");
        pagePanel.add(details, "Details");
        pagePanel.add(plan, "Planner");

        return mainPanel;
    }

    private void updateCard(String pagename) {
        pagesCard.show(pagePanel, pagename);
        btnList.clearSelection();
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
    }
}
