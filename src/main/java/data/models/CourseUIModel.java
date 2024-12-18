package data.models;

import data.manager.*;
import pages.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CourseUIModel extends JPanel {
    public static final int PLAN = 1;
    public static final int DETAIL = 2;

    private JLabel idItem = new JLabel("");
    private JLabel nameItem = new JLabel("");
    private JLabel recommendItem = new JLabel("");
    private JLabel gradeItem = new JLabel("");
    private JLabel creditItem = new JLabel("");
    private JButton deleteBtn;

    public CourseUIModel(int modelType, CourseModel course) {
        setLayout(new GridLayout(1, 5, 10, 2));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(0, 20));
        setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

        idItem.setText(course.getCourseID());
        nameItem.setText(course.getCourseName());
        creditItem.setText(course.getCredit());// gradeItem is set by second initializer
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

    public CourseUIModel(int modelType, String[] infoList){
        setLayout(new GridLayout(1, 5, 10, 2));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(0, 20));
        setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));

        idItem.setText(infoList[0]);
        nameItem.setText(infoList[1]);
        creditItem.setText(infoList[2]);
        if(modelType == DETAIL) gradeItem.setText(infoList[3]);
        else if(modelType == PLAN) recommendItem.setText(infoList[3]);

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

    public String getCourseName(){
        return nameItem.getText();
    }

    public String[] getCourseData(int modelType){
        String[] data = null;
        if(modelType == DETAIL) data = new String[] { idItem.getText(), nameItem.getText(), creditItem.getText(), gradeItem.getText() };
        else if(modelType == PLAN) data = new String[] { idItem.getText(), nameItem.getText(), creditItem.getText(), recommendItem.getText() };
        return data;
    }

    public void addRemoveEvent(java.util.List<CourseUIModel> v, JPanel parentPanel, JPanel titlePanel, CourseManager cm, JLabel infoLabel, int flag) {
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

                String[] infos = cm.calcGradeAverage(v);
                if(flag == DETAIL) {
                    if(!v.isEmpty()) infoLabel.setText("Major Grade " + infos[0] + " Earned Credits " + infos[1]);
                    else infoLabel.setText("Major Grade -  Earned Credits -");
                }
                else if(flag == PLAN) {
                    if(!v.isEmpty()) infoLabel.setText("Credits " + infos[1] + " / 21");
                    else infoLabel.setText("Credits - / 21");
                }
            }
        });
    }

    @Override
    public String toString() {
        if(gradeItem.getText().isEmpty()){
            return nameItem.getText();
        }
        else {
            return nameItem.getText() + "@" + gradeItem.getText();
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CourseUIModel)) return false;
        CourseUIModel other = (CourseUIModel) obj;
        String thisName = this.nameItem.getText();
        String thisRecommend = this.recommendItem.getText();
        String thisGrade = this.gradeItem.getText();
        String otherName = other.nameItem.getText();
        String otherGrade = other.gradeItem.getText();
        if (thisGrade.isEmpty()) {
            return thisName.equals(otherName);
        }
        return thisName.equals(otherName) && thisGrade.equals(otherGrade);
    }

    @Override
    public int hashCode() {
        String thisName = this.nameItem.getText();
        String thisRecommend = this.recommendItem.getText();
        String thisGrade = this.gradeItem.getText();
        if (thisGrade.isEmpty() && !thisRecommend.isEmpty()) {
            return Objects.hash(thisName);
        } else if (thisRecommend.isEmpty() && !thisGrade.isEmpty()) {
            return Objects.hash(thisName, thisGrade);
        }
        return super.hashCode();
    }
}

