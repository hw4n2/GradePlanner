package data.models;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CourseUIModel extends JPanel {
    public static final int PLAN = 1;
    public static final int DETAIL = 2;

    private CourseModel course;
    private JLabel idItem = new JLabel();
    private JLabel nameItem = new JLabel();
    private JLabel recommendItem = new JLabel();
    private JLabel gradeItem = new JLabel();
    private JLabel creditItem = new JLabel();
    private JButton deleteBtn;

    public CourseUIModel(int modelType, CourseModel course) {
        setLayout(new GridLayout(1, 5, 10, 2));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(0, 20));
        setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

        idItem.setText(course.getCourseID());
        nameItem.setText(course.getCourseName());
        creditItem.setText(course.getCredit());
        recommendItem.setText(course.getSemester());

        deleteBtn = new JButton();
        deleteBtn.setPreferredSize(new Dimension(15, 15));
        deleteBtn.setBorder(new EmptyBorder(5, 5, 5, 5));
        deleteBtn.setText("clear");

        add(idItem);
        add(nameItem);
        add(creditItem);
        if(modelType == DETAIL) add(gradeItem);
        else if(modelType == PLAN) add(recommendItem);
        add(deleteBtn);
    }

    public CourseUIModel(int modelType, CourseModel course, String grade){
        this(modelType, course);
        gradeItem.setText(grade);
    }

    public String getCourseName(){
        return nameItem.getText();
    }

    public JButton getButton() {
        return deleteBtn;
    }

    public void addRemoveEvent(ArrayList<CourseUIModel> v, JPanel parentPanel, JPanel titlePanel) {
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(v.isEmpty()) return;
                parentPanel.removeAll();
                parentPanel.add(titlePanel);
                Iterator<CourseUIModel> iter = v.iterator();
                while(iter.hasNext()){
                    if(iter.next().getCourseName().equals(nameItem.getText())) {
                        iter.remove();
                        break;
                    }
                }
                for(CourseUIModel c : v){
                    parentPanel.add(c);
                }
                parentPanel.revalidate();
                parentPanel.repaint();
            }
        });
    }

}

