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
    private GraphPanel graphPanel;

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
        JLabel halfline = new JLabel();
        graphPanel = new GraphPanel();
        btnPanel.setPreferredSize(new Dimension(0, 40));
        infoPanel.setPreferredSize(new Dimension(0, 60));
        halfline.setPreferredSize(new Dimension(0, 3));

        topPanel.setBackground(Color.WHITE);
        btnPanel.setBackground(Color.WHITE);
        infoPanel.setBackground(Color.WHITE);
        halfline.setBackground(Color.WHITE);
        btnPanel.setBorder(new MatteBorder(0, 0, 1, 0, Color.BLACK));


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
        for(int i = 0; i < 3; i++){
            infoLabels[i].setFont(infoLabels[i].getFont().deriveFont(16f));
        }
        setInfoLabels();

        JLabel graphLabel = new JLabel("Grade Chart");
        Font font = new Font(graphLabel.getFont().getName(), Font.PLAIN, 18);
        graphLabel.setFont(font);
        graphLabel.setBackground(Color.BLUE);
        graphLabel.setForeground(Color.WHITE);
        graphLabel.setOpaque(true);
        graphPanel.add(graphLabel);

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
                avgList[i] = -1.0;
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
        graphPanel.setGradeList(avgList);
        graphPanel.repaint();
    }

    class GraphPanel extends JPanel {
        private double[] gradelist;
        GraphPanel() {
            setPreferredSize(new Dimension(0, 100));
            setBackground(Color.WHITE);
            setBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK));
            gradelist = new double[] { -1.0 };
        }
        void setGradeList(double[] gradelist) {
            this.gradelist = gradelist;
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.drawLine(30, 30, 30, getHeight() - 30);
            g.drawLine(30, getHeight() - 30, getWidth() - 30, getHeight() - 30);
            paintGraph(g);
        }

        private void paintGraph(Graphics g) {
            if(gradelist[0] == -1.0) return;

            String[] semester = { "1-1", "1-2", "2-1", "2-2", "3-1", "3-2", "4-1", "4-2" };
            double[] list = new double[]{ 4.5, 4.0, 3.5, 3.0, 2.5, 2.0, 1.5, 1.0, 0.5, 0.0 };
            ArrayList<Double> indexY = new ArrayList<>();

            double lowerBound = roundMinGrade(gradelist);
            for(double i : list){
                if(i >= lowerBound) indexY.add(i);
            }

            int vgap = (getHeight() - 60) / (indexY.size());
            int hgap = (getWidth() - 60) / semester.length;
            int lowerPos = getHeight() - 50;
            Graphics2D g2d = (Graphics2D) g; //chatGPT reference to make thick oval
            Stroke oldstroke = g2d.getStroke();
            int lowerY = lowerPos, upperY = 0;
            for(int i = 0; i < indexY.size(); i++){ //draw y axis grade indexY
                g.setColor(Color.GRAY);
                g.drawLine(50, lowerPos - (i * vgap), getWidth() - 50, lowerPos - (i * vgap));
                g.drawString(indexY.get(indexY.size() - 1 - i).toString(), 5, lowerPos - (i * vgap) + 5);
                if(i == indexY.size() - 1) upperY = lowerPos - (i * vgap);
            }

            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(3.0f));
            for(int i = 0; i < gradelist.length; i++){
                if(gradelist[i] == -1.0) continue;
                g2d.fillOval(73 + (i * hgap), lowerPos - (convertYAxis(gradelist[i], lowerY, upperY, indexY)) - 5, 10, 10);
                if(i + 1 < gradelist.length && gradelist[i + 1] != -1.0) {
                    g2d.drawLine(73 + (i * hgap) + 5, lowerPos - (convertYAxis(gradelist[i], lowerY, upperY, indexY)),
                            73 + ((i + 1) * hgap) + 5, lowerPos - (convertYAxis(gradelist[i + 1], lowerY, upperY, indexY)));
                }
            }

            g2d.setStroke(oldstroke);
            g.setColor(Color.GRAY);
            for(int i = 0; i < semester.length; i++){ //draw x axis semester indexY
                g.drawString(semester[i], 70 + (i * hgap), getHeight() - 10);
            }
        }

        private double roundMinGrade(double[] gradelist) {
            double min = Double.MAX_VALUE;
            for (double i : gradelist) {
                if (i != -1.0 && i < min) {
                    min = i;
                }
            }
            return Math.floor(min * 2) / 2.0;
        }
        
        private int convertYAxis(double y, int lowerY, int upperY, ArrayList<Double> indexY) {
            int ylength = lowerY - upperY;
            double indexGap = indexY.getFirst() - indexY.getLast();
            double value = ylength / indexGap;
            return (int)((y - indexY.getLast()) * value);
        }
    }

}
