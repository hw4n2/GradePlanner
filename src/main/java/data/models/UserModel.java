package data.models;

public class UserModel {
    private final String studentID;
    private final String name;
    private final String enrollment;
    public UserModel(String id, String name) {
        String tmpenroll = null;
        this.studentID = id;
        this.name = name;
        try{
            tmpenroll = studentID.charAt(2) + "" + studentID.charAt(3);
        } catch (Exception e) {
            tmpenroll = "";
        }
        this.enrollment = tmpenroll;
    }

    public String getStudentID() {
        return studentID;
    }
    public String getName() {
        return name;
    }
    public int getEnrollment() { return Integer.parseInt(enrollment); }
}
