package data.models;

class UserModel extends UserDataModel {
    int studentID;
    String name;
    public UserModel() {

    }

    public int getStudentID() {
        return studentID;
    }
    public String getName() {
        return name;
    }
}
