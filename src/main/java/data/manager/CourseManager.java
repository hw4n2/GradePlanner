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
        }
    }
    public ArrayList<CourseModel> getCourseList() {
        return courseList;
    }
    public void clearCourseList(){
        if(courseList != null) courseList.clear();
    }

    public ArrayList<String> searchCourse(String inputText) {
        ArrayList<String> result = new ArrayList<>();
        for(CourseModel c : courseList) {
            if(c.getCourseName().trim().toLowerCase().contains(inputText.trim().toLowerCase())) {
                result.add(c.toString());
            }
        }

        Comparator<String> startsCompare = new Comparator<>() {
            @Override
            public int compare(String c1, String c2) {
                boolean c1start = c1.startsWith(inputText);
                boolean c2start = c2.startsWith(inputText);

                if(c1start && !c2start) return -1;
                else if(!c1start && c2start) return 1;
                else return 0;
            }
        };
        result.sort(startsCompare);
        for(String i : result) {
            System.out.println(i);
        }
        System.out.println("-------------------------------------------------");
        return result;
    }
}
