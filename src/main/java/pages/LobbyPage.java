package pages;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import data.models.*;
import data.manager.*;

public class LobbyPage extends JPanel {
    private JLabel[] infoLabels;
    private JList<String> btnList;
    private UserModel user;
    private CourseManager courseManager;

    public LobbyPage(UserModel user, CourseManager cm, JList<String> l) {
        this.user = user;
        this.courseManager = cm;
        btnList = l;
        setLayout(new BorderLayout(0, 10));
        setBorder(new EmptyBorder(10, 0, 10, 10));
        setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new BorderLayout(0, 5));
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel infoPanel = new JPanel(new GridLayout(2, 3));
        JLabel halfline = new JLabel("---------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        JPanel graphPanel = new JPanel();
        btnPanel.setPreferredSize(new Dimension(0, 40));
        infoPanel.setPreferredSize(new Dimension(0, 60));
        halfline.setPreferredSize(new Dimension(0, 3));
        graphPanel.setPreferredSize(new Dimension(0, 100));

        topPanel.setBackground(Color.WHITE);
        btnPanel.setBackground(Color.WHITE);
        infoPanel.setBackground(Color.WHITE);
        halfline.setBackground(Color.WHITE);
        graphPanel.setBackground(Color.WHITE);
        btnPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));
        graphPanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));

        JButton refreshBtn = new JButton("Refresh");
        JButton usersBtn = new JButton("Users");
        JButton detailsBtn = new JButton("Details");
        usersBtn.addActionListener(new MoveBtnEvent());
        detailsBtn.addActionListener(new MoveBtnEvent());
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setInfoLabels();
            }
        });
        btnPanel.add(refreshBtn);
        btnPanel.add(usersBtn);
        btnPanel.add(detailsBtn);

        infoLabels = new JLabel[] { new JLabel("Current Semester"), new JLabel("Major Grade"), new JLabel("Earned Credits"),
                new JLabel("-"), new JLabel("- / 4.5"), new JLabel("- / 65") };
        for (JLabel la : infoLabels) {
            infoPanel.add(la);
        }
        setInfoLabels();

        JLabel tmpGraph = new JLabel("Graph");
        graphPanel.add(tmpGraph);

        topPanel.add(btnPanel, BorderLayout.NORTH);
        topPanel.add(infoPanel, BorderLayout.CENTER);
        topPanel.add(halfline, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(graphPanel, BorderLayout.CENTER);
    }

    public class MoveBtnEvent implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();
            btnList.setSelectedValue(b.getText(), false);
            setInfoLabels();
        }
    }

    private void setInfoLabels(){
        if(user == null) return;
        String[] semester = { "1-1", "1-2", "2-1", "2-2", "3-1", "3-2", "4-1", "4-2" };
        ArrayList<CourseUIModel> list = new ArrayList<>();
        ArrayList<CourseUIModel> element = null;
        double[] avgList = new double[semester.length];
        for(int i = 0; i < semester.length; i++){
            element = courseManager.loadCourseList(user.getStudentID(), semester[i], CourseUIModel.DETAIL);
            if(!element.isEmpty()) {
                list.addAll(element);
                infoLabels[3].setText(semester[i].charAt(0) + " - " + semester[i].charAt(2));
                avgList[i] = Double.parseDouble(courseManager.calcGradeAverage(element)[0]);
            }
            else {
                avgList[i] = 0.0;
            }
        }
        if(list.isEmpty()){
            infoLabels[4].setText("- / 4.5");
            infoLabels[5].setText("- / 65");
        }
        else{
            String[] results = courseManager.calcGradeAverage(list);
            infoLabels[4].setText(results[0] + " / 4.5");
            infoLabels[5].setText(results[1] + " / 65");
        }
        setGraph(avgList);
    }

    private void setGraph(double[] avgList){

    }

}
