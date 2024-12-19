package data.models;

public class UserModel {
    private final String studentID;
    private final String enrollment;
    private String name;
    private String status;

    public UserModel(String id, String name, String status) {
        String tmpenroll = null;
        this.studentID = id;
        this.name = name;
        this.status = status;
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
    public String getStatus() { return status; }

    public void setName(String name){
        this.name = name;
    }
    public void setStatus(String status){
        this.status = status;
    }
}
