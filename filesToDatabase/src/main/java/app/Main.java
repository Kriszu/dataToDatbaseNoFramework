package app;

import app.Repository.Database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("C:\\Users\\48734\\IdeaProjects\\filesToDatabase\\src\\main\\resources\\application.properties");

            prop.load(input);



            Database database = new Database(
                    prop.getProperty("JDBC_DRIVER"),
                    prop.getProperty("DB_URL"),
                    prop.getProperty("USER"),
                    prop.getProperty("PASS"),
                    prop.getProperty("PATH")
                    );
            database.createDatabase();
            database.addCustomer();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
