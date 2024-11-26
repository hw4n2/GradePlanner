package data.models;

public class CourseModel {
    private final String courseID;
    private final String courseName;
    private final String credit;
    private final String year;
    private final String semester;

    public CourseModel(String[] courseLine) {
        this.courseID = courseLine[0];
        this.courseName = courseLine[1];
        this.credit = courseLine[2];
        this.year = courseLine[3];
        this.semester = courseLine[4];
    }
    public String getCourseName() {
        return courseName;
    }
    public String getCourseID() {
        return courseID;
    }
    public String getCredit() {
        return credit;
    }
    public String getSemester() {
        if(semester.equals("0")) return year;
        else return year + "-" + semester;
    }

    @Override
    public String toString() {
        return courseName;
    }
}
