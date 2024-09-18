package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection){
        this.connection=connection;
    }


    public void viewDoctor(){
        String query= "SELECT * FROM doctors";
        try{
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet= preparedStatement.executeQuery();
            System.out.println("Doctors");
            System.out.println("+*******+**************+******+******+");
            System.out.println("| id    |  name        | department  |");
            System.out.println("+*******+**************+******+******+");
            while(resultSet.next()){
                int id= resultSet.getInt("doctorsid");
                String name= resultSet.getString("name");
                String department= resultSet.getString("department");
                System.out.printf("|%-7s|%-14s|%-13s\n",id,name,department);
                System.out.println("+*******+**************+******+******+");
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean getDoctorById(int id){
        String query= "SELECT * FROM doctors WHERE doctorsid = ?";
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
