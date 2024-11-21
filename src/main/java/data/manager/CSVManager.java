package data.manager;

import data.models.*;
import java.io.File;
import java.io.IOException;
import java.io.*;

class CSVManager {
    private BufferedWriter bw = null;
    private BufferedReader br = null;
    private File usersfile = new File("src/main/userdata/users.csv");

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

    UserModel createUser(String id, String password) {
        try {
            bw = new BufferedWriter(new FileWriter(usersfile, true));
            bw.write(id + "," + password + "," + id);
            bw.newLine();
            bw.close();
            return new UserModel(id, id);
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    UserModel verifyUser(String id, String password) {
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
}
