package data.manager;

import data.models.*;

import javax.swing.*;
import java.io.File;
import java.util.*;

public class UserManager {
    CSVManager fileManager;
    public UserManager() {
        fileManager = new CSVManager();
    }
    public UserModel login(String id, String password) {
        id = id.trim();
        password = password.trim();
        if(id.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "check your inputs", "error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return fileManager.verifyUser(id, password);
    }

    public void saveSettings(UserModel user, String[] args, File imageFile){
        if(fileManager.updateUserData(user.getStudentID(), args, imageFile) == -1){
            JOptionPane.showMessageDialog(null, "save failed", "error", JOptionPane.ERROR_MESSAGE);
        }
        user.setName(args[1]);
        user.setStatus(args[0]);
        JOptionPane.showMessageDialog(null, "save successed", "success", JOptionPane.PLAIN_MESSAGE);
    }

    public ArrayList<String[]> getAllUsers(){
        ArrayList<String[]> users = new ArrayList<>();
        users = fileManager.loadAllUsers();
        if(users.isEmpty()){ return null; }
        return users;
    }
}
