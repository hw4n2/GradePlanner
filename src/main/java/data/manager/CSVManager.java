package data.manager;

import data.models.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.util.ArrayList;

class CSVManager {
    private BufferedWriter bw = null;
    private BufferedReader br = null;
    private File usersfile = new File("src/main/userdata/users.csv");
    private String userdataPath = "src/main/userdata/";
    private File coursefile;

    private String[] detailTitle = { "d1-1", "d1-2", "d2-1", "d2-2", "d3-1", "d3-2", "d4-1", "d4-2" };
    private String[] planTitle = { "p1-1", "p1-2", "p2-1", "p2-2", "p3-1", "p3-2", "p4-1", "p4-2" };

    CSVManager() {
        try {
            if(usersfile.createNewFile()){
                System.out.println("File users.csv has been created");
                bw = new BufferedWriter(new FileWriter(usersfile));
                bw.write("id,password,name"); //csv header write
                bw.newLine();
                bw.close();
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void createUserData(String id) {
        File userDir = new File(userdataPath + id);
        if(userDir.mkdir()) System.out.println("[UserFile Created] " + id);
        File userfile;
        try{
            for(String s : detailTitle){
                userfile = new File(userDir + "/" + s + ".csv");
                userfile.createNewFile();
            }
            for(String s : planTitle){
                userfile = new File(userDir + "/" + s + ".csv");
                userfile.createNewFile();
            }
        } catch(IOException e){
            JOptionPane.showMessageDialog(null, "Error occured\nTry again", "error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        System.out.println("Userdata files has been created");
    }

    UserModel createUser(String id, String password) {
        try {
            bw = new BufferedWriter(new FileWriter(usersfile, true));
            bw.write(id + "," + password + "," + id);
            bw.newLine();
            bw.close();
            createUserData(id);

            return new UserModel(id, id);
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    UserModel verifyUser(String id, String password) {
        if(id.charAt(0) == '2' || id.length() != 8) {
            JOptionPane.showMessageDialog(null,
                    "Only for undergraduate students enrolled in 2019-2024", "register error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        try {
            br = new BufferedReader(new FileReader(usersfile));
            String line = null;

            while((line = br.readLine()) != null){
                String[] field = line.split(",");
                if(field.length == 0) break;
                if(field[0].equals(id)){
                    if(field[1].equals(password)) {
                        return new UserModel(field[0], field[2]);
                    }
                    else return null;
                }
            }
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
        return createUser(id, password);
    }

    ArrayList<CourseModel> loadCourseList(int enrollment) {
        if(enrollment < 19 || enrollment > 24) return null;
        coursefile = new File("src/main/resources/curriculum/" + Integer.toString(enrollment) + "curriculum.csv");
        ArrayList<CourseModel> courseList = new ArrayList<>(40);
        try {
            br = new BufferedReader(new FileReader(coursefile));
            String line = null;
            br.readLine();
            while((line = br.readLine()) != null){
                String[] field = line.split(",");
                if(field.length == 0) break;
                if(field.length != 5) return null;
                courseList.add(new CourseModel(field));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        coursefile = null;
        return courseList;
    }

    void writeCourseData(java.util.List<String[]> data, String userID, String semester, int modelType){
        String lastItem = null;
        try{
            if(modelType == CourseUIModel.DETAIL) {
                lastItem = "Grade";
                bw = new BufferedWriter(new FileWriter(userdataPath + userID + "/" + "d" + semester + ".csv"));
            }
            else if(modelType == CourseUIModel.PLAN) {
                lastItem = "Semester";
                bw = new BufferedWriter(new FileWriter(userdataPath + userID + "/" + "p" + semester + ".csv"));
            }
        } catch(IOException e){
            JOptionPane.showMessageDialog(null, "Cannot find userdata", "error", JOptionPane.ERROR_MESSAGE);
            System.out.println(bw.getClass().getName());
            return;
        }
        addHeader("CourseID,CourseName,Credit," + lastItem);
        for(String[] item : data) {
            try{
                bw.write(item[0] + "," + item[1] + "," + item[2] + "," + item[3]);
                bw.newLine();
            } catch(IOException e){
                JOptionPane.showMessageDialog(null, "Writing CSVdata failed", "error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        try{
            bw.close();
        } catch(IOException e){
            return;
        }
        JOptionPane.showMessageDialog(null, "Save Successed", "success", JOptionPane.PLAIN_MESSAGE);
    }

    private void addHeader(String header){
        try{
            bw.write(header);
            bw.newLine();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Writing CSVHeader failed", "error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
