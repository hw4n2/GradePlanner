package data.models;

public class UserModel {
    String studentID;
    String name;
    public UserModel(String id, String name) {
        this.studentID = id;
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }
    public String getName() {
        return name;
    }
}
