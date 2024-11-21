package data.manager;

import data.models.*;

public class UserManager {
    CSVManager fileManager;
    public UserManager() {
        fileManager = new CSVManager();
    }
    public UserModel login(String id, String password) {
        return fileManager.verifyUser(id, password);
    }
}
