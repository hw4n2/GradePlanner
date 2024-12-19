package pages;

import data.manager.*;
import data.models.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class RankingPage extends JPanel {
    private ArrayList<JPanel> userLabels;
    private String[] data = { "ID", "Name", "Year/Semester", "Grade" };
    private UserModel user;

    private JList<String> btnList;
    private UserManager userManager;
    private CourseManager courseManager;
    private JPanel labelPanel;

    public RankingPage(UserManager um, CourseManager cm, UserModel user, JList<String> btnList) {
        userManager = um;
        courseManager = cm;
        this.user = user;
        this.btnList = btnList;

        userLabels = new ArrayList<>();
        setLayout(new BorderLayout(0, 10));
        setBorder(new EmptyBorder(10, 0, 0, 0));
        setBackground(Color.WHITE);
        JLabel pageTitle = new JLabel("Grade Ranking");
        Font tmp = pageTitle.getFont().deriveFont(20f);
        pageTitle.setFont(tmp);
        pageTitle.setPreferredSize(new Dimension(0, 50));
        pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        pageTitle.setBackground(Color.BLUE);
        pageTitle.setForeground(Color.WHITE);
        pageTitle.setOpaque(true);
        labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setBorder(new EmptyBorder(5, 5, 20, 5));
        labelPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(labelPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);

        add(pageTitle, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        this.btnList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e){
                if(btnList.getSelectedValue() != null && btnList.getSelectedValue().equals("Users")){
                    if(userManager != null && user != null) setRanking();

                }
            }
        });
    }

    private void setRanking(){
        ArrayList<String[]> users;
        users = userManager.getAllUsers();
        labelPanel.removeAll();
        if(users.isEmpty()){
            return;
        }

        int usernum = users.size();
        for (int i = 0; i < usernum; i++){
            String[] newArr = new String[5];
            System.arraycopy(users.get(i), 0, newArr, 0, 3);
            System.arraycopy(getSemGrade(users.get(i)[0]), 0, newArr, 3, 2);
            String tmp = newArr[2];
            newArr[2] = newArr[3];
            newArr[3] = tmp;
            tmp = newArr[3];
            newArr[3] = newArr[4];
            newArr[4] = tmp;
            users.set(i, newArr);
        }
        rankingSort(users);

        for(int i = 0; i < usernum; i++){
            String status = users.get(i)[4];
            JPanel curUserPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));
            curUserPanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
            if(users.get(i)[0].equals(user.getStudentID())){
                curUserPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.BLUE));
            }
            curUserPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

            curUserPanel.add(new JLabel("[" + (i + 1) + "]"));
            curUserPanel.add(new JLabel(getImage(users.get(i)[0], 100, 100)));
            JPanel infoPanel = new JPanel(new GridLayout(2, 4, 30, 5));
            for(int j = 0; j < data.length; j++){
                JLabel tmp = new JLabel(data[j]);
                Font tmpFont = new Font(tmp.getFont().getName(), Font.BOLD, 18);
                tmp.setFont(tmpFont);
                infoPanel.add(tmp);
            }
            for(int j = 0; j < data.length; j++){
                if(status.charAt(j) == 'Y') infoPanel.add(new JLabel(users.get(i)[j]));
                else {
                    JLabel hiddenLabel = new JLabel("Private");
                    hiddenLabel.setForeground(Color.RED);
                    infoPanel.add(hiddenLabel);
                }
            }
            curUserPanel.add(infoPanel);
            labelPanel.add(curUserPanel);
            labelPanel.add(Box.createVerticalStrut(5));

            curUserPanel.setMaximumSize(new Dimension(labelPanel.getMaximumSize().width, 103));
        }

    }

    private ImageIcon getImage(String id, int x, int y){
        ImageIcon photo = new ImageIcon("src/main/userdata/" + id + "/userimage.png");
        ImageIcon resized = new ImageIcon(photo.getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH));
        return resized;
    }

    private String[] getSemGrade(String userID){
        String[] returnVal = new String[2];
        String[] semester = { "1-1", "1-2", "2-1", "2-2", "3-1", "3-2", "4-1", "4-2" };
        ArrayList<CourseUIModel> list = new ArrayList<>();
        ArrayList<CourseUIModel> element = null;
        for(int i = 0; i < semester.length; i++){
            element = courseManager.loadCourseList(userID, semester[i], CourseUIModel.DETAIL);
            if(!element.isEmpty()) {
                list.addAll(element);
                returnVal[0] = semester[i].charAt(0) + " - " + semester[i].charAt(2);
            }
        }
        if(list.isEmpty()){
            returnVal[0] = "-";
            returnVal[1] = "0.0";
        }
        else{
            String[] results = courseManager.calcGradeAverage(list);
            returnVal[1] = results[0];
        }
        return returnVal;
    }

    private void rankingSort(ArrayList<String[]> users){
        if(users.isEmpty()) return;
        for(int i = 0; i < users.size() - 1; i++){
            for(int j = 0; j < users.size() - 1 - i; j++){
                double v1 = Double.parseDouble(users.get(j)[3]);
                double v2 = Double.parseDouble(users.get(j + 1)[3]);
                if(v1 < v2){
                    Collections.swap(users, j, j + 1);
                }
            }
        }
    }
}
