package data.manager;

import data.models.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CourseManager {
    CSVManager csvManager;
    private ArrayList<CourseModel> courseList;

    public static final int NOT_MODIFIED = 0;
    public static final int MODIFIED = 1;
    public static final int BOTH_EMPTY = 2;

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
    public CourseModel getCourse(String name) {
        for(CourseModel c : courseList) {
            if(c.getCourseName().equals(name)) {
                return c;
            }
        }
        return null;
    }
    public void clearCourseList(){
        if(courseList != null) courseList.clear();
    }

    public ArrayList<String> searchCourse(String inputText) {
        ArrayList<String> result = new ArrayList<>();
        for(CourseModel c : courseList) {
            String target = c.getCourseName().trim().toLowerCase();
            if(target.contains(inputText.trim().toLowerCase())) {
                result.add(c.toString());
            }
        }

        Comparator<String> startsCompare = new Comparator<>() {
            @Override
            public int compare(String c1, String c2) {
                boolean c1start = c1.toLowerCase().startsWith(inputText.toLowerCase());
                boolean c2start = c2.toLowerCase().startsWith(inputText.toLowerCase());

                if(c1start && !c2start) return -1;
                else if(!c1start && c2start) return 1;
                else return 0;
            }
        };

        result.sort(startsCompare);
        return result;
    }

    public void saveCourseList(java.util.List<CourseUIModel> list, String userID, String semester, int modelType){
        ArrayList<String[]> courseData = new ArrayList<>();
        for(CourseUIModel c : list) {
            courseData.add(c.getCourseData(modelType));
        }
        csvManager.writeCourseData(courseData, userID, semester, modelType);
    }

    public ArrayList<CourseUIModel> loadCourseList(String userID, String semester, int modelType) {
        ArrayList<CourseUIModel> list = csvManager.readCourseData(userID, semester, modelType);
        if(list == null) return null;
        else return list;
    }

    public double getGradeAverage(java.util.List<CourseUIModel> list){
        double result = 0;
        for(CourseUIModel c : list){
            result += gradeConverter(c.getCourseGrade());
        }
        result /= list.size();
        return result;
    }

//    public double getGradeAverage(String userID){
//
//    }

    public int isModified(java.util.List<CourseUIModel> newlist, java.util.List<String> oldlist){
        if(newlist.size() != oldlist.size()) {
            return MODIFIED;
        }
        if(newlist.isEmpty()) return BOTH_EMPTY;

        for(CourseUIModel c : newlist) {
            if(!oldlist.contains(c.getCourseName())) return MODIFIED;
        }
        return NOT_MODIFIED;
    }

    private double gradeConverter(String grade){
        switch(grade){
            case "A+": return 4.5;
            case "A0": return 4.0;
            case "B+": return 3.5;
            case "B0": return 3.0;
            case "C+": return 2.5;
            case "C0": return 2.0;
            case "D+": return 1.5;
            case "D0": return 1.0;
            case "F": return 0;
            default: return -1;
        }
    }
}
