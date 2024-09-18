package HospitalManagementSystem;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection,Scanner scanner){
       this.connection=connection;
       this.scanner=scanner;
   }

    public void addPatient(){
        System.out.println("Enter Patient Name");
        String name= scanner.next();
        System.out.println("Enter Age");
        int age= scanner.nextInt();
        System.out.println("Enter Gender");
        String gender= scanner.next();

        try{
            String query="INSERT INTO patient(name,age,gender)VALUES(?,?,?)";
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);
            int affectedRows= preparedStatement.executeUpdate();
            if(affectedRows>0){
                System.out.println("Patient Added Successfully");
            }else{
                System.out.println("Failed To Add");
            }

        } catch (SQLException e) {
           e.printStackTrace();
        }

    }
    public void viewPatient(){
        String query= "SELECT * FROM patient";
       try{
           PreparedStatement preparedStatement=connection.prepareStatement(query);
           ResultSet resultSet= preparedStatement.executeQuery();
           System.out.println("Patients");
           System.out.println("+*******+**************+******+**********+");
           System.out.println("| id    |  name        | age  |  gender  |");
           System.out.println("+*******+**************+******+**********+");
           while(resultSet.next()){
               int id= resultSet.getInt("patientid");
               String name= resultSet.getString("name");
               int age= resultSet.getInt("age");
               String gender= resultSet.getString("gender");
               System.out.printf("|%-7s|%-14s|%-6s|%-10s\n",id,name,age,gender);
               System.out.println("+*******+**************+******+**********+");
           }
       }
       catch(SQLException e) {
           e.printStackTrace();
       }
    }
    public boolean getPatientById(int id){
        String query= "SELECT * FROM patient WHERE patientid = ?";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet= preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

