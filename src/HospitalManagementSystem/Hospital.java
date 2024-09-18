package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;



public class Hospital {
     private static String url = "jdbc:mysql://localhost:3306/Hospital";
    private static String username = "root";
    private static String password = "Harshita@123";

    public static void main(String arge[]){
       try{
           Class.forName("com.mysql.cj.jdbc.Driver");
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
       Scanner scanner=new Scanner(System.in);
       try{
           Connection connection= DriverManager.getConnection(url,username,password);
           Patient patient=new Patient(connection,scanner);
           Doctor doctor=new Doctor(connection);
           while(true){
               System.out.println("Hospital Management System");
               System.out.println("1.Add Patient");
               System.out.println("2.View Patient");
               System.out.println("3.View Doctor");
               System.out.println("4.Book Appointment");
               System.out.println("5.Exit");
               System.out.println("Enter your Choice...");
               int choice= scanner.nextInt();

               switch(choice){
                   case 1:
                       patient.addPatient();
                       System.out.println();
                       break;
                   case 2:
                       patient.viewPatient();;
                       System.out.println();
                       break;
                   case 3:
                       doctor.viewDoctor();
                       System.out.println();
                       break;
                   case 4:
                       bookAppointment(patient,doctor,connection,scanner);
                       System.out.println();
                       break;
                   case 5:
                       return;
                   default:
                       System.out.println("Enter Valid choice...");
                       break;
               }
           }

       } catch (SQLException e) {
           e.printStackTrace();
       }
    }
    public static  void bookAppointment(Patient patient,Doctor doctor,Connection connection,Scanner scanner){
         System.out.println("Enter Patient ID:");
         int PatientID =scanner.nextInt();
         System.out.println("Enter Doctor ID:");
        int DoctorID =scanner.nextInt();
        System.out.println("Enter Appointment Date (YYYY-MM-DD)");
        String AppointmentDate= scanner.next();
        if(patient.getPatientById(PatientID) && doctor.getDoctorById(DoctorID)){
            if(checkDoctorAvailability(DoctorID,AppointmentDate,connection)){
                String Appointmentquery = "INSERT INTO appointment(patid, docid, appointmentdate)VALUES(?,?,?)";
                try{
                    PreparedStatement preparedStatement= connection.prepareStatement(Appointmentquery);
                    preparedStatement.setInt(1,PatientID);
                    preparedStatement.setInt(2,DoctorID);
                    preparedStatement.setString(3,AppointmentDate);
                    int rowsAffected= preparedStatement.executeUpdate();
                    if(rowsAffected>0){
                        System.out.print("Appointment Booked");
                    }else{
                        System.out.print("Failed to book Appointment");
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Doctor not available");
            }
        }else{
            System.out.println("Either patient or doctor doesn't exist");
        }

    }
    public static boolean checkDoctorAvailability(int DoctorID, String AppointmentDate,Connection connection){
        String query="SELECT COUNT(*) FROM appointment WHERE docid=? AND appointmentdate=?";
        try{
            PreparedStatement preparedStatement= connection.prepareStatement(query);
            preparedStatement.setInt(1,DoctorID);
            preparedStatement.setString(2,AppointmentDate);
            ResultSet resultSet= preparedStatement.executeQuery();
            if(resultSet.next()){
                int count= resultSet.getInt(1);
                if(count==0){
                    return true;
                }else{
                    return false;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
      return false;
    }
}
