package app.Repository;

import app.model.Customer;
import app.model.CustomerBuilder;
import app.parser.CsvParser;
import app.parser.XmlParser;
import app.service.FileLoadService;

import java.io.InputStream;
import java.sql.*;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class Database {


    private String JDBC_DRIVER;
    private String DB_URL;
    private String USER;
    private String PASS;
    private String PATH;

    public Database(String JDBC_DRIVER, String DB_URL, String USER, String PASS, String PATH) {
        this.JDBC_DRIVER = JDBC_DRIVER;
        this.DB_URL = DB_URL;
        this.USER = USER;
        this.PASS = PASS;
        this.PATH = PATH;
    }

    public void addCustomer(){
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(this.JDBC_DRIVER);



            FileLoadService fileLoadService = new FileLoadService(this.PATH);
            Map<String, String> map = fileLoadService.getFilesContent();

            CsvParser csvParser = new CsvParser(map.get(".txt"));
            CustomerBuilder customerBuilder = new CustomerBuilder();
            List<Customer> customers = customerBuilder.createAllCustomers(new XmlParser(map.get(".xml")).getAttributesMap());
            customers.addAll(customerBuilder.createAllCustomers(csvParser.setAttributesMap(map.get(".txt"))));

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String queryForid = "SELECT MAX(id) FROM `CUSTOMER`";
            PreparedStatement getId = conn.prepareStatement(queryForid);
            ResultSet rs =  getId.executeQuery();
            int customerID = 0;
            int contactId = 0;

            if(rs.next()) {
                customerID = Integer.parseInt(rs.getString(1));
            }

            for(Customer x: customers) {

                customerID++;
                String query = "insert into CUSTOMER(id ,name, surname, age)" + "values (?,?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, String.valueOf(customerID));

                preparedStatement.setString(2, String.valueOf(x.getName()));
                preparedStatement.setString(3, String.valueOf(x.getSurname()));
                if(x.getAge() != 0) {
                    preparedStatement.setString(4,String.valueOf(x.getAge()));
                } else{
                    preparedStatement.setString(4,null);
                }
                preparedStatement.execute();
                 int i = 0;
                for(Map.Entry entry : x.getContats().getContats().entrySet()) {

                    String queryForContactid = "SELECT MAX(id) FROM `CONTACT`";
                    PreparedStatement getContactId = conn.prepareStatement(queryForContactid);
                    ResultSet rsContact =  getContactId.executeQuery();


                    if (rsContact.next()) {
                        contactId = Integer.parseInt(rsContact.getString(1));
                    }
                    String query2 = "insert into CONTACT(id, id_customer, type, contact)" + "values(?,?,?,?)";
                    PreparedStatement preparedStatement2 = conn.prepareStatement(query2);
                    contactId++;
                    preparedStatement2.setString(1, String.valueOf(contactId));
                    preparedStatement2.setString(2, String.valueOf(customerID));
                    preparedStatement2.setString(3, entry.getKey().toString());
                    preparedStatement2.setString(4,entry.getValue().toString());
                    i++;
                    preparedStatement2.execute();
                }

            }
            conn.close();



        }catch(SQLException se){

            se.printStackTrace();
        }catch(Exception e){

            e.printStackTrace();
        }finally{

            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

    }


    public void createDatabase() {
        Connection conn = null;
        Statement stmt = null;
        try{
            Class.forName(this.JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            ResultSet tables = databaseMetaData.getTables(null, null, "CUSTOMER", null);

            if(!tables.next()) {
                String sqlCreateCustomer = "CREATE TABLE CUSTOMER " +
                        "(id INTEGER not NULL, " +
                        " name VARCHAR(255), " +
                        " surname VARCHAR(255), " +
                        " age INTEGER )  ";
                stmt.executeUpdate(sqlCreateCustomer);
            }
            tables = databaseMetaData.getTables(null, null, "CONTACT", null);

            if(!tables.next()) {
                String sqlCreateContact = "CREATE TABLE CONTACT " +
                        "(id INTEGER not NULL, " +
                        " id_customer integer not NULL, " +
                        " type integer(255), " +
                        " contact varchar(255)) ";

                stmt.executeUpdate(sqlCreateContact);
            }

        }catch(SQLException se){

            se.printStackTrace();
        }catch(Exception e){

            e.printStackTrace();
        }finally{

            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

    }
}
