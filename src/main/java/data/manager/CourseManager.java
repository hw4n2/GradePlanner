package data.manager;

import data.models.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CourseManager {
    CSVManager csvManager;
    private ArrayList<CourseModel> courseList;

    public CourseManager() {
        csvManager = new CSVManager();
        courseList = new ArrayList<>(40);
    }

    public void setCourseList(int enrollment) {
        if(courseList == null) courseList = new ArrayList<>(40);
        courseList.clear();
        if((courseList = csvManager.loadCourseList(enrollment)) == null) {
            JOptionPane.showMessageDialog(null,
                    "Only for undergraduate students enrolled in 2019-2024", "course load error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for(int i = 0; i < courseList.size(); i++) {
            System.out.println(courseList.get(i));
        }
    }
    public ArrayList<CourseModel> getCourseList() {
        return courseList;
    }
    public void clearCourseList(){
        if(courseList != null) courseList.clear();
    }
}
