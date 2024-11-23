package data.manager;

import data.models.*;
import java.util.*;

public class CourseManager {
    CSVManager csvManager;
    private ArrayList<CourseModel> courseList;

    public CourseManager() {
        csvManager = new CSVManager();
        courseList = new ArrayList<>(40);
    }

    public void setCourseList(int enrollment) {
        courseList.clear();
        courseList = csvManager.loadCourseList(enrollment);
        for(int i = 0; i < courseList.size(); i++) {
            System.out.println(courseList.get(i));
        }
    }
}
