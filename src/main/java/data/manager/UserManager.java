package data.manager;

import data.models.*;

public class UserManager {
    CSVManager fileManager;
    public UserManager() {
        fileManager = new CSVManager();
    }
    public UserModel login(String id, String password) {
        id = id.trim();
        password = password.trim();
        if(id.isEmpty() || password.isEmpty()) { return null; }
        return fileManager.verifyUser(id, password);
    }
}
