package data.models;

public class CourseModel {
    private final String courseId;
    private final String courseName;
    private final String credit;
    private final String year;
    private final String semester;

    public CourseModel(String courseId, String courseName, String credit, String year, String semester) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credit = credit;
        this.year = year;
        this.semester = semester;
    }
    public String getCourseName() {
        return courseName;
    }
    public String getCourseId() {
        return courseId;
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
        return courseId + " " + courseName + " " + credit + " " + getSemester();
    }
}
